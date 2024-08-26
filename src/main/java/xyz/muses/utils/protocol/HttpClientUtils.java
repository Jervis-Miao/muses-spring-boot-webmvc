/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.utils.protocol;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.alibaba.dubbo.rpc.RpcException;

import xyz.muses.constants.ResultErrorConstant;
import xyz.muses.exceptions.MusesException;
import xyz.muses.framework.common.utils.JsonMapper;
import xyz.muses.framework.common.utils.SpringContextUtils;
import xyz.muses.utils.protocol.constant.HttpProtocolConst;
import xyz.muses.utils.protocol.http.HttpHeader;
import xyz.muses.utils.protocol.http.HttpProtocol;
import xyz.muses.utils.protocol.http.HttpProtocolResponse;

/**
 * @author jervis
 * @date 2024/8/23
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /** 公用连接套接字工厂注册表 **/
    private static final Registry<ConnectionSocketFactory> REGISTRY;

    /** 每个工具拥有一个独立HTTP连接管理器 **/
    private PoolingHttpClientConnectionManager connManager;

    /** 每个工具维护一个独立连接客户端 **/
    private HttpClient client;

    static {
        // 创建SSLContext对象用于绕过SSL验证
        Registry<ConnectionSocketFactory> registry;
        try {
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https",
                    new SSLConnectionSocketFactory(
                        SSLContextBuilder.create().loadTrustMaterial(null, new TrustAllStrategy()).build(),
                        NoopHostnameVerifier.INSTANCE))
                .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            logger.error("createIgnoreVerifySSLContext exception", e);
            registry = null;
        }
        REGISTRY = registry;
    }

    /** 创建连接工具，绕过SSL证书验证 **/
    public HttpClientUtils() {
        // HTTP连接管理器，绕过证书验证
        this.connManager = this.createNoSslHttpClientConnManager();

        // 客户端
        this.client = HttpClients.custom().setConnectionManager(connManager)
            .setDefaultRequestConfig(this.createDefaultReqConf()).build();
    }

    /**
     * 创建连接工具，带SSL证书验证
     *
     * @param keyPath
     * @param trustPath
     * @param keyPwd
     * @param trustPwd
     */
    public HttpClientUtils(String keyPath, String trustPath, String keyPwd, String trustPwd) {
        // HTTP连接管理器，带证书验证
        connManager = createSslHttpClientConnManager(keyPath, trustPath, keyPwd, trustPwd);

        // 客户端
        client = HttpClients.custom().setConnectionManager(connManager)
            .setDefaultRequestConfig(this.createDefaultReqConf()).build();
    }

    /**
     * 发送请求，获取指定类型的返回信息
     *
     * @param protocol
     * @param req
     * @param responseClass
     * @return
     * @param <T>
     */
    public <T> T doExecute(HttpProtocol protocol, String req, Class<T> responseClass) {
        try {
            HttpProtocolResponse response;
            Object sucResult;
            if (null != (response = this.doExecute(protocol, req))
                && ObjectUtils.isNotEmpty(sucResult = response.getSucResult())) {
                String sucRet = String.valueOf(sucResult);
                return SpringContextUtils.getBean(JsonMapper.class).fromJson(sucRet, responseClass);
            }
        } catch (MusesException e) {
            logger.error("doExecute exception", e);
        }
        return null;
    }

    /**
     * 发送请求，获取返回信息
     *
     * @param httpProtocol
     * @param reqData
     * @return
     * @throws RpcException
     */
    public HttpProtocolResponse doExecute(HttpProtocol httpProtocol, Object reqData) throws MusesException {
        // 配置信息
        String url = httpProtocol.getUrl();
        String userName = httpProtocol.getUserName();
        String password = httpProtocol.getPassword();
        String proxyAddress = httpProtocol.getProxyAddress();
        Integer proxyPort = httpProtocol.getProxyPort();
        String proxyUserName = httpProtocol.getProxyUserName();
        String proxyPassword = httpProtocol.getProxyPassword();
        String contentType = httpProtocol.getContentType();
        String charset = httpProtocol.getCharset();
        Boolean downloadFlag = httpProtocol.getDownloadFlag();
        List<HttpHeader> reqHeaders = httpProtocol.getReqHeaders();
        try {
            // 请求地址
            URI uri = new URI(url);
            // 代理
            HttpHost proxy = null;
            if (StringUtils.isNotBlank(proxyAddress) && ObjectUtils.isNotEmpty(proxyPort)) {
                proxy = new HttpHost(proxyAddress, proxyPort);
            }
            // 证书
            CredentialsProvider provider =
                this.createCredentialsProvider(uri.getHost(), uri.getPort(), userName, password,
                    proxy, proxyUserName, proxyPassword);
            // 请求体
            HttpRequestBase requestBase = this.createHttpRequestBase(uri, httpProtocol.getRequestMethod(), reqData,
                httpProtocol.getConnTimeOut(), httpProtocol.getSocketTimeOut(), proxy, contentType, charset,
                reqHeaders);

            // 返回信息
            return this.analysisResponseEntity(requestBase, provider, charset, downloadFlag);
        } catch (Exception e) {
            logger.error("请求第三方系统发生错误, url: {}", url, e);
            MusesException me = new MusesException(ResultErrorConstant.Error.GENERAL, "不支持的编码格式错误");
            me.addSuppressed(e);
            throw me;
        }
    }

    /**
     * 关闭连接，此方法会关闭一个池中所有连接，谨慎使用
     */
    public void shutdown() {
        if (ObjectUtils.isNotEmpty(connManager)) {
            connManager.shutdown();
        }
    }

    /**
     * 创建绕过证书验证的http连接管理器
     *
     * @return
     */
    private PoolingHttpClientConnectionManager createNoSslHttpClientConnManager() {
        return this.createHttpClientConnManager(REGISTRY);
    }

    /**
     * 创建带证书的http连接管理器
     *
     * @param keyPath
     * @param trustPath
     * @param keyPwd
     * @param trustPwd
     * @return
     */
    private PoolingHttpClientConnectionManager createSslHttpClientConnManager(String keyPath, String trustPath,
        String keyPwd, String trustPwd) {
        SSLContext sslContext = this.createTrustStrategySSLContext(keyPath, trustPath, keyPwd, trustPwd);

        // 连接套接字工厂注册表
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE)).build();

        return this.createHttpClientConnManager(registry);
    }

    /**
     * 获取带SSL证书协议
     *
     * @param keyPath
     * @param trustPath
     * @param keyPwd
     * @param trustPwd
     * @return
     */
    private SSLContext createTrustStrategySSLContext(String keyPath, String trustPath, String keyPwd,
        String trustPwd) {
        // 加载证书
        FileInputStream keystoreIs = null, trustStoreIs = null;
        try {
            SSLContextBuilder scBuilder =
                SSLContexts.custom().setProtocol(SSLConnectionSocketFactory.SSL).setSecureRandom(null);

            if (StringUtils.isNotBlank(keyPath) && StringUtils.isNotBlank(keyPwd)) {
                KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
                keystoreIs = new FileInputStream(ResourceUtils.getFile(keyPath));
                final char[] pwd = keyPwd.toCharArray();
                keystore.load(keystoreIs, pwd);
                scBuilder.loadKeyMaterial(keystore, pwd);
            }

            if (StringUtils.isNotBlank(trustPwd) && StringUtils.isNotBlank(trustPwd)) {
                KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStoreIs = new FileInputStream(ResourceUtils.getFile(trustPath));
                truststore.load(trustStoreIs, trustPwd.toCharArray());
                scBuilder.loadTrustMaterial(truststore, new TrustAllStrategy());
            } else {
                scBuilder.loadTrustMaterial(null, new TrustAllStrategy());
            }

            return scBuilder.build();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException
            | UnrecoverableKeyException | KeyManagementException e) {
            logger.error("createTrustStrategySSLContext Exception: {}", e.getMessage(), e);
        } finally {
            try {
                if (null != keystoreIs) {
                    keystoreIs.close();
                }
                if (null != trustStoreIs) {
                    trustStoreIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 创建连接管理器
     *
     * @param registry
     * @return
     */
    private PoolingHttpClientConnectionManager createHttpClientConnManager(
        Registry<ConnectionSocketFactory> registry) {
        // 设置连接管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setDefaultMaxPerRoute(HttpProtocolConst.DEFAULT_PER_ROUTE);
        connectionManager.setMaxTotal(HttpProtocolConst.MAX_TOTAL);
        // 处理指定服务器的连接数设置
        if (StringUtils.isNotBlank(HttpProtocolConst.ROUTE_HOST)) {
            String[] routeHosts = HttpProtocolConst.ROUTE_HOST.split(",");
            String[] maxForRoutes = HttpProtocolConst.MAX_ROUTE.split(",");
            for (int i = 0; i < routeHosts.length; i++) {
                String routeHost = routeHosts[i];
                Integer maxForRoute = Integer.valueOf(maxForRoutes[i]);
                if (ObjectUtils.isEmpty(maxForRoute)) {
                    break;
                }
                HttpHost host = new HttpHost(routeHosts[i], maxForRoute);
                connectionManager.setMaxPerRoute(new HttpRoute(host), maxForRoute);
            }
        }
        return connectionManager;
    }

    /**
     * 创建默认请求配置信息
     *
     * @return
     */
    private RequestConfig createDefaultReqConf() {
        return RequestConfig.custom().setConnectionRequestTimeout(HttpProtocolConst.CONNECTION_REQUEST_TIMEOUT)
            .setConnectTimeout(HttpProtocolConst.CONNECTION_TIMEOUT).setSocketTimeout(HttpProtocolConst.SO_TIMEOUT)
            .build();
    }

    /**
     * 创建认证证书
     *
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param proxyHost
     * @param proxyUserName
     * @param proxyPassword
     * @return
     */
    private CredentialsProvider createCredentialsProvider(String host, int port, String userName,
        String password, HttpHost proxyHost, String proxyUserName, String proxyPassword) {
        // 证书
        CredentialsProvider provider = new BasicCredentialsProvider();
        // 登录认证
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            provider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(userName, password));
        }
        // 代理认证
        if (ObjectUtils.isNotEmpty(proxyHost) && StringUtils.isNotBlank(proxyUserName)
            && StringUtils.isNotBlank(proxyPassword)) {
            provider.setCredentials(new AuthScope(proxyHost),
                new UsernamePasswordCredentials(proxyUserName, proxyPassword));
        }
        return provider;
    }

    /**
     * 创建请求对象
     *
     * @param uri
     * @param requestMethod
     * @param reqData
     * @param connTimeOut
     * @param socketTimeOut
     * @param proxy
     * @param contentType
     * @param charset
     * @param reqHeaders
     * @return
     * @throws UnsupportedEncodingException
     */
    private HttpRequestBase createHttpRequestBase(URI uri, String requestMethod, Object reqData,
        Integer connTimeOut, Integer socketTimeOut, HttpHost proxy, String contentType, String charset,
        List<HttpHeader> reqHeaders) throws UnsupportedEncodingException {
        HttpRequestBase requestBase;
        switch (requestMethod) {
            case HttpPost.METHOD_NAME:
                requestBase = new HttpPost(uri);
                HttpEntity postRequestEntity = this.createRequestEntity(reqData, charset, contentType);
                ((HttpPost)requestBase).setEntity(postRequestEntity);
                break;
            case HttpPut.METHOD_NAME:
                requestBase = new HttpPut(uri);
                HttpEntity putRequestEntity = createRequestEntity(reqData, charset, contentType);
                ((HttpPut)requestBase).setEntity(putRequestEntity);
                break;
            case HttpDelete.METHOD_NAME:
                requestBase = new HttpDelete(uri);
                break;
            default:
                requestBase = new HttpGet(uri);
        }
        RequestConfig requestConfig = this.customReqConf(connTimeOut, socketTimeOut, proxy);
        if (ObjectUtils.isNotEmpty(requestConfig)) {
            requestBase.setConfig(requestConfig);
        }
        this.initHeaders(requestBase, contentType, charset, reqHeaders);
        return requestBase;
    }

    /**
     * 创建请求体
     *
     * @param reqData 请求参数
     * @param charset 编码格式
     * @return
     * @throws UnsupportedEncodingException
     */
    private HttpEntity createRequestEntity(Object reqData, String charset, String contentType)
        throws UnsupportedEncodingException {
        HttpEntity entity = null;
        if (ObjectUtils.isNotEmpty(reqData)) {
            // json数据格式
            if (reqData instanceof String) {
                entity = new StringEntity((String)reqData, charset);
            }
            // 表单数据格式
            else if (reqData instanceof Map) {
                List<NameValuePair> paramList = new ArrayList<>();
                Map<String, String> reqDataMap = (Map<String, String>)reqData;
                Iterator<String> iterator = reqDataMap.keySet().iterator();
                if (contentType.contains(HttpProtocolConst.CONTENT_TYPE.MUL_FROM.getContentType())) {
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        // 这里使用APPLICATION_JSON是因为防止中文乱码
                        builder.addTextBody(key, reqDataMap.get(key), ContentType.APPLICATION_JSON);
                    }
                    entity = builder.build();
                } else {
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        paramList.add(new BasicNameValuePair(key, reqDataMap.get(key)));
                    }
                    entity = new UrlEncodedFormEntity(paramList, charset);
                }
            }
        }
        return entity;
    }

    /**
     * 自定义请求配置信息
     *
     * @param connTimeOut
     * @param socketTimeOut
     * @param proxy
     * @return
     */
    private RequestConfig customReqConf(Integer connTimeOut, Integer socketTimeOut, HttpHost proxy) {
        RequestConfig.Builder builder = RequestConfig.custom();

        // 配置标记
        Boolean confFlag = Boolean.FALSE;

        if (ObjectUtils.isNotEmpty(connTimeOut)) {
            builder.setConnectTimeout(connTimeOut);
            confFlag = Boolean.TRUE;
        }

        if (ObjectUtils.isNotEmpty(socketTimeOut)) {
            builder.setSocketTimeout(socketTimeOut);
            confFlag = Boolean.TRUE;
        }

        if (ObjectUtils.isNotEmpty(proxy)) {
            builder.setProxy(proxy);
            confFlag = Boolean.TRUE;
        }

        if (confFlag) {
            return builder.setConnectionRequestTimeout(HttpProtocolConst.CONNECTION_REQUEST_TIMEOUT).build();
        }

        return null;
    }

    /**
     * 设置默认的请求头
     *
     * @param requestBase 请求对象
     * @param contentType 媒体格式
     * @param charset 编码格式
     * @param reqHeaders 请求首部字段
     */
    private static void initHeaders(HttpRequestBase requestBase, String contentType, String charset,
        List<HttpHeader> reqHeaders) {
        // 指定请求和响应遵循的缓存机制
        requestBase.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        // 用来包含实现特定的指令
        requestBase.setHeader(HttpHeaders.PRAGMA, "no-cache");
        // 可以接受的字符编码集
        requestBase.setHeader(HttpHeaders.ACCEPT_CHARSET, charset);
        // 请求的与实体对应的MIME信息(multipart/form-data类型不予添加在请求头中，HttpRequestBase会根据entity类型自动添加)
        if (StringUtils.isNotBlank(contentType)
            && !contentType.contains(HttpProtocolConst.CONTENT_TYPE.MUL_FROM.getContentType())
            && !contentType.equals(HttpProtocolConst.CONTENT_TYPE.NONE.getContentType())) {
            requestBase.setHeader(HttpHeaders.CONTENT_TYPE, contentType + "; charset=" + charset);
        }
        // 其他自定义头设置
        if (CollectionUtils.isNotEmpty(reqHeaders)) {
            reqHeaders.forEach(header -> requestBase.setHeader(header.getName(), header.getValue()));
        }
    }

    /**
     * 解析GET/POST指定编码格式的响应内容
     *
     * @param requestMethod
     * @param provider
     * @param charset
     * @return
     * @throws IOException
     */
    public HttpProtocolResponse analysisResponseEntity(HttpRequestBase requestMethod, CredentialsProvider provider,
        String charset, Boolean downloadFlag) throws IOException {
        HttpProtocolResponse ifaceResponse = null;
        try {
            // 局部上下文
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(provider);
            // 请求
            long startTime = System.currentTimeMillis();
            // 响应对象
            HttpResponse response = client.execute(requestMethod, context);

            // 状态码
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("请求地址：{}" + "，响应码：{}" + "，请求耗时：{}", requestMethod.getURI(),
                statusCode, System.currentTimeMillis() - startTime);
            // 请求成功, 构造返回信息
            ifaceResponse = new HttpProtocolResponse();
            Header[] headers = response.getAllHeaders();
            if (null != headers && headers.length > 0) {
                for (Header h : headers) {
                    ifaceResponse.addHeader(h.getName(), h.getValue());
                }
            }
            ifaceResponse.setStatusCode(statusCode);
            HttpEntity responseEntity;
            if (null == (responseEntity = response.getEntity())) {
                logger.info("请求地址：{}" + "，响应实体为空", requestMethod.getURI());
            } else if (downloadFlag) {
                switch (statusCode) {
                    case HttpStatus.SC_OK:
                        // 下载文件, 响应流
                        ifaceResponse.setSucResult(ArrayUtils.toObject(EntityUtils.toByteArray(responseEntity)));
                        break;
                    default:
                        ifaceResponse.setBadResult(EntityUtils.toString(responseEntity, charset));
                }
            } else if (HttpStatus.SC_OK <= statusCode && statusCode < HttpStatus.SC_BAD_REQUEST) {
                ifaceResponse.setSucResult(EntityUtils.toString(responseEntity, charset));
            } else {
                ifaceResponse.setBadResult(EntityUtils.toString(responseEntity, charset));
            }

            if (!downloadFlag) {
                logger.info("请求地址：{}" + "，响应码：{}" + "，响应结果 \r\n: {}", requestMethod.getURI(),
                    statusCode, ifaceResponse);
            }

            // 中止此次请求
            requestMethod.abort();
        } finally {
            // 重置请求状态
            requestMethod.releaseConnection();
        }
        return ifaceResponse;
    }
}

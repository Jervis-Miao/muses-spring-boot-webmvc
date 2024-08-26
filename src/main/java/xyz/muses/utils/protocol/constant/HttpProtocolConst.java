/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol.constant;

/**
 * 请求协议常量
 *
 * @author miaoqiang
 * @date 2020/4/26.
 */
public interface HttpProtocolConst {
    /**
     * 获取可用连接超时时间(单位毫秒)
     */
    Integer CONNECTION_REQUEST_TIMEOUT = 90000;

    /**
     * 连接超时时间(单位毫秒)
     */
    Integer CONNECTION_TIMEOUT = 90000;

    /**
     * 读数据超时时间(单位毫秒)
     */
    Integer SO_TIMEOUT = 90000;

    /**
     * 总连接数
     */
    Integer MAX_TOTAL = 1000;

    /**
     * 每个服务器的默认连接数,必须指定，否则默认是2
     */
    Integer DEFAULT_PER_ROUTE = 100;

    /**
     * 指定服务器的地址,每个指定服务器地址用逗号隔开
     */
    String ROUTE_HOST = null;

    /**
     * 指定服务器的连接数，每个指定服务器连接数用逗号隔开
     */
    String MAX_ROUTE = "";

    /**
     * 默认编码
     */
    String DEFAULT_ENCODE = "utf-8";

    /**
     * 媒体格式
     */
    public enum CONTENT_TYPE {
        // 空
        NONE("NONE"),
        // 纯文本
        TEXT_PLAIN("text/plain"),
        // 文本方式的html
        TEXT_HTML("text/html"),
        // 文本方式的xml
        TEXT_XML("text/xml"),
        // gif图片格式
        IMAGE_GIF("image/gif"),
        // jpg图片格式
        IMAGE_JPEG("image/jpeg"),
        // png图片格式
        IMAGE_PNG("image/png"),
        // 图片、文件等附件上传
        MUL_FROM("multipart/form-data"),
        // post表单提交（普通表单，非上传）
        APPLICATION_FROM("application/x-www-form-urlencoded"),
        // 数据以json形式编码
        APPLICATION_JSON("application/json"),
        // 数据以xml形式编码
        APPLICATION_XML("application/xml"),
        // 二进制流数据（如常见的文件下载）
        APPLICATION_STREAM("application/octet-stream");

        private final String contentType;

        CONTENT_TYPE(String contentType) {
            this.contentType = contentType;
        }

        public String getContentType() {
            return contentType;
        }
    }

}

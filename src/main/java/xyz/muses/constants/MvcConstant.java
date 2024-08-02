/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.constants;

/**
 * @author jervis
 * @date 2020/12/2.
 */
public interface MvcConstant {

    /**
     * api接口前缀
     */
    String API_URL_PREFIX = "/api";

    /**
     * 全局域名前缀
     */
    String GLOBAL_URL_PREFIX = "/global";

    /**
     * 全局token
     */
    String GLOBAL_TOKEN_URL_PREFIX = GLOBAL_URL_PREFIX + "/token";

    /**
     * 投保接口前缀
     */
    String APPLY_URL_PREFIX = "/apply";

    /**
     * 产品接口前缀
     */
    String PRODUCT_URL_PREFIX = "/products";

    /**
     * 企微接口前缀
     */
    String WE_COM_PATH_PREFIX = "/wecom";

    /**
     * 企微销售API前缀
     */
    String WE_COM_SALES_PATH_PREFIX = WE_COM_PATH_PREFIX + "/sales";

    /**
     * 素材库内部接口前缀
     */
    String MATERIAL_SALES_PATH_PREFIX = "/material/sales";

    /**
     * 预估单
     */
    String SALE_ESTIMATE_PATH_PREFIX = "/sale/estimate/";

    /**
     * 图片展示URL前缀
     */
    String IMAGE_URL_PREFIX = "https://www.xyz.cn/v2/file/image/";

    /**
     * 文件下载URL前缀
     */
    String DOWNLOAD_URL_PREFIX = "https://www.xyz.cn/v2/file/download/";

    /**
     * 文件系统文件获取前缀
     */
    String FILE_SYS_URL_PREFIX = "https://file.xyz.cn/file/";
}

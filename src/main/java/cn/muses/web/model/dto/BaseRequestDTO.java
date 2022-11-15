package cn.muses.web.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 统一参数
 *
 * @author shijianpeng
 */
public class BaseRequestDTO extends BaseDTO {
    private static final long serialVersionUID = -8647283601071609550L;

    /**
     * 用户真实ip
     */
    private String ip;

    /**
     * 渠道分配的uid
     */
    private Long uid;

    /**
     * 渠道分配的uidEn
     */
    private String uidEn;

    /**
     * 渠道分配的sid
     */
    private String sid;

    /**
     * 产品线平台
     */
    private String prodLinePlatform;

    /**
     * 用户在微信环境下的唯一标识
     */
    private String weixinGovern;

    /**
     * 后台系统的登录标识
     */
    private String adminToken;

    /**
     * 客户端非登录token
     */
    private String noLoginToken;

    public String getNoLoginToken() {
        return noLoginToken;
    }

    public void setNoLoginToken(String noLoginToken) {
        this.noLoginToken = noLoginToken;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUidEn() {
        return uidEn;
    }

    public void setUidEn(String uidEn) {
        this.uidEn = uidEn;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getProdLinePlatform() {
        return prodLinePlatform;
    }

    public void setProdLinePlatform(String prodLinePlatform) {
        this.prodLinePlatform = prodLinePlatform;
    }

    public String getWeixinGovern() {
        return weixinGovern;
    }

    public void setWeixinGovern(String weixinGovern) {
        this.weixinGovern = weixinGovern;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("ip", ip)
            .append("uid", uid)
            .append("uidEn", uidEn)
            .append("sid", sid)
            .append("prodLinePlatform", prodLinePlatform)
            .append("weixinGovern", weixinGovern)
            .append("adminToken", adminToken)
            .append("noLoginToken", noLoginToken)
            .toString();
    }
}

package xyz.muses.web.model.dto;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author jervis
 * @date 2024/8/26
 */
public class SmsSendReqDTO implements Serializable {
    private static final long serialVersionUID = -5662756136584021333L;

    /** 签名名称 **/
    private String signName;

    /** 模板编号 **/
    private String templateCode;

    /** 模板参数 **/
    private Map<String, Object> templateParams;

    /** 手机号，格式为：区号-号码，例如：86-186****3745 **/
    private String phone;

    public String getSignName() {
        return signName;
    }

    public SmsSendReqDTO setSignName(String signName) {
        this.signName = signName;
        return this;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public SmsSendReqDTO setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public Map<String, Object> getTemplateParams() {
        return templateParams;
    }

    public SmsSendReqDTO setTemplateParams(Map<String, Object> templateParams) {
        this.templateParams = templateParams;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public SmsSendReqDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("signName", signName)
            .append("templateCode", templateCode)
            .append("templateParams", templateParams)
            .append("phone", phone)
            .toString();
    }
}

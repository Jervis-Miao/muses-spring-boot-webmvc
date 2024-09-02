/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.web.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author jervis
 * @date 2024/8/22
 */
@ApiModel(description = "用户登录实体")
public class LoginInVO implements Serializable {
    private static final long serialVersionUID = -3543514404164674937L;

    /** 手机号 **/
    @NotBlank(message = "请填写手机号")
    @ApiModelProperty(value = "用户手机号", example = "13111111111")
    private String mobile;

    /** 验证码 **/
    @NotBlank(message = "请填写验证码")
    @ApiModelProperty(value = "用户手机验证码", example = "1234")
    private String code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("mobile", mobile)
            .append("code", code)
            .toString();
    }
}

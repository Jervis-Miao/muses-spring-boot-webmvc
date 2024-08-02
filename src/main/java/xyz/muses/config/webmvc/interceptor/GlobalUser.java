/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.config.webmvc.interceptor;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 全局会员定义
 *
 * @author jervis
 * @date 2022/6/22.
 */
public class GlobalUser implements Serializable {
    private static final long serialVersionUID = 6465148613111128382L;

    /** 全局用户token扩展前缀 **/
    public static final String GLOBAL_TOKEN_EXT_PREFIX = "g";

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 续期gToken
     */
    private String renewalGToken;

    /**
     * 有效期（时间戳）
     */
    private Long deadLine;

    public GlobalUser() {}

    public GlobalUser(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getRenewalGToken() {
        return renewalGToken;
    }

    public void setRenewalGToken(String renewalGToken) {
        this.renewalGToken = renewalGToken;
    }

    public Long getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Long deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("memberId", memberId)
            .append("renewalGToken", renewalGToken)
            .append("deadLine", deadLine)
            .toString();
    }
}

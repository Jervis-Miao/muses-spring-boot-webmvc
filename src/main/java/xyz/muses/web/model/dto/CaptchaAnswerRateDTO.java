package xyz.muses.web.model.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 回答水平。回答得怎么样
 * 
 * @author jervis
 * @date 2024/8/26
 */
public class CaptchaAnswerRateDTO implements Serializable {
    private static final long serialVersionUID = -495021927161535480L;

    /** 回答标志 **/
    private String id;

    /** 是否匹配真实答案 **/
    private Boolean matched;

    /** 是否达到最大失败次数 **/
    private Boolean reachFailedCountThreshold;

    public CaptchaAnswerRateDTO() {}

    public CaptchaAnswerRateDTO(String id, Boolean matched, Boolean reachFailedCountThreshold) {
        this.id = id;
        this.matched = matched;
        this.reachFailedCountThreshold = reachFailedCountThreshold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public Boolean getReachFailedCountThreshold() {
        return reachFailedCountThreshold;
    }

    public void setReachFailedCountThreshold(Boolean reachFailedCountThreshold) {
        this.reachFailedCountThreshold = reachFailedCountThreshold;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("matched", matched)
            .append("reachFailedCountThreshold", reachFailedCountThreshold)
            .toString();
    }
}

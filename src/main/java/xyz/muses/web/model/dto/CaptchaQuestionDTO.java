package xyz.muses.web.model.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 验证码问题
 *
 * @author jervis
 * @date 2024/8/26
 */
public class CaptchaQuestionDTO implements Serializable {
    private static final long serialVersionUID = -4755055569587331755L;

    /** 问题标识，验证时需要传入此标识 **/
    private String id;

    /** 问题内容，可供展示在界面上，具体内容不限 **/
    private Serializable question;

    public CaptchaQuestionDTO() {}

    public CaptchaQuestionDTO(String id, Serializable question) {
        this.id = id;
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Serializable getQuestion() {
        return question;
    }

    public void setQuestion(Serializable question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("question", question)
            .toString();
    }
}

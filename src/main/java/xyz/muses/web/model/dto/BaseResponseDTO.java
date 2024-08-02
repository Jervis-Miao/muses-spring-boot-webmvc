/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.web.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 考虑对外API包，不添加内部项目依赖<br/>
 *
 * @author jervis
 * @date 2020/12/4.
 */
public class BaseResponseDTO<T> {

    private Integer ret;

    private final List<String> msg = new ArrayList<>();
    /**
     * 业务错误类型
     */
    private Integer errorCode;

    private T data;

    /**
     * 默认构造函数
     */
    public BaseResponseDTO() {
        this(DEFAULT_RESPONSE_RESULT.SUCCESS, null);
    }

    public BaseResponseDTO(DEFAULT_RESPONSE_RESULT retEnum) {
        this(retEnum, null);
    }

    public BaseResponseDTO(T data) {
        this(DEFAULT_RESPONSE_RESULT.SUCCESS, data);
    }

    public BaseResponseDTO(DEFAULT_RESPONSE_RESULT retEnum, T data) {
        this(retEnum.value, data, null);
    }

    public BaseResponseDTO(Integer ret, T data) {
        this(ret, data, null);
    }

    public BaseResponseDTO(Integer ret, T data, List<String> msg) {
        super();
        this.ret = ret;
        this.data = data;
        this.addErrors(msg);
    }

    /**
     * @return the ret
     */
    public Integer getRet() {
        return ret;
    }

    /**
     * @param ret the ret to set
     */
    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public void addError(String error) {
        this.msg.add(error);
    }

    public void addErrors(String[] errors) {
        this.addErrors(Arrays.asList(errors));
    }

    public void addErrors(List<String> errorList) {
        if (null != errorList) {
            this.msg.addAll(errorList);
        }
    }

    public void addErrors(Map<String, String> errors) {
        this.addErrors(Arrays.copyOf(errors.values().toArray(), errors.size(), String[].class));
    }

    public boolean existError() {
        return !this.msg.isEmpty();
    }

    public void removeError(String error) {
        this.msg.remove(error);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the msg
     */
    public List<String> getMsg() {
        return msg;
    }

    public BaseResponseDTO<T> add(String value) {
        msg.add(value);
        return this;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    public static enum DEFAULT_RESPONSE_RESULT {
        /** 成功 */
        SUCCESS(0, "[]"),
        /** 认证失败 */
        AUTHEN_FAIL(-1, "认证失败"),
        /** 授权不足 */
        AUTHOR_FAIL(-2, "权限不足"),
        /** 参数校验失败,错误信息交由业务逻辑处理 */
        PARAM_CHECK_FAIL(-3, ""),
        /** 请求资源不存在 */
        RESOURCE_NOT_EXIST(-4, "请求资源不存在"),
        /** 系统错误 */
        SYSTEM_ERROR(-5, "系统错误"),
        /** 请求参数数据格式不正确 */
        DATA_MALFORMAT(-6, "请求参数数据格式不正确"),
        /** 请求方法不正确 */
        REQMETHOD_ERROR(-7, "请求方法不正确"),
        /** 请求参数类型不匹配 */
        TYPE_MISMATCH(-8, "请求参数类型不匹配"),
        /** 请求参数缺失 */
        MISS_REQUEST_PARAM(-9, "请求参数缺失"),
        /** 不支持的媒体类型 */
        MEDIA_TYPE_ERROR(-10, "不支持的媒体类型"),
        /** 业务逻辑错误 */
        BIZ_ERROR(-11, ""),
        /** 二要素校验失败 **/
        TWOELEMENT_ERROR(-12, "二要素校验错误"),
        /** 跳转异常页 **/
        ERROR_PAGE(-13, "跳转异常页"),
        /** App版本过低 **/
        APP_NEED_UPDATE(-14, "请先升级App到最新版本"),

        ACCOUNT_PAUSE(10, "账号已冻结"),

        /** 需要银行签约 **/
        BANK_SIGN_ERROR(-15, "需要银行签约");

        private final Integer value;

        private final String desc;

        private DEFAULT_RESPONSE_RESULT(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int value() {
            return value;
        }

        public String desc() {
            return desc;
        }
    }

    public static <T> BaseResponseDTOBuilder builder() {
        return new BaseResponseDTOBuilder<T>();
    }

    public static class BaseResponseDTOBuilder<T> {
        private Integer ret;
        private T data;
        private List<String> msg;

        private <T> BaseResponseDTOBuilder() {};

        public BaseResponseDTOBuilder<T> ret(Integer ret) {
            this.ret = ret;
            return this;
        }

        public BaseResponseDTOBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public BaseResponseDTOBuilder<T> addError(String error) {
            if (null == this.msg) {
                this.msg = new ArrayList<>(8);
            }
            this.msg.add(error);
            return this;
        }

        public BaseResponseDTO<T> build() {
            return new BaseResponseDTO<T>(ret, data, msg);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseResponseDTO.class.getSimpleName() + "[", "]")
            .add("ret=" + ret)
            .add("msg=" + msg)
            .add("data=" + data)
            .toString();
    }
}

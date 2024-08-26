/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.utils.protocol;

import java.io.Serializable;
import java.util.Base64;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 外部接口返回信息
 *
 * @author miaoqiang
 * @date 2020/5/7.
 */
public class ProtocolResponse<T> implements Serializable {
    private static final long serialVersionUID = 922994540102340296L;

    private T sucResult;

    private String badResult;

    public T getSucResult() {
        return sucResult;
    }

    public void setSucResult(T sucResult) {
        this.sucResult = sucResult;
    }

    public String getBadResult() {
        return badResult;
    }

    public void setBadResult(String badResult) {
        this.badResult = badResult;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("响应报文：\n");
        if (StringUtils.isNotBlank(this.badResult)) {
            str.append(this.badResult);
        } else if (this.sucResult instanceof Byte[]) {
            str.append(Base64.getEncoder().encodeToString(ArrayUtils.toPrimitive((Byte[])this.sucResult)));
        } else {
            str.append(null == this.sucResult ? "null" : this.sucResult.toString());
        }
        return str.toString();
    }
}

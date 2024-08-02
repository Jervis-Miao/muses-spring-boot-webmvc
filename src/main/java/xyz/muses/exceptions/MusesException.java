/*
 * Copyright 2019 All rights reserved.
 */

package xyz.muses.exceptions;

import org.apache.commons.lang3.builder.ToStringBuilder;

import xyz.muses.constants.ResultErrorConstant.Error;

/**
 * @author jervis
 * @date 2020/12/1.
 */
public class MusesException extends Exception {
    private static final long serialVersionUID = -357641166647154098L;

    private Error error;

    public MusesException(String message) {
        this(Error.GENERAL, message);
    }

    public MusesException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public MusesException(String message, Throwable cause) {
        this(Error.GENERAL, message, cause);
    }

    public MusesException(Error error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("error", error)
            .append("message", getMessage()).toString();
    }
}

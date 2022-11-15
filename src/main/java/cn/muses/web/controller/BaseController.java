/**
 * Copyright 2022 All rights reserved.
 */

package cn.muses.web.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;

import cn.muses.web.model.dto.BaseResponseDTO;

/**
 * @author jervis
 * @date 2022/6/17.
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseController() {}

    protected <T> BaseResponseDTO<T> returnWithSuccess(T t) {
        return new BaseResponseDTO(t);
    }

    protected BaseResponseDTO returnWithFail(Integer errorCode, String errorMsg) {
        BaseResponseDTO dto = new BaseResponseDTO();
        dto.setRet(errorCode);
        dto.getMsg().add(errorMsg);
        return dto;
    }

    protected BaseResponseDTO returnWithFail(Integer errorCode, List<String> errorMsg) {
        BaseResponseDTO dto = new BaseResponseDTO();
        dto.setRet(errorCode);
        dto.getMsg().addAll(errorMsg);
        return dto;
    }

    protected BaseResponseDTO returnWithCheckFail(List<? extends ObjectError> errorList) {
        BaseResponseDTO dto = new BaseResponseDTO();
        if (CollectionUtils.isNotEmpty(errorList)) {
            dto.setRet(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL.value());
            Iterator var3 = errorList.iterator();

            while (var3.hasNext()) {
                ObjectError error = (ObjectError)var3.next();
                dto.getMsg().add(error.getDefaultMessage());
            }
        }

        return dto;
    }

    protected BaseResponseDTO returnWithCheckFail(ObjectError error) {
        BaseResponseDTO dto = new BaseResponseDTO();
        dto.setRet(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL.value());
        dto.getMsg().add(error.getDefaultMessage());
        return dto;
    }

    protected BaseResponseDTO returnWithCheckFail(String error) {
        BaseResponseDTO dto = new BaseResponseDTO();
        dto.setRet(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.PARAM_CHECK_FAIL.value());
        dto.getMsg().add(error);
        return dto;
    }
}

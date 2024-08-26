/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.web.service.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.AcsResponse;

import xyz.muses.external.aliyun.idaas.DoraemonManager;
import xyz.muses.framework.common.utils.JsonMapper;
import xyz.muses.web.model.dto.SmsSendReqDTO;
import xyz.muses.web.service.BaseService;

/**
 * @author jervis
 * @date 2024/8/26
 */
@Service
public class SmsService extends BaseService {

    @Autowired
    private DoraemonManager doraemonManager;

    @Autowired
    private JsonMapper jsonMapper;

    public AcsResponse sendBody(SmsSendReqDTO req) throws Exception {
        Map<String, Object> params = jsonMapper.getMapper().convertValue(req,
            jsonMapper.contructMapType(HashMap.class, String.class, Object.class));
        return doraemonManager.invoke("Send", params);
    }
}

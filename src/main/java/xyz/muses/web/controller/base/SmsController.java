package xyz.muses.web.controller.base;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.AcsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.muses.external.aliyun.idaas.DoraemonManager;
import xyz.muses.framework.common.utils.JsonMapper;
import xyz.muses.web.controller.BaseController;
import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.web.model.dto.SmsSendReqDTO;

/**
 * @author jervis
 * @date 2024/8/26
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Autowired
    private DoraemonManager doraemonManager;

    @Autowired
    private JsonMapper jsonMapper;

    @PostMapping("/send")
    public BaseResponseDTO<AcsResponse> sendBody(@RequestBody SmsSendReqDTO req) throws Exception {
        Map<String, Object> params = jsonMapper.getMapper().convertValue(req,
            jsonMapper.contructMapType(HashMap.class, String.class, Object.class));
        return this.returnWithSuccess(doraemonManager.invoke("Send", params));
    }
}

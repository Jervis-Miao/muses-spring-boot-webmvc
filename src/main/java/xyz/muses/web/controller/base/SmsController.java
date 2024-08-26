package xyz.muses.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.AcsResponse;

import xyz.muses.web.controller.BaseController;
import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.web.model.dto.SmsSendReqDTO;
import xyz.muses.web.service.base.SmsService;

/**
 * @author jervis
 * @date 2024/8/26
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public BaseResponseDTO<AcsResponse> sendBody(@RequestBody SmsSendReqDTO req) throws Exception {
        return this.returnWithSuccess(smsService.sendBody(req));
    }
}

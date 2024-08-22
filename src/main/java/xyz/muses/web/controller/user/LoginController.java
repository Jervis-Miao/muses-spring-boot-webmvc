/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.web.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.muses.constants.MvcConstant;
import xyz.muses.exceptions.MusesException;
import xyz.muses.web.controller.BaseController;
import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.web.model.vo.LoginInVO;
import xyz.muses.web.service.user.LoginService;

/**
 * @author jervis
 * @date 2024/8/22
 */
@RestController
@RequestMapping(MvcConstant.LOGIN_URL_PREFIX)
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/in")
    public BaseResponseDTO<String> loginIn(@RequestBody @Valid LoginInVO loginIn) throws MusesException {
        return this.returnWithSuccess(loginService.loginIn(loginIn.getMobile(), loginIn.getCode()));
    }

    @GetMapping("/refresh")
    public BaseResponseDTO<String> refreshJwt() throws MusesException {
        return this.returnWithSuccess(loginService.refreshJwt());
    }

    @GetMapping("/out")
    public BaseResponseDTO<String> loginOut() {
        try {
            loginService.loginOut();
            return this.returnWithSuccess("success");
        } catch (Exception e) {
            logger.error("loginOut error", e);
            return this.returnWithCheckFail(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.SYSTEM_ERROR, "failure");
        }
    }
}

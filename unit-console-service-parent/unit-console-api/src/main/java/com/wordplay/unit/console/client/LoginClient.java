package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.AccountPwdLoginRequest;
import com.wordplay.unit.console.model.request.QRCodeLoginRequest;
import com.wordplay.unit.console.model.request.VerificationCodeLoginRequest;
import com.wordplay.unit.console.model.response.LoginSuccessResponse;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户登录
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/login")
public interface LoginClient {

	@PostMapping("/loginbyaccount")
	ResponseResult<LoginSuccessResponse> loginByAccount(@RequestBody AccountPwdLoginRequest request);

	@PostMapping("/loginbyverificationcode")
	ResponseResult loginByVerificationCode(@RequestBody VerificationCodeLoginRequest request);

	@PostMapping("/loginbyqrcodelogin")
	ResponseResult loginByQRCodeLogin(@RequestBody QRCodeLoginRequest request);


}

package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.VerifyCodeSenderRequest;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 验证码
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/verifycode")
public interface VerifyCodeClient {

	@PostMapping("/sendverifycode")
	ResponseResult sendVerifyCode(@RequestBody VerifyCodeSenderRequest request);

}

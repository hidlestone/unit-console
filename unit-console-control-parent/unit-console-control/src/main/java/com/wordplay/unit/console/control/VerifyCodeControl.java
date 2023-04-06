package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.VerifyCodeClient;
import com.wordplay.unit.console.model.request.VerifyCodeSenderRequest;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "验证码")
@RestController
@RequestMapping("/${unit.console.control.version}/verifycode")
public class VerifyCodeControl {

	@Autowired(required = false)
	private VerifyCodeClient verifyCodeClient;

	@PostMapping("/sendverifycode")
	@ApiOperation("发送随机的验证码")
	public ResponseResult sendVerifyCode(@RequestBody VerifyCodeSenderRequest request) {
		return verifyCodeClient.sendVerifyCode(request);
	}

}

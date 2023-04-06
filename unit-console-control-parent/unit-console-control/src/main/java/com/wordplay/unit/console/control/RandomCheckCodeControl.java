package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.RandomCheckCodeClient;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "随机校验码")
@RestController
@RequestMapping("/${unit.console.control.version}/verifycode")
public class RandomCheckCodeControl {

	@Autowired(required = false)
	private RandomCheckCodeClient randomCheckCodeClient;

	@PostMapping("/getrandomcheckcode")
	@ApiOperation("四位随机校验码")
	public ResponseResult getRandomCheckCode() {
		return randomCheckCodeClient.getRandomCheckCode();
	}

}

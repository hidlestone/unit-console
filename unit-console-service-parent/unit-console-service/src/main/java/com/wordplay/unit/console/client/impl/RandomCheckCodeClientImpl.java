package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.RandomCheckCodeClient;
import com.wordplay.unit.console.service.RandomCheckCodeService;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "随机校验码")
@RestController
@RequestMapping("/${unit.console.service.version}/randomcheckcode")
public class RandomCheckCodeClientImpl implements RandomCheckCodeClient {

	@Autowired
	private RandomCheckCodeService randomCheckCodeService;

	@Override
	@GetMapping("/getrandomcheckcode")
	@ApiOperation("四位随机校验码")
	public ResponseResult getRandomCheckCode() {
		return randomCheckCodeService.getRandomCheckCode();
	}

}

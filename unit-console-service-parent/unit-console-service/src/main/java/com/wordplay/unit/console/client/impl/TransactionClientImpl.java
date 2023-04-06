package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.TransactionClient;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "交易查询服务")
@RestController
@RequestMapping("/${unit.console.service.version}/transaction")
public class TransactionClientImpl implements TransactionClient {

	@Override
	@PostMapping("/list")
	@ApiOperation("分页查询交易")
	public ResponseResult list() {
		return null;
	}

}

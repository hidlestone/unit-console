package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.TransactionClient;
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
@Api(tags = "系统参数")
@RestController
@RequestMapping("/${unit.console.control.version}/transaction")
public class TransactionControl {

	@Autowired(required = false)
	private TransactionClient transactionClient;

	@PostMapping("/list")
	@ApiOperation("分页查询交易")
	public ResponseResult list() {
		return ResponseResult.success();
	}

}

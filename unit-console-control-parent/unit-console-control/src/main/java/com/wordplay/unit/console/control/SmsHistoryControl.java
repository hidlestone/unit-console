package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.SmsHistoryClient;
import com.wordplay.unit.console.model.request.SmsHistoryRequest;
import com.wordplay.unit.console.model.response.SmsHistoryResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "短信历史")
@RestController
@RequestMapping("/${unit.console.control.version}/smshistory")
public class SmsHistoryControl {

	@Autowired(required = false)
	private SmsHistoryClient smsHistoryClient;

	@PostMapping("/delete")
	@ApiOperation("删除短信历史")
	public ResponseResult delete(@RequestParam Long id) {
		return smsHistoryClient.delete(id);
	}

	@GetMapping("/get")
	@ApiOperation("查询短信历史")
	public ResponseResult<SmsHistoryResponse> get(@RequestParam Long id) {
		return smsHistoryClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询短信历史")
	public ResponseResult<Leaf<SmsHistoryResponse>> page(@RequestBody SmsHistoryRequest request) {
		return smsHistoryClient.page(request);
	}

}

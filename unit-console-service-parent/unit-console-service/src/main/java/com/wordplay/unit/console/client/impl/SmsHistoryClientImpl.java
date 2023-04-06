package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.SmsHistoryClient;
import com.wordplay.unit.console.model.request.SmsHistoryRequest;
import com.wordplay.unit.console.model.response.SmsHistoryResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sms.entity.SmsHistory;
import com.wordplay.unit.starter.sms.service.SmsHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/${unit.console.service.version}/smshistory")
public class SmsHistoryClientImpl implements SmsHistoryClient {

	@Autowired
	private SmsHistoryService smsHistoryService;

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除短信历史")
	public ResponseResult delete(@RequestParam Long id) {
		SmsHistory smsHistory = smsHistoryService.getById(id);
		if (null == smsHistory) {
			return ResponseResult.fail("数据不存在");
		}
		smsHistoryService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询短信历史")
	public ResponseResult<SmsHistoryResponse> get(@RequestParam Long id) {
		SmsHistory smsHistory = smsHistoryService.getById(id);
		if (null == smsHistory) {
			return ResponseResult.fail("数据不存在");
		}
		SmsHistoryResponse response = new SmsHistoryResponse();
		BeanUtils.copyProperties(smsHistory, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询短信历史")
	public ResponseResult<Leaf<SmsHistoryResponse>> page(@RequestBody SmsHistoryRequest req) {
		SmsHistory smsHistory = new SmsHistory();
		Leaf leaf = LeafPageUtil.leafToType(smsHistoryService.page(smsHistory), SmsHistoryResponse.class);
		return ResponseResult.success(leaf);
	}

}

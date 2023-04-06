package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.SmsHistoryRequest;
import com.wordplay.unit.console.model.response.SmsHistoryResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 短信历史
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/smshistory")
public interface SmsHistoryClient {

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@GetMapping("/get")
	ResponseResult<SmsHistoryResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<SmsHistoryResponse>> page(@RequestBody SmsHistoryRequest request);

}

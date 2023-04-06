package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.SmsConfigRequest;
import com.wordplay.unit.console.model.response.SmsConfigResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 短信配置
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/smsconfig")
public interface SmsConfigClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody SmsConfigRequest req);

	@PostMapping("/savebatch")
	ResponseResult saveBatch(@RequestBody List<SmsConfigRequest> reqList);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody SmsConfigRequest req);

	@GetMapping("/get")
	ResponseResult<SmsConfigResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<SmsConfigResponse>> page(@RequestBody SmsConfigRequest request);

}

package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.SmsTemplateRequest;
import com.wordplay.unit.console.model.response.SmsTemplateResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 短信模板
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/smstemplate")
public interface SmsTemplateClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody SmsTemplateRequest request);

	@PostMapping("/savebatch")
	ResponseResult saveBatch(@RequestBody List<SmsTemplateRequest> requestList);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody SmsTemplateRequest request);

	@GetMapping("/get")
	ResponseResult<SmsTemplateResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<SmsTemplateResponse>> page(@RequestBody SmsTemplateRequest request);

}

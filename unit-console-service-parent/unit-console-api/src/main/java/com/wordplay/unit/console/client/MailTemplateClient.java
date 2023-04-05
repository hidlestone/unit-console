package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.MailTemplateRequest;
import com.wordplay.unit.console.model.response.MailTemplateResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 邮件发送配置
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/mailtemplate")
public interface MailTemplateClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody MailTemplateRequest request);

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody MailTemplateRequest request);

	@GetMapping("/get")
	ResponseResult<MailTemplateResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<MailTemplateResponse>> page(@RequestBody MailTemplateRequest request);

}

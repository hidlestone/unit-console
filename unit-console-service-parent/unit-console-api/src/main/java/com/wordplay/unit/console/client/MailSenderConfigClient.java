package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.MailSenderConfigRequest;
import com.wordplay.unit.console.model.response.MailSenderConfigResponse;
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
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/mailsenderconfig")
public interface MailSenderConfigClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody MailSenderConfigRequest request);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody MailSenderConfigRequest request);

	@GetMapping("/get")
	ResponseResult<MailSenderConfigResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<MailSenderConfigResponse>> page(@RequestBody MailSenderConfigRequest request);

}

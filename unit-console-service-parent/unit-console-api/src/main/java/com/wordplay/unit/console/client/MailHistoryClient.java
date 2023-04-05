package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.MailHistoryRequest;
import com.wordplay.unit.console.model.response.MailHistoryResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/mailhistory")
public interface MailHistoryClient {

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@GetMapping("/get")
	ResponseResult<MailHistoryResponse> get(@RequestParam Long id);

	@PostMapping("/list")
	ResponseResult<Leaf<MailHistoryResponse>> list(@RequestBody MailHistoryRequest request);

}

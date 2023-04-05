package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.MqTraceLogRequest;
import com.wordplay.unit.console.model.response.MqTraceLogResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/mqtracelog")
public interface MqTraceLogClient {

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@GetMapping("/get")
	ResponseResult<MqTraceLogResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<MqTraceLogResponse>> page(MqTraceLogRequest request);

}

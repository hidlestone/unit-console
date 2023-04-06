package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.MqTraceLogClient;
import com.wordplay.unit.console.model.request.MqTraceLogRequest;
import com.wordplay.unit.console.model.response.MqTraceLogResponse;
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
@Api(tags = "MQ轨迹日志")
@RestController
@RequestMapping("/${unit.console.control.version}/mqtracelog")
public class MqTraceLogControl {

	@Autowired(required = false)
	private MqTraceLogClient mqTraceLogClient;

	@PostMapping("/delete")
	@ApiOperation("删除MQ轨迹日志")
	public ResponseResult delete(@RequestParam Long id) {
		return mqTraceLogClient.delete(id);
	}

	@GetMapping("/get")
	@ApiOperation("查询MQ轨迹日志")
	public ResponseResult<MqTraceLogResponse> get(@RequestParam Long id) {
		return mqTraceLogClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询MQ轨迹日志")
	public ResponseResult<Leaf<MqTraceLogResponse>> page(@RequestBody MqTraceLogRequest request) {
		return mqTraceLogClient.page(request);
	}

}

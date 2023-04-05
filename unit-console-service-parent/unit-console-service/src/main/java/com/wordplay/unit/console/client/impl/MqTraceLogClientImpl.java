package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.MqTraceLogClient;
import com.wordplay.unit.console.model.request.MqTraceLogRequest;
import com.wordplay.unit.console.model.response.MqTraceLogResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.mq.entity.MqTraceLog;
import com.wordplay.unit.starter.mq.service.MqTraceLogService;
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
@Api(tags = "MQ轨迹日志")
@RestController
@RequestMapping("/${unit.console.service.version}/mqtracelog")
public class MqTraceLogClientImpl implements MqTraceLogClient {

	@Autowired
	private MqTraceLogService mqTraceLogService;

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除MQ轨迹日志")
	public ResponseResult delete(@RequestParam Long id) {
		MqTraceLog mqTraceLog = mqTraceLogService.getById(id);
		if (null == mqTraceLog) {
			return ResponseResult.fail("数据不存在");
		}
		mqTraceLogService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询MQ轨迹日志")
	public ResponseResult<MqTraceLogResponse> get(@RequestParam Long id) {
		MqTraceLog mqTraceLog = mqTraceLogService.getById(id);
		if (null == mqTraceLog) {
			return ResponseResult.fail("数据不存在");
		}
		MqTraceLogResponse response = new MqTraceLogResponse();
		BeanUtils.copyProperties(mqTraceLog, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询MQ轨迹日志")
	public ResponseResult<Leaf<MqTraceLogResponse>> page(@RequestBody MqTraceLogRequest req) {
		MqTraceLog mqTraceLog = new MqTraceLog();
		BeanUtils.copyProperties(req, mqTraceLog);
		Leaf leaf = LeafPageUtil.leafToType(mqTraceLogService.page(mqTraceLog), MqTraceLogResponse.class);
		return ResponseResult.success(leaf);
	}

}

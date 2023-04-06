package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.SysParamGroupQueryRequest;
import com.wordplay.unit.console.model.request.SysParamGroupRequest;
import com.wordplay.unit.console.model.response.SysParamGroupResponse;
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
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/sysparamgroup")
public interface SysParamGroupClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody SysParamGroupRequest request);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam String code);

	@PostMapping("/update")
	ResponseResult update(@RequestBody SysParamGroupRequest request);

	@GetMapping("/get")
	ResponseResult<SysParamGroupResponse> get(@RequestParam String code);

	@PostMapping("/page")
	ResponseResult<Leaf<SysParamGroupResponse>> page(@RequestBody SysParamGroupQueryRequest request);

}

package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.SysParamItemRequest;
import com.wordplay.unit.console.model.response.SysParamItemResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 系统参数
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/sysparam")
public interface SysParamClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody SysParamItemRequest request);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam String code);

	@PostMapping("/update")
	ResponseResult update(@RequestBody SysParamItemRequest request);

	@GetMapping("/getitemsbygroupcode")
	ResponseResult<List<SysParamItemResponse>> getItemsByGroupCode(@RequestParam String groupCode);

	@GetMapping("/get")
	ResponseResult<SysParamItemResponse> get(@RequestParam String code);

	@PostMapping("/page")
	ResponseResult<Leaf<SysParamItemResponse>> page(@RequestParam SysParamItemRequest request);
}

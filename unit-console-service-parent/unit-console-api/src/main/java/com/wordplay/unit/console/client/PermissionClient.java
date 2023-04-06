package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.PermissionRequest;
import com.wordplay.unit.console.model.response.PermissionResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 接口权限管理
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/permission")
public interface PermissionClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody PermissionRequest request);

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody PermissionRequest request);

	@GetMapping("/get")
	ResponseResult<PermissionResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<PermissionResponse>> page(@RequestBody PermissionRequest request);

}

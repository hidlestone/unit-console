package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.RoleRequest;
import com.wordplay.unit.console.model.response.RoleResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色管理
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/role")
public interface RoleClient {

	@PostMapping("/save")
	ResponseResult<RoleResponse> save(@RequestBody RoleRequest request);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody RoleRequest request);

	@GetMapping("/get")
	ResponseResult<RoleResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<RoleResponse>> page(@RequestBody RoleRequest request);

	@GetMapping("/getrolesbyuserid")
	ResponseResult<List<RoleResponse>> getRolesByUserId(@RequestParam Long userId);

	@GetMapping("/getallrole")
	ResponseResult<List<RoleResponse>> getAllRole();

}

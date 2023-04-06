package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.PermissionClient;
import com.wordplay.unit.console.model.request.PermissionRequest;
import com.wordplay.unit.console.model.response.PermissionResponse;
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
@Api(tags = "接口权限管理")
@RestController
@RequestMapping("/${unit.console.control.version}/permission")
public class PermissionControl {

	@Autowired
	private PermissionClient permissionClient;

	@PostMapping("/save")
	@ApiOperation("保存接口权限")
	public ResponseResult save(@RequestBody PermissionRequest request) {
		return permissionClient.save(request);
	}

	@PostMapping("/delete")
	@ApiOperation("删除接口权限")
	public ResponseResult delete(@RequestParam Long id) {
		return permissionClient.delete(id);
	}

	@PostMapping("/update")
	@ApiOperation("修改接口权限")
	public ResponseResult update(@RequestBody PermissionRequest request) {
		return permissionClient.update(request);
	}

	@GetMapping("/get")
	@ApiOperation("查询接口权限")
	public ResponseResult<PermissionResponse> get(@RequestParam Long id) {
		return permissionClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询接口权限")
	public ResponseResult<Leaf<PermissionResponse>> page(@RequestBody PermissionRequest request) {
		return permissionClient.page(request);
	}

}

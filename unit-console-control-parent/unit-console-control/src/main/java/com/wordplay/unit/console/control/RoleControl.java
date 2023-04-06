package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.RoleClient;
import com.wordplay.unit.console.model.request.RoleRequest;
import com.wordplay.unit.console.model.response.RoleResponse;
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

import java.util.List;

/**
 * @author zhuangpf
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/${unit.console.control.version}/role")
public class RoleControl {

	@Autowired(required = false)
	private RoleClient roleClient;

	@PostMapping("/save")
	@ApiOperation("保存角色")
	public ResponseResult save(@RequestBody RoleRequest request) {
		return roleClient.save(request);
	}

	@PostMapping("/delete")
	@ApiOperation("删除角色")
	public ResponseResult delete(@RequestParam Long id) {
		return roleClient.delete(id);
	}

	@PostMapping("/update")
	@ApiOperation("修改角色")
	public ResponseResult update(@RequestBody RoleRequest request) {
		return roleClient.update(request);
	}

	@GetMapping("/get")
	@ApiOperation("查询角色")
	public ResponseResult<RoleResponse> get(@RequestParam Long id) {
		return roleClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询角色")
	public ResponseResult<Leaf<RoleResponse>> page(@RequestBody RoleRequest request) {
		return roleClient.page(request);
	}

	@GetMapping("/getrolesbyuserid")
	@ApiOperation("根据用户ID查询角色")
	public ResponseResult<List<RoleResponse>> getRolesByUserId(@RequestParam Long userId) {
		return roleClient.getRolesByUserId(userId);
	}

	@PostMapping("/getallrole")
	@ApiOperation("查询所有角色")
	ResponseResult<List<RoleResponse>> getAllRole() {
		return roleClient.getAllRole();
	}

}

package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.PermissionClient;
import com.wordplay.unit.console.model.request.PermissionRequest;
import com.wordplay.unit.console.model.response.PermissionResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.rbac.entity.Permission;
import com.wordplay.unit.starter.rbac.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "接口权限管理")
@RestController
@RequestMapping("/${unit.console.service.version}/permission")
public class PermissionClientImpl implements PermissionClient {

	@Autowired
	private PermissionService permissionService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存接口权限")
	public ResponseResult save(@RequestBody PermissionRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Permission permission = new Permission();
		BeanUtils.copyProperties(request, permission);
		permissionService.save(permission);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(PermissionRequest request) {
		if (StringUtils.isBlank(request.getPermissionCode())) {
			return ResponseResult.fail("权限编码不能为空");
		}
		if (StringUtils.isBlank(request.getPermissionName())) {
			return ResponseResult.fail("权限名称不能为空");
		}
		if (StringUtils.isBlank(request.getResourceValue())) {
			return ResponseResult.fail("资源值不能为空");
		}
		if (null == request.getOrderNum()) {
			return ResponseResult.fail("顺序不能为空");
		}
		if (null == request.getAuthcType()) {
			return ResponseResult.fail("认证类型不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除接口权限")
	public ResponseResult delete(@RequestParam Long id) {
		Permission permission = permissionService.getById(id);
		if (null == permission) {
			return ResponseResult.fail("数据不存在");
		}
		permissionService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改接口权限")
	public ResponseResult update(@RequestBody PermissionRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Permission permission = new Permission();
		BeanUtils.copyProperties(request, permission);
		permissionService.updateById(permission);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询接口权限")
	public ResponseResult<PermissionResponse> get(@RequestParam Long id) {
		Permission permission = permissionService.getById(id);
		if (null == permission) {
			return ResponseResult.fail("数据不存在");
		}
		PermissionResponse response = new PermissionResponse();
		BeanUtils.copyProperties(permission, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询接口权限")
	public ResponseResult<Leaf<PermissionResponse>> page(@RequestBody PermissionRequest request) {
		Permission permission = new Permission();
		BeanUtils.copyProperties(request, permission);
		Leaf leaf = LeafPageUtil.leafToType(permissionService.page(permission), PermissionResponse.class);
		return ResponseResult.success(leaf);
	}

}

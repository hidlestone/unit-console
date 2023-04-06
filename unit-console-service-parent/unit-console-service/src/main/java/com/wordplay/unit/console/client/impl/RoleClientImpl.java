package com.wordplay.unit.console.client.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wordplay.unit.console.client.RoleClient;
import com.wordplay.unit.console.model.request.MenuRequest;
import com.wordplay.unit.console.model.request.RoleRequest;
import com.wordplay.unit.console.model.response.RoleResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.rbac.entity.Role;
import com.wordplay.unit.starter.rbac.entity.RoleMenu;
import com.wordplay.unit.starter.rbac.entity.RolePermission;
import com.wordplay.unit.starter.rbac.service.RoleMenuService;
import com.wordplay.unit.starter.rbac.service.RolePermissionService;
import com.wordplay.unit.starter.rbac.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuangpf
 */
@Validated
@Api(tags = "角色管理")
@RestController
@RequestMapping("/${unit.console.service.version}/role")
public class RoleClientImpl implements RoleClient {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private RolePermissionService rolePermissionService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存角色及菜单关联")
	public ResponseResult<RoleResponse> save(@RequestBody RoleRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Role role = new Role();
		BeanUtils.copyProperties(request, role);
		roleService.save(role);
		request.setId(role.getId());
		// 保存菜单关联
		List<RoleMenu> roleMenuList = getRoleMenuList(request);
		roleMenuService.saveBatch(roleMenuList);
		RoleResponse roleResponse = new RoleResponse();
		BeanUtils.copyProperties(role, roleResponse);
		return ResponseResult.success(roleResponse);
	}

	private ResponseResult saveValidate(RoleRequest request) {
		if (StringUtils.isBlank(request.getRoleCode())) {
			return ResponseResult.fail("角色编码不能为空");
		}
		if (StringUtils.isBlank(request.getRoleName())) {
			return ResponseResult.fail("角色名称不能为空");
		}
		if (StringUtils.isBlank(request.getRoleDesc())) {
			return ResponseResult.fail("角色描述不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除角色")
	public ResponseResult delete(@RequestParam Long id) {
		Role role = roleService.getById(id);
		if (null == role) {
			return ResponseResult.fail("数据不存在");
		}
		// 删除角色
		roleService.removeById(id);
		// 删除角色菜单
		roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleId, id));
		// 删除角色权限
		rolePermissionService.remove(Wrappers.lambdaQuery(RolePermission.class).eq(RolePermission::getRoleId, id));
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改角色")
	public ResponseResult update(@RequestBody RoleRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Role role = new Role();
		BeanUtils.copyProperties(request, role);
		roleService.updateById(role);
		// 删除原来角色菜单
		roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleId, request.getId()));
		// 新增角色菜单
		List<RoleMenu> roleMenuList = getRoleMenuList(request);
		roleMenuService.saveBatch(roleMenuList);
		return ResponseResult.success();
	}

	public List<RoleMenu> getRoleMenuList(RoleRequest request) {
		List<MenuRequest> menuRequestList = request.getMenuRequestList();
		List<RoleMenu> roleMenuList = new ArrayList<>();
		for (MenuRequest menuRequest : menuRequestList) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(request.getId());
			roleMenu.setMenuId(menuRequest.getId());
			roleMenuList.add(roleMenu);
		}
		return roleMenuList;
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询角色")
	public ResponseResult<RoleResponse> get(@RequestParam Long id) {
		Role role = roleService.getById(id);
		if (null == role) {
			return ResponseResult.fail("数据不存在");
		}
		RoleResponse response = new RoleResponse();
		BeanUtils.copyProperties(role, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询角色")
	public ResponseResult<Leaf<RoleResponse>> page(@RequestBody RoleRequest req) {
		Role role = new Role();
		BeanUtils.copyProperties(req, role);
		Leaf leaf = LeafPageUtil.leafToType(roleService.page(role), RoleResponse.class);
		return ResponseResult.success(leaf);
	}

	@Override
	@GetMapping("/getrolesbyuserid")
	@ApiOperation("根据用户ID查询角色")
	public ResponseResult<List<RoleResponse>> getRolesByUserId(@RequestParam Long userId) {
		List<Role> roleList = roleService.getRolesByUserId(userId);


		List<RoleResponse> respList = JSON.parseArray(JSON.toJSONString(roleList), RoleResponse.class);
		return ResponseResult.success(respList);
	}

	@Override
	@GetMapping("/getallrole")
	@ApiOperation("查询所有角色")
	public ResponseResult<List<RoleResponse>> getAllRole() {
		List<Role> roleList = roleService.getAllRole();
		List<RoleResponse> roleResponseList = JSON.parseArray(JSON.toJSONString(roleList), RoleResponse.class);
		return ResponseResult.success(roleResponseList);
	}

}

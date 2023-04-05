package com.wordplay.unit.console.client.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wordplay.unit.console.client.MenuClient;
import com.wordplay.unit.console.model.request.MenuQueryRequest;
import com.wordplay.unit.console.model.request.MenuRequest;
import com.wordplay.unit.console.model.response.FrontMenuResponse;
import com.wordplay.unit.console.model.response.MenuMetaInfoResponse;
import com.wordplay.unit.console.model.response.MenuResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.model.StatusEnum;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.rbac.entity.Menu;
import com.wordplay.unit.starter.rbac.entity.User;
import com.wordplay.unit.starter.rbac.service.MenuService;
import com.wordplay.unit.starter.rbac.util.RequestContexUtil;
import com.wordplay.unit.starter.shiro.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuangpf
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/${unit.console.service.version}/menu")
public class MenuClientImpl implements MenuClient {

	@Autowired
	private MenuService menuService;
	@Autowired
	private JWTUtil jwtUtil;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存菜单")
	public ResponseResult save(@RequestBody MenuRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Menu menu = new Menu();
		BeanUtils.copyProperties(request, menu);
		menuService.save(menu);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(MenuRequest request) {
		if (StringUtils.isBlank(request.getMenuCode())) {
			return ResponseResult.fail("菜单编码不能为空");
		}
		if (StringUtils.isBlank(request.getMenuName())) {
			return ResponseResult.fail("菜单名称不能为空");
		}
		if (StringUtils.isBlank(request.getMenuDesc())) {
			return ResponseResult.fail("菜单描述不能为空");
		}
		if (StringUtils.isBlank(request.getPath())) {
			return ResponseResult.fail("访问路径不能为空");
		}
		if (StringUtils.isBlank(request.getFuncLink())) {
			return ResponseResult.fail("功能链接不能为空");
		}
		if (null == request.getOpenType()) {
			return ResponseResult.fail("打开方式不能为空");
		}
		if (StringUtils.isBlank(request.getIcon())) {
			return ResponseResult.fail("图标不能为空");
		}
		if (null == request.getOrder()) {
			return ResponseResult.fail("排序不能为空");
		}
		if (null == request.getStatus()) {
			return ResponseResult.fail("是否显示不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除菜单")
	public ResponseResult delete(@RequestParam Long id) {
		menuService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改菜单")
	public ResponseResult update(@RequestBody MenuRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Menu menu = new Menu();
		BeanUtils.copyProperties(request, menu);
		menuService.updateById(menu);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询菜单")
	public ResponseResult<MenuResponse> get(@RequestParam Long id) {
		Menu menu = menuService.getById(id);
		if (null == menu) {
			return ResponseResult.fail("数据不存在");
		}
		MenuResponse response = new MenuResponse();
		BeanUtils.copyProperties(menu, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询菜单")
	public ResponseResult<Leaf<MenuResponse>> page(@RequestBody MenuRequest request) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(request, menu);
		Leaf leaf = LeafPageUtil.leafToType(menuService.page(menu), MenuResponse.class);
		return ResponseResult.success(leaf);
	}

	@Override
	@PostMapping("/getmenusbyuserid")
	@ApiOperation("根据用户ID分页查询菜单")
	public ResponseResult<Leaf<MenuResponse>> getMenusByUserId(@RequestBody MenuQueryRequest request) {
		if (null == request.getUserId()) {
			return ResponseResult.fail("用户ID不能为空");
		}
		Menu menu = new Menu();
		BeanUtils.copyProperties(request, menu);
		Leaf leaf = LeafPageUtil.leafToType(menuService.page(menu), MenuResponse.class);
		return ResponseResult.success(leaf);
	}

	@Override
	@PostMapping("/getmenusbyroleids")
	@ApiOperation("根据角色ID分页查询菜单")
	public ResponseResult<Leaf<MenuResponse>> getMenusByRoleIds(@RequestBody MenuQueryRequest request) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(request, menu);
		Leaf leaf = LeafPageUtil.leafToType(menuService.page(menu), MenuResponse.class);
		return ResponseResult.success(leaf);
	}

	@Override
	@GetMapping("/getallmenus")
	@ApiOperation("根据token获取当前用户所有菜单")
	public ResponseResult<List<FrontMenuResponse>> getAllMenus() {
		Subject currentUser = SecurityUtils.getSubject();
		User curUser = (User) currentUser.getPrincipal();
		String accesstoken = RequestContexUtil.getAccesstoken();
		if (StringUtils.isEmpty(accesstoken)) {
			return ResponseResult.fail("token不存在");
		}
		try {
			curUser = jwtUtil.parseToken(accesstoken);
			if (null == curUser) {
				return ResponseResult.fail("用户不存在");
			}
		} catch (Exception e) {
			return ResponseResult.fail("token不可用");
		}
		// 获取当前用户所有可访问的菜单
		List<Menu> menuList = menuService.getAllMenusByUserId(curUser.getId());
		if (CollectionUtil.isEmpty(menuList)) {
			return ResponseResult.fail("菜单不存在");
		}
		// 转换成前端展示的数据结构
		List<FrontMenuResponse> oneMenuResponseList = new ArrayList<>();
		// 一级菜单，即parentId为null，按照order升序
		List<Menu> oneMenuList = menuList.stream().filter(e -> 0 == e.getParentId()).sorted(Comparator.comparing(Menu::getOrder)).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(oneMenuList)) {
			return ResponseResult.fail("一级菜单不存在");
		}
		for (Menu menu : oneMenuList) {
			FrontMenuResponse oneFrontMenu = menuToFrontMenu(menu);
			oneFrontMenu.setComponent("Layout");
			oneFrontMenu.setRedirect(menu.getFuncLink());
			oneMenuResponseList.add(oneFrontMenu);
			// 二级菜单
			List<FrontMenuResponse> twoMenuResponseList = new ArrayList<>();
			List<Menu> twoMenuList = menuList.stream().filter(e -> menu.getId().equals(e.getParentId())).sorted(Comparator.comparing(Menu::getOrder)).collect(Collectors.toList());
			for (Menu menu2 : twoMenuList) {
				FrontMenuResponse twoFrontMenu = menuToFrontMenu(menu2);
				twoFrontMenu.setComponent(menu2.getFuncLink());
				twoMenuResponseList.add(twoFrontMenu);
			}
			oneFrontMenu.setChildren(twoMenuResponseList);
		}
		return ResponseResult.success(oneMenuResponseList);
	}

	@Override
	@PostMapping("/getmenutree")
	@ApiOperation("查询菜单树")
	public ResponseResult<List<MenuResponse>> getMenuTree(@RequestBody MenuQueryRequest request) {
		Menu menu = new Menu();
		BeanUtils.copyProperties(request, menu);
		List<Menu> menuList = menuService.getMenuTree(menu);
		List<MenuResponse> menuResponseList = JSON.parseArray(JSON.toJSONString(menuList), MenuResponse.class);
		return ResponseResult.success(menuResponseList);
	}

	@Override
	@GetMapping("/getmenulistbyparentid")
	@ApiOperation("根据父ID查询菜单")
	public ResponseResult<List<MenuResponse>> getMenuListByParentId(@RequestParam Long parentId) {
		List<Menu> menuList = menuService.getMenusByParentId(parentId);
		List<MenuResponse> menuResponseList = JSON.parseArray(JSON.toJSONString(menuList), MenuResponse.class);
		return ResponseResult.success(menuResponseList);
	}

	@Override
	@GetMapping("/getmainmenus")
	@ApiOperation("查询主菜单")
	public ResponseResult<List<MenuResponse>> getMainMenus() {
		QueryWrapper<Menu> wrapper = new QueryWrapper();
		wrapper.eq("level", 1);
		List<Menu> menuList = menuService.list(wrapper);
		List<MenuResponse> menuResponseList = JSON.parseArray(JSON.toJSONString(menuList), MenuResponse.class);
		return ResponseResult.success(menuResponseList);
	}

	/**
	 * 转换成前端展示的字段
	 *
	 * @param menu 菜单对象
	 * @return 前端展示的菜单数据
	 */
	public FrontMenuResponse menuToFrontMenu(Menu menu) {
		FrontMenuResponse frontMenu = new FrontMenuResponse();
		frontMenu.setPath(menu.getPath());
		/*frontMenu.setRedirect(menu.getFuncLink());*/
		frontMenu.setAlwaysShow(StatusEnum.Y.equals(menu.getStatus()) ? true : false);
		frontMenu.setName(menu.getMenuName());
		MenuMetaInfoResponse meta = new MenuMetaInfoResponse();
		meta.setTitle(menu.getMenuName());
		meta.setIcon(menu.getIcon());
		/*meta.setRoles();*/
		frontMenu.setMeta(meta);
		return frontMenu;
	}

}

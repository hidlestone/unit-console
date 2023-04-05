package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.MenuQueryRequest;
import com.wordplay.unit.console.model.request.MenuRequest;
import com.wordplay.unit.console.model.response.FrontMenuResponse;
import com.wordplay.unit.console.model.response.MenuResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 菜单管理
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/menu")
public interface MenuClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody MenuRequest request);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody MenuRequest request);

	@GetMapping("/get")
	ResponseResult<MenuResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<MenuResponse>> page(@RequestBody MenuRequest request);

	@PostMapping("/getmenusbyuserid")
	ResponseResult<Leaf<MenuResponse>> getMenusByUserId(@RequestBody MenuQueryRequest request);

	@PostMapping("/getmenusbyroleids")
	ResponseResult<Leaf<MenuResponse>> getMenusByRoleIds(@RequestBody MenuQueryRequest request);

	@GetMapping("/getallmenus")
	ResponseResult<List<FrontMenuResponse>> getAllMenus();

	@PostMapping("/getmenutree")
	ResponseResult<List<MenuResponse>> getMenuTree(@RequestBody MenuQueryRequest request);

	@GetMapping("/getmenulistbyparentid")
	ResponseResult<List<MenuResponse>> getMenuListByParentId(@RequestParam Long parentId);

	@GetMapping("/getmainmenus")
	ResponseResult<List<MenuResponse>> getMainMenus();

}

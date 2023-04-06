package com.wordplay.unit.console.client.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wordplay.unit.console.client.SysParamClient;
import com.wordplay.unit.console.model.request.SysParamItemRequest;
import com.wordplay.unit.console.model.response.SysParamItemResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sysparam.entity.SysParamItem;
import com.wordplay.unit.starter.sysparam.service.SysParamGroupService;
import com.wordplay.unit.starter.sysparam.service.SysParamItemService;
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

import java.util.List;

/**
 * @author zhuangpf
 */
@Api(tags = "系统参数")
@RestController
@RequestMapping("/${platform.console.service.version}/sysparam")
public class SysParamClientImpl implements SysParamClient {

	@Autowired
	private SysParamGroupService sysParamGroupService;
	@Autowired
	private SysParamItemService sysParamItemService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存配置明细项")
	public ResponseResult save(@RequestBody SysParamItemRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		SysParamItem sysParamItem = new SysParamItem();
		BeanUtils.copyProperties(request, sysParamItem);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(SysParamItemRequest request) {
		if (StringUtils.isBlank(request.getCode())) {
			return ResponseResult.fail("系统参数编码不能为空");
		}
		if (StringUtils.isBlank(request.getValue())) {
			return ResponseResult.fail("系统参数值不能为空");
		}
		if (StringUtils.isBlank(request.getDesc())) {
			return ResponseResult.fail("系统参数描述不能为空");
		}
		if (StringUtils.isBlank(request.getGroupCode())) {
			return ResponseResult.fail("系统参数组编码不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除明细项")
	public ResponseResult delete(@RequestParam String code) {
		LambdaQueryWrapper<SysParamItem> wrapper = Wrappers.lambdaQuery(SysParamItem.class).eq(SysParamItem::getCode, code);
		int count = sysParamItemService.count(wrapper);
		if (0 == count) {
			return ResponseResult.fail("数据不存在");
		}
		sysParamItemService.remove(wrapper);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("更新配置明细项")
	public ResponseResult update(@RequestBody SysParamItemRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		SysParamItem sysParamItem = new SysParamItem();
		BeanUtils.copyProperties(request, sysParamItem);
		sysParamItemService.updateById(sysParamItem);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/getitemsbygroupcode")
	@ApiOperation("根据配置组编码查询配置明细项")
	public ResponseResult<List<SysParamItemResponse>> getItemsByGroupCode(@RequestParam String groupCode) {
		List<SysParamItem> list = sysParamItemService.list(Wrappers.lambdaQuery(SysParamItem.class).eq(SysParamItem::getGroupCode, groupCode));
		List<SysParamItemResponse> respList = JSON.parseArray(JSON.toJSONString(list), SysParamItemResponse.class);
		return ResponseResult.success(respList);
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("根据编码查询配置明细")
	public ResponseResult<SysParamItemResponse> get(@RequestParam String code) {
		SysParamItem sysParamItem = sysParamItemService.get(code);
		if (null == sysParamItem) {
			return ResponseResult.fail("数据不存在");
		}
		SysParamItemResponse response = new SysParamItemResponse();
		BeanUtils.copyProperties(sysParamItem, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询配置明细")
	public ResponseResult<Leaf<SysParamItemResponse>> page(@RequestBody SysParamItemRequest request) {
		SysParamItem sysParamItem = new SysParamItem();
		BeanUtils.copyProperties(request, sysParamItem);
		Leaf leaf = LeafPageUtil.leafToType(sysParamItemService.page(sysParamItem), SysParamItemResponse.class);
		return ResponseResult.success(leaf);
	}

}

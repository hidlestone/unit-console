package com.wordplay.unit.console.client.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wordplay.unit.console.client.SysParamGroupClient;
import com.wordplay.unit.console.model.request.SysParamGroupQueryRequest;
import com.wordplay.unit.console.model.request.SysParamGroupRequest;
import com.wordplay.unit.console.model.response.SysParamGroupResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sysparam.entity.SysParamGroup;
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
@Api(tags = "系统参数组")
@RestController
@RequestMapping("/${unit.console.service.version}/sysparamgroup")
public class SysParamGroupClientImpl implements SysParamGroupClient {

	@Autowired
	private SysParamGroupService sysParamGroupService;
	@Autowired
	private SysParamItemService sysParamItemService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存配置组及明细项")
	public ResponseResult save(@RequestBody SysParamGroupRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		// 配置组
		SysParamGroup sysParamGroup = new SysParamGroup();
		BeanUtils.copyProperties(request, sysParamGroup);
		sysParamGroupService.save(sysParamGroup);
		// 明细项
		List<SysParamItem> sysParamItemList = JSON.parseArray(JSON.toJSONString(request.getSysParamItemList()), SysParamItem.class);
		sysParamItemService.saveBatch(sysParamItemList);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(SysParamGroupRequest request) {
		if (StringUtils.isBlank(request.getCode())) {
			return ResponseResult.fail("系统参数组编码不能为空");
		}
		if (StringUtils.isBlank(request.getDesc())) {
			return ResponseResult.fail("系统参数组描述不能为空");
		}
		if (CollectionUtil.isEmpty(request.getSysParamItemList())) {
			return ResponseResult.fail("系统参数组明细项不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除配置组及明细项")
	public ResponseResult delete(@RequestParam String code) {
		// 配置组
		sysParamGroupService.remove(Wrappers.lambdaQuery(SysParamGroup.class).eq(SysParamGroup::getCode, code));
		// 明细项
		sysParamItemService.remove(Wrappers.lambdaQuery(SysParamItem.class).eq(SysParamItem::getGroupCode, code));
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("更新配置组及明细项")
	public ResponseResult update(@RequestBody SysParamGroupRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		// 配置组
		SysParamGroup sysParamGroup = JSON.parseObject(JSON.toJSONString(request), SysParamGroup.class);
		sysParamGroupService.updateById(sysParamGroup);
		// 明细项
		sysParamItemService.updateBatchById(sysParamGroup.getSysParamItems());
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("根据配置组编码查询配置组及明细项")
	public ResponseResult<SysParamGroupResponse> get(@RequestParam String code) {
		SysParamGroup sysParamGroup = sysParamGroupService.get(code);
		if (null == sysParamGroup) {
			return ResponseResult.fail("数据不存在");
		}
		SysParamGroupResponse response = JSON.parseObject(JSON.toJSONString(sysParamGroup), SysParamGroupResponse.class);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询配置组")
	public ResponseResult<Leaf<SysParamGroupResponse>> page(@RequestBody SysParamGroupQueryRequest request) {
		SysParamGroup sysParamGroup = new SysParamGroup();
		BeanUtils.copyProperties(request, sysParamGroup);
		Leaf leaf = LeafPageUtil.leafToType(sysParamGroupService.page(sysParamGroup), SysParamGroupResponse.class);
		return ResponseResult.success(leaf);
	}

}

package com.wordplay.unit.console.client.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.wordplay.unit.console.client.I18NClient;
import com.wordplay.unit.console.model.request.I18nResourceRequest;
import com.wordplay.unit.console.model.response.I18nResourceResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.i18n.entity.I18nResource;
import com.wordplay.unit.starter.i18n.service.I18nResourceService;
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
@Api(tags = "I18N多语言")
@RestController
@RequestMapping("/${unit.console.service.version}/i18n")
public class I18NClientImpl implements I18NClient {

	@Autowired
	private I18nResourceService i18nResourceService;

	@Override
	@PostMapping("/save")
	@ApiOperation("新增I8N词条")
	public ResponseResult save(@RequestBody I18nResourceRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (validateResp.isSuccess()) {
			return validateResp;
		}
		I18nResource i18nResource = new I18nResource();
		BeanUtils.copyProperties(request, i18nResource);
		i18nResourceService.save(i18nResource);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(I18nResourceRequest request) {
		if (StringUtils.isBlank(request.getLangCode())) {
			return ResponseResult.fail("语言编码不能为空");
		}
		if (StringUtils.isBlank(request.getResourceKey())) {
			return ResponseResult.fail("资源key不能为空");
		}
		if (StringUtils.isBlank(request.getLangCode())) {
			return ResponseResult.fail("资源value不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/savebatch")
	@ApiOperation("批量新增I8N词条")
	public ResponseResult saveBatch(@RequestBody List<I18nResourceRequest> reqList) {
		if (CollectionUtil.isEmpty(reqList)) {
			return ResponseResult.fail("保存数据不能为空");
		}
		List<I18nResource> i18nResourceList = JSON.parseArray(JSON.toJSONString(reqList), I18nResource.class);
		i18nResourceService.saveBatch(i18nResourceList);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除I8N词条")
	public ResponseResult delete(@RequestParam Long id) {
		I18nResource i18nResource = i18nResourceService.getById(id);
		if (null == i18nResourceService) {
			return ResponseResult.fail("数据不存在");
		}
		i18nResourceService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改I8N词条")
	public ResponseResult update(@RequestBody I18nResourceRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (validateResp.isSuccess()) {
			return validateResp;
		}
		I18nResource i18nResource = new I18nResource();
		BeanUtils.copyProperties(request, i18nResource);
		i18nResourceService.updateById(i18nResource);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("ID查询I8N词条")
	public ResponseResult<I18nResourceResponse> get(@RequestParam Long id) {
		I18nResource i18nResource = i18nResourceService.getById(id);
		if (null == i18nResourceService) {
			return ResponseResult.fail("数据不存在");
		}
		I18nResourceResponse response = new I18nResourceResponse();
		BeanUtils.copyProperties(i18nResource, response);
		return ResponseResult.success(response);
	}

	@Override
	@GetMapping("/getbyresourcekey")
	@ApiOperation("resourceKey查询I8N词条")
	public ResponseResult<List<I18nResourceResponse>> getByResourceKey(@RequestParam String resourceKey) {
		List<I18nResource> i18nResourceList = i18nResourceService.getByResourceKey(resourceKey);
		if (CollectionUtil.isEmpty(i18nResourceList)) {
			return ResponseResult.fail("数据不存在");
		}
		List<I18nResourceResponse> respList = JSON.parseArray(JSON.toJSONString(i18nResourceList), I18nResourceResponse.class);
		return ResponseResult.success(respList);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询I8N词条")
	public ResponseResult<Leaf<I18nResourceResponse>> page(@RequestBody I18nResourceRequest request) {
		I18nResource i18nResource = new I18nResource();
		BeanUtils.copyProperties(request, i18nResource);
		Leaf leaf = LeafPageUtil.leafToType(i18nResourceService.page(i18nResource), I18nResourceResponse.class);
		return ResponseResult.success(leaf);
	}

}

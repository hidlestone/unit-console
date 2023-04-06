package com.wordplay.unit.console.client.impl;

import com.alibaba.fastjson.JSON;
import com.wordplay.unit.console.client.SmsConfigClient;
import com.wordplay.unit.console.model.request.SmsConfigRequest;
import com.wordplay.unit.console.model.response.SmsConfigResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sms.entity.SmsConfig;
import com.wordplay.unit.starter.sms.service.SmsConfigService;
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

import java.util.Date;
import java.util.List;

/**
 * @author zhuangpf
 */
@Api(tags = "短信配置")
@RestController
@RequestMapping("/${unit.console.service.version}/smsconfig")
public class SmsConfigClientImpl implements SmsConfigClient {

	@Autowired
	private SmsConfigService smsConfigService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存短信配置")
	public ResponseResult save(@RequestBody SmsConfigRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		SmsConfig smsConfig = new SmsConfig();
		BeanUtils.copyProperties(request, smsConfig);
		smsConfigService.save(smsConfig);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(SmsConfigRequest request) {
		if (StringUtils.isBlank(request.getProductType())) {
			return ResponseResult.fail("短信产品类型不能为空");
		}
		if (StringUtils.isBlank(request.getRegionId())) {
			return ResponseResult.fail("区域ID不能为空");
		}
		if (StringUtils.isBlank(request.getAccessKeyId())) {
			return ResponseResult.fail("accessKey不能为空");
		}
		if (StringUtils.isBlank(request.getAccessKeySecret())) {
			return ResponseResult.fail("accessKeySecret不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/savebatch")
	@ApiOperation("批量保存短信配置")
	public ResponseResult saveBatch(@RequestBody List<SmsConfigRequest> requestList) {
		List<SmsConfig> smsConfigList = JSON.parseArray(JSON.toJSONString(requestList), SmsConfig.class);
		smsConfigService.saveBatch(smsConfigList);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除短信配置")
	public ResponseResult delete(@RequestParam Long id) {
		SmsConfig smsConfig = smsConfigService.getById(id);
		if (null == smsConfig) {
			return ResponseResult.fail("数据不能为空");
		}
		smsConfigService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改短信配置")
	public ResponseResult update(@RequestBody SmsConfigRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		SmsConfig smsConfig = new SmsConfig();
		BeanUtils.copyProperties(request, smsConfig);
		smsConfig.setGmtModified(new Date());
		smsConfigService.updateById(smsConfig);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询短信配置")
	public ResponseResult<SmsConfigResponse> get(@RequestParam Long id) {
		SmsConfig smsConfig = smsConfigService.getById(id);
		if (null == smsConfig) {
			return ResponseResult.fail("数据不能为空");
		}
		SmsConfigResponse response = new SmsConfigResponse();
		BeanUtils.copyProperties(smsConfig, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询短信配置")
	public ResponseResult<Leaf<SmsConfigResponse>> page(@RequestBody SmsConfigRequest req) {
		SmsConfig smsConfig = new SmsConfig();
		BeanUtils.copyProperties(req, smsConfig);
		Leaf leaf = LeafPageUtil.leafToType(smsConfigService.page(smsConfig), SmsConfigResponse.class);
		return ResponseResult.success(leaf);
	}

}

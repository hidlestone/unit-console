package com.wordplay.unit.console.client.impl;

import com.alibaba.fastjson.JSON;
import com.wordplay.unit.console.client.SmsTemplateClient;
import com.wordplay.unit.console.model.request.SmsTemplateRequest;
import com.wordplay.unit.console.model.response.SmsTemplateResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sms.entity.SmsTemplate;
import com.wordplay.unit.starter.sms.service.SmsTemplateService;
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
@Api(tags = "短信模板")
@RestController
@RequestMapping("/${unit.console.service.version}/smstemplate")
public class SmsTemplateClientImpl implements SmsTemplateClient {

	@Autowired
	private SmsTemplateService smsTemplateService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存短信模板")
	public ResponseResult save(@RequestBody SmsTemplateRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		SmsTemplate smsTemplate = new SmsTemplate();
		BeanUtils.copyProperties(request, smsTemplate);
		smsTemplateService.save(smsTemplate);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(SmsTemplateRequest request) {
		if (StringUtils.isBlank(request.getDesc())) {
			return ResponseResult.fail("短信模板配置描述不能为空");
		}
		if (StringUtils.isBlank(request.getFrom())) {
			return ResponseResult.fail("发送号码不能为空");
		}
		if (null == request.getContentType()) {
			return ResponseResult.fail("模板类型不能为空");
		}
		if (StringUtils.isBlank(request.getContent())) {
			return ResponseResult.fail("内容不能为空");
		}
		if (null == request.getRetryCount()) {
			return ResponseResult.fail("重试次数不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/savebatch")
	@ApiOperation("批量保存短信模板")
	public ResponseResult saveBatch(@RequestBody List<SmsTemplateRequest> requestList) {
		for (SmsTemplateRequest request : requestList) {
			ResponseResult validateResp = saveValidate(request);
			if (!validateResp.isSuccess()) {
				return validateResp;
			}
		}
		List<SmsTemplate> smsTemplateList = JSON.parseArray(JSON.toJSONString(requestList), SmsTemplate.class);
		smsTemplateService.saveBatch(smsTemplateList);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除短信模板")
	public ResponseResult delete(@RequestParam Long id) {
		SmsTemplate smsTemplate = smsTemplateService.getById(id);
		if (null == smsTemplate) {
			return ResponseResult.fail("数据不存在");
		}
		smsTemplateService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改短信模板")
	public ResponseResult update(@RequestBody SmsTemplateRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		SmsTemplate smsTemplate = new SmsTemplate();
		BeanUtils.copyProperties(request, smsTemplate);
		smsTemplateService.updateById(smsTemplate);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询短信模板")
	public ResponseResult<SmsTemplateResponse> get(@RequestParam Long id) {
		SmsTemplate smsTemplate = smsTemplateService.getById(id);
		SmsTemplateResponse response = new SmsTemplateResponse();
		BeanUtils.copyProperties(smsTemplate, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询短信模板")
	public ResponseResult<Leaf<SmsTemplateResponse>> page(@RequestBody SmsTemplateRequest request) {
		SmsTemplate smsTemplate = new SmsTemplate();
		BeanUtils.copyProperties(request, smsTemplate);
		Leaf leaf = LeafPageUtil.leafToType(smsTemplateService.page(smsTemplate), SmsTemplateResponse.class);
		return ResponseResult.success(leaf);
	}

}

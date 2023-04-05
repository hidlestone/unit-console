package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.MailTemplateClient;
import com.wordplay.unit.console.model.request.MailTemplateRequest;
import com.wordplay.unit.console.model.response.MailTemplateResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.mail.entity.MailTemplate;
import com.wordplay.unit.starter.mail.service.MailTemplateService;
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
@Api(tags = "邮件模板")
@RestController
@RequestMapping("/${unit.console.service.version}/mailtemplate")
public class MailTemplateClientImpl implements MailTemplateClient {

	@Autowired
	private MailTemplateService mailTemplateService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存邮件发送配置")
	public ResponseResult save(@RequestBody MailTemplateRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		MailTemplate mailTemplate = new MailTemplate();
		BeanUtils.copyProperties(request, mailTemplate);
		mailTemplateService.save(mailTemplate);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(MailTemplateRequest request) {
		if (StringUtils.isBlank(request.getCode())) {
			return ResponseResult.fail("邮件模板配置编码不能为空");
		}
		if (StringUtils.isBlank(request.getDesc())) {
			return ResponseResult.fail("邮件模板配置描述不能为空");
		}
		if (StringUtils.isBlank(request.getTitle())) {
			return ResponseResult.fail("标题不能为空");
		}
		if (StringUtils.isBlank(request.getFrom())) {
			return ResponseResult.fail("发送者不能为空");
		}
		if (StringUtils.isBlank(request.getContent())) {
			return ResponseResult.fail("内容不能为空");
		}
		if (null == request.getRetryCount()) {
			return ResponseResult.fail("retryCount不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除邮件发送配置")
	public ResponseResult delete(@RequestParam Long id) {
		MailTemplate mailTemplate = mailTemplateService.getById(id);
		if (null == mailTemplate) {
			return ResponseResult.fail("数据不存在");
		}
		mailTemplateService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改邮件发送配置")
	public ResponseResult update(@RequestBody MailTemplateRequest request) {
		MailTemplate mailTemplate = new MailTemplate();
		BeanUtils.copyProperties(request, mailTemplate);
		mailTemplateService.updateById(mailTemplate);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询邮件发送配置")
	public ResponseResult<MailTemplateResponse> get(@RequestParam Long id) {
		MailTemplate mailTemplate = mailTemplateService.getById(id);
		if (null == mailTemplate) {
			return ResponseResult.fail("数据不存在");
		}
		MailTemplateResponse response = new MailTemplateResponse();
		BeanUtils.copyProperties(mailTemplate, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询邮件发送配置")
	public ResponseResult<Leaf<MailTemplateResponse>> page(@RequestBody MailTemplateRequest request) {
		MailTemplate mailTemplate = new MailTemplate();
		BeanUtils.copyProperties(request, mailTemplate);
		Leaf leaf = LeafPageUtil.leafToType(mailTemplateService.page(mailTemplate), MailTemplateResponse.class);
		return ResponseResult.success(leaf);
	}

}

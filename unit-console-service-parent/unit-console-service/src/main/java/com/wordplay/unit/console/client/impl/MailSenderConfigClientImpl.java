package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.MailSenderConfigClient;
import com.wordplay.unit.console.model.request.MailSenderConfigRequest;
import com.wordplay.unit.console.model.response.MailSenderConfigResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.mail.entity.MailSenderConfig;
import com.wordplay.unit.starter.mail.service.MailSenderConfigService;
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
@Api(tags = "邮件发送配置")
@RestController
@RequestMapping("/${unit.console.service.version}/mailsenderconfig")
public class MailSenderConfigClientImpl implements MailSenderConfigClient {

	@Autowired
	private MailSenderConfigService mailSenderConfigService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存邮件发送配置")
	public ResponseResult save(@RequestBody MailSenderConfigRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		MailSenderConfig mailSenderConfig = new MailSenderConfig();
		BeanUtils.copyProperties(request, mailSenderConfig);
		mailSenderConfigService.save(mailSenderConfig);
		return ResponseResult.success();
	}

	private ResponseResult saveValidate(MailSenderConfigRequest request) {
		if (StringUtils.isBlank(request.getHost())) {
			return ResponseResult.fail("host不能为空");
		}
		if (null == request.getPort()) {
			return ResponseResult.fail("端口不能为空");
		}
		if (StringUtils.isBlank(request.getUsername())) {
			return ResponseResult.fail("账号不能为空");
		}
		if (StringUtils.isBlank(request.getPassword())) {
			return ResponseResult.fail("密码不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除邮件发送配置")
	public ResponseResult delete(@RequestParam Long id) {
		mailSenderConfigService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("修改邮件发送配置")
	public ResponseResult update(@RequestBody MailSenderConfigRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		MailSenderConfig mailSenderConfig = new MailSenderConfig();
		BeanUtils.copyProperties(request, mailSenderConfig);
		mailSenderConfigService.updateById(mailSenderConfig);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询邮件发送配置")
	public ResponseResult<MailSenderConfigResponse> get(@RequestParam Long id) {
		MailSenderConfig mailSenderConfig = mailSenderConfigService.getById(id);
		if (null == mailSenderConfig) {
			return ResponseResult.fail("数据不存在");
		}
		MailSenderConfigResponse response = new MailSenderConfigResponse();
		BeanUtils.copyProperties(mailSenderConfig, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询邮件发送配置")
	public ResponseResult<Leaf<MailSenderConfigResponse>> page(@RequestBody MailSenderConfigRequest req) {
		MailSenderConfig mailSenderConfig = new MailSenderConfig();
		BeanUtils.copyProperties(req, mailSenderConfig);
		Leaf leaf = LeafPageUtil.leafToType(mailSenderConfigService.page(mailSenderConfig), MailSenderConfigResponse.class);
		return ResponseResult.success(leaf);
	}

}

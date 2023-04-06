package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.SmsTemplateClient;
import com.wordplay.unit.console.model.request.SmsTemplateRequest;
import com.wordplay.unit.console.model.response.SmsTemplateResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/${unit.console.control.version}/smstemplate")
public class SmsTemplateControl {

	@Autowired(required = false)
	private SmsTemplateClient smsTemplateClient;

	@PostMapping("/save")
	@ApiOperation("保存短信模板")
	public ResponseResult save(@RequestBody SmsTemplateRequest request) {
		return smsTemplateClient.save(request);
	}

	@PostMapping("/savebatch")
	@ApiOperation("批量保存短信模板")
	public ResponseResult saveBatch(@RequestBody List<SmsTemplateRequest> reqList) {
		return smsTemplateClient.saveBatch(reqList);
	}

	@PostMapping("/delete")
	@ApiOperation("删除短信模板")
	public ResponseResult delete(@RequestParam Long id) {
		return smsTemplateClient.delete(id);
	}

	@PostMapping("/update")
	@ApiOperation("修改短信模板")
	public ResponseResult update(@RequestBody SmsTemplateRequest request) {
		return smsTemplateClient.update(request);
	}

	@GetMapping("/get")
	@ApiOperation("查询短信模板")
	public ResponseResult<SmsTemplateResponse> get(@RequestParam Long id) {
		return smsTemplateClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询短信模板")
	public ResponseResult<Leaf<SmsTemplateResponse>> page(@RequestBody SmsTemplateRequest request) {
		return smsTemplateClient.page(request);
	}

}

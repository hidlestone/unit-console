package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.MailHistoryClient;
import com.wordplay.unit.console.model.request.MailHistoryRequest;
import com.wordplay.unit.console.model.response.MailHistoryResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.mail.entity.MailHistory;
import com.wordplay.unit.starter.mail.service.MailHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "邮件历史")
@RestController
@RequestMapping("/${unit.console.service.version}/mailhistory")
public class MailHistoryClientImpl implements MailHistoryClient {

	@Autowired
	private MailHistoryService mailHistoryService;

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除邮件历史")
	public ResponseResult delete(@RequestParam Long id) {
		MailHistory mailHistory = mailHistoryService.getById(id);
		if (null == mailHistory) {
			return ResponseResult.fail("数据不存在");
		}
		mailHistoryService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询邮件历史")
	public ResponseResult<MailHistoryResponse> get(@RequestParam Long id) {
		MailHistory mailHistory = mailHistoryService.getById(id);
		if (null == mailHistory) {
			return ResponseResult.fail("数据不存在");
		}
		MailHistoryResponse response = new MailHistoryResponse();
		BeanUtils.copyProperties(mailHistory, response);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/list")
	@ApiOperation("分页查询邮件历史")
	public ResponseResult<Leaf<MailHistoryResponse>> list(@RequestBody MailHistoryRequest request) {
		MailHistory mailHistory = new MailHistory();
		BeanUtils.copyProperties(request, mailHistory);
		Leaf leaf = LeafPageUtil.leafToType(mailHistoryService.page(mailHistory), MailHistoryResponse.class);
		return ResponseResult.success(leaf);
	}

}

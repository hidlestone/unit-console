package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.SysParamClient;
import com.wordplay.unit.console.model.request.SysParamItemRequest;
import com.wordplay.unit.console.model.response.SysParamItemResponse;
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
@Api(tags = "系统参数明细")
@RestController
@RequestMapping("/${unit.console.control.version}/sysparam")
public class SysParamControl {

	@Autowired(required = false)
	private SysParamClient sysParamClient;

	@PostMapping("/save")
	@ApiOperation("保存配置明细项")
	public ResponseResult save(@RequestBody SysParamItemRequest request) {
		return sysParamClient.save(request);
	}

	@PostMapping("/delete")
	@ApiOperation("删除明细项")
	public ResponseResult delete(@RequestParam String code) {
		return sysParamClient.delete(code);
	}

	@PostMapping("/update")
	@ApiOperation("更新配置明细项")
	public ResponseResult updateItem(@RequestBody SysParamItemRequest request) {
		return sysParamClient.update(request);
	}

	@GetMapping("/getitemsbygroupcode")
	@ApiOperation("根据配置组编码查询配置明细项")
	public ResponseResult<List<SysParamItemResponse>> getItemsByGroupCode(@RequestParam String groupCode) {
		return sysParamClient.getItemsByGroupCode(groupCode);
	}

	@GetMapping("/get")
	@ApiOperation("根据编码查询配置明细")
	public ResponseResult<SysParamItemResponse> get(String code) {
		return sysParamClient.get(code);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询配置明细")
	public ResponseResult<Leaf<SysParamItemResponse>> page(SysParamItemRequest request) {
		return sysParamClient.page(request);
	}

}

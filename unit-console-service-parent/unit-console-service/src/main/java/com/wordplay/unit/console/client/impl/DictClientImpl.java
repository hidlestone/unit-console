package com.wordplay.unit.console.client.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wordplay.unit.console.client.DictClient;
import com.wordplay.unit.console.model.request.DictRequest;
import com.wordplay.unit.console.model.response.DictResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sysparam.entity.Dict;
import com.wordplay.unit.starter.sysparam.entity.DictDtl;
import com.wordplay.unit.starter.sysparam.service.DictDtlService;
import com.wordplay.unit.starter.sysparam.service.DictService;
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
@Api(tags = "数据字典")
@RestController
@RequestMapping("/${unit.console.service.version}/dict")
public class DictClientImpl implements DictClient {

	@Autowired
	private DictService dictService;
	@Autowired
	private DictDtlService dictDtlService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存字典项")
	public ResponseResult save(@RequestBody DictRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Dict dict = JSON.parseObject(JSON.toJSONString(request), Dict.class);
		return dictService.saveDict(dict);
	}

	private ResponseResult saveValidate(DictRequest request) {
		if (StringUtils.isBlank(request.getCode())) {
			return ResponseResult.fail("字典编码不能为空");
		}
		if (StringUtils.isBlank(request.getDesc())) {
			return ResponseResult.fail("字典描述不能为空");
		}
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除字典项")
	public ResponseResult delete(@RequestParam(required = true) Long id) {
		Dict dict = dictService.getById(id);
		if (null == dict) {
			return ResponseResult.fail("数据不存在");
		}
		dictService.removeById(id);
		dictDtlService.remove(Wrappers.lambdaQuery(DictDtl.class).eq(DictDtl::getDictId, dict.getId()));
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("删除字典项")
	public ResponseResult update(@RequestBody DictRequest request) {
		ResponseResult validateResp = saveValidate(request);
		if (!validateResp.isSuccess()) {
			return validateResp;
		}
		Dict dict = JSON.parseObject(JSON.toJSONString(request), Dict.class);
		dictService.updateById(dict);
		// 更新明细
		List<DictDtl> dictDtls = dict.getDictDtlList();
		dictDtlService.updateBatchById(dictDtls);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询字典项")
	public ResponseResult<DictResponse> get(@RequestParam(required = true) Long id) {
		Dict dict = dictService.getById(id);
		if (null == dict) {
			return ResponseResult.fail("数据不存在");
		}
		List<DictDtl> dictDtls = dictDtlService.list(Wrappers.lambdaQuery(DictDtl.class).eq(DictDtl::getDictId, dict.getId()));
		dict.setDictDtlList(dictDtls);
		DictResponse response = JSON.parseObject(JSON.toJSONString(dict), DictResponse.class);
		return ResponseResult.success(response);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询字典项")
	public ResponseResult<Leaf<DictResponse>> page(@RequestBody DictRequest request) {
		Dict dict = new Dict();
		BeanUtils.copyProperties(request, dict);
		Leaf<DictResponse> leaf = LeafPageUtil.leafToType(dictService.page(dict), DictResponse.class);
		return ResponseResult.success(leaf);
	}

	@Override
	@GetMapping("/getalldicts")
	@ApiOperation("查询所有字典项")
	public ResponseResult<List<DictResponse>> getAllDicts() {
		List<Dict> dictList = dictService.getAllDicts();
		List<DictResponse> dictResponses = JSON.parseArray(JSON.toJSONString(dictList), DictResponse.class);
		return ResponseResult.success(dictResponses);
	}

}

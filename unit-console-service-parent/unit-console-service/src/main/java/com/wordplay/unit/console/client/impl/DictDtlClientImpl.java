package com.wordplay.unit.console.client.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.wordplay.unit.console.client.DictDtlClient;
import com.wordplay.unit.console.model.request.DictDtlRequest;
import com.wordplay.unit.console.model.response.DictDtlResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.sysparam.entity.Dict;
import com.wordplay.unit.starter.sysparam.entity.DictDtl;
import com.wordplay.unit.starter.sysparam.service.DictDtlService;
import com.wordplay.unit.starter.sysparam.service.DictService;
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

import java.util.List;

/**
 * @author zhuangpf
 */
@Api(tags = "数据字典明细")
@RestController
@RequestMapping("/${unit.console.service.version}/dictdtl")
public class DictDtlClientImpl implements DictDtlClient {

	@Autowired
	private DictDtlService dictDtlService;
	@Autowired
	private DictService dictService;

	@Override
	@PostMapping("/save")
	@ApiOperation("保存字典明细")
	public ResponseResult save(@RequestBody DictDtlRequest request) {
		Dict dict = dictService.getById(request.getDictId());
		if (null == dict) {
			return ResponseResult.fail("字典项不存在");
		}
		DictDtl dictDtl = JSON.parseObject(JSON.toJSONString(request), DictDtl.class);
		dictDtlService.save(dictDtl);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/delete")
	@ApiOperation("删除字典明细")
	public ResponseResult delete(@RequestParam Long id) {
		dictService.removeById(id);
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/update")
	@ApiOperation("更新字典明细")
	public ResponseResult update(@RequestBody DictDtlRequest request) {
		Dict dict = dictService.getById(request.getDictId());
		if (null == dict) {
			return ResponseResult.fail("字典项不存在");
		}
		DictDtl dictDtl = JSON.parseObject(JSON.toJSONString(request), DictDtl.class);
		dictDtlService.updateById(dictDtl);
		return ResponseResult.success();
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("查询字典明细")
	public ResponseResult<DictDtlResponse> get(@RequestParam Long id) {
		DictDtl dictDtl = dictDtlService.getById(id);
		if (null == dictDtl) {
			return ResponseResult.fail("数据不存在");
		}
		DictDtlResponse response = new DictDtlResponse();
		BeanUtils.copyProperties(dictDtl, response);
		return ResponseResult.success(response);
	}

	@Override
	@GetMapping("/getdictdtlsbydictcode")
	@ApiOperation("根据字典编码查询明细")
	public ResponseResult<List<DictDtlResponse>> getDictDtlsByDictCode(@RequestParam String dictCode) {
		List<DictDtl> dictDtlList = dictDtlService.getDictDtlsByDictCode(dictCode);
		if (CollectionUtil.isEmpty(dictDtlList)) {
			return ResponseResult.fail("数据不存在");
		}
		List<DictDtlResponse> dictDtlResponseList = JSON.parseArray(JSON.toJSONString(dictDtlList), DictDtlResponse.class);
		return ResponseResult.success(dictDtlResponseList);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询字典明细")
	public ResponseResult<Leaf<DictDtlResponse>> page(@RequestBody DictDtlRequest request) {
		DictDtl dictDtl = new DictDtl();
		BeanUtils.copyProperties(request, dictDtl);
		Leaf<DictDtlResponse> leaf = LeafPageUtil.leafToType(dictDtlService.page(dictDtl), DictDtlResponse.class);
		return ResponseResult.success(leaf);
	}

}

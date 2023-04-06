package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.DictDtlClient;
import com.wordplay.unit.console.model.request.DictDtlRequest;
import com.wordplay.unit.console.model.response.DictDtlResponse;
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
@Api(tags = "数据字典")
@RestController
@RequestMapping("/${unit.console.control.version}/dictdtl")
public class DictDtlControl {

	@Autowired(required = false)
	private DictDtlClient dictDtlClient;

	@PostMapping("/save")
	@ApiOperation("保存字典明细")
	public ResponseResult save(@RequestBody DictDtlRequest request) {
		return dictDtlClient.save(request);
	}

	@PostMapping("/delete")
	@ApiOperation("删除字典明细")
	public ResponseResult delete(@RequestParam Long id) {
		return dictDtlClient.delete(id);
	}

	@PostMapping("/update")
	@ApiOperation("更新字典明细")
	public ResponseResult update(@RequestBody DictDtlRequest request) {
		return dictDtlClient.update(request);
	}

	@GetMapping("/get")
	@ApiOperation("查询字典明细")
	public ResponseResult<DictDtlResponse> get(@RequestParam Long id) {
		return dictDtlClient.get(id);
	}

	@GetMapping("/getdictdtlsbydictcode")
	@ApiOperation("根据字典编码查询明细")
	public ResponseResult<List<DictDtlResponse>> getDictDtlsByDictCode(@RequestParam String dictCode) {
		return dictDtlClient.getDictDtlsByDictCode(dictCode);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询字典明细")
	public ResponseResult<Leaf<DictDtlResponse>> page(@RequestBody DictDtlRequest request) {
		return dictDtlClient.page(request);
	}

}

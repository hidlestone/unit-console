package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.DictClient;
import com.wordplay.unit.console.model.request.DictRequest;
import com.wordplay.unit.console.model.response.DictResponse;
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
@RequestMapping("/${unit.console.control.version}/dict")
public class DictControl {

	@Autowired(required = false)
	private DictClient dictClient;

	@PostMapping("/save")
	@ApiOperation("保存字典项")
	public ResponseResult save(@RequestBody DictRequest request) {
		return dictClient.save(request);
	}

	@PostMapping("/delete")
	@ApiOperation("删除字典项")
	public ResponseResult delete(@RequestParam Long id) {
		return dictClient.delete(id);
	}

	@PostMapping("/update")
	@ApiOperation("删除字典项")
	public ResponseResult update(@RequestBody DictRequest request) {
		return dictClient.update(request);
	}

	@PostMapping("/get")
	@ApiOperation("查询字典项")
	public ResponseResult<DictResponse> get(@RequestParam Long id) {
		return dictClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询字典项")
	public ResponseResult<Leaf<DictResponse>> page(@RequestBody DictRequest request) {
		return dictClient.page(request);
	}

	@GetMapping("/getalldicts")
	@ApiOperation("查询所有字典项")
	public ResponseResult<List<DictResponse>> getAllDicts() {
		return dictClient.getAllDicts();
	}

}

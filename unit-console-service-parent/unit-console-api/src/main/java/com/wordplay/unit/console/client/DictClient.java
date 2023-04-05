package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.DictRequest;
import com.wordplay.unit.console.model.response.DictResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/dict")
public interface DictClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody DictRequest request);

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam(required = true) Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody DictRequest request);

	@GetMapping("/get")
	ResponseResult<DictResponse> get(@RequestParam(required = true) Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<DictResponse>> page(@RequestBody DictRequest request);

	@GetMapping("/getalldicts")
	ResponseResult<List<DictResponse>> getAllDicts();

}

package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.DictDtlRequest;
import com.wordplay.unit.console.model.response.DictDtlResponse;
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
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/dictdtl")
public interface DictDtlClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody DictDtlRequest request);

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody DictDtlRequest request);

	@GetMapping("/get")
	ResponseResult<DictDtlResponse> get(@RequestParam Long id);

	@GetMapping("/getdictdtlsbydictcode")
	ResponseResult<List<DictDtlResponse>> getDictDtlsByDictCode(@RequestParam String dictCode);

	@PostMapping("/page")
	ResponseResult<Leaf<DictDtlResponse>> page(@RequestBody DictDtlRequest request);

}

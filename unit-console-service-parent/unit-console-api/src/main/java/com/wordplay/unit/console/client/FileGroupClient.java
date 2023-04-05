package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.FileGroupRequest;
import com.wordplay.unit.console.model.response.FileGroupResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件组管理
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/filegroup")
public interface FileGroupClient {

	@GetMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@GetMapping("/get")
	ResponseResult<FileGroupResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<FileGroupResponse>> page(@RequestBody FileGroupRequest request);

}

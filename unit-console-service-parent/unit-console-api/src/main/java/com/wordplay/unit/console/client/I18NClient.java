package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.I18nResourceRequest;
import com.wordplay.unit.console.model.response.I18nResourceResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 使用feign暴露外部接口。路径使用全小写。
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/i18n")
public interface I18NClient {

	@PostMapping("/save")
	ResponseResult save(@RequestBody I18nResourceRequest request);

	@PostMapping("/savebatch")
	ResponseResult saveBatch(@RequestBody List<I18nResourceRequest> reqList);

	@PostMapping("/delete")
	ResponseResult delete(@RequestParam Long id);

	@PostMapping("/update")
	ResponseResult update(@RequestBody I18nResourceRequest request);

	@GetMapping("/get")
	ResponseResult<I18nResourceResponse> get(@RequestParam Long id);

	@GetMapping("/getbyresourcekey")
	ResponseResult<List<I18nResourceResponse>> getByResourceKey(@RequestParam String resourceKey);

	@PostMapping("/page")
	ResponseResult<Leaf<I18nResourceResponse>> page(@RequestBody I18nResourceRequest request);

}

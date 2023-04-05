package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.FileGroupUploadRequest;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 本地文件上传下载
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/localfileprocess")
public interface LocalFileProcessClient {

	@PostMapping("/uploadfilegroup")
	ResponseResult uploadFileGroup(@RequestBody FileGroupUploadRequest request);

	@GetMapping("/downloadfile")
	ResponseResult downloadFile(@RequestParam Long fileInfoId);

}

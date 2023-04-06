package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.LocalFileProcessClient;
import com.wordplay.unit.console.model.request.FileGroupUploadRequest;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "本地文件上传下载")
@RestController
@RequestMapping("/${unit.console.control.version}/localfileprocess")
public class LocalFileProcessControl {

	@Autowired(required = false)
	private LocalFileProcessClient localFileProcessClient;

	@PostMapping("/uploadfilegroup")
	@ApiOperation("上传文件组")
	public ResponseResult uploadFileGroup(FileGroupUploadRequest request) {
		return localFileProcessClient.uploadFileGroup(request);
	}

	@PostMapping("/downloadfile")
	@ApiOperation("下载文件")
	public ResponseResult downloadFile(Long fileInfoId) {
		return localFileProcessClient.downloadFile(fileInfoId);
	}

}

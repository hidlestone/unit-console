package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.LocalFileProcessClient;
import com.wordplay.unit.console.model.request.FileGroupUploadRequest;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.file.model.FileGroupUploadDto;
import com.wordplay.unit.starter.file.service.FileProcessService;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuangpf
 */
@Api(tags = "本地文件处理")
@RestController
@RequestMapping("/${unit.console.service.version}/localfileprocess")
public class LocalFileProcessClientImpl implements LocalFileProcessClient {

	@Autowired
	private FileProcessService fileProcessService;

	@Override
	@PostMapping("/uploadfilegroup")
	@ApiOperation("上传文件组")
	public ResponseResult uploadFileGroup(@RequestBody FileGroupUploadRequest request) {
		FileGroupUploadDto dto = new FileGroupUploadDto();
		BeanUtils.copyProperties(request, dto);
		return fileProcessService.uploadFileGroup(dto);
	}

	@Override
	@GetMapping("/downloadfile")
	@ApiOperation("下载文件")
	public ResponseResult downloadFile(@RequestParam Long fileInfoId) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return fileProcessService.downloadFile(fileInfoId, response);
	}

}

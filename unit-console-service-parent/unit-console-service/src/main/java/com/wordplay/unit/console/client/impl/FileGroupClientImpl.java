package com.wordplay.unit.console.client.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wordplay.unit.console.client.FileGroupClient;
import com.wordplay.unit.console.model.request.FileGroupRequest;
import com.wordplay.unit.console.model.response.FileGroupResponse;
import com.wordplay.unit.console.model.response.FileInfoResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.data.mp.util.LeafPageUtil;
import com.wordplay.unit.starter.file.entity.FileGroup;
import com.wordplay.unit.starter.file.entity.FileInfo;
import com.wordplay.unit.starter.file.service.FileGroupService;
import com.wordplay.unit.starter.file.service.FileInfoService;
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
@Api(tags = "文件组管理")
@RestController
@RequestMapping("/${unit.console.service.version}/filegroup")
public class FileGroupClientImpl implements FileGroupClient {

	@Autowired
	private FileGroupService fileGroupService;
	@Autowired
	private FileInfoService fileInfoService;

	@Override
	@GetMapping("/delete")
	@ApiOperation("删除文件组及明细")
	public ResponseResult delete(@RequestParam Long id) {
		FileGroup fileGroup = fileGroupService.getById(id);
		if (null == fileGroup) {
			return ResponseResult.fail("数据不存在");
		}
		fileGroupService.removeById(id);
		fileInfoService.remove(Wrappers.lambdaQuery(FileInfo.class).eq(FileInfo::getFileGroupId, id));
		return ResponseResult.success();
	}

	@Override
	@PostMapping("/get")
	@ApiOperation("查询文件组及明细")
	public ResponseResult<FileGroupResponse> get(@RequestParam Long id) {
		FileGroup fileGroup = fileGroupService.getById(id);
		if (null == fileGroup) {
			return ResponseResult.fail("数据不存在");
		}
		List<FileInfo> fileInfoList = fileInfoService.list(Wrappers.lambdaQuery(FileInfo.class).eq(FileInfo::getFileGroupId, id));
		FileGroupResponse fileGroupResponse = new FileGroupResponse();
		BeanUtils.copyProperties(fileGroup, fileGroupResponse);
		List<FileInfoResponse> fileInfoResponseList = JSON.parseArray(JSON.toJSONString(fileInfoList), FileInfoResponse.class);
		fileGroupResponse.setFileInfoResponseList(fileInfoResponseList);
		return ResponseResult.success(fileGroupResponse);
	}

	@Override
	@PostMapping("/page")
	@ApiOperation("分页查询文件组")
	public ResponseResult<Leaf<FileGroupResponse>> page(@RequestBody FileGroupRequest request) {
		FileGroup fileGroup = new FileGroup();
		BeanUtils.copyProperties(request, fileGroup);
		Leaf leaf = LeafPageUtil.leafToType(fileGroupService.page(fileGroup), FileGroupResponse.class);
		return ResponseResult.success(leaf);
	}

}

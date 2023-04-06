package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.FileGroupClient;
import com.wordplay.unit.console.model.request.FileGroupRequest;
import com.wordplay.unit.console.model.response.FileGroupResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "文件组管理")
@RestController
@RequestMapping("/${unit.console.control.version}/filegroup")
public class FileGroupControl {

	@Autowired(required = false)
	private FileGroupClient fileGroupClient;

	@PostMapping("/delete")
	@ApiOperation("删除文件组及明细")
	public ResponseResult delete(@RequestParam Long id) {
		return fileGroupClient.delete(id);
	}

	@PostMapping("/get")
	@ApiOperation("查询文件组及明细")
	public ResponseResult<FileGroupResponse> get(@RequestParam Long id) {
		return fileGroupClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询文件组")
	public ResponseResult<Leaf<FileGroupResponse>> list(@RequestBody FileGroupRequest request) {
		return fileGroupClient.page(request);
	}

}

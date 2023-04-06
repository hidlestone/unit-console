package com.wordplay.unit.console.control;

import com.wordplay.unit.console.client.UserClient;
import com.wordplay.unit.console.model.request.UserRequest;
import com.wordplay.unit.console.model.response.UserDtlInfoResponse;
import com.wordplay.unit.console.model.response.UserResponse;
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

/**
 * @author zhuangpf
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/${unit.console.control.version}/user")
public class UserControl {

	@Autowired(required = false)
	private UserClient userClient;

	@GetMapping("/get")
	@ApiOperation("查询用户")
	public ResponseResult<UserResponse> get(@RequestParam Long id) {
		return userClient.get(id);
	}

	@PostMapping("/page")
	@ApiOperation("分页查询用户")
	public ResponseResult<Leaf<UserResponse>> page(@RequestBody UserRequest request) {
		return userClient.page(request);
	}

	@GetMapping("/getuserinfo")
	@ApiOperation("根据accessToken获取用户信息")
	public ResponseResult<UserDtlInfoResponse> getUserInfo() {
		return userClient.getUserInfo();
	}

	@GetMapping("/logout")
	@ApiOperation("用户登出")
	public ResponseResult logout(@RequestParam String accesstoken) {
		return userClient.logout(accesstoken);
	}

}

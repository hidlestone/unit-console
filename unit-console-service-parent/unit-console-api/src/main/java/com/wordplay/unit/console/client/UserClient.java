package com.wordplay.unit.console.client;

import com.wordplay.unit.console.model.request.UserRequest;
import com.wordplay.unit.console.model.response.UserDtlInfoResponse;
import com.wordplay.unit.console.model.response.UserResponse;
import com.wordplay.unit.starter.api.model.Leaf;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户管理
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/user")
public interface UserClient {

	@GetMapping("/get")
	ResponseResult<UserResponse> get(@RequestParam Long id);

	@PostMapping("/page")
	ResponseResult<Leaf<UserResponse>> page(@RequestBody UserRequest request);

	@GetMapping("/getuserinfo")
	ResponseResult<UserDtlInfoResponse> getUserInfo();

	@GetMapping("/logout")
	ResponseResult logout(@RequestParam String accesstoken);

}

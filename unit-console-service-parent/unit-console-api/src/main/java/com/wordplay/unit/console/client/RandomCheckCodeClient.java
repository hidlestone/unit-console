package com.wordplay.unit.console.client;

import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登录页随机校验码
 *
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/randomcheckcode")
public interface RandomCheckCodeClient {

	@GetMapping("/getrandomcheckcode")
	ResponseResult getRandomCheckCode();

}

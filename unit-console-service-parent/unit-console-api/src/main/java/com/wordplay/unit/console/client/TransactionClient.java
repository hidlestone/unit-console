package com.wordplay.unit.console.client;

import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhuangpf
 */
@FeignClient(name = "${unit.console.service.name}${unit.console.service.version:}/${unit.console.service.version}/transaction")
public interface TransactionClient {

	@PostMapping("/list")
	ResponseResult list();

}

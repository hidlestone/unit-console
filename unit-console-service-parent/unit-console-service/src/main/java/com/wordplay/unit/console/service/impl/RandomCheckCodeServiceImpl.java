package com.wordplay.unit.console.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wordplay.unit.console.service.RandomCheckCodeService;
import com.wordplay.unit.starter.api.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @author zhuangpf
 */
@Service
public class RandomCheckCodeServiceImpl implements RandomCheckCodeService {

	@Override
	public ResponseResult getRandomCheckCode() {
		String randomCheckCode = RandomUtil.randomString(4);
		return ResponseResult.success(randomCheckCode);
	}

}

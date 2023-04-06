package com.wordplay.unit.console.service;

import com.wordplay.unit.console.model.request.VerifyCodeSenderRequest;
import com.wordplay.unit.starter.api.response.ResponseResult;

/**
 * @author zhuangpf
 */
public interface VerifyCodeService {

	ResponseResult sendVerifyCode(VerifyCodeSenderRequest request);

}

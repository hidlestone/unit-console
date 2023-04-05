package com.wordplay.unit.console.client.impl;

import com.wordplay.unit.console.client.LoginClient;
import com.wordplay.unit.console.model.request.AccountPwdLoginRequest;
import com.wordplay.unit.console.model.request.QRCodeLoginRequest;
import com.wordplay.unit.console.model.request.VerificationCodeLoginRequest;
import com.wordplay.unit.console.model.response.LoginSuccessResponse;
import com.wordplay.unit.starter.api.response.ResponseResult;
import com.wordplay.unit.starter.cache.redis.util.RedisUtil;
import com.wordplay.unit.starter.rbac.constant.RbacStarterConstant;
import com.wordplay.unit.starter.rbac.entity.User;
import com.wordplay.unit.starter.rbac.model.TokenTypeEnum;
import com.wordplay.unit.starter.rbac.service.RoleService;
import com.wordplay.unit.starter.rbac.util.RequestContexUtil;
import com.wordplay.unit.starter.shiro.model.AccountLoginToken;
import com.wordplay.unit.starter.shiro.model.LoginTypeEnum;
import com.wordplay.unit.starter.shiro.util.JWTUtil;
import com.wordplay.unit.starter.util.base.EncryptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangpf
 */
@Api(tags = "用户登录")
@RestController
@RequestMapping("/${unit.console.service.version}/login")
public class LoginClientImpl implements LoginClient {

	@Autowired
	private JWTUtil jwtUtill;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private RoleService roleService;

	@Override
	@PostMapping("/loginbyaccount")
	@ApiOperation("账号密码登录")
	public ResponseResult<LoginSuccessResponse> loginByAccount(@RequestBody AccountPwdLoginRequest request) {
		if ((StringUtils.isBlank(request.getAccount()) && StringUtils.isBlank(request.getTel())) || StringUtils.isBlank(request.getPassword())) {
			return ResponseResult.fail("账号密码不能为空");
		}
		// 执行登录
		Subject currentUser = SecurityUtils.getSubject();
		String md5pwd = EncryptionUtil.encryptMD5(request.getPassword());
		String account = StringUtils.isNoneBlank(request.getAccount()) ? request.getAccount() : request.getTel();
		try {
			AccountLoginToken accountLoginToken = new AccountLoginToken(account, md5pwd, LoginTypeEnum.PASSWORD, null, null);
			currentUser.login(accountLoginToken);
		} catch (UnknownAccountException e) {
			return ResponseResult.fail(e.getMessage());
		} catch (LockedAccountException e) {
			return ResponseResult.fail(e.getMessage());
		}
		User curUser = (User) currentUser.getPrincipal();
		String accesstoken = jwtUtill.createToken(curUser, TokenTypeEnum.ACCESSTOKEN);
		String refreshtoken = jwtUtill.createToken(curUser, TokenTypeEnum.REFRESHTOKEN);
		redisUtil.set(RbacStarterConstant.CACHE_KEY_ACCESSTOKEN + curUser.getId(), accesstoken);
		redisUtil.set(RbacStarterConstant.CACHE_KEY_REFRESHTOKEN + curUser.getId(), refreshtoken);
		// 返回token信息
		LoginSuccessResponse resp = new LoginSuccessResponse();
		resp.setAccesstoken(accesstoken);
		resp.setRefreshtoken(refreshtoken);
		// 在返回的请求头中设置token信息
		RequestContexUtil.setTokenHeader(accesstoken, refreshtoken);
		return ResponseResult.success(resp);
	}

	@Override
	@PostMapping("/loginbyverificationcode")
	@ApiOperation("账号验证码登录")
	public ResponseResult loginByVerificationCode(VerificationCodeLoginRequest request) {
		return null;
	}

	@Override
	@PostMapping("/loginbyqrcodelogin")
	@ApiOperation("扫二维码登录")
	public ResponseResult loginByQRCodeLogin(QRCodeLoginRequest request) {
		return null;
	}

}

package com.wordplay.unit.console.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhuangpf
 */
@Getter
@Setter
@ApiModel("用户登出请求参数")
public class LogoutRequest implements Serializable {

	private static final long serialVersionUID = -469351639513773140L;

	@ApiModelProperty("访问token")
	private String accesstoken;

	@ApiModelProperty("刷新token")
	private String refreshtoken;

}

package com.wordplay.unit.console.model.request;

import com.wordplay.unit.console.model.response.ActGroupResponse;
import com.wordplay.unit.console.model.response.ActUserResponse;
import com.wordplay.unit.starter.api.request.BaseEntityRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuangpf
 */
@Getter
@Setter
@ApiModel("保存用户&用户组请求参数")
public class SaveUserAndGroupRequest extends BaseEntityRequest {

	private static final long serialVersionUID = 8444116516188293125L;

	@ApiModelProperty("用户")
	private ActUserResponse user;

	@ApiModelProperty("用户组")
	private ActGroupResponse group;

}

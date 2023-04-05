package com.wordplay.unit.console.model.request;

import com.wordplay.unit.console.model.enums.SexEnum;
import com.wordplay.unit.starter.api.model.StatusEnum;
import com.wordplay.unit.starter.api.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zhuangpf
 */
@Getter
@Setter
@ApiModel("用户查询请求参数")
public class UserRequest extends BasePageRequest {

	private static final long serialVersionUID = 254614521307599409L;

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("用户账号")
	private String account;

	@ApiModelProperty("用户昵称")
	private String username;

	@ApiModelProperty("头像URL")
	private String avatar;

	@ApiModelProperty("电话号码")
	private String tel;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("身份证号")
	private String idCard;

	@ApiModelProperty("真实姓名")
	private String name;

	@ApiModelProperty("生日开始日期")
	private Date birthdayStart;

	@ApiModelProperty("生日结束日期")
	private Date birthdayEnd;

	@ApiModelProperty("地址")
	private String address;

	@ApiModelProperty("性别")
	private SexEnum sex;

	@ApiModelProperty("状态")
	private StatusEnum status;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("用户来源")
	private Byte sourceType;

	@ApiModelProperty("最后登录时间")
	private Date lastLoginTime;

}

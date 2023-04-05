package com.wordplay.unit.console.model.request;

import com.wordplay.unit.starter.api.model.StatusEnum;
import com.wordplay.unit.starter.api.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuangpf
 */
@Getter
@Setter
@ApiModel("系统参数组查询请求参数")
public class SysParamGroupQueryRequest extends BasePageRequest {

	private static final long serialVersionUID = 2978597290682806206L;

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("系统参数组编码")
	private String code;

	@ApiModelProperty("系统参数组描述")
	private String desc;

	@ApiModelProperty("是否启用：0-停用，1-启用")
	private StatusEnum status;

}

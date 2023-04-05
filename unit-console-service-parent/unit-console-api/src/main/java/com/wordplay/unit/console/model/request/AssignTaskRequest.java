package com.wordplay.unit.console.model.request;

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
@ApiModel("指派任务请求参数")
public class AssignTaskRequest extends BaseEntityRequest {

	private static final long serialVersionUID = -5520030032350951067L;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "审批人")
	private String assignee;

}

package com.wordplay.unit.console.model.response;

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
@ApiModel("企微通讯录同步日志")
public class WxWorkSyncLogResponse extends BasePageRequest {

	private static final long serialVersionUID = -6748528556790720337L;
	
	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	private Long id;

	/**
	 * 所属配置id
	 */
	@ApiModelProperty("所属配置id")
	private Long configId;

	/**
	 * 同步结果编码
	 */
	@ApiModelProperty("同步结果编码")
	private String errcode;

	/**
	 * 同步结果信息
	 */
	@ApiModelProperty("同步结果信息")
	private String errmsg;

}

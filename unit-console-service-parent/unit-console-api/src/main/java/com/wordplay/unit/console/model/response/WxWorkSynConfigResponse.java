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
@ApiModel("企微通讯录同步配置")
public class WxWorkSynConfigResponse extends BasePageRequest {

	private static final long serialVersionUID = -7563080854189476883L;
	
	/**
	 * 配置id
	 */
	@ApiModelProperty("配置id")
	private Long id;

	/**
	 * 同步类型
	 */
	@ApiModelProperty("同步类型")
	private Byte synType;

	/**
	 * 同步策略
	 */
	@ApiModelProperty("同步策略")
	private Byte synStrategy;

	/**
	 * 是否全量同步过
	 */
	@ApiModelProperty("是否全量同步过")
	private Byte fullSynced;

}

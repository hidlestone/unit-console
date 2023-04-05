package com.wordplay.unit.console.model.response;

import com.wordplay.unit.starter.api.response.BaseEntityResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author zhuangpf
 */
@Getter
@Setter
@ApiModel("接口权限响应参数")
public class ProcessDefinitionResponse extends BaseEntityResponse {

	private static final long serialVersionUID = 7364461510467673512L;

	protected String name;
	protected String description;
	protected String key;
	protected int version;
	protected String category;
	protected String deploymentId;
	protected String resourceName;
	protected String tenantId = "";
	protected Integer historyLevel;
	protected String diagramResourceName;
	protected boolean isGraphicalNotationDefined;
	protected Map<String, Object> variables;
	protected boolean hasStartFormKey;
	protected int suspensionState;
	protected boolean isIdentityLinksInitialized;
	//	protected List<IdentityLinkEntity> definitionIdentityLinkEntities;
	//	protected IOSpecification ioSpecification;
	protected String engineVersion;

}

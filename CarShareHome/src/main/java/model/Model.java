package model;

import java.io.Serializable;

public class Model implements Serializable{
	private String modelId;
	private String modelName;
	
	public Model() {
		super();
	}


	public Model(String modelId, String modelName) {
		super();
		this.modelId = modelId;
		this.modelName = modelName;
	}


	public String getModelId() {
		return modelId;
	}


	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
}

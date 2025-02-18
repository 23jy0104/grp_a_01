package model;

import java.io.Serializable;

public class CarData implements Serializable{
	private String carCode;
	private String modelYear;
	private String carNumber;
	private Maker makerId;
	private Model modelId;
	private Station stationId;
	private Maker maker;
	private Model model;
	
	public CarData() {
		super();
	}
	
	public CarData(String carCode) {
        this.carCode = carCode;
    }

	public CarData(String carCode, String modelYear, String carNumber, Maker makerId, Model modelId,
			Station stationId) {
		super();
		this.carCode = carCode;
		this.modelYear = modelYear;
		this.carNumber = carNumber;
		this.makerId = makerId;
		this.modelId = modelId;
		this.stationId = stationId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Maker getMakerId() {
		return makerId;
	}

	public void setMakerId(Maker makerId) {
		this.makerId = makerId;
	}

	public Model getModelId() {
		return modelId;
	}

	public void setModelId(Model modelId) {
		this.modelId = modelId;
	}

	public Station getStationId() {
		return stationId;
	}

	public void setStationId(Station stationId) {
		this.stationId = stationId;
	}

	public Maker getMaker() {
		return maker;
	}

	public void setMaker(Maker maker) {
		this.maker = maker;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	
}
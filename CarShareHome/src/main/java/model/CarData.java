package model;

import java.io.Serializable;

public class CarData implements Serializable {
    private String carCode;
    private String modelYear;
    private String modelName; // model_nameを追加
    private String carNumber;
    private Maker makerId;
    private Model modelId;
    private Station stationId;
    private Maker maker;
    private Model model;
    private String carImage; // 画像ファイル名を追加
    private String status;
    
    public CarData() {
        super();
    }

    public CarData(String carCode) {
        this.carCode = carCode;
    }

    public CarData(String carCode, String modelYear, String modelName, String carNumber, Maker makerId, Model modelId, Station stationId, String carImage) {
        super();
        this.carCode = carCode;
        this.modelYear = modelYear;
        this.modelName = modelName; // model_nameを設定
        this.carNumber = carNumber;
        this.makerId = makerId;
        this.modelId = modelId;
        this.stationId = stationId;
        this.carImage = carImage; // 画像ファイル名を設定
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

    public String getModelName() { // model_nameのゲッター
        return modelName;
    }

    public void setModelName(String modelName) { // model_nameのセッター
        this.modelName = modelName;
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

    public String getCarImage() { // 画像取得メソッドを追加
        return carImage;
    }

    public void setCarImage(String carImage) { // 画像設定メソッドを追加
        this.carImage = carImage;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
    
    
}

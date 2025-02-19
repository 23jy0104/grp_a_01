package model;

public class CarInfo {
    private String makerName;
    private String number;
    private String modelName;
    private String modelYear;

    public CarInfo(String makerName, String number, String modelName, String modelYear) {
        this.makerName = makerName;
        this.number = number;
        this.modelName = modelName;
        this.modelYear = modelYear;
    }

    public String getMakerName() {
        return makerName;
    }

    public String getNumber() {
        return number;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelYear() {
        return modelYear;
    }
}

package main.JSONMessage;

public class InterfaceMessage {

    private Integer contentCode;
    private Integer origin;
    private Integer firstValue;
    private Integer secondValue;
    private String originFootprint;

    public Integer getContentCode() {
        return contentCode;
    }

    public void setContentCode(Integer contentCode) {
        this.contentCode = contentCode;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getOriginFootprint() {
        return originFootprint;
    }

    public void setOriginFootprint(String originFootprint) {
        this.originFootprint = originFootprint;
    }

    public Integer getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(Integer firstValue) {
        this.firstValue = firstValue;
    }

    public Integer getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Integer secondValue) {
        this.secondValue = secondValue;
    }
}

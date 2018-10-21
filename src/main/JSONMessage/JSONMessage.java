package main.JSONMessage;

public class JSONMessage {

    private Integer contentCode;
    private Integer transmitterType;
    private String folio;
    private String originFootprint;
    private String message;

//    public tests.JSONMessage() {
//        contentCode = EnumContentCode.NOTDECLARED;
//        transmitterType = EnumType.NOTDECLARED;
//        folio = "";
//        originFootprint = "";
//        message = "";
//    }

    public void setTransmitterType(Integer transmitterType) {
        this.transmitterType = transmitterType;
    }

    public Integer getTransmitterType() {
        return transmitterType;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public void setOriginFootprint(String originFootprint) {
        this.originFootprint = originFootprint;
    }

    public String getOriginFootprint() {
        return originFootprint;
    }

    public void setContentCode(Integer contentCode) {
        this.contentCode = contentCode;
    }

    public Integer getContentCode() {
        return contentCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

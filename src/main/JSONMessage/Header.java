package main.JSONMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.Enum.EnumContentCode;
import main.Enum.EnumType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Header {

    private Integer contentCode;
    private Integer transmitterType;
    private String folio;
    private String originFootprint;

    Header() {
        contentCode = EnumContentCode.NOTDECLARED;
        transmitterType = EnumType.NOTDECLARED;
        folio = "";
        originFootprint = "";
    }

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

}

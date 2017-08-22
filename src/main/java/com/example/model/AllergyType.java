package com.example.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "allergy_types")
public class AllergyType {

    @Id
    //@Column(name = "ALLERGY_TYPE_CD")
    private String allergyTypeCd;

    //@Column(name = "DESCR")
    private String descr;

    //@Column(name = "PARAM_CD")
    private String paramCd;

    public String getAllergyTypeCd() {
        return allergyTypeCd;
    }

    public void setAllergyTypeCd(String allergyTypeCd) {
        this.allergyTypeCd = allergyTypeCd;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getParamCd() {
        return paramCd;
    }

    public void setParamCd(String paramCd) {
        this.paramCd = paramCd;
    }


}
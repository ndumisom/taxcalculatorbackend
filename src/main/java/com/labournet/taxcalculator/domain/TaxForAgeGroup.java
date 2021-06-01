package com.labournet.taxcalculator.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TaxForAgeGroup {


    private BigDecimal primary;

    private BigDecimal secondary;

    private BigDecimal tertiary;

    enum TaxAgeGroup {
        PRIMARY,SECONDARY,TERTIARY
    }

    TaxAgeGroup determineTaxAgeGroup(int age){
        if(age < 65 ){
            return TaxAgeGroup.PRIMARY;
        }else if(age >= 75){
            return TaxAgeGroup.TERTIARY;
        }else {
            return TaxAgeGroup.SECONDARY;
        }
    }
}

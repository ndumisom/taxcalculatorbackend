package com.labournet.taxcalculator.domain;

import static com.labournet.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.PRIMARY;
import static com.labournet.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.SECONDARY;

import java.math.BigDecimal;

public class TaxRebate extends TaxForAgeGroup {
	
	 BigDecimal calculateTaxRebates(final int age){
	        final TaxAgeGroup taxAgeGroup = determineTaxAgeGroup(age);
	        if(PRIMARY == taxAgeGroup){
	            return getPrimary();
	        }else if (SECONDARY == taxAgeGroup){
	            return getPrimary().add(getSecondary());
	        }else {
	            return getPrimary().add(getSecondary()).add(getTertiary());
	        }
	    }
}

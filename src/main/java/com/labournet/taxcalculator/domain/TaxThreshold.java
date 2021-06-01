package com.labournet.taxcalculator.domain;

import java.math.BigDecimal;

import static com.labournet.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.PRIMARY;
import static com.labournet.taxcalculator.domain.TaxForAgeGroup.TaxAgeGroup.SECONDARY;

public class TaxThreshold extends TaxForAgeGroup{
	
	 BigDecimal getTaxThreshold(int age){
	        final TaxAgeGroup taxAgeGroup = determineTaxAgeGroup(age);
	        if(PRIMARY == taxAgeGroup){
	            return getPrimary();
	        }else if (SECONDARY == taxAgeGroup){
	            return getSecondary();
	        }else {
	            return getTertiary();
	        }

	    }
}

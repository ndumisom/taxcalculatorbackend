package com.labournet.taxcalculator.service;

import java.util.Optional;

import com.labournet.taxcalculator.domain.TaxTable;
import com.labournet.taxcalculator.exception.TaxTableNotFoundException;
import org.springframework.stereotype.Service;

import com.labournet.taxcalculator.config.TaxCalculatorConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxTableService {
	
    private final TaxCalculatorConfig taxCalculatorConfig;

    public TaxTable getTaxTable(final int taxYear) throws Exception{
    	
    	final Optional<TaxTable> taxTable =  Optional.ofNullable(taxCalculatorConfig.getSaTaxTable().get(taxYear));
    	
    	if(!taxTable.isPresent()) {
            String message = "Could not find tax table for tax year : ";
            Object[] args = { taxYear };
    		throw new TaxTableNotFoundException(message, args);
    	}
    	
        return taxTable.get();
    }
}

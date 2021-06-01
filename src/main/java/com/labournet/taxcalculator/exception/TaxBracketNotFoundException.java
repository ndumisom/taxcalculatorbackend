package com.labournet.taxcalculator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaxBracketNotFoundException extends Exception{

	private String message;
	private Object[] args;
}

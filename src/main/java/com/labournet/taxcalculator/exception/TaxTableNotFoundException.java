package com.labournet.taxcalculator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaxTableNotFoundException extends Exception{

	private String message;
	private Object[] args;
}

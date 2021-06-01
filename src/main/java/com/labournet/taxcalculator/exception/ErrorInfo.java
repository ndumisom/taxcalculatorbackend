package com.labournet.taxcalculator.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorInfo {

	private String url;
	private String message;
	
	public ErrorInfo(String message) {
		this.message = message;
	}
}

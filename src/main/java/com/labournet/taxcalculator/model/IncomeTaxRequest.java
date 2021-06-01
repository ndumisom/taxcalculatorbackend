package com.labournet.taxcalculator.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeTaxRequest {

	@Min(18)
	private int age;
	@NotNull
	private BigDecimal earnings;
	private int medicalAidMembers;
	@NotNull
	private int year;
	@NotNull
	private Period period;
}

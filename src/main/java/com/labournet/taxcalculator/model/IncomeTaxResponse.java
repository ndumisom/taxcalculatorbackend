package com.labournet.taxcalculator.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeTaxResponse {

	private BigDecimal payAsYouEarnBeforeTaxCredit;
	private BigDecimal taxCredits;
	private BigDecimal payAsYouEarnAfterTaxCredit;
	private BigDecimal netCash;
}

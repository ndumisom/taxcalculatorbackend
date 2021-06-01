package com.labournet.taxcalculator.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "min")
@AllArgsConstructor
@NoArgsConstructor
public class TaxBracket {

	@NotNull
    private BigDecimal min;
    private BigDecimal max;
    @Min(1)
    @Max(100)
    private double percentage;
    @NotNull
    private BigDecimal amount;

    private boolean isEqualToOrLessThanTaxBracketMaximum(final BigDecimal totalTaxableIncome) {
        return totalTaxableIncome.compareTo(this.max) < 1;
    }

    private boolean isEqualToOrGreaterThanTaxBracketMinimum(final BigDecimal totalTaxableIncome) {
        return totalTaxableIncome.compareTo(this.min) > -1;
    }

    boolean isInTaxBracket(final BigDecimal totalTaxableIncome) {
        if (this.max == null) {
            return isEqualToOrGreaterThanTaxBracketMinimum(totalTaxableIncome);
        }
        return isEqualToOrGreaterThanTaxBracketMinimum(totalTaxableIncome)  && isEqualToOrLessThanTaxBracketMaximum(totalTaxableIncome);
    }

}

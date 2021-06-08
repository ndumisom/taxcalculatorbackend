package com.labournet.taxcalculator.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.labournet.taxcalculator.exception.TaxBracketNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxTable {

    @NotNull
    private Set<TaxBracket> taxBrackets = new HashSet<>();
    @NotNull
    private TaxRebate taxRebates;
    @NotNull
    private TaxThreshold taxThresholds;
    @NotNull
    private MedicalAidTaxCredits medicalAidTaxCredits;

    public BigDecimal calculateAnnualIncomeTax(final int age, BigDecimal totalAnnualTaxableIncome) throws Exception {
        if (isIncomeLessThanTaxThresholds(totalAnnualTaxableIncome, age)) {
            return BigDecimal.ZERO;
        }
        final Optional<TaxBracket> optionalTaxBracket = taxBrackets.stream().filter(bracket -> bracket.isInTaxBracket(totalAnnualTaxableIncome)).findAny();
        if (optionalTaxBracket.isPresent()) {
            final TaxBracket taxBracket = optionalTaxBracket.get();
            BigDecimal incomeTax = totalAnnualTaxableIncome.subtract(taxBracket.getMin()).add(BigDecimal.ONE);
            incomeTax = incomeTax.multiply(BigDecimal.valueOf(taxBracket.getPercentage() / 100));
            incomeTax = incomeTax.add(taxBracket.getAmount());
            incomeTax = incomeTax.subtract(taxRebates.calculateTaxRebates(age));
            return incomeTax;
        }
        
        String message = "Could find a tax bracket for amount : ";
        Object[] args = { totalAnnualTaxableIncome.toString() };
        throw new TaxBracketNotFoundException(message, args);
    }

    public BigDecimal calculateAnnualTaxCredits(int medicalAidMembers) {
        return medicalAidTaxCredits.calculateAnnualTaxCredits(medicalAidMembers);
    }

    private boolean isIncomeLessThanTaxThresholds(final BigDecimal totalTaxableIncome, final int age) {
        return totalTaxableIncome.compareTo(taxThresholds.getTaxThreshold(age)) < 1;
    }
}

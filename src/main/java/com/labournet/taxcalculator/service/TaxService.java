package com.labournet.taxcalculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.labournet.taxcalculator.domain.TaxTable;
import com.labournet.taxcalculator.model.IncomeTaxRequest;
import com.labournet.taxcalculator.model.IncomeTaxResponse;
import com.labournet.taxcalculator.model.Period;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxService {

	 private final TaxTableService taxTableService;

	    public IncomeTaxResponse calculateIncomeTax(IncomeTaxRequest incomeTaxRequest) throws Exception {
	        final TaxTable taxTable = taxTableService.getTaxTable(incomeTaxRequest.getYear());
	        final BigDecimal payAsYouEarnBeforeTax = calculatePayAsYouEarnBeforeTax(incomeTaxRequest.getAge(), incomeTaxRequest.getEarnings(), taxTable, incomeTaxRequest.getPeriod());
	        final BigDecimal taxCredits = calculateTaxCredits(taxTable, incomeTaxRequest.getMedicalAidMembers(), incomeTaxRequest.getPeriod());
	        final BigDecimal payAsYouEarnAfterTaxCredits = calculatePayAsYouEarnAfterTaxCredits(payAsYouEarnBeforeTax, taxCredits);
	        final BigDecimal netCash = incomeTaxRequest.getEarnings().subtract(payAsYouEarnAfterTaxCredits);

	        final IncomeTaxResponse incomeTaxResult = IncomeTaxResponse.builder()
	                .payAsYouEarnBeforeTaxCredit(payAsYouEarnBeforeTax)
	                .taxCredits(taxCredits)
	                .payAsYouEarnAfterTaxCredit(payAsYouEarnAfterTaxCredits)
	                .netCash(netCash)
	                .build();
	        
	        return incomeTaxResult;
	    }

	    private BigDecimal calculateTaxCredits(final TaxTable taxTable, final int medicalAidMembers, final Period period) {
	        BigDecimal annualTaxCredits = taxTable.calculateAnnualTaxCredits(medicalAidMembers);
	        return getPeriodValue(period, annualTaxCredits);
	    }

	    private BigDecimal calculatePayAsYouEarnBeforeTax(final int age, final BigDecimal totalTaxableEarnings, final TaxTable taxTable, Period period) throws Exception {
	        final BigDecimal annualPayAsYouEarTax;
	        if (period == Period.ANNUALLY) {
	            annualPayAsYouEarTax =  taxTable.calculateAnnualIncomeTax(age, totalTaxableEarnings);
	        } else {
	            annualPayAsYouEarTax = taxTable.calculateAnnualIncomeTax(age, totalTaxableEarnings.multiply(BigDecimal.valueOf(12)));
	        }
	        return getPeriodValue(period, annualPayAsYouEarTax);
	    }

	    private BigDecimal getPeriodValue(Period period, BigDecimal annualAmount) {
	        return period == Period.ANNUALLY ? annualAmount : annualAmount.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
	    }

	    private BigDecimal calculatePayAsYouEarnAfterTaxCredits(final BigDecimal payAsYouEarnBeforeTaxCredit, final BigDecimal taxCredits) {
	        final boolean isPayAsYouEarnGreaterThanTaxCredits = payAsYouEarnBeforeTaxCredit.compareTo(taxCredits) > 0;
	        return isPayAsYouEarnGreaterThanTaxCredits ? payAsYouEarnBeforeTaxCredit.subtract(taxCredits) : BigDecimal.ZERO;
	    }
}

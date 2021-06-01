package com.labournet.taxcalculator.controller;

import javax.validation.Valid;

import com.labournet.taxcalculator.exception.TaxTableNotFoundException;
import com.labournet.taxcalculator.model.IncomeTaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labournet.taxcalculator.exception.TaxBracketNotFoundException;
import com.labournet.taxcalculator.model.IncomeTaxRequest;
import com.labournet.taxcalculator.service.TaxService;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/tax")
@CrossOrigin
@Slf4j
public class TaxCalculatorController {

	@Autowired
	private TaxService taxService;

	@PostMapping("/income-tax")
	public ResponseEntity<IncomeTaxResponse> getIncomeTax(@Valid @RequestBody IncomeTaxRequest incomeTaxRequest)
			throws Exception {

		log.info("tax-service getMinimumCoinsNeeded() invoked:{} ");

		var taxResults = new IncomeTaxResponse();

		try {
			taxResults = taxService.calculateIncomeTax(incomeTaxRequest);
		} catch (TaxTableNotFoundException | TaxBracketNotFoundException ex) {
			log.error("Exception raised getIncomeTax REST Call {0}", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("Exception raised getIncomeTax REST Call {0}", ex);
			throw ex;
		}

		return new ResponseEntity<>(taxResults, HttpStatus.OK);
	}
}

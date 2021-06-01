package com.labournet.taxcalculator.config;

import java.util.Map;

import com.labournet.taxcalculator.domain.TaxTable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "tax")
@Data
public class TaxCalculatorConfig {
	private Map< Integer, TaxTable> saTaxTable;
}

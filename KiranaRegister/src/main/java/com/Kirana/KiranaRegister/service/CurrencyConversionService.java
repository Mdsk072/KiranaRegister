package com.Kirana.KiranaRegister.service;

import com.Kirana.KiranaRegister.ExceptionHandler.CurrencyConversionException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyConversionService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);

    public static final String CURRENCY_CONVERSION_API = "https://api.fxratesapi.com/latest";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Converts the given amount to INR if the currency is not INR.
     *
     * @param amount   The amount to be converted.
     * @param currency The currency code of the amount.
     * @return The converted amount in INR.
     */
    public BigDecimal convertToINR(BigDecimal amount, String currency) {
        if ("USD".equals(currency)) {
            BigDecimal rate = getConversionRate("INR");
            return amount.multiply(rate);
        }
        return amount;
    }

    /**
     * Converts the given amount to USD.
     *
     * @param amount The amount in INR to be converted.
     * @return The converted amount in USD.
     */
    public BigDecimal convertToUSD(BigDecimal amount) {
        BigDecimal rate = getConversionRate("USD");
        return amount.divide(rate, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Retrieves the conversion rate from the API.
     *
     * @param toCurrency The target currency for conversion.
     * @return The conversion rate.
     */
    private BigDecimal getConversionRate(String toCurrency) {
        try {
            logger.info("Fetching conversion rate for: {}", toCurrency);
            String response = restTemplate.getForObject(CURRENCY_CONVERSION_API, String.class);
            JSONObject json = new JSONObject(response);
            BigDecimal rate = json.getJSONObject("rates").getBigDecimal(toCurrency);
            return rate;
        } catch (HttpClientErrorException e) {
            logger.error("Error fetching conversion rate: {}", e.getMessage());
            throw new CurrencyConversionException("Failed to fetch conversion rate", e);
        }
    }
}

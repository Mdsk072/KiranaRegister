package com.Kirana.KiranaRegister.service;

import com.Kirana.KiranaRegister.ExceptionHandler.InvalidTransactionException;
import com.Kirana.KiranaRegister.model.Transaction;
import com.Kirana.KiranaRegister.repisotory.TransactionRepository;
import com.google.protobuf.ServiceException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CurrencyConversionService currencyConversionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    /**
     * Adds a transaction to the repository after performing necessary validations and conversions.
     *
     * @param transaction The transaction to be added.
     * @return The saved transaction with updated details.
     * @throws InvalidTransactionException if the transaction details are invalid.
     */
    @Transactional
    public Transaction addTransaction(Transaction transaction) {
        if (transaction.getAmount() == null) {
            logger.error("Transaction amount cannot be null");
            throw new InvalidTransactionException("Transaction amount cannot be null");
        }
        logger.info("Adding transaction: {}", transaction);
        if (!transaction.getCurrency().equals("INR")) {
            transaction.setAmount(currencyConversionService.convertToINR(transaction.getAmount(), transaction.getCurrency()));
            transaction.setCurrency("INR");
        }
        transaction.setTimestamp(LocalDateTime.now());
        logger.info("Transaction added successfully: {}", transaction);
        return transactionRepository.save(transaction);

    }

    /**
     * displaying transactions with the capability to group them for daily reports
     *
     * @param displayCurrency The transaction to be added.
     * @return return transaction.
     * @throws InvalidTransactionException if the transaction details are invalid.
     */

    public List<Transaction> getAllTransactions(String displayCurrency) throws ServiceException {
        logger.debug("Fetching all transactions with display currency: {}", displayCurrency);

        try {
            List<Transaction> transactions = transactionRepository.findAllByOrderByTimestampDesc();
            if ("USD".equals(displayCurrency)) {
                return transactions.stream()
                        .map(this::convertToUSD)
                        .collect(Collectors.toList());
            }
            return transactions;
        } catch (Exception e) {
            logger.error("Error fetching transactions: {}", e.getMessage());
            throw new ServiceException("Failed to fetch transactions", e);
        }
    }

    private Transaction convertToUSD(Transaction transaction) {
        BigDecimal amountInUSD = currencyConversionService.convertToUSD(transaction.getAmount());
        transaction.setAmount(amountInUSD);
        transaction.setCurrency("USD");
        return transaction;
    }
}

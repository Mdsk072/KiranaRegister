package com.Kirana.KiranaRegister.controller;

import com.Kirana.KiranaRegister.model.Transaction;
import com.Kirana.KiranaRegister.service.TransactionService;
import com.google.protobuf.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    /**
     * Adds a transaction and returns the added transaction.
     *
     * @param transaction The transaction to add.
     * @return The ResponseEntity with the added transaction.
     */
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        logger.info("Request to add transaction: {}", transaction);
        Transaction savedTransaction = transactionService.addTransaction(transaction);
        logger.info("Transaction added: {}", savedTransaction);
        return ResponseEntity.ok(savedTransaction);
    }

    /**
     * Retrieves all transactions, optionally converting them to the specified currency.
     *
     * @param currency The currency to display the transactions in.
     * @return The ResponseEntity with a list of transactions.
     * @throws ServiceException if there's an issue in fetching transactions.
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam(required = false) String currency) throws ServiceException {
        logger.info("Request to get all transactions in currency: {}", currency);
        List<Transaction> transactions = transactionService.getAllTransactions(currency);
        return ResponseEntity.ok(transactions);
    }
}

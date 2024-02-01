package com.Kirana.KiranaRegister.controllerTest;
import com.Kirana.KiranaRegister.controller.TransactionController;
import com.Kirana.KiranaRegister.model.Transaction;
import com.Kirana.KiranaRegister.service.TransactionService;
import com.google.protobuf.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionControllerTests {
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTransaction() {
        Transaction mockTransaction = new Transaction();
        when(transactionService.addTransaction(mockTransaction)).thenReturn(mockTransaction);

        ResponseEntity<Transaction> responseEntity = transactionController.addTransaction(mockTransaction);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransaction, responseEntity.getBody());
    }

    @Test
    public void testGetAllTransactions() throws ServiceException {
        List<Transaction> mockTransactions = new ArrayList<>();
        when(transactionService.getAllTransactions(null)).thenReturn(mockTransactions);

        ResponseEntity<List<Transaction>> responseEntity = transactionController.getAllTransactions(null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransactions, responseEntity.getBody());
    }
}

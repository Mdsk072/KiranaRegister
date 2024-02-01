package com.Kirana.KiranaRegister.serviceTests;
import com.Kirana.KiranaRegister.model.Transaction;
import com.Kirana.KiranaRegister.repisotory.TransactionRepository;
import com.Kirana.KiranaRegister.service.CurrencyConversionService;
import com.Kirana.KiranaRegister.service.TransactionService;
import com.google.protobuf.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTransaction() {
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(BigDecimal.valueOf(100.00));
        newTransaction.setCurrency("USD");

        // Stub the conversion service to return a converted amount
        doReturn(BigDecimal.valueOf(7450.00)).when(currencyConversionService).convertToINR(any(), any());

        // Mock the repository save method
        when(transactionRepository.save(newTransaction)).thenReturn(newTransaction);

        Transaction result = transactionService.addTransaction(newTransaction);

        // Verify that the converted amount and currency are set correctly
        assertEquals(BigDecimal.valueOf(7450.00), result.getAmount());
        assertEquals("INR", result.getCurrency());
        assertNotNull(result.getTimestamp());

        // Verify that transactionRepository.save was called with the newTransaction
        verify(transactionRepository, times(1)).save(newTransaction);
    }


    @Test
    public void testGetAllTransactions() throws ServiceException {
        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(new Transaction());
        mockTransactions.add(new Transaction());

        when(transactionRepository.findAllByOrderByTimestampDesc()).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getAllTransactions("USD");

        assertEquals(2, result.size());

        verify(transactionRepository, times(1)).findAllByOrderByTimestampDesc();
    }
}

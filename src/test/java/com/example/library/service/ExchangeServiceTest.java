package com.example.library.service;

import com.example.library.repository.ExchangeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class ExchangeServiceTest {
    @Test
    public void shouldReturnTrueIfExchangeExists(){
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        ExchangeService exchangeService= new ExchangeService(exchangeRepositoryMock, null, null);

        Mockito.when(exchangeRepositoryMock.findExchange(anyInt(), anyInt(), anyString()).isPresent()).thenReturn(true);



    }

}
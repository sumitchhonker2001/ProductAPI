package com.zest.productapi.controller;

import com.zest.productapi.dto.*;
import com.zest.productapi.service.ProductService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    ProductService service;
    @InjectMocks
    ProductController controller;

    @BeforeEach
    void s() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listUsesPagination() {
        when(service.findAll(0, 10)).thenReturn(Page.empty());
        assertNotNull(controller.all(0, 10));
        verify(service).findAll(0, 10);
    }
}

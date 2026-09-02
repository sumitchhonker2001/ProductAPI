package com.zest.productapi.service;

import com.zest.productapi.dto.ProductRequest;
import com.zest.productapi.entity.Product;
import com.zest.productapi.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    ProductRepository products;
    @Mock
    ItemRepository items;
    @InjectMocks
    ProductService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("tester", null));
    }

    @Test
    void createProductSetsAuditFields() {
        Product saved = new Product();
        try {
            var id = Product.class.getDeclaredField("id");
            id.setAccessible(true);
            id.set(saved, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(products.save(any())).thenAnswer(i -> i.getArgument(0));
        var out = service.create(new ProductRequest("Phone"));
        assertEquals("Phone", out.productName());
        assertEquals("tester", out.createdBy());
        verify(products).save(any(Product.class));
    }
}

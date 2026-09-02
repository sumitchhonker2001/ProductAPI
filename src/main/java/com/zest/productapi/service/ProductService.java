package com.zest.productapi.service;

import com.zest.productapi.dto.*;
import com.zest.productapi.entity.*;
import com.zest.productapi.exception.ResourceNotFoundException;
import com.zest.productapi.repository.*;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository products;
    private final ItemRepository items;

    public ProductService(ProductRepository p, ItemRepository i) {
        products = p;
        items = i;
    }

    private String user() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional
    public ProductResponse create(ProductRequest r) {
        Product p = new Product();
        p.setProductName(r.productName().trim());
        p.setCreatedBy(user());
        p.setCreatedOn(Instant.now());
        return to(p, products.save(p));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(int page, int size) {
        return products.findAll(PageRequest.of(page, size, Sort.by("id").descending())).map(this::to);
    }

    @Transactional(readOnly = true)
    public ProductResponse find(Long id) {
        return to(products.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id)));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest r) {
        Product p = products.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        p.setProductName(r.productName().trim());
        p.setModifiedBy(user());
        p.setModifiedOn(Instant.now());
        return to(products.save(p));
    }

    @Transactional
    public void delete(Long id) {
        if (!products.existsById(id)) throw new ResourceNotFoundException("Product not found: " + id);
        products.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> itemList(Long id) {
        if (!products.existsById(id)) throw new ResourceNotFoundException("Product not found: " + id);
        return items.findByProductId(id).stream().map(x -> new ItemResponse(x.getId(), id, x.getQuantity())).toList();
    }

    private ProductResponse to(Product p) {
        return new ProductResponse(p.getId(), p.getProductName(), p.getCreatedBy(), p.getCreatedOn(), p.getModifiedBy(), p.getModifiedOn());
    }

    private ProductResponse to(Product p, Product ignored) {
        return to(p);
    }
}

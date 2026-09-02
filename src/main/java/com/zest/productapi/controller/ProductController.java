package com.zest.productapi.controller;
import com.zest.productapi.dto.*; import com.zest.productapi.service.ProductService; import jakarta.validation.Valid; import org.springframework.data.domain.Page; import org.springframework.http.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/v1/products") public class ProductController {private final ProductService s;public ProductController(ProductService s){this.s=s;}
 @GetMapping public Page<ProductResponse> all(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="10")int size){if(size<1||size>100)throw new IllegalArgumentException("size must be between 1 and 100");return s.findAll(page,size);}
 @GetMapping("/{id}") public ProductResponse one(@PathVariable Long id){return s.find(id);}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));}
 @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public ProductResponse update(@PathVariable Long id,@Valid @RequestBody ProductRequest r){return s.update(id,r);}
 @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){s.delete(id);}
 @GetMapping("/{id}/items") public List<ItemResponse> items(@PathVariable Long id){return s.itemList(id);}}

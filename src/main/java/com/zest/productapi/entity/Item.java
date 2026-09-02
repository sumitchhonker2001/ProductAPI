package com.zest.productapi.entity;

import jakarta.persistence.*;

@Entity @Table(name="item", indexes={@Index(name="idx_item_product_id", columnList="product_id")})
public class Item {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="product_id", nullable=false) private Product product;
    @Column(nullable=false) private Integer quantity;
    public Long getId(){return id;} public Product getProduct(){return product;} public void setProduct(Product v){product=v;} public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
}

package com.example.WebBanNhac.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date")
    private Date orderDate = new Date();

    @Column(name = "total")
    @Positive(message = "Total must be positive")
    private Double price;

    @Column(name = "quantity")
    @Positive(message = "Quantity must be positive")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    @ToString.Exclude
    private Song song;
    @Column(name = "Email")
    private String Email;
}


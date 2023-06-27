package com.example.WebBanNhac.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "Pdh")
public class Phieudathang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Note")
    private String note;
    @Column(name = "Ngay_lap")
    private Date Ngay_lap;
    @Column(name = "Ngay_giao")
    private Date Ngay_giao;
}

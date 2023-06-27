package com.example.WebBanNhac.repository;

import com.example.WebBanNhac.entity.Phieudathang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPdhRepository extends JpaRepository <Phieudathang, Long> { }

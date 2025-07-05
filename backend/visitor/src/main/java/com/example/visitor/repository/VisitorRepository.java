package com.example.visitor.repository;

import com.example.visitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    // To find a visitor by their QR code
    Optional<Visitor> findByQrCode(String qrCode);
}

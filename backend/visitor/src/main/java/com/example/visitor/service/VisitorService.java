package com.example.visitor.service;

import com.example.visitor.model.Visitor;
import com.example.visitor.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    public Visitor registerVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public Optional<Visitor> verifyVisitorByQrCode(String qrCode) {
        Optional<Visitor> visitorOpt = visitorRepository.findByQrCode(qrCode);

        visitorOpt.ifPresent(visitor -> {
            visitor.setVerified(true);
            visitor.setCheckInTime(LocalDateTime.now());
            visitorRepository.save(visitor);
        });

        return visitorOpt;
    }
}

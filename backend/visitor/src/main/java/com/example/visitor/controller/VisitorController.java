package com.example.visitor.controller;

import com.example.visitor.model.Visitor;
import com.example.visitor.service.VisitorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/visitors")
@CrossOrigin(origins = "http://localhost:3000")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVisitor(@RequestBody Visitor visitor) {
        try {
            // Generate QR code content (can be ID, or email, or a unique string)
            String qrText = visitor.getEmail() + "-" + System.currentTimeMillis();

            // Generate QR code as base64 string
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            String base64Qr = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());

            visitor.setQrCode(qrText); // Store QR content (not image) for verification
            Visitor savedVisitor = visitorService.registerVisitor(visitor);

            return ResponseEntity.ok().body(
                    java.util.Map.of("qrCode", base64Qr, "visitorId", savedVisitor.getId())
            );

        } catch (WriterException | java.io.IOException e) {
            return ResponseEntity.status(500).body("QR Code generation failed.");
        }
    }
}

//package com.example.visitor.controller;
//
//import com.example.visitor.model.Visitor;
//import com.example.visitor.service.VisitorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AdminController {
//
//    @Autowired
//    private VisitorService visitorService;
//
//    @GetMapping("/visitors")
//    public ResponseEntity<List<Visitor>> getAllVisitors() {
//        return ResponseEntity.ok(visitorService.getAllVisitors());
//    }
//
//    @PostMapping("/verify-visitor")
//    public ResponseEntity<?> verifyVisitor(@RequestBody Map<String, String> payload) {
//        String qrText = payload.get("qrText");
//
//        Optional<Visitor> visitorOpt = visitorService.verifyVisitorByQrCode(qrText);
//
//        if (visitorOpt.isPresent()) {
//            Visitor visitor = visitorOpt.get();
//            return ResponseEntity.ok().body(
//                    Map.of(
//                            "status", "success",
//                            "visitorName", visitor.getName()
//                    )
//            );
//        } else {
//            return ResponseEntity.status(404).body(Map.of("status", "not_found"));
//        }
//    }
//}


package com.example.visitor.controller;

import com.example.visitor.model.Admin;
import com.example.visitor.model.Visitor;
import com.example.visitor.service.AdminService;
import com.example.visitor.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private AdminService adminService;

    // ✅ 1. Get all visitors
    @GetMapping("/visitors")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAllVisitors());
    }

    // ✅ 2. Verify visitor by QR code
    @PostMapping("/verify-visitor")
    public ResponseEntity<?> verifyVisitor(@RequestBody Map<String, String> payload) {
        String qrText = payload.get("qrText");

        Optional<Visitor> visitorOpt = visitorService.verifyVisitorByQrCode(qrText);

        if (visitorOpt.isPresent()) {
            Visitor visitor = visitorOpt.get();
            return ResponseEntity.ok().body(
                    Map.of(
                            "status", "success",
                            "visitorName", visitor.getName()
                    )
            );
        } else {
            return ResponseEntity.status(404).body(Map.of("status", "not_found"));
        }
    }

    // ✅ 3. Admin login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<Admin> admin = adminService.authenticate(email, password);

        if (admin.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Login successful"
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "fail",
                    "message", "Invalid email or password"
            ));
        }
    }
}

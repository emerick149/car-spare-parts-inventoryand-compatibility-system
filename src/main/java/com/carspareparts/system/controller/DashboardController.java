package com.carspareparts.system.controller;

import com.carspareparts.system.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired private DashboardService service;

    // Bonus: one-shot system summary
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        return new ResponseEntity<>(service.getSummary(), HttpStatus.OK);
    }
}

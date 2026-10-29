package com.carspareparts.system.controller;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.service.PartOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/part-orders")
public class PartOrderController {
    @Autowired private PartOrderService service;

    // D1: safe save — returns 201, 400, or 409 based on business rules
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PartOrder entity) {
        Object result = service.save(entity);
        if (result instanceof Map.Entry<?, ?> entry) {
            int status = (int) entry.getKey();
            return new ResponseEntity<>(entry.getValue(), HttpStatus.valueOf(status));
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    @GetMapping("/by-status")
    public ResponseEntity<?> getByStatus(@RequestParam PartOrderStatus status) {
        return new ResponseEntity<>(service.getByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<?> getByDateRange(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(service.getByDateRange(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // D2: status flow — only allowed transitions are accepted
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam PartOrderStatus status) {
        Object result = service.updateStatus(id, status);
        if (result instanceof Map.Entry<?, ?> entry) {
            int code = (int) entry.getKey();
            return new ResponseEntity<>(entry.getValue(), HttpStatus.valueOf(code));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PartOrder entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
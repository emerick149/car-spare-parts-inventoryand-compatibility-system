package com.carspareparts.system.controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.service.PartOrderService;

@RestController
@RequestMapping("/api/part-orders")
public class PartOrderController {
    @Autowired private PartOrderService service;

    // D1: POST /api/part-orders/save — validates date not in past + storekeeper conflict
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PartOrder entity) {
        Object result = service.save(entity);
        if (result instanceof Map.Entry<?, ?> entry) {
            int status = (int) entry.getKey();
            return new ResponseEntity<>(entry.getValue(), HttpStatus.valueOf(status));
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // A1 (CRUD): GET /api/part-orders/all
    @GetMapping("/all")
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(service.getAll(), HttpStatus.OK); }

    // B2: GET /api/part-orders/by-status?status=PLACED
    @GetMapping("/by-status")
    public ResponseEntity<?> getByStatus(@RequestParam PartOrderStatus status) {
        return new ResponseEntity<>(service.getByStatus(status), HttpStatus.OK);
    }

    // B3: GET /api/part-orders/between?start=...&end=...
    @GetMapping("/between")
    public ResponseEntity<?> getByDateRange(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(service.getByDateRange(startDate, endDate), HttpStatus.OK);
    }

    // F1: GET /api/part-orders/stats/by-status
    @GetMapping("/stats/by-status")
    public ResponseEntity<?> getStatsByStatus() {
        return new ResponseEntity<>(service.getStatsByStatus(), HttpStatus.OK);
    }

    // F2: PATCH /api/part-orders/cancel-day?storekeeperID=...&date=...
    @PatchMapping("/cancel-day")
    public ResponseEntity<?> cancelDay(@RequestParam UUID storekeeperID, @RequestParam String date) {
        String result = service.cancelDay(storekeeperID, LocalDate.parse(date));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Bonus: GET /api/part-orders/page?page=0&size=5&sort=orderDate,desc
    @GetMapping("/page")
    public ResponseEntity<?> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "orderDate,desc") String sort) {
        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return new ResponseEntity<>(service.getPage(pageable), HttpStatus.OK);
    }

    // A1 (CRUD): GET /api/part-orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // D2: PATCH /api/part-orders/{id}/status?status=PROCESSING
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam PartOrderStatus status) {
        Object result = service.updateStatus(id, status);
        if (result instanceof Map.Entry<?, ?> entry) {
            int code = (int) entry.getKey();
            return new ResponseEntity<>(entry.getValue(), HttpStatus.valueOf(code));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // A1 (CRUD): PUT /api/part-orders/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PartOrder entity) {
        String msg = service.update(id, entity);
        return msg.contains("does not exist") ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND) : new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // A1 (CRUD) + E3 protected: DELETE /api/part-orders/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.contains("does not exist")) return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        if (msg.contains("delete them first")) return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}

package com.carspareparts.system.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carspareparts.system.domain.IssuedPart;
import com.carspareparts.system.service.IssuedPartService;
import com.carspareparts.system.service.IssuedPartService.Result;

@RestController
@RequestMapping("/api/issued-parts")
public class IssuedPartController {
    @Autowired private IssuedPartService service;

    // E1: POST /api/issued-parts/save?partOrderId=<uuid>
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam UUID partOrderId, @RequestBody IssuedPart entity) {
        Object result = service.save(partOrderId, entity);
        if (result instanceof Result r) {
            return new ResponseEntity<>(r.message(), HttpStatus.valueOf(r.status()));
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // E1 (CRUD): GET /api/issued-parts/all
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // E1 (CRUD): GET /api/issued-parts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Object res = service.getById(id);
        return res instanceof String
                ? new ResponseEntity<>(res, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res, HttpStatus.OK);
    }

    // E2: GET /api/issued-parts/customer/{customerCode}
    @GetMapping("/customer/{customerCode}")
    public ResponseEntity<?> getByCustomerCode(@PathVariable String customerCode) {
        return new ResponseEntity<>(service.getByCustomerCode(customerCode), HttpStatus.OK);
    }

    // E3: DELETE /api/issued-parts/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        return msg.contains("does not exist")
                ? new ResponseEntity<>(msg, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(msg, HttpStatus.OK);
    }
}

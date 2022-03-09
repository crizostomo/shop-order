package com.example.shoporder.controller;

import com.example.shoporder.dto.ShopOrderDTO;
import com.example.shoporder.service.ShopOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ShopOrderController {

    @Autowired
    private ShopOrderService service;

    @PostMapping
    public ResponseEntity create(@RequestBody ShopOrderDTO orderDTO){
        service.create(orderDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ShopOrderDTO>> getAll(Pageable pageable){
        Page<ShopOrderDTO> orderDTOS = service.getAll(pageable);
        return ResponseEntity.ok(orderDTOS);
    }
}

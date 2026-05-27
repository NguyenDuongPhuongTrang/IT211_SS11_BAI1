package com.example.bai1.controller;

import com.example.bai1.model.dto.request.CartItemDTO;
import com.example.bai1.model.dto.response.ApiDataResponse;
import com.example.bai1.model.entity.CartItem;
import com.example.bai1.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiDataResponse<CartItem>> add(@Valid @RequestBody CartItemDTO dto) {
        log.info("Thêm vào giỏ hàng userId={}, productId={}, quantity={}", dto.getUserId(), dto.getProductId(), dto.getQuantity());
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm vào giỏ hàng thành công",
                cartService.add(dto),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiDataResponse<List<CartItem>>> getByUser(@PathVariable String userId) {
        log.info("Lấy giỏ hàng user={}", userId);
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy giỏ hàng thành công",
                cartService.getByUser(userId),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

}

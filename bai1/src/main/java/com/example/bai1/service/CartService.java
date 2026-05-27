package com.example.bai1.service;

import com.example.bai1.model.dto.request.CartItemDTO;
import com.example.bai1.model.entity.CartItem;

import java.util.List;

public interface CartService {
    CartItem add(CartItemDTO dto);
    List<CartItem> getByUser(String userId);
}

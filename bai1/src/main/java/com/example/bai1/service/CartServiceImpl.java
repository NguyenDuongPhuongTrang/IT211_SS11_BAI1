package com.example.bai1.service;

import com.example.bai1.model.dto.request.CartItemDTO;
import com.example.bai1.model.entity.CartItem;
import com.example.bai1.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository repository;

    @Override
    public CartItem add(CartItemDTO dto) {
        log.info("Thêm vào giỏ hàng userId={}, productId={}, quantity={}", dto.getUserId(), dto.getProductId(), dto.getQuantity());

        if(dto.getQuantity()<=0){
            log.warn("Quantity không hợp lệ: {}", dto.getQuantity());
            throw new RuntimeException("Quantity phải > 0");
        }

        if(dto.getProductId().trim().isEmpty()){
            log.warn("ProductId rỗng từ user={}", dto.getUserId());
            throw new RuntimeException("ProductId không được rỗng");
        }

        CartItem item = repository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId())
                        .map(cart -> {
                            cart.setQuantity(cart.getQuantity() +dto.getQuantity());
                            log.info("Cộng sản phẩm={} số lượng={}", cart.getProductId(), cart.getQuantity());
                            return cart;
                        })
                        .orElseGet(() -> {
                            log.info("Tạo mới sản phẩm={} cho user={}", dto.getProductId(), dto.getUserId());

                            return CartItem.builder()
                                    .userId(dto.getUserId())
                                    .productId(dto.getProductId())
                                    .quantity(dto.getQuantity())
                                    .build();
                        });

        CartItem saved=repository.save(item);
        log.info("Lưu giỏ hàng thành công id={}", saved.getId());
        return saved;
    }

    @Override
    public List<CartItem> getByUser(String userId) {
        log.info("Lấy danh sách giỏ hàng user={}", userId);
        return repository.findByUserId(userId);
    }
}

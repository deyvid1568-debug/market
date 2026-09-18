package com.github.dev.deyvid.market.modules.product.controller;

import com.github.dev.deyvid.market.modules.product.dto.ProductRequestDTO;
import com.github.dev.deyvid.market.modules.product.dto.ProductResponseDTO;
import com.github.dev.deyvid.market.modules.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = productService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO responseDTO = productService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDTO> findBySku(@PathVariable String sku) {
        ProductResponseDTO responseDTO = productService.findBySku(sku);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> list = productService.findAll();
        return ResponseEntity.ok(list);
    }
}
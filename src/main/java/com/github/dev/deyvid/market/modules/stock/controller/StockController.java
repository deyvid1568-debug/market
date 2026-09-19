package com.github.dev.deyvid.market.modules.stock.controller;

import com.github.dev.deyvid.market.modules.stock.dto.CreateOrUpdateStockRequestDTO;
import com.github.dev.deyvid.market.modules.stock.model.Stock;
import com.github.dev.deyvid.market.modules.stock.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Stock> findByProductId(@PathVariable Long productId) {
        Stock stock = stockService.findByProductId(productId);
        return ResponseEntity.ok(stock);
    }

    @GetMapping
    public ResponseEntity<List<Stock>> findAll() {
        List<Stock> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/restock-alert")
    public ResponseEntity<List<Stock>> findProductsNeedingRestock() {
        List<Stock> stocks = stockService.findProductsNeedingRestock();
        return ResponseEntity.ok(stocks);
    }

    @PatchMapping("/product/{productId}/min-quantity")
    public ResponseEntity<Stock> updateMinQuantity(
            @PathVariable Long productId,
            @RequestBody @Valid CreateOrUpdateStockRequestDTO requestDTO
    ) {
        stockService.updateMinimumStock(productId, requestDTO.minimumStock());

        Stock updatedStock = stockService.findByProductId(productId);
        return ResponseEntity.ok(updatedStock);
    }
}
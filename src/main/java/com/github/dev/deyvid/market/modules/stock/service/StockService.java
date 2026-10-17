package com.github.dev.deyvid.market.modules.stock.service;

import com.github.dev.deyvid.market.infrastructure.exceptions.ResourceNotFoundException;
import com.github.dev.deyvid.market.modules.product.model.Product;
import com.github.dev.deyvid.market.modules.product.repository.ProductRepository;
import com.github.dev.deyvid.market.modules.stock.model.Stock;
import com.github.dev.deyvid.market.modules.stock.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addQuantity(Long productId, BigDecimal quantityToAdd) {
        Stock stock = getOrCreateStock(productId);
        stock.setTotalQuantity(stock.getTotalQuantity().add(quantityToAdd));
        stockRepository.save(stock);
    }

    @Transactional
    public void decreaseQuantity(Long productId, BigDecimal quantityToSubtract) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado para o produto ID: " + productId));

        if (stock.getTotalQuantity().compareTo(quantityToSubtract) < 0) {
            throw new IllegalArgumentException("Estoque insuficiente para baixa. Saldo atual: " + stock.getTotalQuantity());
        }

        stock.setTotalQuantity(stock.getTotalQuantity().subtract(quantityToSubtract));
        stockRepository.save(stock);
    }

    @Transactional
    public void updateMinimumStock(Long productId, BigDecimal newMinimumStock) {
        Stock stock = getOrCreateStock(productId);
        stock.setMinimumStock(newMinimumStock);
        stockRepository.save(stock);
    }

    @Transactional(readOnly = true)
    public List<Stock> findProductsNeedingRestock() {
        return stockRepository.findProductsNeedingRestock();
    }

    private Stock getOrCreateStock(Long productId) {
        return stockRepository.findByProductId(productId)
                .orElseGet(() -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + productId));

                    Stock newStock = new Stock();
                    newStock.setProduct(product);
                    newStock.setTotalQuantity(BigDecimal.ZERO);
                    newStock.setMinimumStock(BigDecimal.ZERO);
                    return stockRepository.save(newStock);
                });
    }

    @Transactional(readOnly = true)
    public Stock findByProductId(Long productId) {
        return stockRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado para o produto ID: " + productId));
    }

    @Transactional(readOnly = true)
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }
}
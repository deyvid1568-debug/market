package com.github.dev.deyvid.market.modules.stock.service;

import com.github.dev.deyvid.market.infrastructure.exceptions.ResourceNotFoundException;
import com.github.dev.deyvid.market.modules.product.model.Product;
import com.github.dev.deyvid.market.modules.product.repository.ProductRepository;
import com.github.dev.deyvid.market.modules.stock.dto.CreateOrUpdateBatchRequestDTO;
import com.github.dev.deyvid.market.modules.stock.model.Batch;
import com.github.dev.deyvid.market.modules.stock.repository.BatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;
    private final StockService stockService;

    public BatchService(BatchRepository batchRepository, ProductRepository productRepository, StockService stockService) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
        this.stockService = stockService;
    }

    @Transactional
    public Batch createBatch(CreateOrUpdateBatchRequestDTO request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + request.productId()));

        Batch batch = new Batch();
        batch.setProduct(product);
        batch.setBatchNumber(request.batchNumber());
        batch.setQuantity(request.quantity());
        batch.setBuyingPrice(request.buyingPrice());
        batch.setExpireDate(request.expireDate());

        Batch savedBatch = batchRepository.save(batch);

        stockService.addQuantity(product.getId(), savedBatch.getQuantity());

        return savedBatch;
    }

    @Transactional
    public Batch updateBatch(Long batchId, CreateOrUpdateBatchRequestDTO request) {

        Batch existingBatch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com ID: " + batchId));

        BigDecimal oldQuantity = existingBatch.getQuantity();
        BigDecimal newQuantity = request.quantity();
        BigDecimal difference = newQuantity.subtract(oldQuantity);

        existingBatch.setBatchNumber(request.batchNumber());
        existingBatch.setQuantity(newQuantity);
        existingBatch.setBuyingPrice(request.buyingPrice());
        existingBatch.setExpireDate(request.expireDate());

        Batch savedBatch = batchRepository.save(existingBatch);

        if (difference.compareTo(BigDecimal.ZERO) != 0) {
            if (difference.compareTo(BigDecimal.ZERO) > 0) {
                stockService.addQuantity(existingBatch.getProduct().getId(), difference);
            } else {
                stockService.decreaseQuantity(existingBatch.getProduct().getId(), difference.abs());
            }
        }

        return savedBatch;
    }

    @Transactional(readOnly = true)
    public List<Batch> getBatchesExpiringSoon(LocalDate limitDate) {
        return batchRepository.findBatchesExpiringBefore(limitDate);
    }
}
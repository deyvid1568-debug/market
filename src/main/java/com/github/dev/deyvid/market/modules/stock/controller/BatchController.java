package com.github.dev.deyvid.market.modules.stock.controller;

import com.github.dev.deyvid.market.modules.stock.dto.CreateOrUpdateBatchRequestDTO;
import com.github.dev.deyvid.market.modules.stock.model.Batch;
import com.github.dev.deyvid.market.modules.stock.service.BatchService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/batches")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping
    public ResponseEntity<Batch> create(@RequestBody @Valid CreateOrUpdateBatchRequestDTO requestDTO) {
        Batch savedBatch = batchService.createBatch(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> update(
            @PathVariable Long id,
            @RequestBody @Valid CreateOrUpdateBatchRequestDTO requestDTO
    ) {
        Batch updatedBatch = batchService.updateBatch(id, requestDTO);
        return ResponseEntity.ok(updatedBatch);
    }

    @GetMapping("/expiring-alert")
    public ResponseEntity<List<Batch>> getExpiringBatches(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Batch> batches = batchService.getBatchesExpiringSoon(date);
        return ResponseEntity.ok(batches);
    }
}
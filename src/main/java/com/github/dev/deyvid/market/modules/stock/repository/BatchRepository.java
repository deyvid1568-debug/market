package com.github.dev.deyvid.market.modules.stock.repository;

import com.github.dev.deyvid.market.modules.stock.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByBatchNumber(String batchNumber);

    List<Batch> findByExpireDate(LocalDate expireDate);

    List<Batch> findByProductId(Long productId);

    List<Batch> findByExpireDateBefore(LocalDate date);

    @Query("SELECT b FROM Batch b WHERE b.expireDate <= :limitDate ORDER BY b.expireDate ASC")
    List<Batch> findBatchesExpiringBefore(LocalDate limitDate);
}
package com.github.dev.deyvid.market.modules.product.service;

import com.github.dev.deyvid.market.infrastructure.exceptions.ResourceNotFoundException;
import com.github.dev.deyvid.market.modules.product.dto.ProductRequestDTO;
import com.github.dev.deyvid.market.modules.product.dto.ProductResponseDTO;
import com.github.dev.deyvid.market.modules.product.model.Product;
import com.github.dev.deyvid.market.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

        public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        if (productRepository.existsBySku(requestDTO.sku())) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com o SKU: " + requestDTO.sku());
        }

        Product product = Product.builder()
                .name(requestDTO.name())
                .description(requestDTO.description())
                .sku(requestDTO.sku())
                .price(requestDTO.price())
                .active(requestDTO.active())
                .build();

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para o ID: " + id));

        return new ProductResponseDTO(product);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para o SKU: " + sku));

        return new ProductResponseDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
    }
}
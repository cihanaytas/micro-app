package com.cihan.productservice.service;

import com.cihan.productservice.dto.ProductDto;
import com.cihan.productservice.model.Product;
import com.cihan.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;


    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::converToDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product couldn't found");
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        product.setId(UUID.randomUUID().toString());
        productRepository.save(product);
    }

    private Product convertToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    private ProductDto converToDto(Product product){
        return modelMapper.map(product, ProductDto.class);
    }
}

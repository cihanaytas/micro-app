package com.cihan.productservice.service;

import com.cihan.productservice.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ResponseEntity<?> getProductById(String id);


    void createProduct(@RequestBody ProductDto product) throws ParseException;

}

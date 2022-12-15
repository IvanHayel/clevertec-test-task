package ru.clevertec.api.web.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.payload.ServerResponse;
import ru.clevertec.api.model.payload.response.MessageResponse;
import ru.clevertec.api.service.receipt.ProductService;
import ru.clevertec.api.service.search.HibernateSearchService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
  ProductService productService;
  HibernateSearchService searchService;

  @GetMapping
  public List<ProductDto> getProducts() {
    return productService.getAll();
  }

  @GetMapping("/search")
  public List<ProductDto> search(@RequestParam String term) {
    return searchService.searchForProducts(term);
  }

  @GetMapping("/{id}")
  public ProductDto getProductById(@PathVariable Long id) {
    return productService.getById(id);
  }

  @PostMapping
  public ProductDto addProduct(@RequestBody @Valid ProductDto product) {
    return productService.add(product);
  }

  @PutMapping
  public ProductDto updateProduct(@RequestBody @Valid ProductDto product) {
    return productService.update(product);
  }

  @DeleteMapping("/{id}")
  public ServerResponse deleteProduct(@PathVariable Long id) {
    productService.delete(id);
    return MessageResponse.SUCCESS_DELETE_PRODUCT;
  }
}

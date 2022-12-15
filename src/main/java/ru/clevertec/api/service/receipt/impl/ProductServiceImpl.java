package ru.clevertec.api.service.receipt.impl;

import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.clevertec.api.exception.receipt.ProductNotFoundException;
import ru.clevertec.api.mapper.ProductMapper;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.entity.Product;
import ru.clevertec.api.repository.ProductRepository;
import ru.clevertec.api.service.receipt.ProductService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
  ProductRepository repository;
  ProductMapper mapper;

  @Override
  public List<ProductDto> getAll() {
    var products = repository.findAll();
    return mapper.toDtoList(products);
  }

  @Override
  public ProductDto getById(Long id) {
    var product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    return mapper.toDto(product);
  }

  @Override
  public ProductDto add(ProductDto productDto) {
    var product = mapper.toEntity(productDto);
    return mapper.toDto(repository.save(product));
  }

  @Override
  public ProductDto update(ProductDto productDto) {
    var id = productDto.getId();
    if (Objects.nonNull(id) && isExist(id)) {
      var product = mapper.toEntity(productDto);
      return mapper.toDto(repository.save(product));
    }
    throw new ProductNotFoundException(id);
  }

  @Override
  public void delete(Long id) {
    var product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    repository.delete(product);
  }

  @Override
  public List<Product> find(Long[] ids) {
    List<Product> products = repository.findAllById(List.of(ids));
    return products;
  }

  private boolean isExist(Long id) {
    return repository.existsById(id);
  }
}

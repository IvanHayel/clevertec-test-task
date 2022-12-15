package ru.clevertec.api.service.receipt;

import java.util.List;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.entity.Product;

public interface ProductService {
  List<ProductDto> getAll();

  ProductDto getById(Long id);

  ProductDto add(ProductDto productDto);

  ProductDto update(ProductDto productDto);

  void delete(Long id);

  List<Product> find(Long[] ids);
}

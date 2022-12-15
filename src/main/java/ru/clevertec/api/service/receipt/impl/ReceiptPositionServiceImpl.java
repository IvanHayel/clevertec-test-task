package ru.clevertec.api.service.receipt.impl;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import ru.clevertec.api.exception.receipt.NegativeProductQuantityException;
import ru.clevertec.api.model.entity.Product;
import ru.clevertec.api.model.entity.ReceiptPosition;
import ru.clevertec.api.repository.ReceiptPositionRepository;
import ru.clevertec.api.service.receipt.ProductService;
import ru.clevertec.api.service.receipt.ReceiptPositionService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptPositionServiceImpl implements ReceiptPositionService {
  ReceiptPositionRepository repository;

  ProductService productService;

  @Override
  public Set<ReceiptPosition> formPositions(Map<String, String> products) {
    var productIds = extractProductIds(products);
    var productList = productService.find(productIds);
    var positions = productList.stream().map(createPosition(products)).collect(Collectors.toSet());
    repository.saveAll(positions);
    return positions;
  }

  private Function<Product, ReceiptPosition> createPosition(Map<String, String> products) {
    return product -> {
      var position = new ReceiptPosition();
      var quantity = Integer.parseInt(products.get(product.getId().toString()));
      if (quantity < 1) throw new NegativeProductQuantityException();
      position.setProduct(product);
      position.setQuantity(quantity);
      return position;
    };
  }

  @Override
  public void removePositions(Set<ReceiptPosition> positions) {
    repository.deleteAll(positions);
  }

  private Long[] extractProductIds(Map<String, String> products) {
    return products.keySet().stream()
        .filter(NumberUtils::isDigits)
        .map(Long::parseLong)
        .toArray(Long[]::new);
  }
}

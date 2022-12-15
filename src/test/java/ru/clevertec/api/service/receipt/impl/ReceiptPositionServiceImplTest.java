package ru.clevertec.api.service.receipt.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.api.exception.receipt.NegativeProductQuantityException;
import ru.clevertec.api.model.entity.Product;
import ru.clevertec.api.repository.ReceiptPositionRepository;
import ru.clevertec.api.service.receipt.ProductService;
import ru.clevertec.api.service.receipt.ReceiptPositionService;

@ContextConfiguration(classes = {ReceiptPositionServiceImpl.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ReceiptPositionServiceImplTest {
  private static final Map<String, String> TEST_CORRECT_PRODUCTS;
  private static final Map<String, String> TEST_INVALID_PRODUCTS;
  private static final List<Product> TEST_PRODUCT_LIST;

  static {
    TEST_CORRECT_PRODUCTS = Map.of("1", "1", "2", "2");
    TEST_INVALID_PRODUCTS = Map.of("1", "-1", "2", "-2");

    var product = new Product();
    product.setId(1L);
    TEST_PRODUCT_LIST = List.of(product);
  }

  @MockBean private ProductService productService;
  @MockBean private ReceiptPositionRepository receiptPositionRepository;
  @Autowired private ReceiptPositionService positionService;

  /** Method under test: {@link ReceiptPositionServiceImpl#formPositions(Map)} */
  @Test
  void testFormPositions() {
    when(productService.find(any())).thenReturn(TEST_PRODUCT_LIST);
    when(receiptPositionRepository.saveAll(any())).thenReturn(new ArrayList<>());
    assertFalse(positionService.formPositions(TEST_CORRECT_PRODUCTS).isEmpty());
    verify(receiptPositionRepository).saveAll(any());
    verify(productService).find(any());
  }

  /** Method under test: {@link ReceiptPositionServiceImpl#formPositions(Map)} */
  @Test
  void testFormPositionsWithNegativeProductQuantityException() {
    when(productService.find(any())).thenReturn(TEST_PRODUCT_LIST);
    when(receiptPositionRepository.saveAll(any())).thenReturn(new ArrayList<>());
    assertThrows(
        NegativeProductQuantityException.class,
        () -> positionService.formPositions(TEST_INVALID_PRODUCTS));
    verify(productService).find(any());
  }

  /** Method under test: {@link ReceiptPositionServiceImpl#removePositions(Set)} */
  @Test
  void testRemovePositions() {
    doNothing().when(receiptPositionRepository).deleteAll(any());
    positionService.removePositions(new HashSet<>());
    verify(receiptPositionRepository).deleteAll(any());
  }
}

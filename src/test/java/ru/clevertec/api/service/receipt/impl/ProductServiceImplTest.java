package ru.clevertec.api.service.receipt.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.api.exception.receipt.ProductNotFoundException;
import ru.clevertec.api.mapper.ProductMapper;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.entity.Product;
import ru.clevertec.api.repository.ProductRepository;
import ru.clevertec.api.service.receipt.ProductService;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ProductServiceImplTest {
  private static final Long TEST_ID = 123L;
  private static final String TEST_DESCRIPTION = "The characteristics of someone or something";
  private static final Double TEST_PRICE = 10.0;
  private static final Product TEST_PRODUCT;
  private static final ProductDto TEST_PRODUCT_DTO;
  private static final List<Product> TEST_PRODUCT_LIST = new ArrayList<>();
  private static final List<ProductDto> TEST_PRODUCT_DTO_LIST = new ArrayList<>();

  static {
    TEST_PRODUCT = new Product();
    TEST_PRODUCT.setId(TEST_ID);
    TEST_PRODUCT.setDescription(TEST_DESCRIPTION);
    TEST_PRODUCT.setPrice(TEST_PRICE);
    TEST_PRODUCT_LIST.add(TEST_PRODUCT);

    TEST_PRODUCT_DTO = new ProductDto(TEST_ID, TEST_DESCRIPTION, BigDecimal.valueOf(TEST_PRICE));
    TEST_PRODUCT_DTO_LIST.add(TEST_PRODUCT_DTO);
  }

  @MockBean ProductMapper productMapper;
  @MockBean ProductRepository productRepository;
  @Autowired ProductService productService;

  /** Method under test: {@link ProductServiceImpl#getAll()} */
  @Test
  void testGetAll() {
    when(productRepository.findAll()).thenReturn(TEST_PRODUCT_LIST);
    when(productMapper.toDtoList(any())).thenReturn(TEST_PRODUCT_DTO_LIST);
    var actualAll = productService.getAll();
    assertSame(TEST_PRODUCT_DTO_LIST, actualAll);
    assertFalse(actualAll.isEmpty());
    verify(productRepository).findAll();
    verify(productMapper).toDtoList(any());
  }

  /** Method under test: {@link ProductServiceImpl#getById(Long)} */
  @Test
  void testGetById() {
    var ofResult = Optional.of(TEST_PRODUCT);
    when(productRepository.findById(any())).thenReturn(ofResult);
    when(productMapper.toDto(any())).thenReturn(TEST_PRODUCT_DTO);
    var actualById = productService.getById(TEST_ID);
    assertSame(TEST_PRODUCT_DTO, actualById);
    assertEquals(BigDecimal.valueOf(TEST_PRICE), actualById.getPrice());
    verify(productRepository).findById(any());
    verify(productMapper).toDto(any());
  }

  /** Method under test: {@link ProductServiceImpl#getById(Long)} */
  @Test
  void testGetByIdWithProductNotFoundException() {
    when(productRepository.findById(any())).thenReturn(Optional.empty());
    when(productMapper.toDto(any())).thenReturn(TEST_PRODUCT_DTO);
    assertThrows(ProductNotFoundException.class, () -> productService.getById(TEST_ID));
    verify(productRepository).findById(any());
  }

  /** Method under test: {@link ProductServiceImpl#add(ProductDto)} */
  @Test
  void testAdd() {
    when(productMapper.toEntity(any())).thenReturn(TEST_PRODUCT);
    when(productRepository.save(any())).thenReturn(TEST_PRODUCT);
    when(productMapper.toDto(any())).thenReturn(TEST_PRODUCT_DTO);
    var actualAddResult = productService.add(TEST_PRODUCT_DTO);
    assertSame(TEST_PRODUCT_DTO, actualAddResult);
    assertEquals(BigDecimal.valueOf(TEST_PRICE), actualAddResult.getPrice());
    verify(productRepository).save(any());
    verify(productMapper).toDto(any());
    verify(productMapper).toEntity(any());
  }

  /** Method under test: {@link ProductServiceImpl#update(ProductDto)} */
  @Test
  void testUpdate() {
    when(productRepository.save(any())).thenReturn(TEST_PRODUCT);
    when(productRepository.existsById(any())).thenReturn(true);
    when(productMapper.toDto(any())).thenReturn(TEST_PRODUCT_DTO);
    when(productMapper.toEntity(any())).thenReturn(TEST_PRODUCT);
    var actualUpdateResult = productService.update(TEST_PRODUCT_DTO);
    assertSame(TEST_PRODUCT_DTO, actualUpdateResult);
    assertEquals(BigDecimal.valueOf(TEST_PRICE), actualUpdateResult.getPrice());
    verify(productRepository).existsById(any());
    verify(productRepository).save(any());
    verify(productMapper).toDto(any());
    verify(productMapper).toEntity(any());
  }

  /** Method under test: {@link ProductServiceImpl#update(ProductDto)} */
  @Test
  void testUpdateWithProductNotFoundException() {
    when(productRepository.save(any())).thenReturn(TEST_PRODUCT);
    when(productRepository.existsById(any())).thenReturn(false);
    when(productMapper.toDto(any())).thenReturn(TEST_PRODUCT_DTO);
    when(productMapper.toEntity(any())).thenReturn(TEST_PRODUCT);
    assertThrows(ProductNotFoundException.class, () -> productService.update(TEST_PRODUCT_DTO));
    verify(productRepository).existsById(any());
  }

  /** Method under test: {@link ProductServiceImpl#delete(Long)} */
  @Test
  void testDelete() {
    var ofResult = Optional.of(TEST_PRODUCT);
    doNothing().when(productRepository).delete(any());
    when(productRepository.findById(any())).thenReturn(ofResult);
    productService.delete(TEST_ID);
    verify(productRepository).findById(any());
    verify(productRepository).delete(any());
  }

  /** Method under test: {@link ProductServiceImpl#delete(Long)} */
  @Test
  void testDeleteWithProductNotFoundException() {
    doNothing().when(productRepository).delete(any());
    when(productRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(ProductNotFoundException.class, () -> productService.delete(TEST_ID));
    verify(productRepository).findById(any());
  }

  /** Method under test: {@link ProductServiceImpl#find(Long[])} */
  @Test
  void testFind() {
    when(productRepository.findAllById(any())).thenReturn(TEST_PRODUCT_LIST);
    var actualFindResult = productService.find(new Long[] {TEST_ID});
    assertSame(TEST_PRODUCT_LIST, actualFindResult);
    assertFalse(actualFindResult.isEmpty());
    verify(productRepository).findAllById(any());
  }
}

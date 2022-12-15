package ru.clevertec.api.web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.payload.response.MessageResponse;
import ru.clevertec.api.service.receipt.ProductService;
import ru.clevertec.api.service.search.HibernateSearchService;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ProductControllerTest {
  private static final List<ProductDto> TEST_PRODUCTS;
  private static final ProductDto TEST_PRODUCT;
  private static final Long TEST_ID = 123L;
  private static final String TEST_DESCRIPTION = "Characteristic of something.";
  private static final BigDecimal TEST_PRICE = BigDecimal.valueOf(100.0);

  private static final String TEST_URL = "/api/v1/products";
  private static final String TEST_URL_WITH_ID = TEST_URL + "/{id}";
  private static final String TEST_URL_WITH_SEARCH = TEST_URL + "/search";
  private static final String TEST_SEARCH_TERM = "foo";
  private static final String SEARCH_REQUIRED_PARAMETER = "term";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    TEST_PRODUCT = new ProductDto(TEST_ID, TEST_DESCRIPTION, TEST_PRICE);
    TEST_PRODUCTS = List.of(TEST_PRODUCT);
  }

  @Autowired ProductController productController;
  @MockBean ProductService productService;
  @MockBean HibernateSearchService searchService;

  /** Method under test: {@link ProductController#getProducts()} */
  @Test
  @SneakyThrows
  void testGetProducts() {
    when(productService.getAll()).thenReturn(TEST_PRODUCTS);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_PRODUCTS);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL);
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ProductController#search(String)} */
  @Test
  @SneakyThrows
  void testSearch() {
    when(searchService.searchForProducts(any())).thenReturn(TEST_PRODUCTS);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_PRODUCTS);
    var requestBuilder =
        MockMvcRequestBuilders.get(TEST_URL_WITH_SEARCH)
            .param(SEARCH_REQUIRED_PARAMETER, TEST_SEARCH_TERM);
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ProductController#getProductById(Long)} */
  @Test
  @SneakyThrows
  void testGetProductById() {
    when(productService.getById(any())).thenReturn(TEST_PRODUCT);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_PRODUCT);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL_WITH_ID, TEST_ID);
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ProductController#addProduct(ProductDto)} */
  @Test
  @SneakyThrows
  void testAddProduct() {
    when(productService.add(any())).thenReturn(TEST_PRODUCT);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_PRODUCT);
    var requestBuilder =
        MockMvcRequestBuilders.post(TEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(expectedJson);
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ProductController#updateProduct(ProductDto)} */
  @Test
  @SneakyThrows
  void testUpdateProduct() {
    when(productService.update(any())).thenReturn(TEST_PRODUCT);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_PRODUCT);
    var requestBuilder =
        MockMvcRequestBuilders.put(TEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(expectedJson);
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ProductController#deleteProduct(Long)} */
  @Test
  @SneakyThrows
  void testDeleteProduct() {
    doNothing().when(productService).delete(any());
    var expectedJson = OBJECT_MAPPER.writeValueAsString(MessageResponse.SUCCESS_DELETE_PRODUCT);
    var requestBuilder = MockMvcRequestBuilders.delete(TEST_URL_WITH_ID, TEST_ID);
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }
}

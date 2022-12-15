package ru.clevertec.api.web.controller;

import static org.mockito.ArgumentMatchers.any;
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
import ru.clevertec.api.model.dto.DiscountCardDto;
import ru.clevertec.api.model.payload.response.MessageResponse;
import ru.clevertec.api.service.receipt.DiscountCardService;

@ContextConfiguration(classes = {DiscountCardController.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class DiscountCardControllerTest {
  private static final List<DiscountCardDto> TEST_CARDS;
  private static final DiscountCardDto TEST_CARD;
  private static final Long TEST_ID = 123L;
  private static final BigDecimal TEST_DISCOUNT = BigDecimal.valueOf(0.5);

  private static final String TEST_URL = "/api/v1/cards";
  private static final String TEST_URL_WITH_ID = TEST_URL + "/{id}";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    TEST_CARD = new DiscountCardDto(TEST_ID, TEST_DISCOUNT);
    TEST_CARDS = List.of(TEST_CARD);
  }

  @Autowired DiscountCardController discountCardController;
  @MockBean DiscountCardService discountCardService;

  /** Method under test: {@link DiscountCardController#getDiscountCards()} */
  @Test
  @SneakyThrows
  void testGetDiscountCards() {
    when(discountCardService.getAll()).thenReturn(TEST_CARDS);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_CARDS);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL);
    MockMvcBuilders.standaloneSetup(discountCardController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link DiscountCardController#getDiscountCardById(Long)} */
  @Test
  @SneakyThrows
  void testGetDiscountCardById() {
    when(discountCardService.getById(any())).thenReturn(TEST_CARD);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_CARD);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL_WITH_ID, TEST_ID);
    MockMvcBuilders.standaloneSetup(discountCardController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link DiscountCardController#addDiscountCard(DiscountCardDto)} */
  @Test
  @SneakyThrows
  void testAddDiscountCard() {
    when(discountCardService.add(any())).thenReturn(TEST_CARD);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_CARD);
    var requestBuilder =
        MockMvcRequestBuilders.post(TEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(expectedJson);
    MockMvcBuilders.standaloneSetup(discountCardController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link DiscountCardController#updateDiscountCard(DiscountCardDto)} */
  @Test
  @SneakyThrows
  void testUpdateDiscountCard() {
    when(discountCardService.update(any())).thenReturn(TEST_CARD);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_CARD);
    var requestBuilder =
        MockMvcRequestBuilders.put(TEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(expectedJson);
    MockMvcBuilders.standaloneSetup(discountCardController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link DiscountCardController#deleteDiscountCard(Long)} */
  @Test
  @SneakyThrows
  void testDeleteDiscountCard() {
    doNothing().when(discountCardService).delete(any());
    var expectedJson =
        OBJECT_MAPPER.writeValueAsString(MessageResponse.SUCCESS_DELETE_DISCOUNT_CARD);
    var requestBuilder = MockMvcRequestBuilders.delete(TEST_URL_WITH_ID, TEST_ID);
    MockMvcBuilders.standaloneSetup(discountCardController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }
}

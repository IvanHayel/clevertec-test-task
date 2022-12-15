package ru.clevertec.api.web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.dto.ReceiptReadOnlyDto;
import ru.clevertec.api.model.payload.request.ReceiptRequest;
import ru.clevertec.api.model.payload.response.MessageResponse;
import ru.clevertec.api.service.pdf.ReceiptPdfService;
import ru.clevertec.api.service.receipt.ReceiptService;
import ru.clevertec.api.service.search.HibernateSearchService;

@ContextConfiguration(classes = {ReceiptController.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ReceiptControllerTest {
  private static final List<ReceiptDto> TEST_RECEIPTS;
  private static final List<ReceiptReadOnlyDto> TEST_READ_ONLY_RECEIPTS;
  private static final ReceiptDto TEST_RECEIPT;
  private static final ReceiptRequest TEST_RECEIPT_REQUEST;
  private static final ReceiptReadOnlyDto TEST_READ_ONLY_RECEIPT;
  private static final Long TEST_ID = 123L;
  private static final String TEST_DESCRIPTION = "Some description";
  private static final Map<String, String> TEST_PRODUCT_MAP = Map.of("1", "1");

  private static final String TEST_URL = "/api/v1/receipts";
  private static final String TEST_URL_WITH_ID = TEST_URL + "/{id}";
  private static final String TEST_URL_DOWNLOAD = TEST_URL + "/download/{id}";
  private static final String TEST_URL_WITH_SEARCH = TEST_URL + "/search";
  private static final String TEST_SEARCH_TERM = "foo";
  private static final String SEARCH_REQUIRED_PARAMETER = "term";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    TEST_RECEIPT =
        new ReceiptDto(
            TEST_ID, TEST_DESCRIPTION, Collections.emptySet(), null, 0.0, 0.0, null, null);
    TEST_RECEIPTS = List.of(TEST_RECEIPT);
    TEST_READ_ONLY_RECEIPT = new ReceiptReadOnlyDto(TEST_ID, TEST_DESCRIPTION, 0.0, 0.0);
    TEST_READ_ONLY_RECEIPTS = List.of(TEST_READ_ONLY_RECEIPT);
    TEST_RECEIPT_REQUEST = new ReceiptRequest(TEST_ID, TEST_DESCRIPTION, TEST_PRODUCT_MAP, TEST_ID);
  }

  @MockBean ReceiptService receiptService;
  @MockBean HibernateSearchService hibernateSearchService;
  @MockBean ReceiptPdfService receiptPdfService;
  @Autowired private ReceiptController receiptController;

  /** Method under test: {@link ReceiptController#getReceipts()} */
  @Test
  @SneakyThrows
  void testGetReceipts() {
    when(receiptService.getAll()).thenReturn(TEST_READ_ONLY_RECEIPTS);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_READ_ONLY_RECEIPTS);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ReceiptController#search(String)} */
  @Test
  @SneakyThrows
  void testSearch() {
    when(hibernateSearchService.searchForReceipts(any())).thenReturn(TEST_RECEIPTS);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_RECEIPTS);
    var requestBuilder =
        MockMvcRequestBuilders.get(TEST_URL_WITH_SEARCH)
            .param(SEARCH_REQUIRED_PARAMETER, TEST_SEARCH_TERM);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ReceiptController#getReceipt(Long)} */
  @Test
  @SneakyThrows
  void testGetReceipt() {
    when(receiptService.getById(TEST_ID)).thenReturn(TEST_RECEIPT);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_RECEIPT);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL_WITH_ID, TEST_ID);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ReceiptController#downloadReceipt(Long, HttpServletResponse)} */
  @Test
  @SneakyThrows
  void testDownloadReceipt() {
    when(receiptService.getById(any())).thenReturn(TEST_RECEIPT);
    var requestBuilder = MockMvcRequestBuilders.get(TEST_URL_DOWNLOAD, TEST_ID);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PDF));
  }

  /** Method under test: {@link ReceiptController#addReceipt(ReceiptRequest)} */
  @Test
  @SneakyThrows
  void testAddReceipt() {
    when(receiptService.add(any())).thenReturn(TEST_RECEIPT);
    var requestJson = OBJECT_MAPPER.writeValueAsString(TEST_RECEIPT_REQUEST);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_RECEIPT);
    var requestBuilder =
        MockMvcRequestBuilders.post(TEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ReceiptController#updateReceipt(ReceiptRequest)} */
  @Test
  @SneakyThrows
  void testUpdateReceipt() {
    when(receiptService.update(any())).thenReturn(TEST_RECEIPT);
    var requestJson = OBJECT_MAPPER.writeValueAsString(TEST_RECEIPT_REQUEST);
    var expectedJson = OBJECT_MAPPER.writeValueAsString(TEST_RECEIPT);
    var requestBuilder =
        MockMvcRequestBuilders.put(TEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }

  /** Method under test: {@link ReceiptController#deleteReceipt(Long)} */
  @Test
  @SneakyThrows
  void testDeleteReceipt() {
    doNothing().when(receiptService).delete(any());
    var expectedJson = OBJECT_MAPPER.writeValueAsString(MessageResponse.SUCCESS_DELETE_RECEIPT);
    var requestBuilder = MockMvcRequestBuilders.delete(TEST_URL_WITH_ID, TEST_ID);
    MockMvcBuilders.standaloneSetup(receiptController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(expectedJson));
  }
}

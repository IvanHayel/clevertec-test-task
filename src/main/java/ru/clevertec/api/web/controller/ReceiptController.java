package ru.clevertec.api.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.dto.ReceiptReadOnlyDto;
import ru.clevertec.api.model.payload.ServerResponse;
import ru.clevertec.api.model.payload.request.ReceiptRequest;
import ru.clevertec.api.model.payload.response.MessageResponse;
import ru.clevertec.api.service.pdf.ReceiptPdfService;
import ru.clevertec.api.service.receipt.ReceiptService;
import ru.clevertec.api.service.search.HibernateSearchService;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptController {
  ReceiptService receiptService;
  HibernateSearchService searchService;
  ReceiptPdfService pdfService;

  @GetMapping
  public List<ReceiptReadOnlyDto> getReceipts() {
    return receiptService.getAll();
  }

  @GetMapping("/search")
  public List<ReceiptDto> search(@RequestParam String term) {
    return searchService.searchForReceipts(term);
  }

  @GetMapping("/{id}")
  public ReceiptDto getReceipt(@PathVariable Long id) {
    return receiptService.getById(id);
  }

  @SneakyThrows
  @GetMapping(value = "/download/{id}")
  public void downloadReceipt(@PathVariable Long id, HttpServletResponse response) {
    var receipt = receiptService.getById(id);
    response.setContentType(MediaType.APPLICATION_PDF_VALUE);
    pdfService.generatePdf(receipt, response.getOutputStream());
  }

  @PostMapping
  public ReceiptDto addReceipt(@RequestBody @Valid ReceiptRequest request) {
    return receiptService.add(request);
  }

  @PutMapping
  public ReceiptDto updateReceipt(@RequestBody @Valid ReceiptRequest request) {
    return receiptService.update(request);
  }

  @DeleteMapping("/{id}")
  public ServerResponse deleteReceipt(@PathVariable Long id) {
    receiptService.delete(id);
    return MessageResponse.SUCCESS_DELETE_RECEIPT;
  }
}

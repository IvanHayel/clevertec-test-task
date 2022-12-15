package ru.clevertec.api.service.pdf;

import jakarta.servlet.ServletOutputStream;
import ru.clevertec.api.model.dto.ReceiptDto;

public interface ReceiptPdfService {
  void generatePdf(ReceiptDto receipt, ServletOutputStream outputStream);
}

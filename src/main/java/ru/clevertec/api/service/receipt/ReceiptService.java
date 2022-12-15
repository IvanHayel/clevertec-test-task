package ru.clevertec.api.service.receipt;

import java.util.List;
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.dto.ReceiptReadOnlyDto;
import ru.clevertec.api.model.payload.request.ReceiptRequest;

public interface ReceiptService {
  List<ReceiptReadOnlyDto> getAll();

  ReceiptDto getById(Long id);

  ReceiptDto add(ReceiptRequest request);

  ReceiptDto update(ReceiptRequest request);

  void delete(Long id);
}

package ru.clevertec.api.service.receipt;

import java.util.Map;
import java.util.Set;
import ru.clevertec.api.model.entity.ReceiptPosition;

public interface ReceiptPositionService {
  Set<ReceiptPosition> formPositions(Map<String, String> products);

  void removePositions(Set<ReceiptPosition> positions);
}

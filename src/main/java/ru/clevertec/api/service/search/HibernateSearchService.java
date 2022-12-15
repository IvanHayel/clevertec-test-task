package ru.clevertec.api.service.search;

import java.util.List;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.dto.ReceiptDto;

public interface HibernateSearchService {
  List<ProductDto> searchForProducts(String searchTerm);

  List<ReceiptDto> searchForReceipts(String searchTerm);
}

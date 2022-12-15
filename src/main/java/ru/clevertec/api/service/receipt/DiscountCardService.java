package ru.clevertec.api.service.receipt;

import java.util.List;
import ru.clevertec.api.model.dto.DiscountCardDto;
import ru.clevertec.api.model.entity.DiscountCard;

public interface DiscountCardService {
  List<DiscountCardDto> getAll();

  DiscountCardDto getById(Long id);

  DiscountCard get(Long id);

  DiscountCardDto add(DiscountCardDto discountCard);

  DiscountCardDto update(DiscountCardDto discountCard);

  void delete(Long id);
}

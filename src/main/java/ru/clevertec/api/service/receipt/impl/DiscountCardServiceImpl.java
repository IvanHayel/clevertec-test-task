package ru.clevertec.api.service.receipt.impl;

import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.clevertec.api.exception.receipt.DiscountCardNotFoundException;
import ru.clevertec.api.mapper.DiscountCardMapper;
import ru.clevertec.api.model.dto.DiscountCardDto;
import ru.clevertec.api.model.entity.DiscountCard;
import ru.clevertec.api.repository.DiscountCardRepository;
import ru.clevertec.api.service.receipt.DiscountCardService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountCardServiceImpl implements DiscountCardService {
  DiscountCardRepository repository;
  DiscountCardMapper mapper;

  @Override
  public List<DiscountCardDto> getAll() {
    var cards = repository.findAll();
    return mapper.toDtoList(cards);
  }

  @Override
  public DiscountCardDto getById(Long id) {
    var card = get(id);
    return mapper.toDto(card);
  }

  @Override
  public DiscountCard get(Long id) {
    return repository.findById(id).orElseThrow(() -> new DiscountCardNotFoundException(id));
  }

  @Override
  public DiscountCardDto add(DiscountCardDto discountCard) {
    var card = mapper.toEntity(discountCard);
    return mapper.toDto(repository.save(card));
  }

  @Override
  public DiscountCardDto update(DiscountCardDto discountCardDto) {
    var id = discountCardDto.getId();
    if (Objects.nonNull(id) && isExist(id)) {
      var discountCard = mapper.toEntity(discountCardDto);
      return mapper.toDto(repository.save(discountCard));
    }
    throw new DiscountCardNotFoundException(id);
  }

  @Override
  public void delete(Long id) {
    var card = get(id);
    repository.delete(card);
  }

  private boolean isExist(Long id) {
    return repository.existsById(id);
  }
}

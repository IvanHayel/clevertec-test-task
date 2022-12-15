package ru.clevertec.api.service.receipt.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.clevertec.api.exception.receipt.ReceiptNotFoundException;
import ru.clevertec.api.mapper.ReceiptMapper;
import ru.clevertec.api.mapper.ReceiptReadOnlyMapper;
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.dto.ReceiptReadOnlyDto;
import ru.clevertec.api.model.entity.Receipt;
import ru.clevertec.api.model.payload.request.ReceiptRequest;
import ru.clevertec.api.repository.ReceiptRepository;
import ru.clevertec.api.service.receipt.DiscountCardService;
import ru.clevertec.api.service.receipt.ReceiptPositionService;
import ru.clevertec.api.service.receipt.ReceiptService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptServiceImpl implements ReceiptService {
  ReceiptRepository repository;
  ReceiptMapper mapper;
  ReceiptReadOnlyMapper readOnlyMapper;

  ReceiptPositionService positionService;
  DiscountCardService discountCardService;

  @Override
  public List<ReceiptReadOnlyDto> getAll() {
    var receipts = repository.findAll();
    return readOnlyMapper.toDtoList(receipts);
  }

  @Transactional
  @Override
  public ReceiptDto getById(Long id) {
    var receipt = repository.findById(id).orElseThrow(() -> new ReceiptNotFoundException(id));
    return mapper.toDto(receipt);
  }

  @Override
  public ReceiptDto add(ReceiptRequest request) {
    Receipt receipt = parseReceiptRequest(request);
    return mapper.toDto(repository.save(receipt));
  }

  @Transactional
  @Override
  public ReceiptDto update(ReceiptRequest request) {
    var id = request.getId();
    if (Objects.nonNull(id) && isExist(id)) {
      var toUpdate = repository.findById(id).orElseThrow(() -> new ReceiptNotFoundException(id));
      var receipt = parseReceiptRequest(request);
      positionService.removePositions(toUpdate.getPositions());
      toUpdate.copy(receipt);
      return mapper.toDto(repository.save(toUpdate));
    }
    throw new ReceiptNotFoundException(id);
  }

  @Override
  public void delete(Long id) {
    var receipt = repository.findById(id).orElseThrow(() -> new ReceiptNotFoundException(id));
    repository.delete(receipt);
  }

  private Receipt parseReceiptRequest(ReceiptRequest request) {
    var id = request.getId();
    var description = request.getDescription();
    var products = request.getProducts();
    var positions = positionService.formPositions(products);
    var cardId = request.getDiscountCard();
    var card = Objects.isNull(cardId) ? null : discountCardService.get(cardId);
    var receipt = new Receipt(description, positions, card);
    receipt.setId(id);
    return receipt;
  }

  private boolean isExist(Long id) {
    return repository.existsById(id);
  }
}

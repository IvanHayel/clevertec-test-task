package ru.clevertec.api.web.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.api.model.dto.DiscountCardDto;
import ru.clevertec.api.model.payload.ServerResponse;
import ru.clevertec.api.model.payload.response.MessageResponse;
import ru.clevertec.api.service.receipt.DiscountCardService;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountCardController {
  DiscountCardService discountCardService;

  @GetMapping
  public List<DiscountCardDto> getDiscountCards() {
    return discountCardService.getAll();
  }

  @GetMapping("/{id}")
  public DiscountCardDto getDiscountCardById(@PathVariable Long id) {
    return discountCardService.getById(id);
  }

  @PostMapping
  public DiscountCardDto addDiscountCard(@RequestBody @Valid DiscountCardDto discountCard) {
    return discountCardService.add(discountCard);
  }

  @PutMapping
  public DiscountCardDto updateDiscountCard(@RequestBody @Valid DiscountCardDto discountCard) {
    return discountCardService.update(discountCard);
  }

  @DeleteMapping("/{id}")
  public ServerResponse deleteDiscountCard(@PathVariable Long id) {
    discountCardService.delete(id);
    return MessageResponse.SUCCESS_DELETE_DISCOUNT_CARD;
  }
}

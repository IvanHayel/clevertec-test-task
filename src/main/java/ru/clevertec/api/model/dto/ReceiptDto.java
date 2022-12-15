package ru.clevertec.api.model.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptDto implements Serializable {
  Long id;

  String description;

  Set<ReceiptPositionDto> positions;

  DiscountCardDto discountCard;

  Double total;

  Double discount;

  ZonedDateTime createDate;

  ZonedDateTime updateDate;
}

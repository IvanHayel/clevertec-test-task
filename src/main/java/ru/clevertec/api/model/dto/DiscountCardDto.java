package ru.clevertec.api.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class DiscountCardDto implements Serializable {
  @Positive(message = "Card ID must be positive!")
  Long id;

  @NotNull(message = "Discount cannot be null!")
  @PositiveOrZero(message = "Discount must be positive or zero!")
  @DecimalMax(value = "1.0", message = "Discount must be less than 1.0!")
  @Digits(
      integer = 1,
      fraction = 2,
      message =
          "Discount integer part must be between 0 and 1, "
              + "fractional part must be between 0 and 2!")
  BigDecimal discount;
}

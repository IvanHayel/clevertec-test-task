package ru.clevertec.api.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
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
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto implements Serializable {
  @Positive(message = "Product ID must be positive!")
  Long id;

  @NotNull(message = "Product description cannot be null!")
  @NotBlank(message = "Product description cannot be blank!")
  @Length(min = 1, max = 255, message = "Product description length must be between 1 and 255!")
  String description;

  @NotNull(message = "Product price cannot be null!")
  @PositiveOrZero(message = "Product price must be positive or zero!")
  @Digits(
      integer = 9,
      fraction = 2,
      message =
          "Product price integer part must be between 0 and 9, "
              + "fractional part must be between 0 and 2!")
  BigDecimal price;
}

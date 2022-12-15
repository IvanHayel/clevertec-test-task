package ru.clevertec.api.model.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.clevertec.api.model.payload.ServerRequest;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptRequest implements ServerRequest {
  @Positive(message = "Receipt ID must be positive!")
  Long id;

  @NotNull(message = "Receipt must contain description field!")
  @NotBlank(message = "Receipt description must not be blank!")
  @Size(min = 1, max = 255, message = "Description length must be in range from 1 to 255!")
  String description;

  @NotNull(message = "Receipt products must not be null!")
  @Size(min = 1, message = "Receipt products must not be empty!")
  Map<String, String> products;

  @Positive(message = "Discount card number must be positive!")
  Long discountCard;
}

package ru.clevertec.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "discount_cards")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountCard extends BaseEntity {
  @Column(nullable = false, precision = 1, scale = 2)
  Double discount;
}

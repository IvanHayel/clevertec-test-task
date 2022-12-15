package ru.clevertec.api.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed
@Table(name = "receipts")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Receipt extends BaseEntity {
  private static final Double DEFAULT_DISCOUNT = 0.0;

  @Column(nullable = false)
  @FullTextField
  String description;

  @OneToMany(
      cascade = {CascadeType.REMOVE, CascadeType.MERGE},
      orphanRemoval = true)
  @JoinTable(name = "receipt_positions")
  Set<ReceiptPosition> positions = new HashSet<>();

  @OneToOne DiscountCard discountCard;

  @Column(nullable = false, scale = 2)
  Double total;

  @Column(nullable = false, scale = 2)
  Double discount;

  public Receipt(String description, Set<ReceiptPosition> positions, DiscountCard discountCard) {
    this.description = description;
    this.positions = positions;
    this.discountCard = discountCard;
    calculateTotal();
  }

  public void setPositions(Set<ReceiptPosition> positions) {
    this.positions.clear();
    this.positions.addAll(positions);
    calculateTotal();
  }

  public void copy(Receipt other) {
    setId(other.getId());
    setDescription(other.getDescription());
    setDiscountCard(other.getDiscountCard());
    setPositions(other.getPositions());
  }

  private void calculateTotal() {
    total = positions.stream().mapToDouble(ReceiptPosition::getTotal).sum();
    discount = Objects.isNull(discountCard) ? DEFAULT_DISCOUNT : total * discountCard.getDiscount();
    discount = round(discount);
    total = round(total - discount);
  }

  private double round(double value) {
    return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }
}

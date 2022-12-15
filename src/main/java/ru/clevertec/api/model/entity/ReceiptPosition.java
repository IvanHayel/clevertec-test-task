package ru.clevertec.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

@Entity
@Table(name = "positions")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptPosition extends BaseEntity {
  @ManyToOne(optional = false)
  Product product;

  @Column(nullable = false)
  Integer quantity;

  @Column(nullable = false, scale = 2)
  Double total;

  @PrePersist
  void prePersist() {
    calculateTotal();
  }

  @PreUpdate
  void preUpdate() {
    calculateTotal();
  }

  private void calculateTotal() {
    total = quantity * product.getPrice();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ReceiptPosition that = (ReceiptPosition) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

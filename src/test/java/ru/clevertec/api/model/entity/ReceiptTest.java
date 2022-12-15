package ru.clevertec.api.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReceiptTest {
  private static final String TEST_DESCRIPTION = "Some description";
  private static final Set<ReceiptPosition> TEST_POSITIONS = new HashSet<>();
  private static final DiscountCard TEST_DISCOUNT_CARD = new DiscountCard();

  private static final double TEST_EXPECTED_TOTAL = 90.0;
  private static final double TEST_EXPECTED_DISCOUNT = 10.0;

  static {
    var testPosition = new ReceiptPosition();
    testPosition.setQuantity(1);
    testPosition.setTotal(100.0);
    TEST_POSITIONS.add(testPosition);

    TEST_DISCOUNT_CARD.setDiscount(0.1);
  }

  /** Method under test: {@link Receipt#Receipt(String, Set, DiscountCard)} */
  @Test
  void testConstructor() {
    var actualReceipt = new Receipt(TEST_DESCRIPTION, TEST_POSITIONS, TEST_DISCOUNT_CARD);
    assertEquals(TEST_EXPECTED_TOTAL, actualReceipt.getTotal().doubleValue());
    assertFalse(actualReceipt.getPositions().isEmpty());
    assertSame(TEST_DISCOUNT_CARD, actualReceipt.getDiscountCard());
    assertEquals(TEST_EXPECTED_DISCOUNT, actualReceipt.getDiscount().doubleValue());
    assertEquals(TEST_DESCRIPTION, actualReceipt.getDescription());
  }

  /** Method under test: {@link Receipt#setPositions(Set)} */
  @Test
  void testSetPositions() {
    Receipt receipt = new Receipt();
    receipt.setDiscountCard(TEST_DISCOUNT_CARD);
    receipt.setPositions(TEST_POSITIONS);
    assertEquals(TEST_EXPECTED_TOTAL, receipt.getTotal().doubleValue());
    assertFalse(receipt.getPositions().isEmpty());
    assertEquals(TEST_EXPECTED_DISCOUNT, receipt.getDiscount().doubleValue());
  }

  /** Method under test: {@link Receipt#copy(Receipt)} */
  @Test
  void testCopy() {
    Receipt receipt = new Receipt();
    receipt.copy(new Receipt(TEST_DESCRIPTION, TEST_POSITIONS, TEST_DISCOUNT_CARD));
    assertEquals(TEST_EXPECTED_TOTAL, receipt.getTotal().doubleValue());
    assertFalse(receipt.getPositions().isEmpty());
    assertEquals(TEST_EXPECTED_DISCOUNT, receipt.getDiscount().doubleValue());
  }
}

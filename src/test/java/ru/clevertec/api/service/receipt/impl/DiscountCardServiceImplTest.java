package ru.clevertec.api.service.receipt.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.api.exception.receipt.DiscountCardNotFoundException;
import ru.clevertec.api.mapper.DiscountCardMapper;
import ru.clevertec.api.model.dto.DiscountCardDto;
import ru.clevertec.api.model.entity.DiscountCard;
import ru.clevertec.api.repository.DiscountCardRepository;
import ru.clevertec.api.service.receipt.DiscountCardService;

@ContextConfiguration(classes = {DiscountCardServiceImpl.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class DiscountCardServiceImplTest {
  private static final Long TEST_ID = 123L;
  private static final Double TEST_DISCOUNT = 0.5;
  private static final DiscountCard TEST_CARD;
  private static final DiscountCardDto TEST_CARD_DTO;
  private static final List<DiscountCard> TEST_CARD_LIST = new ArrayList<>();
  private static final List<DiscountCardDto> TEST_CARD_DTO_LIST = new ArrayList<>();

  static {
    TEST_CARD = new DiscountCard();
    TEST_CARD.setId(TEST_ID);
    TEST_CARD.setDiscount(TEST_DISCOUNT);
    TEST_CARD_LIST.add(TEST_CARD);

    TEST_CARD_DTO = new DiscountCardDto(TEST_ID, BigDecimal.valueOf(TEST_DISCOUNT));
    TEST_CARD_DTO_LIST.add(TEST_CARD_DTO);
  }

  @MockBean DiscountCardMapper discountCardMapper;
  @MockBean DiscountCardRepository discountCardRepository;
  @Autowired DiscountCardService discountCardService;

  /** Method under test: {@link DiscountCardServiceImpl#getAll()} */
  @Test
  void testGetAll() {
    when(discountCardRepository.findAll()).thenReturn(TEST_CARD_LIST);
    when(discountCardMapper.toDtoList(any())).thenReturn(TEST_CARD_DTO_LIST);
    var actualAll = discountCardService.getAll();
    assertSame(TEST_CARD_DTO_LIST, actualAll);
    assertFalse(actualAll.isEmpty());
    verify(discountCardRepository).findAll();
    verify(discountCardMapper).toDtoList(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#getById(Long)} */
  @Test
  void testGetById() {
    var ofResult = Optional.of(TEST_CARD);
    when(discountCardRepository.findById(any())).thenReturn(ofResult);
    when(discountCardMapper.toDto(any())).thenReturn(TEST_CARD_DTO);
    var actualById = discountCardService.getById(TEST_ID);
    assertSame(TEST_CARD_DTO, actualById);
    assertEquals(BigDecimal.valueOf(TEST_DISCOUNT), actualById.getDiscount());
    verify(discountCardRepository).findById(any());
    verify(discountCardMapper).toDto(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#getById(Long)} */
  @Test
  void testGetByIdWithDiscountCardNotFoundException() {
    when(discountCardRepository.findById(any())).thenReturn(Optional.empty());
    when(discountCardMapper.toDto(any())).thenReturn(TEST_CARD_DTO);
    assertThrows(DiscountCardNotFoundException.class, () -> discountCardService.getById(123L));
    verify(discountCardRepository).findById(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#get(Long)} */
  @Test
  void testGet() {
    var ofResult = Optional.of(TEST_CARD);
    when(discountCardRepository.findById(any())).thenReturn(ofResult);
    assertSame(TEST_CARD, discountCardService.get(TEST_ID));
    verify(discountCardRepository).findById(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#get(Long)} */
  @Test
  void testGetWithDiscountCardNotFoundException() {
    when(discountCardRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(DiscountCardNotFoundException.class, () -> discountCardService.get(TEST_ID));
    verify(discountCardRepository).findById(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#add(DiscountCardDto)} */
  @Test
  void testAdd() {
    when(discountCardRepository.save(any())).thenReturn(TEST_CARD);
    when(discountCardMapper.toDto(any())).thenReturn(TEST_CARD_DTO);
    when(discountCardMapper.toEntity(any())).thenReturn(TEST_CARD);
    var actualAddResult = discountCardService.add(TEST_CARD_DTO);
    assertSame(TEST_CARD_DTO, actualAddResult);
    assertEquals(BigDecimal.valueOf(TEST_DISCOUNT), actualAddResult.getDiscount());
    verify(discountCardRepository).save(any());
    verify(discountCardMapper).toDto(any());
    verify(discountCardMapper).toEntity(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#update(DiscountCardDto)} */
  @Test
  void testUpdate() {
    when(discountCardRepository.save(any())).thenReturn(TEST_CARD);
    when(discountCardRepository.existsById(any())).thenReturn(true);
    when(discountCardMapper.toDto(any())).thenReturn(TEST_CARD_DTO);
    when(discountCardMapper.toEntity(any())).thenReturn(TEST_CARD);
    var actualUpdateResult = discountCardService.update(TEST_CARD_DTO);
    assertSame(TEST_CARD_DTO, actualUpdateResult);
    assertEquals(BigDecimal.valueOf(TEST_DISCOUNT), actualUpdateResult.getDiscount());
    verify(discountCardRepository).existsById(any());
    verify(discountCardRepository).save(any());
    verify(discountCardMapper).toDto(any());
    verify(discountCardMapper).toEntity(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#update(DiscountCardDto)} */
  @Test
  void testUpdateWithDiscountCardNotFoundException() {
    when(discountCardRepository.save(any())).thenReturn(TEST_CARD);
    when(discountCardRepository.existsById(any())).thenReturn(false);
    when(discountCardMapper.toDto(any())).thenReturn(TEST_CARD_DTO);
    when(discountCardMapper.toEntity(any())).thenReturn(TEST_CARD);
    assertThrows(
        DiscountCardNotFoundException.class, () -> discountCardService.update(TEST_CARD_DTO));
    verify(discountCardRepository).existsById(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#delete(Long)} */
  @Test
  void testDelete() {
    var ofResult = Optional.of(TEST_CARD);
    doNothing().when(discountCardRepository).delete(any());
    when(discountCardRepository.findById(any())).thenReturn(ofResult);
    discountCardService.delete(TEST_ID);
    verify(discountCardRepository).findById(any());
    verify(discountCardRepository).delete(any());
  }

  /** Method under test: {@link DiscountCardServiceImpl#delete(Long)} */
  @Test
  void testDeleteWithDiscountCardNotFoundException() {
    doNothing().when(discountCardRepository).delete(any());
    when(discountCardRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(DiscountCardNotFoundException.class, () -> discountCardService.delete(TEST_ID));
    verify(discountCardRepository).findById(any());
  }
}

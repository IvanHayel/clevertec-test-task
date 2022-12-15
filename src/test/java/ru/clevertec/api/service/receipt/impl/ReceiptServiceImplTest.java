package ru.clevertec.api.service.receipt.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.api.exception.receipt.ReceiptNotFoundException;
import ru.clevertec.api.mapper.ReceiptMapper;
import ru.clevertec.api.mapper.ReceiptReadOnlyMapper;
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.dto.ReceiptReadOnlyDto;
import ru.clevertec.api.model.entity.Receipt;
import ru.clevertec.api.model.entity.ReceiptPosition;
import ru.clevertec.api.model.payload.request.ReceiptRequest;
import ru.clevertec.api.repository.ReceiptRepository;
import ru.clevertec.api.service.receipt.DiscountCardService;
import ru.clevertec.api.service.receipt.ReceiptPositionService;
import ru.clevertec.api.service.receipt.ReceiptService;

@ContextConfiguration(classes = {ReceiptServiceImpl.class})
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ReceiptServiceImplTest {
  private static final Long TEST_ID = 123L;
  private static final String TEST_DESCRIPTION = "Buxums resistere, tanquam bi-color gabalium.";
  private static final Set<ReceiptPosition> TEST_POSITIONS = new HashSet<>();
  private static final Receipt TEST_RECEIPT;
  private static final ReceiptDto TEST_RECEIPT_DTO;
  private static final ReceiptReadOnlyDto TEST_RECEIPT_READ_ONLY_DTO;
  private static final ReceiptRequest TEST_RECEIPT_REQUEST;
  private static final List<Receipt> TEST_RECEIPT_LIST = new ArrayList<>();
  private static final List<ReceiptReadOnlyDto> TEST_RECEIPT_READ_ONLY_DTO_LIST = new ArrayList<>();

  static {
    TEST_RECEIPT = new Receipt(TEST_DESCRIPTION, TEST_POSITIONS, null);
    TEST_RECEIPT.setId(TEST_ID);
    TEST_RECEIPT_LIST.add(TEST_RECEIPT);

    TEST_RECEIPT_REQUEST = new ReceiptRequest(TEST_ID, TEST_DESCRIPTION, new HashMap<>(), null);

    TEST_RECEIPT_DTO = new ReceiptDto(TEST_ID, TEST_DESCRIPTION, null, null, 0.0, 0.0, null, null);

    TEST_RECEIPT_READ_ONLY_DTO = new ReceiptReadOnlyDto(TEST_ID, TEST_DESCRIPTION, null, null);
    TEST_RECEIPT_READ_ONLY_DTO_LIST.add(TEST_RECEIPT_READ_ONLY_DTO);
  }

  @MockBean ReceiptRepository receiptRepository;
  @MockBean DiscountCardService discountCardService;
  @MockBean ReceiptPositionService receiptPositionService;
  @MockBean ReceiptMapper receiptMapper;
  @MockBean ReceiptReadOnlyMapper receiptReadOnlyMapper;
  @Autowired ReceiptService receiptService;

  /** Method under test: {@link ReceiptServiceImpl#getAll()} */
  @Test
  void testGetAll() {
    when(receiptRepository.findAll()).thenReturn(TEST_RECEIPT_LIST);
    when(receiptReadOnlyMapper.toDtoList(any())).thenReturn(TEST_RECEIPT_READ_ONLY_DTO_LIST);
    var actualAll = receiptService.getAll();
    assertSame(TEST_RECEIPT_READ_ONLY_DTO_LIST, actualAll);
    assertFalse(actualAll.isEmpty());
    verify(receiptRepository).findAll();
    verify(receiptReadOnlyMapper).toDtoList(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#getById(Long)} */
  @Test
  void testGetById() {
    when(receiptRepository.findById(any())).thenReturn(Optional.of(TEST_RECEIPT));
    when(receiptMapper.toDto(any())).thenReturn(TEST_RECEIPT_DTO);
    var actualById = receiptService.getById(TEST_ID);
    assertSame(TEST_RECEIPT_DTO, actualById);
    verify(receiptRepository).findById(any());
    verify(receiptMapper).toDto(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#getById(Long)} */
  @Test
  void testGetByIdWithReceiptNotFoundException() {
    when(receiptRepository.findById(any())).thenReturn(Optional.empty());
    when(receiptMapper.toDto(any())).thenReturn(TEST_RECEIPT_DTO);
    assertThrows(ReceiptNotFoundException.class, () -> receiptService.getById(TEST_ID));
    verify(receiptRepository).findById(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#add(ReceiptRequest)} */
  @Test
  void testAdd() {
    when(receiptRepository.save(any())).thenReturn(TEST_RECEIPT);
    when(receiptMapper.toDto(any())).thenReturn(TEST_RECEIPT_DTO);
    when(receiptPositionService.formPositions(any())).thenReturn(TEST_POSITIONS);
    var actualAddResult = receiptService.add(TEST_RECEIPT_REQUEST);
    assertSame(TEST_RECEIPT_DTO, actualAddResult);
    verify(receiptRepository).save(any());
    verify(receiptMapper).toDto(any());
    verify(receiptPositionService).formPositions(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#update(ReceiptRequest)} */
  @Test
  void testUpdate() {
    when(receiptRepository.save(any())).thenReturn(TEST_RECEIPT);
    when(receiptRepository.findById(any())).thenReturn(Optional.of(TEST_RECEIPT));
    when(receiptRepository.existsById(any())).thenReturn(true);
    when(receiptMapper.toDto(any())).thenReturn(TEST_RECEIPT_DTO);
    when(receiptPositionService.formPositions(any())).thenReturn(TEST_POSITIONS);
    doNothing().when(receiptPositionService).removePositions(any());
    var actualUpdateResult = receiptService.update(TEST_RECEIPT_REQUEST);
    assertSame(TEST_RECEIPT_DTO, actualUpdateResult);
    verify(receiptRepository).existsById(any());
    verify(receiptRepository).save(any());
    verify(receiptRepository).findById(any());
    verify(receiptMapper).toDto(any());
    verify(receiptPositionService).formPositions(any());
    verify(receiptPositionService).removePositions(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#update(ReceiptRequest)} */
  @Test
  void testUpdateWithReceiptNotFoundException() {
    var ofResult = Optional.of(TEST_RECEIPT);
    when(receiptRepository.save(any())).thenReturn(TEST_RECEIPT);
    when(receiptRepository.findById(any())).thenReturn(ofResult);
    when(receiptRepository.existsById(any())).thenReturn(false);
    when(receiptMapper.toDto(any())).thenReturn(TEST_RECEIPT_DTO);
    when(receiptPositionService.formPositions(any())).thenReturn(TEST_POSITIONS);
    doNothing().when(receiptPositionService).removePositions(any());
    assertThrows(ReceiptNotFoundException.class, () -> receiptService.update(TEST_RECEIPT_REQUEST));
    verify(receiptRepository).existsById(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#delete(Long)} */
  @Test
  void testDelete() {
    doNothing().when(receiptRepository).delete(any());
    when(receiptRepository.findById(any())).thenReturn(Optional.of(TEST_RECEIPT));
    receiptService.delete(TEST_ID);
    verify(receiptRepository).findById(any());
    verify(receiptRepository).delete(any());
  }

  /** Method under test: {@link ReceiptServiceImpl#delete(Long)} */
  @Test
  void testDeleteWithReceiptNotFoundException() {
    doNothing().when(receiptRepository).delete(any());
    when(receiptRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(ReceiptNotFoundException.class, () -> receiptService.delete(TEST_ID));
    verify(receiptRepository).findById(any());
  }
}

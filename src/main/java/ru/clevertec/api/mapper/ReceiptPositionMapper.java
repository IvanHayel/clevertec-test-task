package ru.clevertec.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.api.model.dto.ReceiptPositionDto;
import ru.clevertec.api.model.entity.ReceiptPosition;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {ProductMapper.class})
public interface ReceiptPositionMapper {
  ReceiptPosition toEntity(ReceiptPositionDto receiptPositionDto);

  ReceiptPositionDto toDto(ReceiptPosition receiptPosition);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ReceiptPosition partialUpdate(
      ReceiptPositionDto receiptPositionDto, @MappingTarget ReceiptPosition receiptPosition);
}

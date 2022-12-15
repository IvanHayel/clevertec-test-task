package ru.clevertec.api.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.api.model.dto.ReceiptReadOnlyDto;
import ru.clevertec.api.model.entity.Receipt;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReceiptReadOnlyMapper {
  Receipt toEntity(ReceiptReadOnlyDto receiptReadOnlyDto);

  ReceiptReadOnlyDto toDto(Receipt receipt);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Receipt partialUpdate(ReceiptReadOnlyDto receiptReadOnlyDto, @MappingTarget Receipt receipt);

  List<ReceiptReadOnlyDto> toDtoList(List<Receipt> receipts);
}

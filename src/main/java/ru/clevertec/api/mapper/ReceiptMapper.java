package ru.clevertec.api.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.entity.Receipt;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {ReceiptPositionMapper.class})
public interface ReceiptMapper {
  Receipt toEntity(ReceiptDto receiptDto);

  ReceiptDto toDto(Receipt receipt);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Receipt partialUpdate(ReceiptDto receiptDto, @MappingTarget Receipt receipt);

  List<ReceiptDto> toDtoList(List<Receipt> receipts);
}

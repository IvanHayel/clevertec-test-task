package ru.clevertec.api.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.api.model.dto.DiscountCardDto;
import ru.clevertec.api.model.entity.DiscountCard;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DiscountCardMapper {
  DiscountCard toEntity(DiscountCardDto discountCardDto);

  DiscountCardDto toDto(DiscountCard discountCard);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  DiscountCard partialUpdate(
      DiscountCardDto discountCardDto, @MappingTarget DiscountCard discountCard);

  List<DiscountCardDto> toDtoList(List<DiscountCard> discountCards);
}

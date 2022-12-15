package ru.clevertec.api.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.entity.Product;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
  Product toEntity(ProductDto productDto);

  ProductDto toDto(Product product);

  List<ProductDto> toDtoList(List<Product> products);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Product partialUpdate(ProductDto productDto, @MappingTarget Product product);
}

package ru.clevertec.api.service.search.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import ru.clevertec.api.exception.search.HibernateSearchInitializationException;
import ru.clevertec.api.mapper.ProductMapper;
import ru.clevertec.api.mapper.ReceiptMapper;
import ru.clevertec.api.model.dto.ProductDto;
import ru.clevertec.api.model.dto.ReceiptDto;
import ru.clevertec.api.model.entity.Product;
import ru.clevertec.api.model.entity.Receipt;
import ru.clevertec.api.model.search.ProductSearchFields;
import ru.clevertec.api.service.search.HibernateSearchService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HibernateSearchServiceImpl implements HibernateSearchService {
  final ProductMapper productMapper;
  final ReceiptMapper receiptMapper;

  @PersistenceContext EntityManager entityManager;

  boolean isInitialized = false;

  public void initializeHibernateSearch() {
    try {
      var searchSession = Search.session(entityManager);
      var indexer = searchSession.massIndexer(Product.class, Receipt.class);
      indexer.startAndWait();
      isInitialized = true;
    } catch (InterruptedException exception) {
      throw new HibernateSearchInitializationException();
    }
  }

  private <T> SearchResult<T> searchAll(Class<T> type, String searchTerm, String... fields) {
    if (!isInitialized) initializeHibernateSearch();
    var searchSession = Search.session(entityManager);
    return searchSession
        .search(type)
        .where(field -> field.match().fields(fields).matching(searchTerm))
        .fetchAll();
  }

  @Override
  @Transactional
  public List<ProductDto> searchForProducts(String searchTerm) {
    var result = searchAll(Product.class, searchTerm, ProductSearchFields.getAllFieldNames());
    return productMapper.toDtoList(result.hits());
  }

  @Override
  @Transactional
  public List<ReceiptDto> searchForReceipts(String searchTerm) {
    var result = searchAll(Receipt.class, searchTerm, ProductSearchFields.getAllFieldNames());
    return receiptMapper.toDtoList(result.hits());
  }
}

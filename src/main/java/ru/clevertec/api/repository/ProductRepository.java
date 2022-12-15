package ru.clevertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.api.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}

package ru.clevertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.api.model.entity.ReceiptPosition;

@Repository
public interface ReceiptPositionRepository extends JpaRepository<ReceiptPosition, Long> {}

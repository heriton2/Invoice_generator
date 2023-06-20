package com.challenge.invoice_generator.interfaces.repository;

import com.challenge.invoice_generator.entity.ImportedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportedItemRepository extends JpaRepository<ImportedItem, Long> {
}


package com.challenge.invoice_generator.repository;

import com.challenge.invoice_generator.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}

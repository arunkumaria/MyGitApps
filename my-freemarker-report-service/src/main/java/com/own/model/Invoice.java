package com.own.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    private String invoiceNumber;
    private String customerName;
    private List<InvoiceItem> items;
    private double totalAmount;
}

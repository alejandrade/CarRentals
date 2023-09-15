package com.techisgood.carrentals.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/payments/v1")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminPaymentsEndpoints {
    private final PaymentsService paymentsService;

    @GetMapping("/statement")
    public ResponseEntity<byte[]> exportCsv(@RequestParam Date startDate) {
            List<PaymentStatementsRow> data = paymentsService.createStatement(startDate);
            String csvContent = generateCSVContent(data);
            // Generate the CSV content as a string
            // Convert the CSV content to bytes
            byte[] csvBytes = csvContent.getBytes();

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "statement.csv");
            headers.setContentLength(csvBytes.length); // Set the content length

            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    private String generateCSVContent(List<PaymentStatementsRow> rows) {
        // Generate the CSV content as a string (similar to previous example)
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("location,subTotal,taxRate,taxTotal,total,paidBy,invoiceType,createdAt\n");
        for (PaymentStatementsRow row : rows) {
            csvContent.append(
                    row.getLocation() + "," +
                            row.getSubTotal() + "," +
                            row.getTaxRate() + "," +
                            row.getTaxTotal() + "," +
                            row.getTotal() + "," +
                            (row.getPaidBy() != null ? row.getPaidBy() : "") + "," +
                            row.getInvoiceType() + "," +
                            row.getCreatedAt() + "\n"
            );
        }
        return csvContent.toString();
    }

}

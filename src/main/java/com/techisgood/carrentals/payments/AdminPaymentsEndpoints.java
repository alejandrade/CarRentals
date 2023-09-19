package com.techisgood.carrentals.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/payments/v1")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminPaymentsEndpoints {
    private final PaymentsService paymentsService;

    @GetMapping("/statement")
    public ResponseEntity<byte[]> exportCsv(@RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
            List<PaymentStatementsRow> data = paymentsService.createStatement(startDate);
            String csvContent = generateCSVContent(data);
            byte[] csvBytes = csvContent.getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        headers.setContentDispositionFormData("attachment", """
                    statement-%s.csv""".formatted(isoFormat.format(startDate)));
            headers.setContentLength(csvBytes.length);
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

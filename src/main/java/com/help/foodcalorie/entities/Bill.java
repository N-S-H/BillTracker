package com.help.foodcalorie.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bill")
public class Bill {
    @Id
    private String billNumber;
    private String billedTo;
    private String billedDate;
    private Double billedPrice;
    private Double billTax;
    private Double totalPrice;
}

package com.moaplace.dto.payment;

import lombok.Data;

@Data
public class PaymentDTO {
   private int booking_price;
   private String payment_method;
   private String merchant_uid;
   private String imp_uid;
   private String payment_status;
}

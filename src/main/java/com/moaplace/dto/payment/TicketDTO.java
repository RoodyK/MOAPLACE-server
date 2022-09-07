package com.moaplace.dto.payment;

import lombok.Data;

@Data
public class TicketDTO {
   private String grade;
   private int count;
   private int priceA;
   private int priceY;
   private int countA;
   private int countY;
}

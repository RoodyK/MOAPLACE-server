package com.moaplace.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TicketDTO {
   private String grade;
   private int count;
   private int priceA;
   private int priceY;
   private int countA;
   private int countY;
}

package com.moaplace.dto.payment;

import java.util.List;

import lombok.Data;

@Data
public class BookingDTO {
 private int booking_num;
 private int booking_count;
 private int booking_price;
 private List<String> booking_seat;
 private int member_num;
 private int schedule_num;
 private int use_point;
}

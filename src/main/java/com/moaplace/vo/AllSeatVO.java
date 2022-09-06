package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllSeatVO {
    private int all_seat_num;
    private int schedule_num;
    private int show_num;
    private String booking_seat;
    private int booking_num;

}

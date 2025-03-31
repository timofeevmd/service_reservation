package com.service.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationCreateDTO {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String status;
    private Long userId;
    private Long itemId;
}

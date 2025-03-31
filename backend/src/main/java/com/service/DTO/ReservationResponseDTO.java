package com.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
    private Long id;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String status;
    private UserDTO user;
    private Long itemId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private Long id;
        private String username;
    }
}

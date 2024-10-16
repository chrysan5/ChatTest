package com.example.chatTest.rabbitmqtest;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuctionInfoMessage {
    private String title;
    private String endTime;
    //private String registerMemberUsername;
    private Long registerMemberUserId;
}

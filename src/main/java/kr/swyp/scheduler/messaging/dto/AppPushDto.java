package kr.swyp.scheduler.messaging.dto;

import kr.swyp.scheduler.messaging.enums.AppPushTokenOsType;
import lombok.Builder;
import lombok.Getter;

public class AppPushDto {

    @Getter
    @Builder
    public static class AppPushRequestDto {

        private String token;
        private AppPushTokenOsType osType;
        private String title;
        private String body;
    }
}

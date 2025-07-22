package kr.swyp.scheduler.messaging.service;

import kr.swyp.scheduler.messaging.dto.AppPushDto.AppPushRequestDto;

public interface FcmService {

    void sendMessage(AppPushRequestDto requestDto);
}

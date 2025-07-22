package kr.swyp.scheduler.messaging.service;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.swyp.scheduler.messaging.dto.AppPushDto.AppPushRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendMessage(AppPushRequestDto requestDto) {
        try {
            Message message = switch (requestDto.getOsType()) {
                case ANDROID -> getAosMessage(requestDto);
                case IOS -> getIosMessage(requestDto);
            };

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Firebase 메시지 전송 중 예외 발생: {}", e.getMessage());
        }
    }

    private Message getAosMessage(AppPushRequestDto requestDto) {
        return Message.builder()
                .setToken(requestDto.getToken())
                .putData("title", requestDto.getTitle())
                .putData("body", requestDto.getTitle())
                .build();
    }

    private Message getIosMessage(AppPushRequestDto requestDto) {
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setSound("default")
                        .build())
                .build();

        return Message.builder()
                .setToken(requestDto.getToken())
                .setNotification(notification)
                .setApnsConfig(apnsConfig)
                .build();
    }
}

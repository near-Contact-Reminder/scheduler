package kr.swyp.scheduler.messaging.service;

import java.io.IOException;

public interface FirebaseClientService {

    void sendMessage(String message) throws IOException;
}

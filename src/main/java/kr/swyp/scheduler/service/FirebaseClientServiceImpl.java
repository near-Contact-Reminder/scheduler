package kr.swyp.scheduler.service;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import kr.swyp.scheduler.client.FirebaseClient;
import kr.swyp.scheduler.common.config.FirebaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FirebaseClientServiceImpl implements FirebaseClientService {

    private final FirebaseClient firebaseClient;
    private final FirebaseProperties properties;

    @Override
    @Transactional
    public void sendMessage(String message) throws IOException {
        firebaseClient.sendMessage(getAccessToken(), message);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = properties.getKeyPath();

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();
    }
}

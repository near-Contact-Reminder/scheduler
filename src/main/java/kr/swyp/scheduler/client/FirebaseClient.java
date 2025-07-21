package kr.swyp.scheduler.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "firebase-client", url = "${swyp.firebase.url}")
public interface FirebaseClient {

    @PostMapping("/messages:send")
    ResponseEntity<String> sendMessage(@RequestHeader("Authorization") String authorization,
            @RequestBody String message);
}

package crud.topic.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
public class TopicoContorller {

    @PostMapping("/create")
    public ResponseEntity createTopic() {
        return new ResponseEntity<>("Authenticated", HttpStatus.CREATED);
    }
}

package com.soulbridge.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class STTService {

//    @Value("${openAI.URI}")
    private final String uri = "https://api.openai.com/v1/audio/transcriptions";
    private final String token = "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";


    public String speechToText(String filename) {
        RestTemplate restTemplate = new RestTemplate();

        //set header
        HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.setBearerAuth(token);

        //set body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource("uploads/" + filename));
        body.add("model", "whisper-1");

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        HttpEntity<String> response = restTemplate.postForEntity(uri, requestMessage, String.class);
        log.info(response.getBody());

        return response.getBody().split(":")[1].replaceAll("\"", "").replaceAll("}", "");
    }
}

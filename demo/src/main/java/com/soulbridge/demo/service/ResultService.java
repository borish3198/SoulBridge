package com.soulbridge.demo.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.soulbridge.demo.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {

    private final String uri = "https://api.openai.com/v1/chat/completions";
    private final String token = "api-token";
    private final String header = "templet info";

    public String getAdvice(String worries) {
        RestTemplate restTemplate = new RestTemplate();

        //set header
        HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);

        //set body
//        Message message = new Message();
//        message.setRole("user");
//        message.setContent(templet + worries);
        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");

        JSONArray messages = new JSONArray();
//        JSONObject system = new JSONObject();
//        system.put("role", "system");
//        system.put("content", header);
//        messages.add(system);

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", header + worries);
        messages.add(message);

        body.put("messages", messages);

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        log.info("requestMessage : " + requestMessage.toString());

        HttpEntity<String> response = restTemplate.postForEntity(uri, requestMessage, String.class);

        log.info(response.getBody());

        return response.getBody();
    }
}

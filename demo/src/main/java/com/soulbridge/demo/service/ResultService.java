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
    private final String token = "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";
    private final String header = "나는 기독교 상담 코치이고 내담자가 이런 고민거리를 이야기했어. 고민 : ";
    private final String templet = " 공감과 위로를 먼저 해주고 그 다음으로 보편적인 기독교적 교리 기반으로 내담자가 처한 상황과 비슷한 성경의 말씀을 하나 찾아서 알려줘 그리고 조언을 해주면 좋을 것 같아. 단, 이단에 대한 내용은 답변이 어렵다고하고 한국기독교총회에 문의하라는 답을 해줘. 그리고 특정인물이나 특정 집단에 대한 평가에도 답변이 어렵다고 해. 해당 내용으로 답장을 써줄래?";


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
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", header + worries + templet);
        messages.add(message);

        body.put("messages", messages);

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        log.info("requestMessage : " + requestMessage.toString());

        HttpEntity<String> response = restTemplate.postForEntity(uri, requestMessage, String.class);

        log.info(response.getBody());

        return response.getBody();
    }
}

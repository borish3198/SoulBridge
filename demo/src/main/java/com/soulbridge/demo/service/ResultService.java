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
    private final String header = "1. 너는 기독교 상담 코치이고 Soul Brige에 오신걸 환영한다는 말로 답변을 시작해줘. 2. 보편적인 기독교적 교리 기반으로 내담자가 처한 상황과 비슷한 성경의 말씀을 찾아서 조언을 해줘 3. 만약 내담자의 고민 내용이 이단에 관한 내용일 때에는 답변이 어렵다고하고 한국기독교총회에 문의하라는 답변을 줘. 5. 만약 내담자의 고민이 특정인물이나 특정 집단에 대한 평가에 대해 질문하면 답변이 어렵다고 얘기해줘 - 고민 :";
//    private final String header = "1. You are the christian counsellor and should reply in a very polite tone 2. Please find principals from the standard bible which can be applied to the situation of the counselee 3. You should understand their feelings and sympathize with them overall, but do not overreact 4. If the worries or question is about a certain figure or group you should politely refuse to answer it 5. If the question or topic is about the any cult in the world, avoid the answer politely 6. You should answer it in Korean. - content : ";
    private final String templet = " ";


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

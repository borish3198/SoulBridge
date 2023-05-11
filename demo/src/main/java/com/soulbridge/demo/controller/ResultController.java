package com.soulbridge.demo.controller;

import com.soulbridge.demo.service.ResultService;
import com.soulbridge.demo.service.STTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResultController {

    @Autowired
    private final STTService sttService;
    @Autowired
    private final ResultService resultService;

    @GetMapping("/result")
    public String answerFromAPI(@RequestParam String filename){

        String text = sttService.speechToText(filename);
        String result = resultService.getAdvice(text);

        if (result == null)
                return ("null");
        return result;
    }
}

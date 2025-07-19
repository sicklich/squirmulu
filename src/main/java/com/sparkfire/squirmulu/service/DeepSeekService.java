package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.exception.ServiceException;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DeepSeekService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static String url = "https://api.deepseek.com/chat/completions";
    private static String apiKey = "sk-c78950a758bb4c95a121aa0f7fdbc848"; // 替换为你的 API Key

    public String answer(String content, BigDecimal temperature) {

        logger.info("测试房间记录：\n{}", content);

        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + (Objects.equals(temperature, new BigDecimal(-1)) ? apiKey + "2" : apiKey));

        // 构建 JSON 请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");
        requestBody.put("frequency_penalty", 0);
        requestBody.put("max_tokens", 2048);
        requestBody.put("presence_penalty", 0);
        requestBody.put("stop", null);
        requestBody.put("stream", false);
        requestBody.put("stream_options", null);
        requestBody.put("temperature", temperature);
        requestBody.put("top_p", 1);
        requestBody.put("tools", null);
        requestBody.put("tool_choice", "none");
        requestBody.put("logprobs", false);
        requestBody.put("top_logprobs", null);

        // 设置 response_format
        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "text");
        requestBody.put("response_format", responseFormat);

        // 设置 messages
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a novel editor"));
        messages.add(Map.of("role", "user", "content", content));
        requestBody.put("messages", messages);

        // 创建请求实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 发送 POST 请求
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        }catch (HttpClientErrorException e) { // 4xx 错误
            logger.info("error:{}, code:{}", e.getMessage(), e.getStatusCode().value());
            throw new ServiceException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpServerErrorException e) { // 5xx 错误
            throw new ServiceException(e.getResponseBodyAsString(), e.getStatusCode().value());
        } catch (RestClientException e) { // 其他错误，如网络问题
            throw new ServiceException("未知错误", -1);
        }
        // 输出 API 响应
        return response.getBody();
    }
}


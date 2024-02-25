package com.sulsul.suldaksuldak.component.batch;

import com.fasterxml.jackson.databind.JsonNode;
import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import com.sulsul.suldaksuldak.constant.batch.BatchServerUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
public class ToBatchServer {
    private final RestTemplate restTemplate;

    @Value("${jwt.key}")
    private String jwtKey;

    @Autowired
    public ToBatchServer() {
        HttpComponentsClientHttpRequestFactory factory
                = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);

        restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public Boolean partyBatchApiToBatchServer(
            Long partyPriKey,
            HttpMethod httpMethod
    ) {
        try {
            String localUrl = "http://localhost:8084" + BatchServerUrl.PARTY_SCHEDULE_URL.getUrl() + partyPriKey;

            HttpEntity<JsonNode> requestEntity = getPostHeaderAndBody();

            if (requestEntity == null) {
                return false;
            }

            UriComponents URI_COMPONENTS =
                    UriComponentsBuilder.fromUriString(localUrl)
                            .build();

            if (httpMethod.equals(HttpMethod.POST)) {
                ResponseEntity<JsonNode> postResult =
                        restTemplate.postForEntity(
                                URI_COMPONENTS.toUri(),
                                requestEntity,
                                JsonNode.class
                        );
                return Objects.requireNonNull(postResult.getBody()).get("data").asBoolean();
            } else if (httpMethod.equals(HttpMethod.PUT)) {
                ResponseEntity<JsonNode> putResult = restTemplate.exchange(
                        URI_COMPONENTS.toUri(),
                        httpMethod,
                        requestEntity,
                        JsonNode.class
                );
                return Objects.requireNonNull(putResult.getBody()).get("data").asBoolean();
            } else if (httpMethod.equals(HttpMethod.DELETE)) {
                ResponseEntity<JsonNode> putResult = restTemplate.exchange(
                        URI_COMPONENTS.toUri(),
                        httpMethod,
                        requestEntity,
                        JsonNode.class
                );
                return Objects.requireNonNull(putResult.getBody()).get("data").asBoolean();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private <T> HttpEntity<JsonNode> getPostHeaderAndBody(
//            T dto
    ) {
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule());
//            ObjectNode jsonNodes = objectMapper.valueToTree(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(SDTokken.REFRESH_HEADER.getText(), jwtKey);

            return new HttpEntity<>(headers);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

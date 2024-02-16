package com.sulsul.suldaksuldak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
public class SuldakBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SuldakBatchApplication.class, args);
    }
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);
    }
}

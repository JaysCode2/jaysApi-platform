package com.example.apiplatform.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TimeJob {
    @Scheduled(fixedRate = 5000)
    public void printJob(){
        log.info("job============");
    }
}

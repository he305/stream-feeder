package com.github.he305.streamfeeder.application.scheduler;

import com.github.he305.streamfeeder.common.scheduler.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Feeder {

    private final Task streamFeederTask;

    @Scheduled(fixedDelayString = "${scheduler.delay:300000}")
    public void updateChannels() {
        boolean result = streamFeederTask.doTask();
        log.info("Task ended, " + (result ? "success" : "failed"));
    }
}

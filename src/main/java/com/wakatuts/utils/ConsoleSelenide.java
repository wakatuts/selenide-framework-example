package com.wakatuts.utils;

import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleSelenide implements LogEventListener {
    @Override
    public void afterEvent(LogEvent logEvent) {
      log.info(logEvent.toString());
    }

    @Override
    public void beforeEvent(LogEvent logEvent) {
        log.info(logEvent.toString());
    }
}

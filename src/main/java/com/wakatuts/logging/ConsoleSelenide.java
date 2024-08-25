package com.wakatuts.logging;

import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleSelenide implements LogEventListener {

    @Override
    public void afterEvent(LogEvent logEvent) {
    }

    @Override
    public void beforeEvent(LogEvent logEvent) {
        log.info(logEvent.toString());
    }
}

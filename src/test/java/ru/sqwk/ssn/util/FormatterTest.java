package ru.sqwk.ssn.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты форматирования даты
 */
class FormatterTest {

    @Test
    void format() {
        System.out.println(Instant.now().plus(3, ChronoUnit.HOURS));
        System.out.println(Formatter.format(Date.from(Instant.now().plus(3, ChronoUnit.HOURS))));
        System.out.println(Formatter.format(Date.from(Instant.now().plus(2, ChronoUnit.HOURS))));
        System.out.println(Formatter.format(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))));
        System.out.println(Formatter.format(Date.from(Instant.now())));
        System.out.println(Date.from(Instant.now()));
        System.out.println(Formatter.format(Date.from(Instant.now().minus(1, ChronoUnit.HOURS))));
    }
}

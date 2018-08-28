package imie.campus.security.services.impl;

import imie.campus.security.configuration.Configuration;
import imie.campus.security.services.DateTimeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static imie.campus.utils.commons.GeneralUtils.fromDate;
import static java.lang.Math.abs;
import static java.time.ZonedDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class DateTimeServiceImplTest {

    private static long DUMMY_TTL = 86400;

    static DateTimeService service;

    static Configuration config;

    @BeforeEach
    void setUp() {
        config = mock(Configuration.class);
        service = new DateTimeServiceImpl(config);
    }

    @AfterEach
    void tearDown() {
        service = null;
        config = null;
    }

    @Test
    void testNow() {
        ZonedDateTime nowAtTest = now();
        long offset = abs(SECONDS.between(fromDate(service.now()), nowAtTest));
        assertTrue(offset < 10, "L'écart entre le temps fourni par le services et le " +
                "temps actuel est supérieur à 10 secondes. Ce test peut échouer si le moment entre lequel " +
                "le test appel la méthode cible est trop long");
    }

    /*@Test
    void expiration() {
        final long instant = currentTimeMillis();
        when(service.now()).thenReturn(new Date(instant));
        when(config.timeToLeave()).thenReturn(DUMMY_TTL);

        long expiration = service.expiration().getTime();
        assertEquals(expiration, instant + DUMMY_TTL);
    }*/
}
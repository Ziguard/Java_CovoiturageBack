package imie.campus.security.services.impl;

import imie.campus.security.configuration.Configuration;
import imie.campus.security.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

import static imie.campus.utils.commons.GeneralUtils.toDate;
import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class DateTimeServiceImpl implements DateTimeService {
    final ZonedDateTime now = ZonedDateTime.now();

    private final Configuration securityConfig;

    @Autowired
    public DateTimeServiceImpl(Configuration securityConfig) {
        this.securityConfig = securityConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date now() {
        return toDate(now);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date expiration() {
        return toDate(now.plus(securityConfig.timeToLeave(), SECONDS));
    }
}

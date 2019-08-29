package com.sailpoint.extended.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Contains common methods for all mappers
 */
@Mapper
public abstract class CommonMapper {

    /**
     * Name of qualifier name for converting localDateTime to string using {@link DateTimeFormatter#ISO_LOCAL_DATE}
     */
    public static final String LOCAL_DATE_TIME_TO_ISO_LOCAL_DATE_QUALIFIER_NAME = "asIsoLocalDateString";

    /**
     * Convert local date time to string using {@link DateTimeFormatter#ISO_LOCAL_DATE}
     *
     * @param localDateTime - local date time instance to string
     * @return string representation of local date time
     */
    @Named(LOCAL_DATE_TIME_TO_ISO_LOCAL_DATE_QUALIFIER_NAME)
    public String asIsoLocalDateString(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .orElse(null);
    }
}

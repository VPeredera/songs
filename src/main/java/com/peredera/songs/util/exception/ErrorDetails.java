package com.peredera.songs.util.exception;

import java.util.Date;

public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;

    ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}

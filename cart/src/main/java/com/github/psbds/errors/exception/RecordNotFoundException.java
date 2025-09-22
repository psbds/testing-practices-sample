package com.github.psbds.errors.exception;

import lombok.Getter;

@Getter
public class RecordNotFoundException extends RuntimeException {

    private String recordType;
    private String recordId;

    public RecordNotFoundException(String recordType, String recordId) {
        super(String.format("Record not found: %s with ID %s", recordType, recordId));
        this.recordType = recordType;
        this.recordId = recordId;
    }
}

package com.canvas.generator.flux.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FluxResultStatus {
    TASK_NOT_FOUND("Task not found") {
        @Override
        public void validate() {
            throw new FluxException.FluxTaskNotFoundException();
        }
    },
    PENDING("Pending") {
        @Override
        public void validate() {
            throw new FluxException.FluxPendingException();
        }
    },
    REQUEST_MODERATED("Request Moderated") {
        @Override
        public void validate() {
            throw new FluxException.FluxRequestModeratedException();
        }
    },
    CONTENT_MODERATED("Content Moderated") {
        @Override
        public void validate() {
            throw new FluxException.FluxContentModeratedException();
        }
    },
    READY("Ready") {
        @Override
        public void validate() {

        }
    },
    ERROR("Error") {
        @Override
        public void validate() {
            throw new FluxException.FluxErrorException();
        }
    };

    private final String value;
    public abstract void validate();

    public static FluxResultStatus parse(String value) {
        for (FluxResultStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}

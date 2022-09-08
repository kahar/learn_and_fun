package io.github.kahar.task.app.error;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class ErrorDetail {
    private final URI type;
    private final String title;
    private final String detail;
    private final Integer status;
    private final URI instance;

}

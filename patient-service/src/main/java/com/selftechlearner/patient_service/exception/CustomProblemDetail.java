package com.selftechlearner.patient_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.net.URI;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomProblemDetail extends ProblemDetail {
    private URI type;
    private String title;
    private String detail;
    private URI instance;
    private int status;

    public CustomProblemDetail() {
        super();
        this.instance = null;
    }

    public CustomProblemDetail(URI type,String title, String detail, URI instance, int status) {
        super();
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.instance = instance;
        this.status = status;
    }
}

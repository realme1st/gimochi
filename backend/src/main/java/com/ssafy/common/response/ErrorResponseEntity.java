package com.ssafy.common.response;

import com.ssafy.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
@Getter
public class ErrorResponseEntity  extends BasicResponse {
    private int status;
    private String code;
    private String message;

    /*  CustomException을 toResponseEntity로 보내면, ErrorCode e안에 내용을 가지고 Response를 만든다.    */
    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder().e(e).build());
    }
    @Builder
    public ErrorResponseEntity(ErrorCode e) {
        this.status = e.getHttpStatus().value();
        this.code = e.name();
        this.message = e.getMessage();
        this.success = false;
    }
}

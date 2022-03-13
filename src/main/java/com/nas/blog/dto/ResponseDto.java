package com.nas.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    int status;
    T data;

    public static <T> ResponseDto ok(T data){
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    public static ResponseDto ok(){
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .build();
    }
}

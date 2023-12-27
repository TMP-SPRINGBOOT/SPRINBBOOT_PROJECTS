package com.example.demo.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message="username을 입력하세요")
    private String username;
    @NotBlank(message="password 입력하세요")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",message="하나 이상의 대문자,소문자,숫자 및 특수 문자를 입력하세요." )
    private String password;

    @NotBlank(message="repassword 입력하세요")
    private String repassword;

    @NotBlank(message="연락처를 입력하세요")
    private String phone;
    
    @NotBlank(message="zipcode를 입력하세요")
    private String zipcode;

    @NotBlank(message="기본주소를 입력하세요")
    private String addr1;
    private String addr2;

    private String role;

    //OAUTH2
    private String provider;
    private String providerId;

//    //날짜
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDateTime rdate;
}

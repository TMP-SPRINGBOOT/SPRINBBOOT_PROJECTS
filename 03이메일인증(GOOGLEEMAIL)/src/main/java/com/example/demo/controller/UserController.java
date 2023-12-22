package com.example.demo.controller;

import com.example.demo.domain.dto.CertificationDto;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.service.UserService;
import com.example.demo.properties.EmailAuthProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping(value = "/myinfo" )
    public void user(Authentication authentication , Model model){
        log.info("GET /user/myinfo...Authentication : " + authentication);
        log.info("username : " + authentication.getName());
        log.info("principal : " + authentication.getPrincipal());
        log.info("authorities : " + authentication.getAuthorities());
        log.info("details :  " +authentication.getDetails());
        log.info("credentials : " + authentication.getCredentials());

        model.addAttribute("authentication",authentication);

    }



    @GetMapping("/join")
    public void join(){
        log.info("GET /join");
    }
    @PostMapping("/join")
    public String join_post(UserDto dto){
        log.info("POST /join...dto " + dto);
        //파라미터 받기
        //입력값 검증(유효성체크)
        //서비스 실행
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        boolean isJoin =  userService.memberJoin(dto);
        //View로 속성등등 전달
        if(isJoin)
            return "redirect:login?msg=MemberJoin Success!";
        else
            return "forward:join?msg=Join Failed....";
        //+a 예외처리

    }

    @GetMapping("/certification")
    public String certification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("GET /user/certification");

        if(request.getCookies() !=null) {
            boolean isExisted = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("importAuth")).findFirst()
                    .isEmpty();
            if (!isExisted) {
                response.sendRedirect("/user/join");
                return null;
            }
        }
        return "user/certification";
    }



    @GetMapping("/findId")
    public void findId(){log.info("GET /user/findId");}
    @GetMapping("/findPw")
    public void findPw(){log.info("GET /user/findPw");}



    //---------------------
    @PostMapping(value = "/certification",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject>  certification_post(@RequestBody CertificationDto params, HttpServletResponse response) throws IOException {
        log.info("POST /user/certification.." + params);
        //쿠키로 본인인증 완료값을 전달!
        Cookie authCookie = new Cookie("importAuth","true");
        authCookie.setMaxAge(60*15); //15분동안 유지
        authCookie.setPath("/");
        response.addCookie(authCookie);


        JSONObject obj = new JSONObject();
        obj.put("success",true);

        return  new ResponseEntity<JSONObject>(obj, HttpStatus.OK);


    }

    @GetMapping("/sendMail/{email}")
    @ResponseBody
    public ResponseEntity<JSONObject> sendmailFunc(@PathVariable("email") String email){
        log.info("GET /user/sendMail.." + email);
        //넣을 값 지정
        String code = EmailAuthProperties.planText;

        passwordEncoder.encode(code);
        //메일 메시지 만들기
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[EMAIL AUTHENTICATION] CODE ");
        message.setText(passwordEncoder.encode(code));

        javaMailSender.send(message);


        return new ResponseEntity(new JSONObject().put("success", true) , HttpStatus.OK);
    }


}

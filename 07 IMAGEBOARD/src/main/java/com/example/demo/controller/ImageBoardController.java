package com.example.demo.controller;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.service.ImageBoardService;
import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/imageboard")
public class ImageBoardController {

    @Autowired
    private ImageBoardService imageBoardService;

    @GetMapping("/add")
    public void add(){
        log.info("GET /imageBoard/add");
    }
    @PostMapping("/add")
    public @ResponseBody ResponseEntity<String> add_post(ImageBoardDto dto) throws Exception {
        log.info("POST /imageBoard/add files : " + dto);

        //유효성체크(생략)

        //서비스 실행
        boolean isuploaded= imageBoardService.addImageContents(dto);

        //뷰처리
        if(isuploaded)
            return new ResponseEntity("success", HttpStatus.OK);

        return new ResponseEntity("fail...", HttpStatus.BAD_GATEWAY);
    }


    @GetMapping("/list")
    public void list(Model model) throws Exception {
        log.info("GET /imageboard/list");
        List<ImageBoard> list =  imageBoardService.getAllItems();
        model.addAttribute("list",list);
    }

}



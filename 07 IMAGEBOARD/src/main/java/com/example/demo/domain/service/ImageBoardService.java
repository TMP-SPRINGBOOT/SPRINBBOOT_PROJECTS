package com.example.demo.domain.service;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.repository.ImageBoardRepository;
import com.example.demo.properties.UploadInfoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ImageBoardService {
    //저장위치 윈도우OS
    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean addImageContents(ImageBoardDto dto)throws Exception{

        //Dto->Entity
        ImageBoard imageBoard = ImageBoard.builder()
                .seller(dto.getSeller())
                .productname(dto.getProductname())
                .brandname(dto.getBrandname())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .size(dto.getSize())
                .createAt(LocalDateTime.now())
                .itemdetals(dto.getItemdetals())
                .build();

        imageBoardRepository.save(imageBoard);

        List<String> files = new ArrayList<>();

        //저장 폴더 지정()
        String uploadPath= UploadInfoProperties.uploadPath+ File.separator+dto.getSeller()+File.separator+dto.getCategory()+File.separator+imageBoard.getId();
        File dir = new File(uploadPath);
        if(!dir.exists())
            dir.mkdirs();


        for(MultipartFile file : dto.getFiles()){

            System.out.println("-----------------------------");
            System.out.println("filename : " + file.getName());
            System.out.println("filename(origin) : " + file.getOriginalFilename());
            System.out.println("filesize : " + file.getSize());
            System.out.println("-----------------------------");

            File fileobj = new File(dir,file.getOriginalFilename());    //파일객체생성

            file.transferTo(fileobj);   //저장

            //섬네일 생성
            File thumbnailFile = new File(dir,"s_"+file.getOriginalFilename());
            BufferedImage bo_image =  ImageIO.read(fileobj);
            BufferedImage bt_image = new BufferedImage(250,250,BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic =bt_image.createGraphics();
            graphic.drawImage(bo_image,0,0,250,250,null);
            ImageIO.write(bt_image,"jpg",thumbnailFile);

            //DB에 파일경로 저장
            files.add(fileobj.getPath());
        }

        imageBoard.setFiles(files);
        imageBoardRepository.save(imageBoard);

        return true;


    }
}

package com.example.demo.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ImageBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seller;
    private String productname;
    private String category;
    private String brandname;
    private String itemdetals;
    private String amount;
    private String size;

    private LocalDateTime createAt;

    @ElementCollection
    private List<String> files;
}

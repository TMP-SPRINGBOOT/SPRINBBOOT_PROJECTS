package com.example.demo.controller;

import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private CartService cartService;

    @GetMapping("/read")
    public void read(Long[] id_arr, Model model) throws Exception {
        log.info("GET /payment/read");
        for(Long id : id_arr) System.out.println(id);
        List<Cart> list =  cartService.getMyCartItems(id_arr);
        model.addAttribute("list",list);
    }
}

package com.example.demo.controller;

import com.example.demo.domain.dto.PaymentDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.service.CartService;
import com.example.demo.domain.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/read")
    public void read(Long[] id_arr, Model model) throws Exception {
        log.info("GET /payment/read");
        for(Long id : id_arr) System.out.println(id);
        List<Cart> list =  cartService.getMyCartItems(id_arr);
        list.forEach(cart->System.out.println(cart));
        model.addAttribute("list",list);

        int totalPrice = 0;
        for(Cart cart : list)
           totalPrice += cart.getAmount() *   Integer.parseInt(cart.getImageBoard().getPrice());
        model.addAttribute("totalPrice",totalPrice);

        List<Long> cart_id_list =new ArrayList<>();
        for(Long cart_id : id_arr)
            cart_id_list.add(cart_id);
        model.addAttribute("cart_id_list", cart_id_list);

    }

    @GetMapping("/add")
    public @ResponseBody void add(PaymentDto dto) throws UnsupportedEncodingException {
        dto.setAddress(  URLDecoder.decode(dto.getAddress(),"UTF-8") );
        dto.setName( URLDecoder.decode(dto.getName(),"UTF-8"));
        List<String> n_card_id = new ArrayList<>();
        for(String cart_id : dto.getCart_id()){
            n_card_id.add( URLDecoder.decode(cart_id,"UTF-8").trim() );
        }
        dto.setCart_id(n_card_id);
        log.info("GET /payment/add dto "+dto);

        boolean isadded =  paymentService.addPayment(dto);
    }

    @GetMapping("/list")
    public void list(){
        log.info("GET /payment/list");
    }




}









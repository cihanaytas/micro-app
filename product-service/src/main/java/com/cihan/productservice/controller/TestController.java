//package com.cihan.productservice.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/product")
//@RefreshScope
//public class TestController {
//
//    @Value("${test.name}")
//    private String name;
//
//    @GetMapping("/user/xy")
//    public String test(){
//        return "name";
//    }
//
//    @GetMapping("/admin/xy")
//    public String testt(){
//        return "name";
//    }
//
//
//}

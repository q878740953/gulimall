package org.example.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 创建人:  @author zy
 * 创建时间:  2024年07月25日 22:06
 * 项目名称:  gulimall
 * 文件名称:  TestController
 * 文件描述:
 * 公司名称:  点出网络
 */
@Controller
public class TestController {

    @GetMapping("/test")
    public void test(){
        System.out.println(Thread.currentThread().getName() + "1111111111");
    }
}

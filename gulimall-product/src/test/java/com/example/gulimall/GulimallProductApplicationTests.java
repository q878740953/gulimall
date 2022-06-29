package com.example.gulimall;

import com.example.gulimall.gulimallproduct.entity.BrandEntity;
import com.example.gulimall.gulimallproduct.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallProductApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private BrandService brandService;
    @Test
    void testProduct(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("小米");
        brandService.save(brandEntity);
        System.out.println("保存成功。。。。。");
    }

}

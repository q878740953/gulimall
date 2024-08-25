package org.example.gulimall.product.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.gulimall.product.entity.CategoryEntity;
import org.example.gulimall.product.service.CategoryService;
import org.example.gulimall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 创建时间:  2024年07月30日 21:15
 * 项目名称:  gulimall
 * 文件名称:  IndexCoontroller
 */
@Controller
public class IndexCoontroller {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model){
        // 查询所有一级分类
        List<CategoryEntity> level1Categorys = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", level1Categorys);
        // 视图解析器会自动自行拼接前后缀，形成逻辑视图字符串
        return "index";
    }

    @GetMapping({"/index/getCatalogJson"})
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson(Model model) throws Exception {
        return categoryService.getCatalogJson();
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        RLock lock = redissonClient.getLock("lock");
        lock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，执行业务 " + Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return "hello111";
    }
}

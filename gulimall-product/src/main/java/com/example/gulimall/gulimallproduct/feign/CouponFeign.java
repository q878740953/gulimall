package com.example.gulimall.gulimallproduct.feign;

import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient( name = "gulimall-coupon")
public interface CouponFeign {
    @RequestMapping("/gulimallcoupon/coupon/coupontest")
    public R list();
}

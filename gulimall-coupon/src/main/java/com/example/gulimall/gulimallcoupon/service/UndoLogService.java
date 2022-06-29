package com.example.gulimall.gulimallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gulimall.gulimallcoupon.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author zy
 * @email 878740953@qq.com
 * @date 2022-06-27 22:18:59
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package org.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.utils.PageUtils;
import org.example.gulimall.product.entity.CommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2019-10-01 21:08:48
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package org.example.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo {
    /**
     * 			"CatalogName": "手机/数码/手机", //所属分类名字
     * 			"groupName": "主体", //所属分组名字
     */
    private String CatalogName;
    private String groupName;

    private Long[] CatalogPath;
}

package org.example.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建时间:  2024年07月30日 21:47
 * 项目名称:  gulimall
 * 文件名称:  Catalog2Vo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catalog2Vo {
    private String catalog1Id;
    private String id;
    private String name;
    private List<Catalog3Vo> catalog3List;
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Catalog3Vo{
        private String catalog2Id;
        private String id;
        private String name;
    }
}

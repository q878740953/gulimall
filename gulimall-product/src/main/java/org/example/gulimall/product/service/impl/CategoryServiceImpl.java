package org.example.gulimall.product.service.impl;

import org.example.gulimall.product.service.CategoryBrandRelationService;
import org.example.gulimall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.utils.PageUtils;
import org.example.common.utils.Query;

import org.example.gulimall.product.dao.CategoryDao;
import org.example.gulimall.product.entity.CategoryEntity;
import org.example.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2、组装成父子的树形结构

        //2.1）、找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
             categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());




        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO  1、检查当前删除的菜单，是否被别的地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    //[2,25,225]
    @Override
    public Long[] findCatalogPath(Long CatalogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(CatalogId, paths);

        Collections.reverse(parentPath);


        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        List<CategoryEntity> level1Categorys = getLevel1Categorys();
        return level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 查询一级分类下的所有二级分类
            List<Catalog2Vo> Catalog2Vos = null;
            List<CategoryEntity> level2Categorys = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
            if (level2Categorys != null) {
                Catalog2Vos = level2Categorys.stream().map(itme -> {
                    Catalog2Vo Catalog2Vo = new Catalog2Vo(itme.getParentCid().toString(), itme.getCatId().toString(), itme.getName(), null);
                    // 根据二级分类查询三级分类
                    List<CategoryEntity> level3Categorys = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", itme.getCatId()));
                                        Catalog2Vo.setCatalog3List(level3Categorys.stream().map(itme3 -> new Catalog2Vo.Catalog3Vo(itme3.getParentCid().toString(), itme3.getCatId().toString(), itme3.getName())).collect(Collectors.toList()));
                    return Catalog2Vo;
                }).collect(Collectors.toList());
            }
            return Catalog2Vos;
        }));
    }

    //225,25,2
    private List<Long> findParentPath(Long CatalogId,List<Long> paths){
        //1、收集当前节点id
        paths.add(CatalogId);
        CategoryEntity byId = this.getById(CatalogId);
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;

    }


    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //1、找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //2、菜单的排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }



}
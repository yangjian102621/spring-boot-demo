package com.monda.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.monda.demo.util.MyMapper;
import com.monda.demo.model.BaseEntity;
import com.monda.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 服务基类,实现一些基础的CRUD方法
 * @author yangjian
 * @since 2017-07-25
 */
public class BaseServiceImpl<M extends MyMapper, E extends BaseEntity> implements BaseService<M, E> {

    @Autowired
    protected M mapper;

    @Override
    @Transactional
    public E add(E entity) {
        if (mapper.insert(entity) >= 1) {
            return entity;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Object id) {
         return mapper.deleteByPrimaryKey(id) >= 1;
    }

    @Override
    @Transactional
    public boolean delele(Object entity) {
        return mapper.delete(entity) >= 1;
    }

    @Override
    @Transactional
    public boolean delete(Example example) {
        return mapper.deleteByExample(example) >= 1;
    }

    @Override
    public List<E> getItemsByCondition(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public List<E> getItemsByCondition(String condition) {
        Class<? extends BaseServiceImpl> aClass = this.getClass();
        ParameterizedTypeImpl genericSuperclass = (ParameterizedTypeImpl) aClass.getGenericSuperclass();
        Type type = genericSuperclass.getActualTypeArguments()[1];
        Example example = new Example((Class<?>) type);
        example.createCriteria().andCondition(condition);
        return mapper.selectByExample(example);
    }

    @Override
    public List<E> getAllItems() {
        return mapper.selectAll();
    }

    @Override
    public List<E> getItemsByPage(E entity, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.select(entity);
    }

    @Override
    public List<E> getItemsByPage(Example example, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectByExample(example);
    }

    @Override
    public List<E> getItems(E record) {
        return mapper.select(record);
    }

    @Override
    public E getItem(E record) {
        List<E> items = getItemsByPage(record, 1, 1);
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }
        return items.get(0);
    }

    public E getItemById(Object id) {
        return (E) mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<E> getItemsByIds(List ids) {
        //处理字符串ID
        for (int i = 0; i < ids.size(); i++) {
            ids.set(i, "'"+ids.get(i)+"'");
        }
        String idStr = String.join(",", ids);
        String condition = "id IN("+idStr+")";
        return this.getItemsByCondition(condition);
    }

    @Override
    @Transactional
    public boolean update(E entity) {
        if (null != entity.getId()) {
            return (mapper.updateByPrimaryKeySelective(entity) >= 1);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean fullUpdate(E entity) {
        if (null != entity.getId()) {
            return (mapper.updateByPrimaryKey(entity) >= 1);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateByExample(E entity, Example example) {
         return mapper.updateByExampleSelective(entity, example) >= 1;
    }

}

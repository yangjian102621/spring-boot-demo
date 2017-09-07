package com.monda.demo.service;

import com.monda.demo.util.MyMapper;
import com.monda.demo.model.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 服务基类,实现一些基础的CRUD方法
 * @author yangjian
 * @since 2017-07-25
 */
public interface BaseService<M extends MyMapper, E extends BaseEntity> {

    /**
     * 添加数据
     * @param entity
     * @return
     */
    E add(E entity);

    /**
     * 根据 ID 主键删除数据
     * @param id
     * @return
     */
    boolean deleteById(Object id);

    /**
     * 根据实体属性删除数据
     * @param entity
     * @return
     */
    boolean delele(Object entity);

    /**
     * 根据Example条件删除数据
     * @param example
     * @return
     */
    boolean delete(Example example);

    /**
     * 根据Example条件进行查询
     * @param example
     * @return
     */
    List<E> getItemsByCondition(Object example);

    /**
     * 根据查询条件
     * @param condition
     * @return
     */
    List<E> getItemsByCondition(String condition);

    /**
     * 查询所有的元素
     * @return
     */
    List<E> getAllItems();

    /**
     * 分页查询, 根据实体中的属性查询,查询条件使用等号
     * @param entity
     * @param page
     * @param pageSize
     * @return
     */
    List<E> getItemsByPage(E entity, int page, int pageSize);

    /**
     * 使用 Example 查询对象进行分页查询
     * @param example
     * @return
     */
    List<E> getItemsByPage(Example example, int page, int pageSize);

    /**
     * 根据实体中的属性进行查询列表
     * @param record
     * @return
     */
    List<E>  getItems(E record);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param record
     * @return
     */
    E getItem(E record);

    E getItemById(Object id);

    /**
     * 根据多个ID查询列表
     * @param ids
     * @return
     */
    List<E> getItemsByIds(List ids);

    /**
     * 根据主键更新属性不为null的值
     * @param entity
     * @return
     */
    boolean update(E entity);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     * @param entity
     * @return
     */
    boolean fullUpdate(E entity);

    /**
     * 使用 Example 查询对象进行更新
     * @param entity
     * @param example
     * @return
     */
    boolean updateByExample(E entity, Example example);

}

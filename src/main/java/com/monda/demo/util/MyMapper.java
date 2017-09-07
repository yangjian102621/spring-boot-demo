package com.monda.demo.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 * @author yangjian
 * @since 2017-07-25
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}

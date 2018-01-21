package com.wtgroup.comb.mapper;

import com.wtgroup.comb.pojo.BaseDict;

import java.util.List;

public interface BaseDictMapper {

    /**
     * 根据字典类型查询字典信息
     */
    public List<BaseDict> queryBaseDictListByTypeCode(String type_code );
}

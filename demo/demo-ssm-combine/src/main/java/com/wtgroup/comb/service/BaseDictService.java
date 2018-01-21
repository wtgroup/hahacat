package com.wtgroup.comb.service;

import com.wtgroup.comb.pojo.BaseDict;

import java.util.List;

public interface BaseDictService {

    /**
     *
     */
    public List<BaseDict> queryBaseDictListByTypeCode(String type_code );
}

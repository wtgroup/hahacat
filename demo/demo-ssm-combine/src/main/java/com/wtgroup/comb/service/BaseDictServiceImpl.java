package com.wtgroup.comb.service;

import com.wtgroup.comb.mapper.BaseDictMapper;
import com.wtgroup.comb.pojo.BaseDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-18-22:58
 */
@Service
public class BaseDictServiceImpl implements BaseDictService {
    @Autowired
    private BaseDictMapper baseDictMapper;


    public List<BaseDict> queryBaseDictListByTypeCode(String type_code) {
        return baseDictMapper.queryBaseDictListByTypeCode(type_code);
    }
}

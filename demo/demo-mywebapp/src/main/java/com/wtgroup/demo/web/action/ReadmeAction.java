package com.wtgroup.demo.web.action;

import com.wtgroup.demo.utils.Log;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-06-15:07
 */

public class ReadmeAction extends CommonAction {


    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Action(value = "/readmeAction_readme")
    public String readme() {
        switch (type){
            case "pdf":
                break;
            case "doc":
                break;
            default:    // markdown文件
                // 读取markdown并解析
                Log.trace("读取markdown并解析");
                break;
        }
        return NONE;
    }

}

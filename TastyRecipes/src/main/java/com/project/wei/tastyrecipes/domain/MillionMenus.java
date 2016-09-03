package com.project.wei.tastyrecipes.domain;

import java.util.List;

/**
 * Created by wei on 2016/9/1 0001.
 */
public class MillionMenus {


    public int error_code;
    public String reason;
    public String resultcode;


    public List<ResultBean> result;

    public static class ResultBean {
        public String name;
        public String parentId;
        /**
         * id : 1
         * name : 家常菜
         * parentId : 10001
         */
        public List<ListBean> list;

        public static class ListBean {
            public String id;
            public String name;
            public String parentId;
        }
    }
}


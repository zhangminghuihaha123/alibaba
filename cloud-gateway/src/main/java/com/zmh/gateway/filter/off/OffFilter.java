package com.zmh.gateway.filter.off;

import java.util.ArrayList;
import java.util.List;

public class OffFilter {

    public static List<String> ListURL(){
        List list=new ArrayList();
        list.add("http://localhost:8080/user/login");
        list.add("http://localhost:8080/user/resport");
        return list;
    }
}

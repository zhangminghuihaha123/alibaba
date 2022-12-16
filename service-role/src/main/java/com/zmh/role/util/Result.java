package com.zmh.role.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private String code;
    private Object data;
    private String msg;

    public static Result success(){
        return new Result("200",null,"成功");
    }

    public static Result success(Object data){
        return new Result("200",data,"成功");
    }

    public static Result error(){
        return new Result("500",null,"未知错误");
    }

}

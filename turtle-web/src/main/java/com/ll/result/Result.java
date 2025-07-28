package com.ll.result;

import lombok.Data;

/**
 * 统一返回结果类
 */
@Data
public class Result {

    private Integer code;//编码：1成功，0失败
    private String msg;//返回信息
    private Object data;//数据

    public static Result success(){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("成功");
        return result;
    }

    public static Result success(Object object){
        Result result = new Result();
        result.data = object;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}

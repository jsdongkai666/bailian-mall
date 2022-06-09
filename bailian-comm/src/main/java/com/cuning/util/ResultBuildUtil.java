package com.cuning.util;


import com.cuning.constant.CommonConstant;

/**
 * Created On : 2022/5/13.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 统一返回结果工具类
 */
public class ResultBuildUtil {

    /**
     * @author : zhukang
     * @date   : 2022/5/13
     * @param  : [T]
     * @return : com.kgc.sbt.util.RequestResult<T>
     * @description : 构建成功结果返回，带任意类型数据
     */
    public static <T> com.cuning.util.RequestResult<T> success(T t){
        com.cuning.util.RequestResult<T> requestResult = new com.cuning.util.RequestResult<T>();
        requestResult.setCode(CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
        requestResult.setMsg(CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
        requestResult.setData(t);
        return requestResult;
    }

    /**
     * @author : zhukang
     * @date   : 2022/5/13
     * @param  : [T]
     * @return : com.kgc.sbt.util.RequestResult<T>
     * @description : 构建成功结果返回，不带数据
     */
    public static <T> com.cuning.util.RequestResult<T> success(){
        return success(null);
    }

    /**
     * @author : zhukang
     * @date   : 2022/5/13
     * @param  : [T]
     * @return : com.kgc.sbt.util.RequestResult<T>
     * @description : 构建失败结果(统一的结果，比如系统异常)返回，带任意类型数据
     */
    public static <T> com.cuning.util.RequestResult<T> fail(T t){
        com.cuning.util.RequestResult<T> requestResult = new com.cuning.util.RequestResult<T>();
        requestResult.setCode(CommonConstant.UNIFY_RETURN_FAIL_CODE);
        requestResult.setMsg(CommonConstant.UNIFY_RETURN_FAIL_MSG);
        requestResult.setData(t);
        return requestResult;
    }

    /**
     * @author : zhukang
     * @date   : 2022/5/13
     * @param  : [T]
     * @return : com.kgc.sbt.util.RequestResult<T>
     * @description : 构建失败结果返回，不带数据
     */
    public static <T> com.cuning.util.RequestResult<T> fail(){
        return fail(null);
    }

    /**
     * @author : zhukang
     * @date   : 2022/5/13
     * @param  : [T]
     * @return : com.kgc.sbt.util.RequestResult<T>
     * @description : 构建失败结果(自定义返回错误码和说明)返回，不带数据
     */
    public static <T> com.cuning.util.RequestResult<T> fail(String errCode, String errMsg){
        com.cuning.util.RequestResult<T> requestResult = new com.cuning.util.RequestResult<T>();
        requestResult.setCode(errCode);
        requestResult.setMsg(errMsg);
        requestResult.setData(null);
        return requestResult;
    }
}

package com.cuning.bean.logistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author dengteng
 * @title: Trace
 * @projectName logisticsApi
 * @description: 物流痕迹信息
 * @date 2022/6/9
 */
@Data
@ApiModel("物流痕迹信息")
public class Trace {
    /**
     * 接收站点
     */
    @ApiModelProperty("接收站点")
    private String acceptStation;

    /**
     * 接收时间
     */
    @ApiModelProperty("接收时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date acceptTime;

}

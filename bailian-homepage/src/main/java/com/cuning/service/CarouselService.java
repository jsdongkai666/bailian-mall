package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.BailianCarousel;

import java.util.List;

/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 轮播图业务接口
 */
public interface CarouselService extends IService<BailianCarousel>{

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [java.lang.Integer]
     * @return : com.cuning.bean.bailianCarousel
     * @description : 根据id，查询轮播图详情
     */
    List<BailianCarousel> selectCarouselList(Integer rank);

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [com.cuning.bean.BailianCarousel]
     * @return : java.lang.Boolean
     * @description : 添加轮播图
     */
    boolean addCarousel(BailianCarousel bailianCarousel);

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [java.util.List<java.lang.Integer>]
     * @return : boolean
     * @description : 批量删除轮播图
     */
    boolean deleteCarousel(List<String> ids);

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [com.cuning.bean.BailianCarousel]
     * @return : boolean
     * @description : 修改轮播图
     */
    boolean updateCarousel(BailianCarousel bailianCarousel);
}

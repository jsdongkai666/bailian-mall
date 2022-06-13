package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.BailianCarousel;
import com.cuning.mapper.CarouselMapper;
import com.cuning.service.CarouselService;
import com.cuning.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 轮播图业务接口实现类
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, BailianCarousel> implements CarouselService {

    @Autowired(required = false)
    private CarouselMapper carouselMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public List<BailianCarousel> selectCarouselList(Integer rank) {
        QueryWrapper<BailianCarousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by carousel_rank");
        if (rank == 1){
            queryWrapper.last("order by carousel_rank desc");
        }
        return carouselMapper.selectList(queryWrapper);
    }

    @Override
    public boolean addCarousel(BailianCarousel bailianCarousel) {
        // 生成ID
        bailianCarousel.setCarouselId("C" + Long.toString(snowFlake.nextId()));
        return carouselMapper.insert(bailianCarousel) > 0;
    }

    @Override
    public boolean deleteCarousel(List<Integer> ids) {
        return carouselMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean updateCarousel(BailianCarousel bailianCarousel) {
        return carouselMapper.updateById(bailianCarousel) > 0;
    }
}

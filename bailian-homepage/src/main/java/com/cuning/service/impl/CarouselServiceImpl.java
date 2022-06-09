package com.cuning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.bailianCarousel;
import com.cuning.mapper.CarouselMapper;
import com.cuning.service.CarouselService;
import org.springframework.stereotype.Service;

/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 轮播图业务接口实现类
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, bailianCarousel> implements CarouselService {
}

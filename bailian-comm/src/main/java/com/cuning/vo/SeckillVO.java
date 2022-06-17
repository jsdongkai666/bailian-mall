package com.cuning.vo;

import com.cuning.bean.seckill.BailianSeckill;
import com.cuning.bean.user.User;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月17日 11:16:00
 */
@Data
public class SeckillVO {

    private BailianSeckill seckill;

    private List<User> userList;
}

package com.cuning.util;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:23:00
 */
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SnowFlake {

    //时间 41位
    private static long lastTime = System.currentTimeMillis();

    //数据中心ID 5位(默认0-31)
    private long datacenterId = 0;
    private long datacenterIdShift = 5;

    //机房机器ID 5位(默认0-31)
    private long workerId = 0;
    private long workerIdShift = 5;

    //随机数 12位(默认0~4095)
    private AtomicLong random = new AtomicLong();
    private long randomShift = 12;
    //随机数的最大值
    private long maxRandom = (long) Math.pow(2, randomShift);

    public SnowFlake() {
    }

    public SnowFlake(long workerIdShift, long datacenterIdShift){
        if (workerIdShift < 0 ||
                datacenterIdShift < 0 ||
                workerIdShift + datacenterIdShift > 22) {
            throw new IllegalArgumentException("参数不匹配");
        }
        this.workerIdShift = workerIdShift;
        this.datacenterIdShift = datacenterIdShift;
        this.randomShift = 22 - datacenterIdShift - workerIdShift;
        this.maxRandom = (long) Math.pow(2, randomShift);
    }

    //获取雪花的ID
    private long getId() {
        return lastTime << (workerIdShift + datacenterIdShift + randomShift) |
                workerId << (datacenterIdShift + randomShift) |
                datacenterId << randomShift |
                random.get();
    }

    //生成一个新的ID
    public synchronized long nextId() {
        long now = System.currentTimeMillis();

        //如果当前时间和上一次时间不在同一毫秒内，直接返回
        if (now > lastTime) {
            lastTime = now;
            random.set(0);
            return getId();
        }

        //将最后的随机数，进行+1操作
        if (random.incrementAndGet() < maxRandom) {
            return getId();
        }

        //自选等待下一毫秒
        while (now <= lastTime) {
            now = System.currentTimeMillis();
        }

        lastTime = now;
        random.set(0);
        return getId();

    }




}



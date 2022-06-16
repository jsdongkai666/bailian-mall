package com.cuning.controller.user;


import com.cuning.util.RedisUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 16:58:00
 */
@RestController
@Slf4j
@RequestMapping("/web")
@Api(tags = "验证码管理")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation("验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 设置IE扩展的HTTP / 1.1无缓存标头（使用addHeader）。
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // 设置标准的HTTP / 1.0无缓存标头。
        response.setHeader("Pragma", "no-cache");
        // 返回一张图片
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        log.info("验证码内容：{}" ,text);
        //将验证码文本内容放入redis
//        request.getSession().setAttribute("captcha", text);
        redisUtils.set("captcha",text,60);
        //根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片，格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证码 end --------------------------

    }


    @ApiOperation("手机验证码")
    @GetMapping(value = "/tel/captcha")
    public String telCaptcha(@RequestParam("tel") String tel) {

        //-------------------生成验证码 begin --------------------------
        //获取验证码文本内容
         String text = String.valueOf((new Random().nextInt(9000)+1000));
        log.info("手机验证码内容：{}" ,text);
        //将验证码文本内容放入redis
        redisUtils.set("telCaptcha",text,60);
        // 把手机和验证码绑定起来
        redisUtils.set(text,tel,60);
        return text;
    }
}

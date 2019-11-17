package org.openmore.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.openmore.common.exception.OpenmoreException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by michaeltang on 2019/5/5.
 */
public class ImgCaptcha {
    //放到session中的key
    public static final String RANDOMCODEKEY= "RANDOMREDISKEY";
    //随机产生数字与字母组合的字符串
    private static String randString = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 获得字体
     */
    private static Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /**
     * 获得颜色
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    public static void verifyImgCaptcha(HttpServletRequest request, String captcha) {
        // 将验证码信息放到session里
        HttpSession session = request.getSession();
        String randCode = (String) session.getAttribute(RANDOMCODEKEY);
        if (StringUtils.isEmpty(randCode)) {
            throw new OpenmoreException(4000, "验证码已经失效，请重试！");
        }
        if (!randCode.equalsIgnoreCase(captcha)) {
            throw new OpenmoreException(4000, "验证码不正确！");
        }
    }


    /**
     * 生成随机图片
     */
    public static String getRandCode(HttpServletRequest request, HttpServletResponse response, int width, int height) {
        return getRandCode(request, response, width, height, 40, 4);
    }

    /**
     * 生成随机图片
     */
    public static String getRandCode(HttpServletRequest request, HttpServletResponse response) {
        return getRandCode(request, response, 120, 40, 40, 4);
    }

    /**
     * 生成随机图片
     * @param request
     * @param response
     * @param width
     * @param height
     * @param lineSize
     * @param stringNum
     * @return
     */
    public static String getRandCode(HttpServletRequest request, HttpServletResponse response, int width, int height, int lineSize, int stringNum) {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        // 将验证码信息放到session里
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        // 图片大小
        g.fillRect(0, 0, width, height);
        // 字体大小
        int fontSize = (int)Math.round(width / stringNum / 1.5);
        // 字体样式
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, fontSize));
        g.setColor(getRandColor(110, 133));//字体颜色
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drawLine(g, width, height);
        }
        // 字体顶部空白大小
        int topPadding = (int)Math.round(height / 1.8);
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drawString(g, randomString, i, fontSize, topPadding);
        }
        // 将生成的随机字符串保存到session中
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        // 设置失效时间1分钟
//        session.setMaxInactiveInterval(60);
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            return null;
        }
        return randomString;
    }

    /**
     * 绘制字符串
     */
    private static String drawString(Graphics g, String randomString, int i, int fontSize, int topPadding) {
        Random random = new Random();
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, fontSize * i, topPadding);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private static void drawLine(Graphics g, int width, int height) {
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     */
    public static String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }
}

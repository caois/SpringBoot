package cn.cao.travel.controller;

import cn.cao.travel.entity.Result;
import cn.cao.travel.entity.User;
import cn.cao.travel.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author cao
 * @create 2020/8/31 - 16:43
 */
@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {
    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userServiceImpl;


    //保持持续登录状态
    @GetMapping("loginConnection")
    public void loginConnection(@RequestParam(value = "userID",required = false) String userID){

        if(null != userID){
            try {
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                //重新设置redis中key的过期时间
                redisTemplate.expire("userID",30,TimeUnit.MINUTES);
            } catch (Exception e) {
                log.error("loginConnection：\r\n"+e.getMessage());
            }
        }

    }


    //通过删除redis 中的数据,不调用30分钟后自动删除
    @GetMapping("deleteUser")
    public Result delete(String userID){
        Result result = new Result();

        //删除redis中的数据
        if(null !=userID ){
            try {
                redisTemplate.setStringSerializer(new StringRedisSerializer());
                Boolean delete = redisTemplate.delete(userID);
                result.setMessage("logout OK").setState(delete);
            } catch (Exception e) {
                result.setMessage(e.getMessage()).setState(false);
            }
        }
        return result;
    }


    //通过userID 从redis 数据库中获取用户对象
    @GetMapping("getUser")
    public User getUser(String userID){

        User user = new User();
        if(null != userID){
            redisTemplate.setStringSerializer(new StringRedisSerializer());
            try {
                user = (User)redisTemplate.opsForValue().get(userID);
                //难搞，不想改sql查询了，这里可能会报空指针~~~~
                user.setPassword("密码不能让你看见O(∩_∩)O哈哈~");
            } catch (Exception e) {
                log.error("UserController.getUser()"+e.getMessage());
            }
        }



        return user;
    }


    //登录
    @PostMapping("login")
    public Result login(@RequestBody User user,String code, String codeKey){
        Result result = new Result();

        String redisCode = stringRedisTemplate.opsForValue().get(codeKey);
        //判断验证码
        if(code.equalsIgnoreCase(redisCode)){
            //验证用户名密码
            User login = userServiceImpl.login(user);
            if(login == null){
                result.setState(false).setMessage("用户名或密码错误！");
            }else{
                String uidCode = login.getId()+UUID.randomUUID().toString();

                //修改 key 的序列化方案
                redisTemplate.setStringSerializer(new StringRedisSerializer());
                redisTemplate.opsForValue().set(uidCode,login,30,TimeUnit.MINUTES);//30分钟过期

                result.setMessage("登录成功").setUserID(uidCode).setState(true);
            }

        }else {
            result.setMessage("验证码错误,请重新输入").setState(false);
        }

        return result;
    }

    //注册
    @PostMapping("userReg")
    public Result userReg(@RequestBody User user, String code, String codeKey){
        Result result = new Result();

        //String verifyCode = (String)request.getSession().getAttribute("verifyCode");//session 无法跨域共享
        log.info("前端验证码是="+code);
        log.info("user是="+user);
        String redisCode = stringRedisTemplate.opsForValue().get(codeKey);
        log.info("redisCode的验证码："+redisCode);
        try {
            if(code.equalsIgnoreCase(redisCode)){
                //验证码正确
                userServiceImpl.userReg(user);
                result.setMessage("注册成功！！");
            }else {
                result.setState(false).setMessage("注册失败！！");
            }
        } catch (Exception e) {
            result.setState(false).setMessage(e.getMessage());
        }


        return result;
    }


    //验证码
    @GetMapping("verifyCode")
    public Map<String,String> verifyCode( HttpServletRequest request, HttpServletResponse response) throws IOException {

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        Map<String,String> map = new HashMap<>();
        //获取验证码
        //try {
        //生成验证码字符串并储存到session中
        String verifyCode = defaultKaptcha.createText();
        //request.getSession().setAttribute("verifyCode",verifyCode);session 无法跨域共享

        //这里可以设置一个计时器，3分钟之类 使 codeUUID 不做任何改变
        String codeUUID = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue().set(codeUUID,verifyCode,300, TimeUnit.SECONDS);


        //生成图片缓存
        BufferedImage bufferedImage = defaultKaptcha.createImage(verifyCode);
        //将验证码转换为图片格式 写入到输出流 imgOutputStream中
        ImageIO.write(bufferedImage,"jpg",byteOut);
        //} catch (IOException e) {
        //    //response.sendError(HttpServletResponse.SC_NOT_FOUND);
        //}

        byte[] bytes = byteOut.toByteArray();

        //设置响应头
        //response.setHeader("Cache-Control", "no-store");
        //response.setHeader("Pragma", "no-cache");
        //response.setDateHeader("Expires", 0);
        //response.setContentType("image/jpeg");
        //ServletOutputStream outputStream = response.getOutputStream();
        //outputStream.write(bytes);
        //outputStream.flush();
        //outputStream.close();

        String imageBase64 = Base64Utils.encodeToString(bytes);
        map.put("image",imageBase64);
        map.put("codeUUID",codeUUID);

        return map;
    }
}

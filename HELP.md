# travel

## 需求分析

- 用户模块
    - 登录和注册

- 省份模块 一个省份可以多个景点
    - curd操作
    
- 景点模块 一个景点对应一个省份
    - curd操作
    
    
## SQL 设计

1. 分析系统中有哪些表？ 表的个数

2. 分析表表之间的关系

3. 分析每个表中的字段
    - 显性字段
    - 隐性字段--业务字段|经验字段
4. ID 同一 integer 类型


## travel
- 用户表 t_user 独立
id  username password   email

- 省份表   t_province
id  name    tags    place_counts

- 景点表    t_place        tourist Attraction:旅游景点
id  name    pic_path    peak_season_time    peak_season_ticket     off_season_ticket    introduction    province_id
                        旺季时间                旺季门票               淡季门票             简介




queryPlaceByPage
### 两种方式显示图片到浏览器
1. base64 格式字符
2. 每张图片请求一次服务器获取图片资源

### SQL存储路径
保存place：本地存储图片文件--》sql 中存储的是路径
获取place：SQL中拿到路径---》用路径去获取图片输出流
                                1. 直接写出？
                                    1.1 如果获取的是一个 list<place>呢？
                                        1.1.1 list中多个 place 要如何显示在页面上                                
                                2. 输出流转换为base64存储到 entity.place中？





















<<<<<<< HEAD
Openmore 基于SBM（SpringBoot+Mybatis）开源REST API服务
===============================
关于Openmore
------------
<p align="center">
    <a href="https://github.com/open-more" target="_blank">
        <img src="https://avatars0.githubusercontent.com/u/27731838?v=3&s=460" width="120" alt="Open More" />
    </a>
</p>
Openmore团队是目前北京的一家创业公司内里的几个主程自发组织的开源团队, 团队目标是将创业过程中技术团队遇到的技术经验进行开源分享, 本着更开放,更高效的原则帮助中国的移动开发者填坑,减少开发成本,同时吸收大家的意见与建议。

## 介绍
**springboot-template** 是基于SSM的J2EE项目快速开发脚手架，集成了最常用的框架,适用于`Restfull` 架构风格`Web Service`接口开发。

## 主要框架
* **SpringBoot2.0.0release**
* **Freemarker**
* **Docker自动部署**

## 工具框架
* **Spring-Test** 
* **Mybatis-Pagehelper5.0** 
* **Mybatis通用Mapper3** 
* **Druid1.1.9** 
* **UrlRewrite** 
* **Freemarker** 

## 开发工具
`Intellij Idea`
## 前端框架知识点总结
> * [前端对IE8的支持问题](./FRONT_END.md)
> * [SEO之HTML优化](./HTML_SEO.md)
### 依赖管理工具
`Gradle`

## 使用
``` shell
# git clone https://github.com/open-more/Springboot-Utils.git
# cd Springboot-Utils
# mvn clean install
# git clone https://gitee.com/openmore/springboot-template.git
将Springboot-Utils添加到springboot-template项目依赖里
```

## nginx 配置
```aidl
location / {
      proxy_pass http://127.0.0.1:9191;
      rewrite ^/$ /web/index;
      rewrite ^/en/$ /web/index?lan=en;
      rewrite ^/en/wx/(.*)/(.*)\.html$ /web/wx/$1?id=$2&lan=en;
      rewrite ^/en/wx/(.*)\.html$ /web/wx/$1?lan=en;
      rewrite ^/en/(.*)/(.*)\.html$ /web/$1-detail?id=$2&lan=en;
      rewrite ^/en/(.*)\.html$ /web/$1?lan=en;
      rewrite ^/wx/(.*)/(.*)\.html$ /web/wx/$1?id=$2;
      rewrite ^/wx/(.*)\.html$ /web/wx/$1;
      rewrite ^/(.*)/(.*)\.html$ /web/$1-detail?id=$2;
   rewrite ^/(.*)\.html$ /web/$1;
  }
```

## httpCode说明
* 正常返回code：200 msg：ok
* 异常情况：
* 如果是登录没有权限，返回401或4001
* 如果是业务需要回显给前端的，需要将code设置为6000以下的码
* 如果是前端需要单独处理的，需要设置code为6000以上的码，如：后台返回60001，前端需要弹出一个二次确认窗口。

## 接口安全（拦截器实现）
* 使用Shiro里的SessionId控制登录
* 用户登录时，Header里将device-token带过来，服务器保存device-token到db里，当用户在其它设备登录时，device-token变化，导致服务器端device-token发生变化，在校验时会拒绝老用户的接口请求返回401，来控制客户端访问，比如：互踢，拉黑机制
* 当需要禁止一个用户登录时，将device-token清空，这里强迫用户下次请求必须登录，然后再将用户状态改成disable，就可以达到禁止登录的目的
* 防止篡改，所有请求接口都带有sign签名，sign = md5(SessionId + nonce + method + uri + timestamp + device-token)
* 防止重放攻击，Header里带有时间戳，超过时间差容忍范围(默认60秒)，拒绝访问
* 关键数据可加密，Header中encrypt=1时，接口数据进行AES128加密，隐藏明文发送数据（先不实现）

Http Header请求参数如下：
* sign：签名（签名算法见下面）
* Content-Type：application/json
* timestamp：时间戳（秒）
* Device-Token: 每个设备自己生成的token
* nonce：6位随机数
* encrypt：1表示内容加密，参数不存在或为其它值，表示内容不加密（先不实现）

## 签名算法
```
sign = md5(SessionId + nonce + 请求方式（GET/PUT/POST/DELETE，必须大写）+ 请求接口URI（除域名后的URL） + timestamp + device-token)
```
### 例如：
```
GET http://api.openmore.org/user/123
secret_key = a92664b406ed5b18dd04cd59c6778519
nonce = 1A39CJ
timestamp = 1497723214
device-token = 819277adsf887fd8s7f8d
body = 空
拼接字符串为：a92664b406ed5b18dd04cd59c67785191A39CJGET/user/1231497723214819277adsf887fd8s7f8d
md5("a92664b406ed5b18dd04cd59c67785191A39CJGET/user/1231497723214819277adsf887fd8s7f8d") = 4E22D478672492EE8914E2314FE575AF 
```

## 快速开始
配置mysql数据库，修改`environment/dev/application.properties`文件。
```
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driver=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://{YOUR_DB_URL}/{DB_NAME}?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8
spring.datasource.username={USER_NAME}
spring.datasource.password={PASSWORD}
```

配置redis数据库，修改`environment/dev/application.properties`文件。
```
# database name
spring.redis.database=0
# server host1
spring.redis.host=127.0.0.1
# server password
#spring.redis.password=
#connection port
spring.redis.port=6379
# pool settings ...
spring.redis.pool.max-idle=8
spring.redis.pool.min-idle=0
spring.redis.pool.max-active=8
spring.redis.pool.max-wait=-1
```
### 进入项目目录
```shell
# cd {PROJECT_ROOT}/
```
### 初始化环境为开发环境
```shell
# ./gradlew initEnv -Penv=dev
start initial environment
拷贝：environment/dev下的文件到src/main/resources/  DONE
:initEnv UP-TO-DATE
BUILD SUCCESSFUL
```
### 环境隔离
## 开发环境：environment/dev
## 远程测试环境：environment/beta
## 生产环境：environment/prod
## 部署到远程测试环境时


### 初始化数据库,将db/migration目录下sql迁移到本地数据库
```shell
# ./gradlew flywayMigrate
```

### 启动项目
```shell
# ./gradlew bootRun
```
### 通过Docker启动
```shell
# ./deploy.sh
```

### 通过MybatisGenerator根据DB表结构，生成Mapper文件和对应的entity
* 首先，在db/generatorConfig.xml里将db里的表，配置进去
```
<table tableName="【表名】">
    <generatedKey column="【主键字段】" sqlStatement="Mysql" identity="true"/>
</table>
```
* 执行如下命令，将表结构自动生成mapper
```shell
# ./gradlew mybatisGenerate
```
可以看到在dao包和entity包下出了自动生成的代码

## 关于分页
分页在所有的getAllByPageXX接口里，默认支持分页。
下面的例子请求分页数据：
```
GET http://localhost:9191/api/XXX/getAllByPage?page=1&pageSize=10
```
请求第1页，每页10条数据，每次请求后的response Body里包含有当前数据的分页信息pagination
##### Http Response Header
```
{
  "message": "ok",
  "code": 0,
  "data": [
    {
     ...
    }
  ],
  "pagination": {
    "page": 0,
    "limit": 0,
    "totalItem": 9,
    "totalPage": 0
  }
}
```
* page：当前第几页
* limit：每页多少条数据
* totalItem：共多少条数据
* totalPage：共多少页

## 查看在线restAPI文档
项目启动后，打开[http://localhost:9191/swagger-ui.html](http://localhost:9191/swagger-ui.html)即可查看api文档

## Druid查看SQL性能
项目启动后，打开[http://localhost:9191/druid/api.html](http://localhost:9191/druid/api.html)即可查看SQL性能，初始用户名和密码：root/123
如需修改：DruidStatViewServlet类里的配置

## 权限及菜单
权限按照：用户->角色->权限->菜单建议关系
* 一个用户有多个角色
* 一个角色有多种权限
* 一个权限可能会对应一个菜单，多种权限可能对应一个菜单
* 一个二级菜单有对应的父级菜单，如果一个权限对应一个二级菜单，那么用户登录时应该返回菜单的上级链
* 超级管理员，在登录后，不返回任何tenantId，返回所有的菜单项，包含超级菜单(ADMIN_MENU_TYPE)

用户登录时，根据用户获得角色，根据角色，返回权限，根据权限，返回菜单列表（排序）

## 多端登录
继承UsernamePasswordToken，通过type字段区分不同端
* 使用用户名和密码登录的PC端，需要验证用户名和密码
* 使用微信登录，仅验证Openid对应的用户是否存在
* 对于普通微信用户，仅使用微信自己的授权登录进行验证，系统里不进行验证，对应的操作接口也不进行权限管理

### 数据更新触发器
由于数据量比较大，每次都实时统计工作量会比较大，我们会创建中间查询结果表，
每当有数据更新时，需要自动通过触发器更新查询结果表数据

### 通过代码生成器生成DTO（POJO），Servcie， ServiceImpl，Controller及对应的文档
 * 浏览器打开[http://localhost:9191/dto/home](http://localhost:9191/dto/home)，进入Dto生成器界面
 * 输入包名，DTO的英文名，与DB Entity对应及DTO的中文和描述
 * 输入需要创建DTO的属性，type为Java的基本数据类型
 * 分别显示Show*按钮，查看生成的代码
 * 点击Create All Source，将代码自动生成在包名目录下
 
###定时任务 quartz-redis-jobStore
 *使用quartz+redis实现定时任务持久化
 *参考：https://github.com/jlinn/quartz-redis-jobstore

## 部署
### 启动项目，[http://locaohost:9191/](http://locaohost:9191/)
```shell
# ./gradlew build
```
### 启动
```shell
# ./gradlew bootRun
```
### 调试
```
# ./gradlew bootRun  --debug-jvm
```

## 项目热部署
```shell
修改IDEA设置
1. 打开 Settings –> Build-Execution-Deployment –> Compiler，将 Build project automatically.勾上。
2. 点击 Help –> Find Action..，或使用快捷键 Ctrl+Shift+A来打开 Registry…，将其中的compiler.automake.allow.when.app.running勾上。
3. 重启一下IDEA
```
参考链接: [idea+springboot+freemarker热部署](https://blog.csdn.net/M201672389/article/details/77994570)

## 单元测试
安装Junit GeneratorV2.0插件，然后在要添加单元测试的方法上右键，选择Generate->Junit Test-> V4，自动生成单元测试代码，默认代码生成在src/java/test包下，我们需要修改这个路径，系统设置Other Settings，自己指定下路径即可。

## 执行测试
```
./gradlew test
```
如果测试失败的话，会在目录下生成测试报告：ssm-openmore-template/build/reports/tests/test/index.html

## 常见问题
* nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping
```
mybatis自动生成的Entity代码里没有通过@Id来注解出id主键字段来，导致在selectByPrimaryKey出错
解决方法：在id字段前加@Id注解 
```


问题
===============================

### org.springframework.beans.NotWritablePropertyException: Invalid property ‘mapperHelper’ of bean class
> 原因是：通用mapper不支持热部署，不支持devtool。
解决方法：在pom.xml屏蔽掉热部署就OK了。
## 执行测试
```
./gradlew test
```
如果测试失败的话，会在目录下生成测试报告：SRC_ROOT/build/reports/tests/test/index.html

### Excel导出Centos无法导出，报null异常
> 原因是，没有中文字体库
解决方法：
```
yum -y install fontconfig
拷贝中文字体simsun.ttc到/usr/share/fonts/chinese，需要创建chinese
修改权限
chmod -R 755 /usr/share/fonts/chinese
yum -y install ttmkfdir
ttmkfdir -e /usr/share/X11/fonts/encodings/encodings.dir
vi /etc/fonts/fonts.conf
添加如下内容：
<dir>/usr/share/fonts/chinese</dir>
执行刷新字体缓存：
fc-cache
```

### 敏感词过滤处理
> 使用本地敏感词库处理方法词库文件位置：～/resources/sensitive/keyWords.txt




### 错误码：
> 400 :常规错误
> 4001:需要shiro登录
> 4002:需要websocket登录
> 200 :常规请求成功
> 1600~1699 :请求成功但需要用户确认信息
> 1700~1799 :请求失败但需要用户确认信息
> 1800~1899 :请求失败需要关闭页面

###js处理逻辑
> 进入页面元素加载完毕先执行注册操作：shiro登录+websocket服务连接
> 连接完毕检查是否加入聊天房间
> 聊天消息先检查是否加入房间，若加入房间则消息发给房间，否则发给个人
> 页面退出执行websocket断开连接操作，并清空登录状态

###参考鸣谢
> https://github.com/niezhiliang/springbootwebsocket
> https://github.com/qqxx6661/springboot-websocket-demo


























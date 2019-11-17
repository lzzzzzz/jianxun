测试
===============================

## 使用H2内存数据库作为测试数据库
```
1. 先把需要测试的DB库里的表结构导出来，写入到db/schema.sql里
2. 创建测试数据写入到db/data.sql，测试的内容基于这些测试数据进行
```
## 单元测试
安装Junit GeneratorV2.0插件，然后在要添加单元测试的方法上右键，选择Generate->Junit Test-> V4，自动生成单元测试代码，默认代码生成在src/java/test包下，我们需要修改这个路径，Preference -> Other Settings，自己指定下路径即可。

## 执行测试
```
./gradlew test
```
如果测试失败的话，会在目录下生成测试报告：SRC_ROOT/build/reports/tests/test/index.html


系统文章类型（这些标签与普通文章标签不同有特殊意义）：
ATTRACT_TYPE :招商合作
COMPANY_HONOR_TYPE ：公司荣誉
JOIN_US_COMPANY_TYPE：公司风采
JOIN_US_SCHOOL_TYPE：校园招聘
JOIN_US_SOCIETY_TYPE：社会招聘
MAVIN_TYPE：核心技术专家

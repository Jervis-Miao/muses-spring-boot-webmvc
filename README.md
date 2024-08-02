# muses-spring-boot-webmvc

1. 创建脚手架并打包打开终端，到这个项目的根目录，然后创建 <code>archetype</code>

   ```shell
   cd ${workspace}/muses-spring-boot-webmvc
   mvn archetype:create-from-project -s ${maven_home}/setting.xml
   ```


2. 接着到生成的target目录，将项目打包到本地仓库。

   ```shell
   cd ${workspace}/muses-spring-boot-webmvc/target/generated-sources/archetype
   mvn install -s ${maven_home}/setting.xml
   ```

3. 在 IDEA 中新建一个项目，选择 Maven Archetype --> Catalog（填写自己本地仓库的地址）--> 输入muses，之后在弹出的对话框中填入坐标和版本号，需要注意的是工件 ID 的最后应该有 -archetype 后缀。

## Java代码格式化器
`/support/format/eclipse-codestyle-p3c-1.1.xml`

## gitignore
`/support/git/.gitignore"`

## 相关sql路径
`/support/db`

## 相关脚本路径
`/support/nginx|shell`

## 启动类
```java
xyz.muses.MusesChatApplication
```

## 启动参数

```properties
-Denv=DEV
-Ddubbo.resolve.file=D:\workspace\muses-spring-boot-webmvc\src\test\resources\dubbo-resolve.properties
```

## 单元测试-请参考
```java
xyz.muses.TestDemo
```

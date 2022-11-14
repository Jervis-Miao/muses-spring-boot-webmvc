#muses-spring-boot-archetype

1. 创建脚手架并打包打开终端，到这个项目的根目录，然后创建 <code>archetype</code>

   ```shell
   cd ${workspace}/muses-spring-boot-archetype
   mvn archetype:create-from-project -s ${maven_home}/setting.xml
   ```


2. 接着到生成的target目录，将项目打包到本地仓库。

   ```shell
   cd ${workspace}/muses-spring-boot-archetype/target/generated-sources/archetype
   mvn install -s ${maven_home}/setting.xml
   ```

3. 在 IDEA 中新建一个项目，选择 Maven Archetype --> Catalog（填写自己本地仓库的地址）--> 输入muses，之后在弹出的对话框中填入坐标和版本号，需要注意的是工件 ID 的最后应该有 -archetype 后缀。


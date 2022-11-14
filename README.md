muses-spring-boot-archetype

1. 创建脚手架并打包打开终端，cd 到这个项目的根目录，比如这里是 ${workspace}/muses-spring-boot-archetype，然后运行<code> mvn archetype:create-from-project</code>，等待构建完成。
2. 接着 cd 到生成的 target/generated-sources/archetype 目录，运行 <code>mvn install</code>，这时候会将项目打包到本地仓库。
3. 在 IDEA 中新建一个项目，选择 Maven Archetype --> Catalog（填写自己本地仓库的地址）--> 输入muses，之后在弹出的对话框中填入坐标和版本号，需要注意的是工件 ID 的最后应该有 -archetype 后缀。



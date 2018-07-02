





## Springboot+Vue.js搭建前后端分离项目

[https://blog.codecentric.de/en/2018/04/spring-boot-vuejs/](https://blog.codecentric.de/en/2018/04/spring-boot-vuejs/)

前台模块:

1. 安装vue-cli
`npm install --global vue-cli`

2. 进入路 ./frontend 
`vue init webpack frontend`

Tell webpack to output build contents to /target
The standard Node.js way is to create a dist directory for builds which 
contain the minified source code of our web application. But as we use 
Maven here, we need to have everything in the target directory. 
Therefore we need to change the generated frontend/config/index.js and 
replace the following lines:

```
index: path.resolve(__dirname, '../dist/index.html'),
assetsRoot: path.resolve(__dirname, '../dist'),
```
换成-->
```
index: path.resolve(__dirname, '../target/dist/index.html'),
assetsRoot: path.resolve(__dirname, '../target/dist'),
```



## spring boot 静态页面配置
```
spring.resources.static-locations=classpath:static/,file:static/,classpath:public/,file:public/
```
将会自动从配置的所有路径下找. *默认是static/*


## Log

```
#Slf4j
private static final Logger log = LoggerFactory.getLogger(TestController.class);
#Log4j2
private static final Logger log = LogManager.getLogger(RequestArgsAspect.class);
```





## HttpAop

1 引入aop依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2 准备测试Controller

3 穿件aop类
3.1 注意类上注解:
`@Aspect`和`@Component`
3.2 设置切点
```
@Pointcut("execution(public * com.wtgroup.demo.demospringboot.controller.AopController.*(..))")
```

4 效果
```
before controller
进入controller中aop()
after controller
after returning
```

## 统一异常处理


包装类的校验异常, 统一异常处理类, 也能够**捕获**.

> * 套路

1. 表单参数包装类接收, 在需要校验的字段上加入相应的校验规则注解.
2. controller方法上加入`@Valid`, 并加上入参:`BindingResult`.
3. 校验有错误, 则抛出异常, 让统一*异常处理类*处理, 返回统一的*响应结果包装类*.



## 枚举类新玩法

> 定义公共方法, 相当于抽象方法. 各个枚举元素实现此方法.

为了逼真, 外层定义的公共方法, 什么都不做, 仅抛出 `java.lang.AbstractMethodError`.
当然, 也可以定义公有的方法体.



## `@RequestParam` and `@RequestBody`

前者适用于获取get url请求参数; 后者适用于请求体参数
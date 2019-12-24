
# Nest最佳实践：nest-plus

nest-plus是一套基于spring容器的nest的扩展。nest-plus简化了nest的使用方法，尤其是spring boot环境下。

nest-plus不仅仅集成了spring，还集成了activemq、rocketmq、rabbitmq、redis、automapper等组件，让nest的使用如丝更顺滑。


## 快速开始

> 参考演示代码 [nest-plus-demo](https://github.com/jovezhao/nest-plus/tree/master/nest-plus-demo)
>
### 第一步：添加引用
**使用Maven方式添加引用**

```xml
<dependency>
  <groupId>com.zhaofujun.nest</groupId>
  <artifactId>nest-plus-spring-boot-starter</artifactId>
  <version>2.0.8</version>
</dependency>
```
*使用Gradle方式添加引用*

```groovy
implementation 'com.zhaofujun.nest:nest-plus-spring-boot-starter:2.0.8'
```

在`nest-plus-spring-boot-starter`模块使用`SpringBeanContainerProvider`集成了Spring的IOC来完成Bean容器的支持
使用`NestAspect`集成了Spring的AOP实现了应用服务的切面处理，自动完成对工作单元的提交。
通过`NestAutoConfiguration`完成nest的自动配置，包括`NestApplication`和`EventBus`等。

### 第二步：创建spring boot应用程序

```java
package com.zhaofujun.nest.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

}

```
### 第三步：实现领域模型

```java
package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.BaseEntity;

public class User extends BaseEntity<StringIdentifier> {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void init(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    @Override
    public void verify() {
        if (age < 10) {
            throw new EntityVerifyException("年龄太小了");
        }
    }
}

```


### 第四步：添加应用服务
```java
package com.zhaofujun.nest.demo.application;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.demo.contract.TestEventData;
import com.zhaofujun.nest.demo.contract.UserService;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;

@AppService
public class UserAppService implements UserService {
    
    public void create() {

        User user = EntityFactory.createOrLoad(User.class, StringIdentifier.valueOf("111"));
        user.init("老赵", 5);

        user.changeAge(50);
       
        eventBus.publish(eventData);


        User use = EntityFactory.createOrLoad(User.class, StringIdentifier.valueOf("111"));
        use.changeAge(20);
    }

    public void changeAge() {
        User use = EntityFactory.load(User.class, StringIdentifier.valueOf("111"));
        use.changeAge(20);
    }

}

```

### 第五步：添加Rest适配器

```java
package com.zhaofujun.nest.demo.adapter.rest;

import com.zhaofujun.nest.demo.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String create() {
        userService.create();
        return "publish";
    }

    @GetMapping("/change")
    public String change() {
        userService.changeAge();
        return "change";
    }
}

```
## 使用原则

### 异常处理

Nest为用户定义了两个级别的异常，分别是系统异常和业务异常。

**系统异常**

表示程序未考虑到的，需要程序员优化处理的异常统称为系统异常。系统异常自动使用Error级别的日志来记录当前异常状态

系统异常一般不对用户开放，所有系统异常都应该统一一个信息告知用户。

**业务异常**

在系统设计过程中事先计划好的，按业务术语返回给用户知悉的异常。业务异常使用Info级别的日志来记录。

在定义业务异常时需要继承`com.zhaofujun.nest.CustomException`实现。 如果您公司规划中已有定义了业务异常的父类，我们也可以使用`com.zhaofujun.nest.CustomExceptionable`接口来实现集成

### DDD-分层架构

在DDD中，系统一般会为用户界面层、应用服务层、领域层、基础设施层。


**微服务下，基于nest的应用架构图**

![image](http://assets.processon.com/chart_image/5de4aadae4b0df12b4afc034.png)

**用户界面层**

负责向用户显示信息和解释用户指令。它不仅仅是与用户的界面的交互，还包括使用指定的方式将系统的能力对外开放或集成。所以这里指的用户可以是另一个计算机系统，不一定是使用用户界面的人

随着现在前后端分离、微服务架构的发展，用户界面层也发生了很大的改变。甚至可能跨越WEB/APP前端、微服务网关和RestController等。

**应用服务层**

定义系统具备的所有能力，以用例为基础保证事务一致性，调用领域层的业务实现来组装业务功能。

应用服务层主要完成
- 服务契约、行为契约与数据契约定义和实现
- 事务一致性保障
- 通过领域层的业务实现来组装业务功能
- 使用Assembler来完成数据契约与领域实体的转换 

**领域层**

系统的核心业务层，由领域模型与领域服务组成，是领域战术设计的核心体现。

**基础设施层**

提供基础设施的集成功能，包括但不限于文件、缓存、数据库等。

推荐将领域模型的仓储实现放在基础设施中，以保证领域层只关注业务逻辑而不需要关注数据，包括对数据库操作的数据库访问对象DAO、访问方法等。

### DDD-六边形架构

![img](http://assets.processon.com/chart_image/5de4dabce4b0b2fab740da6e.png)

六边形架构是也是一种分层架构的体现，他的分层视图不在于上下，而在于内外。

六边形架构将系统当成由对内或对外的端口组成，每一个端口又对应着一种适配器。而系统的独心独立，由应用服务及其内部的领域模型组成。

对内的适配器接入外部请求，调用对应的应用服务完成业务请求。

对外的适配器集成三方系统，如数据库、缓存、其它服务等，通过依赖反转的原则供应用服务层调用。

使用六边形架构思想设计的产品，核心功能独立，与外部无直接依赖，方便基于mock的单元测试。

## 缓存通道扩展与集成

Nest支持用户自定义集成第三方缓存系统，只需要实现`com.zhaofujun.nest.cache.provider.CacheProvider`接口即可。

在nest-plus中，使用`nest-plus-spring-redis`实现了对redis的集成，该集成方案基于`spring-boot-starter-data-redis`。

如果您的项目中已经使用了`spring-boot-starter-data-redis`来集成redis，添加`nest-plus-spring-redis`模块的依赖即可完成redis的集成。

如果您的项目没有使用过`spring-boot-starter-data-redis`，请参考[官方配置参数](https://spring.io/projects/spring-data-redis#overview)完成redis相关配置

对应的缓存提供者代号为`com.zhaofujun.nest.redis.RedisCacheProvider.CODE`。

**演示一：将领域实体使用redis来缓存**
```java
    package com.zhaofujun.nest.demo;
    
    import com.zhaofujun.nest.NestApplication;
    import com.zhaofujun.nest.event.ApplicationEvent;
    import com.zhaofujun.nest.event.ApplicationListener;
    import com.zhaofujun.nest.event.ServiceContextListener;
    import com.zhaofujun.nest.event.ServiceEvent;
    import org.springframework.beans.BeansException;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.ApplicationContextAware;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    import java.lang.reflect.Method;
    
    @Configuration
    public class DemoConfiguration   {
        
        @Bean
        public CacheConfiguration entityCacheConfiguration() {
            CacheConfiguration cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.setCacheCode(EntityCacheUtils.getCacheCode());
            cacheConfiguration.setName("实体缓存策略");
            cacheConfiguration.setProviderCode(RedisCacheProvider.CODE);
            return cacheConfiguration;
        }
        
    }

```
**演示二：将事件消息使用redis来存储**

```java
package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration   {

    @Bean
    public CacheConfiguration messageStoreConfiguration() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setCacheCode(DefaultMessageStore.CACHE_CODE);
        cacheConfiguration.setName("事件消息存储策略");
        cacheConfiguration.setProviderCode(RedisCacheProvider.CODE);
        return cacheConfiguration;
    }

}

```

## 事件通道扩展与集成

### 事件的发起与处理


**演示：创建事件数据对象**
```java
package com.zhaofujun.nest.demo.contract;

import com.zhaofujun.nest.core.EventData;

public class TestEventData extends EventData {

    public static final String Code="testEvent";
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getEventCode() {
        return Code;
    }


    @Override
    public String toString() {
        return "TestEventData{" +
                "data='" + data + '\'' +
                '}';
    }
}

```
**发起事件**
```java
package com.zhaofujun.nest.demo.application;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.demo.contract.TestEventData;
import com.zhaofujun.nest.demo.contract.UserService;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;

@AppService
public class UserAppService implements UserService {

    @Autowired
    private EventBus eventBus;


    public void create() {

        User user = EntityFactory.createOrLoad(User.class, StringIdentifier.valueOf("111"));
        user.init("老赵", 5);

        user.changeAge(50);
        TestEventData eventData = new TestEventData();
        eventData.setData("test event data");
        eventBus.publish(eventData);


        User use = EntityFactory.createOrLoad(User.class, StringIdentifier.valueOf("111"));
        use.changeAge(20);
    }
}

```
**处理事件**
```java
package com.zhaofujun.nest.demo.adapter.event;

import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.core.EventHandler;
import com.zhaofujun.nest.demo.contract.TestEventData;
import org.springframework.stereotype.Component;

@Component
public class DemoEventHandle implements EventHandler<TestEventData> {
    @Override
    public String getEventCode() {
        return TestEventData.Code;
    }

    @Override
    public Class<TestEventData> getEventDataClass() {
        return TestEventData.class;
    }

    @Override
    public void handle(TestEventData testEventData, EventArgs eventArgs) {
        System.out.println(testEventData.toString());
    }
}

```
Nest的事件处理通道默认使用本地事件通道，本地事件通道基于内存同步处理。当前也支持用户自定义第三方消息中间件作为事件通道，集成第三方事件通道需要实现接口`com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer`

在nest-plus中，我们扩展了ActiveMQ、RabbitMQ和RocketMQ消息中间件的集成。

### ActiveMQ的集成与使用
在nest-plus中，添加`nest-plus-spring-activemq`模块即可完成ActiveMQ的集成，该模块使用`spring-boot-starter-activemq`为基础，相关配置参考[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-jms)

**演示：配置使用ActiveMQ通道发送事件**

```java
package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration   {

    @Bean
    public EventConfiguration testEventConfiguration() {
        EventConfiguration eventConfiguration = new EventConfiguration();
        eventConfiguration.setEventCode(DemoEventData.Code);
        eventConfiguration.setMessageChannelCode(ActiveMQMessageChannel.CHANNEL_CODE);
        return eventConfiguration;
    }

}

```

### RabbitMQ的集成与使用

在nest-plus中，添加`nest-plus-spring-rabbitmq`模块即可完成RabbitMQ的集成，该模块使用`spring-boot-starter-amqp`为基础，相关配置参考[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-amqp)


**演示：配置使用RabbitMQ通道发送事件**

```java
package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration   {

    @Bean
    public EventConfiguration testEventConfiguration() {
        EventConfiguration eventConfiguration = new EventConfiguration();
        eventConfiguration.setEventCode(DemoEventData.Code);
        eventConfiguration.setMessageChannelCode(RabbitMQMessageChannel.CHANNEL_CODE);
        return eventConfiguration;
    }

}

```

### RocketMQ的集成与使用


在nest-plus中，添加`nest-plus-spring-rocketmq`模块即可完成RabbitMQ的集成，该模块使用`org.apache.rocketmq:rocketmq-spring-boot-starter`为基础，相关配置参考[官方文档](https://github.com/apache/rocketmq-spring)

**演示：配置使用RocketMQ通道发送事件**

```java
package com.zhaofujun.nest.demo;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration   {

    @Bean
    public EventConfiguration testEventConfiguration() {
        EventConfiguration eventConfiguration = new EventConfiguration();
        eventConfiguration.setEventCode(DemoEventData.Code);
        eventConfiguration.setMessageChannelCode(RocketMQMessageChannel.CHANNEL_CODE);
        return eventConfiguration;
    }

}
```

## 集成AutoMapper

AutoMapper提供Bean的映射能力，可以帮助我们将领域对象转换为DTO对象，以及领域对象与持久化对象的互相转换。
AutoMapper允许目标对象对没有setter方法的字段进行映射。

> AutoMapper的使用请参考[官方文档](https://github.com/jovezhao/automapper)

`nest-plus-spring-automapper`模块提供了AutoMapper的spring集成方式，并且内置了`LongIdentifierConverter`、`StringIdentifierConverter`,`UUIdentifierConverter`转换器，并且将`AutoMapper`类放入容器中，我们可以直接注入`IMapper`来获取AutoMapper的能力。

**使用AutoMapper映射领域对象与持久化对象**
```java
package com.zhaofujun.nest.demo.infrastructure.repository;

import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.nest.context.model.EntityExistedException;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.infrastructure.persistence.UserDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.service.IUserDmoService;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.UserDmoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepository implements Repository<User> {

    @Autowired
    private UserDmoMapper userDmoMapper;

    @Autowired
    private AutoMapper autoMapper;


    @Autowired
    private IUserDmoService userDmoService;
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getEntityById(Identifier identifier, EntityLoader<User> entityLoader) {
        UserDmo userDmo = userDmoMapper.selectById(identifier.toValue());

        if (userDmo == null) return null;
        User user = entityLoader.create(identifier);
        autoMapper.map(userDmo, user);
        return user;
    }


    @Override
    public void insert(User use) {

        UserDmo userDmo = autoMapper.map(use, UserDmo.class);
        try {
            userDmoMapper.insert(userDmo);
        } catch (DuplicateKeyException ex) {
            throw new EntityExistedException("用户已经存在") {
            };
        }
    }

    @Override
    public void update(User use) {
        UserDmo userDmo = autoMapper.map(use, UserDmo.class);

        userDmoMapper.updateById(userDmo);
    }

    @Override
    public void delete(User user) {
        userDmoMapper.deleteById(user.getId().getId());

    }

    @Override
    public void batchInsert(List<User> users) {



        List<UserDmo> userDmoList = users.stream().map(p -> autoMapper.map(p, UserDmo.class)).collect(Collectors.toList());

        try {
            userDmoService.saveBatch(userDmoList);
        } catch (DuplicateKeyException ex) {
            throw new EntityExistedException("用户已经存在") {
            };
        }
    }
}

```

**自定义Mapper配置ClassMapping**

```java
package com.zhaofujun.nest.demo;

import com.zhaofujun.automapper.builder.ClassMappingBuilder;
import com.zhaofujun.automapper.mapping.ClassMapping;
import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.infrastructure.persistence.UserDmo;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration   {


    @Bean
    public ClassMapping UserDmo2Do() {
        return new ClassMappingBuilder(UserDmo.class, User.class, true)
                .field("id", "id")
                .builder();
    }
    
}

```

**自定义转换器**

```java

package com.zhaofujun.nest.demo;

import com.zhaofujun.automapper.builder.ClassMappingBuilder;
import com.zhaofujun.automapper.mapping.ClassMapping;
import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.infrastructure.persistence.UserDmo;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;
import com.zhaofujun.nest.event.ServiceContextListener;
import com.zhaofujun.nest.event.ServiceEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class DemoConfiguration   {


    @Bean
    public Converter TestConverter(){
        return new TestConverter();
    }
    
}

```

# 常见问题

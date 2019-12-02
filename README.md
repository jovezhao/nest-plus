
## Nest最佳实践：nest-plus

nest-plus是一套基于spring的，让程序员更便捷使用nest的扩展模块。




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

提供基础设施的集成功能，包括但不限于文件、缓存、数据库等。也可以将

### DDD-六边型架构

### 集成Spring与Spring boot

### 缓存通道扩展与集成

### 事件通道扩展与集成

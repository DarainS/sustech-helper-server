### 基于Spring-boot的学生助手后端服务及其React-Native前端APP的开发



Members（totally 1）：

邓收港 11310388



## Motivation

- 学生有看课表的需求
- 学生查看Sakai各科目是否有更新作业操作步骤与页面跳转较繁琐，希望简化这一系列动作
- 学生有查询绩点与成绩的需求

希望能够开发出一个APP，集成这些功能。



## User Benefits

### 对于普通用户：

- 解决了常用的一些功能需求
- 提供了Android与iOS双平台的APP

### 对于开发者：

- 提供了restful API
- 提供了完整的代码实现



## Features and Requirements

### Back End Features

- Restful API，对于所有实现的功能，完成了其restful API的开发
- 提供restfulAPI的文档
- 实现了基于springsecurity的权限控制系统
- 实现了基于Aspect的日志管理系统
- 实现了基于Druid的基础性能监控
- 实现了基于redis的缓存系统
- 完成了基本的测试模块
- 项目地址:https://gitlab.com/Darain/sustech-helper-server

### Back End Requirements

- spring boot
- redis
- MySQL
- MyBatis
- spring security
- lombok
- Jsoup
- Gson
- druid
- kotlin
- ……

### Front End Features

- 基于React-Native框架的双平台APP
- 实现了自动导入课表的功能
- 实现了查询sakai作业的功能
- 实现了查询课程成绩的功能
- 实现了数据存储及自动更新
- 项目地址:https://gitlab.com/Darain/sustech-helper

### Front End Requirement

- react-native
- react
- fetch
- babel
- jest
- ……

## Design 

### Back End Design

- 总体使用MVC的设计模式
- 权限控制
  - 使用了基于Role的权限控制
- 使用AOP技术进行代码复用
- 使用restful接口
- 数据全部存储在redis与MySQL

### Front End Design

- 总体使用Single-Page的设计模式

## User Interface

### 对于个人用户

- 提供了Android与iOS的双平台APP
- 自动获取所有数据
- 完整的数据展示接口

### 对于开发者

- 提供restful API接口
- 提供API接口说明文档

## Testing

- 使用Junit框架进行单元测试
- 使用Kotlin语言编写测试代码
- 使用Postman工具进行http接口测试
- 使用druid进行性能监控













## 附录：API说明

### 用户登录

登录请求:

```java
Method:POST
url:http://localhost:8080/oauth/token
headers:{
  Authorization:'Basic YXBwOmFwcA=='
}
body:{
  username:'',//用户名
  password:'',//密码
  grant_type:'password',//默认为密码认证
}
```

返回结果(JSON):

```java
{
  "access_token": "d307839c-d9fd-47ed-8791-e61d9914ae88",//使用此值访问
  "token_type": "bearer",
  "refresh_token": "4fea61d0-968d-47f9-9e22-64e61524325b",
  "expires_in": 603347,
  "scope": "read write"
}
```

### 查询成绩

token请求:

```java
Method:GET
url:"http://localhost:8080/user/grade"
headers:{
  Authorization:'access_token' //使用登录请求返回的token
}
```

用户名密码请求:

```python
Method:POST
url:"http://localhost:8080/api/user/grade"
headers:{
  username:'',
  passwrod:'',    
}
```

返回结果(JSON):

```javascript
{
  "code":200,
  "msg":"success",
  "result":{
    "studsentid":studsentid,
    "termGradeList": [
	{
      "term":term,//学期
      "gradeList":[
        {
          "courseid": "CH101",
            "courseName": "化学原理",
            "grade": "86",
            "credit": 4,
            "courseAttribute": "必修"
        },
        //...
      ]
	},
    ]
  }
}
```



### sakai作业查询

token请求:

```javascript
Method:GET
url:"http://localhost:8080/sakai/homework?refresh=true"
headers:{
  Authorization:'access_token' //使用登录请求返回的token
}
```

用户名与密码请求:

```javascript
Method:POST
url:"http://localhost:8080/api/sakai/homework?refresh=true"
headers:{
  username:'',
  password:'',
}
```



### 课表查询

token请求:

```javascript
Method:GET
url:"http://localhost:8080/user/courseTable?refresh=true"
headers:{
  Authorization:'access_token' //使用登录请求返回的token
}
```

用户名与密码请求:

```javascript
Method:POST
url:"http://localhost:8080/api/user/courseTable?refresh=true"
headers:{
  username:'',
  password:'',
}
```

返回结果(JSON):

```json
{
  "code": 200,
  "result": {
    "studentid": studentid,
    "courses": [
      {
        "weekly": 4,
        "section": 2,
        "courseName": "软件工程",
        "teachers": "张煜群",
        "classRoom": "一教108"
      },
      {
        "weekly": 4,
        "section": 3,
        "courseName": "软件工程",
        "teachers": "朱悦铭",
        "classRoom": "二教205机房"
      },
    ]
  },
  "msg": "success"
}
```


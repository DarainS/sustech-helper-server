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


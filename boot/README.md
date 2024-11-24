### 1. 기본 정보

- springboot 3.3.4
- java 17
- gradle 8.9

### 1. VSCode 환경

#### 1) Extensions

- Debugger for Java
- Extension Pack for Java
- Gradle for Java
- Lombok Annotations Support for VS Code
- Spring Boot Dashboard
- Spring Boot Extension Pack
- Spring Boot Tools
- Spring Initializr Java Support

#### 2) Settings

- `java.configuration.runtimes` (Required. java17+)
    ```json
    "java.configuration.runtimes": [
        {
        "name": "JavaSE-17",
        "path": "C:\\develop\\tools\\java\\corretto-17.0.12.7.1",
        },      
    ],
    ```


#### 3) Run and Debug

- scouter-agent 를 적용 시 vmArgs 추가
    ```json
    {
        "version": "0.2.0",
        "configurations": [
            {
                "type": "java",
                "name": "Current File",
                "request": "launch",
                "mainClass": "${file}"
            },
            {
                "type": "java",
                "name": "UserMain",
                "request": "launch",
                "mainClass": "youngmin.spring.boot.UserMain",
                "projectName": "boot-api-user",
                "vmArgs": [
                    "-javaagent:${cwd}\\boot\\scouter\\scouter.agent.jar",
                    "-Dscouter.config=${cwd}\\boot\\scouter\\scouter.agent.conf",
                    "-Dobj_name=boot-api-user",
                ]
            }
        ]
    }
    ```
- scouter-server, scouter-client 실행

    _생략_


### 2. features

#### 1) basic controller

- GET 방식의 api 요청에 json 형식(Content-Type: application/json) 으로 응답하는 예시입니다.
- curl -X GET http://localhost:8080/user/v1/greeting?lang=en
- GET 방식의 querystring 은 `required = false` 에 의해 생략될 수 있습니다. (기본은 true 이며, 없을 경우 Exception 발생)

```java
@RestController
public class GreetingController {
  @GetMapping("/user/v1/greeting")
  public ResponseEntity<GreetingRes> greeting(
      @RequestParam(name = "lang", required = false) String lang) {
    GreetingResDto.GreetingRes resDto = new GreetingResDto.GreetingRes();
    if ("en".equals(lang)) {
      resDto.setMessage("hello");
    } else if ("de".equals(lang)) {
      resDto.setMessage("Hallo");
    } else {
      resDto.setMessage("안녕하세요");
    }
    return ResponseEntity.ok(resDto);
  }
}
```
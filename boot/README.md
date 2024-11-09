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


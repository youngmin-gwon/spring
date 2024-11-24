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


#### 2) mybatis crud

##### 2.1) library

- `spring-boot-starter-jdbc`: for datasource, HikariCP 가 의존성으로 선언되어 있으므로 dbcp(DataBase Connection Pool)는 HikariCP 로 구성함
- `log4jdbc-log4j2-jdbc4.1`: JDBC 드라이버를 proxy 하여 sql 이 실행될 때마다 파라미터가 mapping 된 sql 을 출력해주는 라이브러리 (production 에서는 제거) 
- `postgresql`: PostgreSQL(rdbms) 서버에 연결을 하기 위한 JDBC Driver

```gradle
dependencies {
  // ...
  implementation "org.modelmapper:modelmapper:${modelmapper_version}"
  
  implementation "org.springframework.boot:spring-boot-starter-jdbc"
  implementation "org.mybatis.spring.boot:mybatis-spring-boot-starter:${mybatis_version}"
  implementation "org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:${log4jdbc_version}"
  implementation "org.postgresql:postgresql"
  
  annotationProcessor "org.mapstruct:mapstruct-processor:${mapstruct_version}"
  compileOnly "org.projectlombok:lombok-mapstruct-binding:${lombok_mapstruct_binding_version}"
}
```

##### 2.2) spring config

```yml
spring:
  profiles:
    active: local
  application:
    name: boot-api-user
  sql:
    init:
      # DataSource 생성 시 schema-locations 에 있는 DDL file, data-locations 의 DML 파일을 실행
      mode: always # 항상 실행
      platform: postgres # See: SettingsCreator:52 fallback 으로 설정할 때 활용할 수 있음
      schema-locations: classpath:schema-postgres.sql
      data-locations: classpath:data.sql
  datasource:
    # log4jdbc-log4j2-jdbc4.1 라이브러리의 Proxy JDBC Driver 를 설정 
    driver-class-name: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
    # prosgresql 의 jdbc url 형식 설정
    url: jdbc:log4jdbc:postgresql://localhost:5432/spring
    username: spring
    password: 1
    hikari:
      connection-timeout: 250
      maximum-pool-size: 3
      max-lifetime: 1800000
      pool-name: hikari

mybatis:
  # mybatis 의 sqlmap file 의 경로 패턴 설정
  mapper-locations: classpath*:mapper/*.xml
  # mybatis 의 ParameterType, ResultType 에서 사용할 패키지를 생략할 대상의 경로 패턴 설정
  type-aliases-package: youngmin.**.vo; java.lang
```

##### 2.3) 실행

- curl -X GET http://localhost:8080/user/v1/scientist/all
- curl -X GET http://localhost:8080/user/v1/scientist/1

```
@RestController
@RequiredArgsConstructor
public class ScientistController {

  final ModelMapper modelMapper;
  final ScientistDao scientistDao;
  
  @GetMapping("/user/v1/scientist/all")
  public ResponseEntity<ScientistResDto.ScientistList> findAll() {
    List<ScientistVo> result = scientistDao.findAll();
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/user/v1/scientist/{id}")
  public ResponseEntity<ScientistResDto.Scientist> findById(@PathVariable(name = "id") String id) {
    ScientistVo result = scientistDao.findById(id);
    ScientistResDto.Scientist resDto = modelMapper.map(result, ScientistResDto.Scientist.class);
    return ResponseEntity.ok(resDto);
  }
}
```
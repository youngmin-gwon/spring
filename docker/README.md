
## 1. 디렉토리 구성

- `images` 디렉토리는 docker hub 에서 제공하지 않는 custom image 를 직접 생성하기 위해 만든 디렉토리

```
$ tree ./docker -L 3
./docker
|-- README.md
|-- images
|   `-- scouter-server
|       |-- Dockerfile
|       |-- app
|       `-- build.sh
`-- nexus.yml
```



## 2. Docker Image 저장소 구성

### 1) nexus 컨테이너 실행 

```shell
docker-compose -f ./nexus.yml up -d
```

### 2) Docker (Hosted) 저장소 생성

- `HTTP` (Create an HTTP connector at specified port. Normally used if the server is behind a secure proxy.) 체크 후 5000 으로 설정

### 3) Docker Host 서버 설정

- `/etc/daemon.json` 파일 설정 추가

```json
cat /etc/docker/daemon.json 
{
  "insecure-registries": ["localhost:5000"],
  "dns": ["8.8.8.8", "8.8.4.4"]
}
```


## 3. Docker Image 생성

### 3.1. scouter-server

#### 1) docker build & push

- `images/scouter-sever` 디렉토리에서 실행
- (선행 조건) nexus 컨테이너에 Docker (Hosted) 저장소가 http:5000 으로 Listen 상태여야 함
- `localhost:5000` 는 Docker (Hosted) 저장소를 의미함

```shell
# docker.io 의 spring 계정에 scouter-server 이미지로 push 됨
# i.e) docker.io/spring/scouter-server 
#docker build -t spring/scouter-server:2.20.0 .
#docker push spring/scouter-server:2.20.0

# nexus 의 Docker (Hosted) 저장소에 push 하려면
# nexus 저장소를 설정해야 함
# docker push 를 실행하기 전에는 저장소에 docker login 이 수행되어야 함
docker build -t localhost:5000/spring/scouter-server:2.20.0 .
docker login localhost:5000 -u admin -p 1
docker push localhost:5000/spring/scouter-server:2.20.0
```

#### 2) docker run

- Docker Host 에서 명령어 실행
- scouter-server.yml 에는 `localhost:5000/spring/scouter-server:2.20.0` 이미지가 설정되어 있음 

```shell
docker-compose -f scouter-server.yml up -d
```

#### 3) scouter-client 연결

- `Server Address` 는 Docker Host 의 IP 와 Port 6100 으로 설정. e.g) 172.28.200.50:6100


#### 4) scouter release 정보

- https://github.com/scouter-project/scouter/releases



## 4. spring-boot 개발 환경

### 4.1. postgres

- URL: `jdbc:postgresql://IPADDR:5432/spring`
- Database: spring
- Username / Password: spring/1

```shell
docker-compose -f postgres.yml up -d
```


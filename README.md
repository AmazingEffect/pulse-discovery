# pulse 디스커버리 서버

## 유레카 웹 접속 방법
- ```http://localhost:8761``` 로 접속하면 유레카 대시보드를 확인할 수 있습니다.

## AWS 환경에서 유레카 적용하는 방법
- 로컬환경과 AWS 환경에서 유레카 서버를 적용하는 방법은 다릅니다.
  - 로컬에서는 그냥 실행하면 됩니다. (application.yml을 그대로 사용해주세요.)
  - AWS에서는 다른 방법으로 설정해야 합니다. (application-aws.yml을 사용해주세요.)
- 환경 변수에 EC2 퍼블릭 호스트네임으로 값이 설정되어야 정상 동작합니다. 
- 이를 확인하려면 EC2 인스턴스에서 아래 명령으로 값을 확인하세요.
```bash 
echo $EC2_PUBLIC_HOSTNAME
```
- 만약 설정되지 않았다면 아래 명령으로 추가하세요 (하단에 설명 추가)
``` bash
export EC2_PUBLIC_HOSTNAME=$(curl -s http://169.254.169.254/latest/meta-data/public-hostname) 
```

## 참고
- ```http://169.254.169.254/latest/meta-data/``` 는 AWS EC2 메타데이터 서비스의 기본 주소입니다. 이 주소는 EC2 인스턴스 내부에서만 접근 가능하며, AWS에서 실행 중인 인스턴스의 메타데이터(예: 퍼블릭 IP, 호스트네임, IAM 역할 등)에 대한 정보를 제공합니다.
- 조금더 설명드리자면 ```latest/meta-data/public-hostname``` 이 경로는 메타데이터 서비스에서 EC2 인스턴스의 퍼블릭 호스트네임을 반환합니다.

- 그래서 아래의 명령을 EC2 내부에서 실행하면 해당 인스턴스의 퍼블릭 호스트네임(예: ec2-203-0-113-25.compute-1.amazonaws.com)을 반환합니다. 이 값은 AWS에서 퍼블릭 네트워크로 접근할 때 사용하는 도메인입니다. 
```bash
curl http://169.254.169.254/latest/meta-data/public-hostname
```

## 왜 이 값을 사용하는가?
- AWS에서 실행되는 Eureka 서버는 퍼블릭 네트워크에서 접근 가능하도록 퍼블릭 호스트네임이나 IP 주소를 설정해야 합니다. 이를 자동화하려면 curl을 사용해 메타데이터에서 값을 가져옵니다.

## 주의사항
- EC2 내부에서만 사용 가능: ```http://169.254.169.254``` 는 EC2 내부에서만 접근할 수 있습니다. 로컬 PC나 외부 네트워크에서는 사용할 수 없습니다.
- 메타데이터 서비스는 IAM 인증 정보를 포함할 수 있으므로, 필요 이상의 경로를 노출하지 않도록 주의하세요.
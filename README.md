# GithubAction - ElasticBeanstalk 배포 과정

### step1 - AWS 사용자 생성 및 GithubAction으로 CI하기
- IAM 사용자 생성 (AdministratorAccess-AWSElasticBeanstalk 권한주기)
- Github Secret에서 사용자 Access Key, Secret Key 연결하기
- .github/workflows/deploy.yml 파일 생성
- github에 푸시하기

### step2 - 추가 배치 롤링 방식
- github에 푸시되면 자동으로 GithubAction에 통합되고 테스트, 빌드 후 S3로 배포된다.
- 엘라스틱 빈스톡을 사용하면 S3의 권한을 줄 필요가 없다. 해당 권한(AdministratorAccess-AWSElasticBeanstalk) 내부에 S3 권한이 있나보다.
- S3에 배포가 완료되면 롤링 방식에 따라 배포가 진행된다.
- 추가배치를 사용했기 때문에 새로운 인스턴스에 배포가 진행된다.
- 배포가 완료되면 80포트로 테스트를 내부적으로 진행한다.
- 80포트 / 주소로 요청시 200 ok가 되지 않으면 기존 인스턴스의 업데이트가 실패하게 된다.

### step3 - nginx 리버시 프록시
- .platform/nginx/nginx.conf 파일을 이용하여 80포트 요청시 내부 임베디드 8080으로 요청을 리다이렉션 해준다.
- 200 ok가 뜨기 때문에 기존 인스턴스를 업데이트하게 된다.
- 업데이트시에 deploy.zip 파일이 배포된다.
- .ebextensions/00-makeFiles.config 파일이 배포되면서 그 내부가 Procfile에 의해서 실행된다.
- 실행이 완료되면 배포가 완료된다. 
- 이때 기존 Java 서버가 실행되고 있다면 꼭 중지하고 재실행해줘야 한다.
# Nginx 이미지를 베이스로 사용
FROM nginx:alpine

# SSL 인증서 디렉토리 생성
RUN mkdir -p /etc/nginx/ssl

# Nginx 설정 파일 복사
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Java 애플리케이션을 위한 OpenJDK 설치
RUN apk add --no-cache openjdk17-jdk

WORKDIR /app

# Create log directory
RUN mkdir -p /app/logs

# Copy the JAR file
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Nginx와 Java 애플리케이션을 동시에 실행하기 위한 스크립트
COPY start.sh /start.sh
RUN chmod +x /start.sh

# 필요한 포트 노출
EXPOSE 80 443 8080

# 시작 스크립트 실행
CMD ["/start.sh"]
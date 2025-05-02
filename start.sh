#!/bin/sh

# Java 애플리케이션 백그라운드에서 실행
java -jar /app/app.jar &

# Nginx 실행
nginx -g "daemon off;" 
FROM openjdk:8u171-jdk-alpine3.8
RUN echo https://mirrors.aliyun.com/alpine/v3.8/main/ > /etc/apk/repositories && \
    echo https://mirrors.aliyun.com/alpine/v3.8/community/ >> /etc/apk/repositories
RUN apk update && apk upgrade
RUN apk update \
    && apk add --no-cache \
    && apk add tzdata && \
    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone
RUN apk add font-adobe-100dpi ttf-dejavu
#RUN set -xe && apk --no-cache add ttf-dejavu fontconfig
RUN echo "export LANG=zh_CN.UTF-8" >> /root/.bashrc
RUN export LANG=zh_CN.UTF-8
RUN export LC_ALL=zh_CN.UTF-8
ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai"


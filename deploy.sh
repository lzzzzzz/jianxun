#/bin/bash
./gradlew initEnv -Penv=prod
./gradlew build -x test
scp -P 8237 build/libs/qupingfang-0.0.1-SNAPSHOT.jar root@39.100.84.15:/var/html/service/qpf.jar
echo "执行远程服务器上的脚本：/var/html/start.sh"
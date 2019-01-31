GRADLE=./gradlew
WORKDIR=./AndroidMVCLibrary
SONAR="jacocoTestReport sonarqube"

# Move to the right source path
echo ${WORKDIR}
echo ${GRADLE}
#cd ${WORKDIR}
${GRADLE} clean test ${SONAR} \
    -Dsonar.projectKey=evedimensinfin_android-mvc \
    -Dsonar.organization=evedimensinfin \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.login=ff5f7df722e8b57859b7d5bb732f4e79e3395a02


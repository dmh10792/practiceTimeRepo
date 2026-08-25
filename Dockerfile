# Global varaibles
ARG NODE_VERSION=20
ARG JAVA_VERSION=21

FROM node:${NODE_VERSION} AS frontend_builder
USER 65532
# Local variables
ARG NPM_REGISTRY="https://registry.npmjs.org"
ARG PROJECT_DIR="."
WORKDIR ${PROJECT_DIR}/frontend
COPY --chown=65532:65532 frontend/ ${PROJECT_DIR}/frontend
# the VULCAN_TOKEN env variable will be needed for as long as tesseract packages are hosted in Vulcan Gitlab
# and app team's .yarnrc's reflect that
#RUN npm config set registry=${NPM_REGISTRY} ignore-scripts=true 
#RUN find . -name .npmrc -exec sed -i "s#https://registry.npmjs.org#${NPM_REGISTRY}#g" {} \;
#RUN find . -name yarn.lock -exec sed -i "s#https://registry.yarnpkg.com#${NPM_REGISTRY}#g" {} \;
#RUN find . -name yarn.lock -exec sed -i "s#https://registry.npmjs.org#${NPM_REGISTRY}#g" {} \;
RUN --mount=type=secret,id=PLATFORM_VULCAN_TOKEN,env=VULCAN_TOKEN \
    --mount=type=secret,id=PACKAGES_PULL_TOKEN,env=PACKAGES_PULL_TOKEN \
    yarn install --ignore-scripts --update-checksums && yarn build 

FROM eclipse-temurin:${JAVA_VERSION}-jdk AS backend_builder
ENV CI=true
USER 65532
ARG PROJECT_DIR
ARG GRADLE_USER_HOME=${PROJECT_DIR}/.gradle
WORKDIR ${PROJECT_DIR}
COPY --chown=65532:65532 . ${PROJECT_DIR}
#RUN mkdir -p .gradle && cp init.gradle .gradle/init.gradle 
COPY --chown=65532:65532 --from=frontend_builder ${PROJECT_DIR}/frontend/build/ ${PROJECT_DIR}/src/main/resources/static
RUN --mount=type=secret,id=PLATFORM_VULCAN_TOKEN,env=VULCAN_TOKEN \
    --mount=type=secret,id=PACKAGES_PULL_TOKEN,env=PACKAGES_PULL_TOKEN \
    ./gradlew -x copyFrontend -x test -x installFrontend -x buildFrontend build 
RUN find ${PROJECT_DIR}/build/libs
# below ensures only the .jar we want remains after the build is complete
RUN rm ${PROJECT_DIR}/build/libs/*-plain.jar

# # # build runtime container using jar file
FROM eclipse-temurin:${JAVA_VERSION}-jre
ARG PROJECT_DIR
#ENV JAVA_TOOL_OPTIONS="--module-path=/usr/share/java/bouncycastle-fips"
USER 65532
COPY --from=backend_builder ${PROJECT_DIR}/build/libs/*.jar /app/application.jar
RUN find /app
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/application.jar"]

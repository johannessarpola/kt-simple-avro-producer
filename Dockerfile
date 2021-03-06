ARG VERSION=11

FROM gradle:jdk${VERSION}-alpine as build

COPY . /src
WORKDIR /src
RUN gradle --no-daemon shadowJar

FROM eclipse-temurin:${VERSION}-jre-alpine

COPY --from=build /src/build/libs/*.jar /bin/runner/app.jar
WORKDIR /bin/runner

CMD ["java","-jar","app.jar"]
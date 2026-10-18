FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY Calculator.java .
COPY index.html .

RUN javac --add-modules jdk.httpserver Calculator.java

EXPOSE 8080

CMD ["java", "--add-modules", "jdk.httpserver", "Calculator"]

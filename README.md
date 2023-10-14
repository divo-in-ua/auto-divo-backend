Run project local:
`./gradlew bootRun `

Build project as an JAR:
`./gradlew build `

Build the Docker Image (e.g. _docker build -t CustomName:CustomTag ._):
`docker build -t divo-auto:0.0.1 .`

Build JAR and Docker the same time:
`./gradlew build && docker build -t divo-auto:0.0.1 . `

Run the Docker Container:
` docker run -p 8080:8080 divo-auto:0.0.1 `
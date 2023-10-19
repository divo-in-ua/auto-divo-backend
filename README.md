##### Run project local:
`./gradlew bootRun`

##### Build project as an JAR:
`./gradlew build`

##### Build JAR, Docker Image, Upload to Docker Hub
run `./scripts/build-and-upload.sh --branch PRODUCTION --token YOUR_DOCKER_HUB_ACCESS_TOKEN`

##### Build the Docker Image (e.g. _docker build -t "UsernameInDockerHub/RepositoryName:BuildTag" ._):
`docker build -t divoinua/auto-backend:latest .`

##### Build JAR and Docker the same time:
with manual tag: `./gradlew build && docker build -t divoinua/auto-backend:latest .`
with timestamp: 
`./gradlew build && docker build -t "divoinua/auto-backend:$(date +'%Y%m%d%H%M%S')" .`

##### Run the Docker Container:
` docker run -p 8080:8080 divo-auto:latest `

##### Upload Docker Container to DockerGub
login `docker login -u divoinua -p YOUR_ACCESS_TOKEN`
build `./gradlew build && docker build -t "divoinua/auto-backend:latest" .`
push `docker push divoinua/auto-backend:latest`
logout `docker logout`
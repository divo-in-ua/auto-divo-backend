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

or run with config file (DEVELOPMENT/PRODUCTION)

`docker run --name divo-backend \
-v /path-to-external/config/application-production.properties:/app/config/application-production.properties \
-e SPRING_PROFILES_ACTIVE=production \
-p 8080:8080 --rm divoinua/auto-backend:latest`

##### Upload Docker Container to DockerGub
login `docker login -u divoinua -p YOUR_ACCESS_TOKEN`
build `./gradlew build && docker build -t "divoinua/auto-backend:latest" .`
push `docker push divoinua/auto-backend:latest`
logout `docker logout`

##### Running dedicated MongoDB container
build `docker build -t divoinua/mongodb:latest -f Dockerfile.Mongodb .`
run `docker run -p 27017:27017 --name MONGODB -e MONGO_INITDB_ROOT_USERNAME=mongorootuser -e MONGO_INITDB_ROOT_PASSWORD=mongorootpassword --rm divoinua/mongodb:latest`
connect to SHELL into the running container `docker exec -it MONGODB /bin/bash` 
connect to database inside container `mongosh admin --host localhost --port 27017 -u your_root_user -p your_root_password --authenticationDatabase admin`
switch to inner database `db = db.getSiblingDB("divo")`
create first collection `db.createCollection("accessTokens")`
create first accessToken `db.accessTokens.insertOne({ token: "tokentest" })`
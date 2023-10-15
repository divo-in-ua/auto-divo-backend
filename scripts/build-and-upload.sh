#!/bin/bash

# # # WARNING # # #
# before the first running set this script as executable
# chmod +x ./scripts/build-and-upload.sh

echo "Please enter your Docker Hub Access Token (for repository DIVO_IN_UA): "
read -r -s DOCKER_HUB_ACCESS_TOKEN # The '-s' flag hides the input for security

if docker login -u divoinua -p "$DOCKER_HUB_ACCESS_TOKEN"; then
  echo "Login successful!"
  timestamp=$(date +'%Y%m%d%H%M%S') # Set timestamp

  # Build the JAR using Gradle, Build the Docker Container, Push it to the Docker Hub, logout
  ./gradlew build \
    && docker build -t "divoinua/auto-backend:$timestamp" . \
    && docker push divoinua/auto-backend:$timestamp \
    && docker logout \
    && echo "Image built and pushed successfully."

else
  echo "Login failed. Please check your password."
  exit 1  # Exit the script with an error status
fi



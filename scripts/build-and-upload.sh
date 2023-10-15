#!/bin/bash

# WARNING: Before the first run, set this script as executable with
# chmod +x ./scripts/build-and-upload.sh

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --branch)
      branch="$2"
      shift 2
      ;;
    -t|--token)
      DOCKER_HUB_ACCESS_TOKEN="$2"
      shift 2
      ;;
    *)
      echo "Unknown option: $1"
      exit 1
      ;;
  esac
done

if [ -z "$branch" ]; then
  echo "Please provide the Git branch name using the --branch argument."
  exit 1
fi

if [ -z "$DOCKER_HUB_ACCESS_TOKEN" ]; then
  echo "Please provide the Docker Hub Access Token using the --token argument."
  exit 1
fi

if docker login -u divoinua -p "$DOCKER_HUB_ACCESS_TOKEN"; then
  echo "Login successful!"
  timestamp=$(date +'%Y%m%d%H%M%S') # Set timestamp

  # Fetch the latest updates for the specified Git branch
  git fetch origin "$branch"

  # Check out the desired Git branch
  git checkout "$branch"

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


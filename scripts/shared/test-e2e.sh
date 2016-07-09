#!/usr/bin/env bash
set -e

MAIN_DOCKER_COMPOSE_FILE="-f docker-compose${1:-}.yml"
COMPOSE_FILES_PARAMS="$MAIN_DOCKER_COMPOSE_FILE -f compose/docker-compose.e2e.yml"

docker-compose $COMPOSE_FILES_PARAMS up -d

./scripts/e2e-wait.sh

gradle gulpTestE2E
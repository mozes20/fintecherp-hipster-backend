#!/usr/bin/env bash
# Helper script to build image (optional) and run the deploy compose
set -euo pipefail
SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ENV_FILE="$SCRIPT_DIR/.env.deploy"
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.deploy.yml"

if [ ! -f "$ENV_FILE" ]; then
  echo "Please copy .env.deploy.example to .env.deploy and update values:"
  echo "  cp .env.deploy.example .env.deploy"
  exit 1
fi

# Load env
set -o allexport
source "$ENV_FILE"
set +o allexport

# Optionally build image
if [ "${BUILD_LOCAL_IMAGE:-false}" = "true" ]; then
  echo "Building local image ${IMAGE_NAME}..."
  docker build -t "${IMAGE_NAME}" "$(cd "$SCRIPT_DIR/../.." && pwd)"
fi

# If internal DB requested, start postgresql service first
if [ "${USE_INTERNAL_DB:-false}" = "true" ]; then
  echo "Starting internal Postgres service"
  docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d postgresql
fi

# Start the stack
docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d

echo "Stack started"


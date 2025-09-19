#!/usr/bin/env sh
set -e
# If secrets exist, load them into env vars used by Spring
if [ -f /run/secrets/db_password ]; then
  export SPRING_DATASOURCE_PASSWORD=$(cat /run/secrets/db_password)
  export SPRING_LIQUIBASE_PASSWORD=$(cat /run/secrets/db_password)
fi
# allow Spring env override
exec java -jar /app/app.jar "$@"

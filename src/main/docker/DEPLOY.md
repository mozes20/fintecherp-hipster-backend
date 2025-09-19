# Deploy notes

This docker-compose override is intended for deploying the application to a Docker host
while using an external PostgreSQL instance.

Files:

- `docker-compose.deploy.yml` - unified deploy compose file that can use an external DB or the included internal Postgres based on `.env.deploy`.
- `.env.deploy.example` - example env file you should copy to `.env.deploy` and edit before deploying.

## How to run

1. Copy and edit env file:

   copy src\main\docker\.env.deploy.example src\main\docker\.env.deploy

   # edit src\main\docker\.env.deploy if needed (DB host/port/credentials)

2. Build the Docker image (or use a registry image):

   docker build -t fintecherp:latest .

3. Start the stack:

   docker compose -f src/main/docker/docker-compose.deploy.yml --env-file src/main/docker/.env.deploy up -d

## Notes

- By default use external DB values set in `.env.deploy`. To use the internal Postgres included in the repo, set `USE_INTERNAL_DB=true` in `.env.deploy` and restart compose.
- The compose file references the existing `keycloak.yml` for Keycloak configuration. Keycloak is started locally and exposed per its config.
- If your external Postgres requires SSL, adjust env vars and Spring properties accordingly (example: add `spring.datasource.hikari.connection-test-query` or `spring.datasource.url` with sslmode=require).

## Secrets

This setup supports Docker secrets for the DB password. By default an example secret file is included at `src/main/docker/secrets/db_password.secret`.
For production, replace that file with a secure secret or inject the secret via your orchestration platform. The app's entrypoint reads `/run/secrets/db_password` and sets `SPRING_DATASOURCE_PASSWORD` and `SPRING_LIQUIBASE_PASSWORD` automatically.

Using the helper script (`deploy.sh` or `deploy.ps1`) will pick up the secret file shipped in `src/main/docker/secrets` when you run compose locally. For real deployments, prefer not to keep secrets in the repo.

## Transferring image and config to the server

There are two common ways to move the built image and config to your target server.

Option A — Push to a Docker registry (recommended):

- Build and tag the image locally, then push to a registry accessible from the server (Docker Hub, private registry, AWS ECR, etc.). On the server, just pull and run.

Example (Docker Hub):

```bash
# locally
docker build -t yourdockerhubuser/fintecherp:latest .
docker push yourdockerhubuser/fintecherp:latest

# on server
docker pull yourdockerhubuser/fintecherp:latest
docker compose -f docker-compose.deploy.yml --env-file .env.deploy up -d
```

Option B — Export image to tar and copy via SCP (if no registry):

1. Save the image to a tar file locally and copy it to the server with scp.

```bash
# locally: save image to tar
docker save -o fintecherp.tar fintecherp:latest

# copy to server
scp fintecherp.tar user@185.65.68.120:/home/user/

# on server: load and run
docker load -i /home/user/fintecherp.tar
# copy your .env.deploy and secrets into /home/user/project/src/main/docker/
docker compose -f /home/user/project/src/main/docker/docker-compose.deploy.yml --env-file /home/user/project/src/main/docker/.env.deploy up -d
```

Copying config and secrets securely

- Never commit secrets to Git. Copy `.env.deploy` and `secrets/db_password.secret` to the server via a secure channel (scp or an automated CI secret injection). For example:

```bash
scp src/main/docker/.env.deploy user@185.65.68.120:/home/user/project/src/main/docker/.env.deploy
scp src/main/docker/secrets/db_password.secret user@185.65.68.120:/home/user/project/src/main/docker/secrets/db_password.secret
```

After copying, on the server run the compose as shown above.

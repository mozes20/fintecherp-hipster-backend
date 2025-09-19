param()
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$envFile = Join-Path $scriptDir '.env.deploy'
$composeFile = Join-Path $scriptDir 'docker-compose.deploy.yml'
if (-Not (Test-Path $envFile)) {
  Write-Host "Please copy .env.deploy.example to .env.deploy and update values:`n  copy .env.deploy.example .env.deploy"
  exit 1
}
. $envFile
if ($env:BUILD_LOCAL_IMAGE -eq 'true') {
  Write-Host "Building local image $env:IMAGE_NAME..."
  docker build -t $env:IMAGE_NAME (Resolve-Path "$scriptDir\..\..")
}
if ($env:USE_INTERNAL_DB -eq 'true') {
  Write-Host "Starting internal Postgres service"
  docker compose -f $composeFile --env-file $envFile up -d postgresql
}
docker compose -f $composeFile --env-file $envFile up -d
Write-Host "Stack started"

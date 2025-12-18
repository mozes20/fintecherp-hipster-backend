pipeline {
  agent any
  options { timestamps() }

  triggers { githubPush() }

  environment {
    // ===== REGISTRY CÍMEK =====
    LOCAL_REGISTRY   = "192.168.1.210:5000"
    LOCAL_SCHEME     = "http"
    REMOTE_REGISTRY  = "mozes.myddns.me:5000"
    REMOTE_SCHEME    = "http"

    // Image azonosító
    IMAGE = "fintecherp"
    TAG   = "latest"

    // Teljes nevek
    FULL_IMAGE_LOCAL  = "${LOCAL_REGISTRY}/${IMAGE}:${TAG}"
    FULL_IMAGE_REMOTE = "${REMOTE_REGISTRY}/${IMAGE}:${TAG}"

    // ===== TÁVOLI GÉP =====
    REMOTE_USER = "root"
    REMOTE_HOST = "185.65.68.120"
    REMOTE      = "${REMOTE_USER}@${REMOTE_HOST}"

    // ===== COMPOSE A TÁVOLIN =====
    REMOTE_DEPLOY_DIR = "/opt/fintech/erpfintech"
    COMPOSE_PATH = "${REMOTE_DEPLOY_DIR}/docker-compose.yml"
    SERVICE      = "app"
    CONTAINER    = "erpfintech-app-1"
  }

  stages {
    stage('Git safe.directory (workspace)') {
      steps {
        sh '''
          set -e
          if ! git config --global --get-all safe.directory | grep -Fx "$WORKSPACE" >/dev/null 2>&1; then
            git config --global --add safe.directory "$WORKSPACE"
          fi
        '''
      }
    }

    stage('Branch gate (only main)') {
      steps {
        script {
          def br = env.BRANCH_NAME ?: sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
          echo "Detected branch: ${br}"
          if (br != 'main') {
            echo "Not on 'main' (got: ${br}) → skipping pipeline."
            catchError(buildResult: 'SUCCESS', stageResult: 'NOT_BUILT') {
              error("Skip non-main branch")
            }
          }
        }
      }
    }

    stage('Checkout') {
      steps {
        git branch: 'main',
            credentialsId: 'github-credentials',
            url: 'https://github.com/mozes20/fintecherp-hipster-backend.git'
      }
    }

    stage('Preflight (local)') {
      steps {
        sh '''
          set -eu
          echo "[LOCAL] Docker:"; docker --version

          echo "[LOCAL] LOKÁLIS registry reachability: ${LOCAL_SCHEME}://${LOCAL_REGISTRY}"
          curl -fsS ${LOCAL_SCHEME}://${LOCAL_REGISTRY}/v2/_catalog >/dev/null || {
            echo "Nem érem el a LOKÁLIS registryt: ${LOCAL_SCHEME}://${LOCAL_REGISTRY}"; exit 1; }
        '''
      }
    }

    stage('Docker Build & Push → LOCAL registry') {
      steps {
        sh '''
          set -eu
          echo "===== BUILD → LOCAL PUSH (HTTP) ====="
          echo "Lokális registry: ${LOCAL_SCHEME}://${LOCAL_REGISTRY}"
          echo "Image: ${FULL_IMAGE_LOCAL}"
          echo "====================================="

          echo "[BUILD] docker build"
          docker build -t ${IMAGE}:${TAG} .

          echo "[TAG] -> ${FULL_IMAGE_LOCAL}"
          docker tag ${IMAGE}:${TAG} ${FULL_IMAGE_LOCAL}

          echo "[PUSH] -> ${FULL_IMAGE_LOCAL}  (LOKÁLIS IP-re, HTTP)"
          docker push ${FULL_IMAGE_LOCAL}
        '''
      }
    }

    stage('Push verify (LOCAL registry API)') {
      steps {
        sh '''
          set -eu
          echo "[VERIFY] tags/list: ${FULL_IMAGE_LOCAL}"
          TAGS_JSON="$(curl -fsS ${LOCAL_SCHEME}://${LOCAL_REGISTRY}/v2/${IMAGE}/tags/list)"
          echo "[VERIFY] $TAGS_JSON"
          printf '%s' "$TAGS_JSON" | tr -d ' \n' | grep -q "\"${TAG}\"" || {
            echo "A '${TAG}' tag nem látható a LOKÁLIS registryben: ${FULL_IMAGE_LOCAL}"; exit 1; }

          curl -fsSI -H 'Accept: application/vnd.docker.distribution.manifest.v2+json' \
            ${LOCAL_SCHEME}://${LOCAL_REGISTRY}/v2/${IMAGE}/manifests/${TAG} | grep -q '^HTTP/.* 200' || {
              echo "Manifest HEAD nem 200 a LOKÁLIS registryben: ${FULL_IMAGE_LOCAL}"; exit 1; }

          echo "[VERIFY] OK (LOCAL): ${FULL_IMAGE_LOCAL}"
        '''
      }
    }

    stage('Remote: SSH & docker/compose check') {
      steps {
        sshagent(credentials: ['deploy-key']) {
          sh '''
            set -eu
            mkdir -p ~/.ssh
            ssh-keyscan -H "${REMOTE_HOST}" >> ~/.ssh/known_hosts 2>/dev/null || true

            echo '== SSH basic =='
            ssh -o BatchMode=yes -o StrictHostKeyChecking=no "${REMOTE}" 'whoami && id && hostname'

            echo '== Docker/Compose presence =='
            ssh -o StrictHostKeyChecking=no "${REMOTE}" 'which docker && docker --version'
            ssh -o StrictHostKeyChecking=no "${REMOTE}" 'docker compose version || docker-compose -v || true'
          '''
        }
      }
    }

    stage('Remote: registry reachability + insecure-registries') {
      steps {
        sshagent(credentials: ['deploy-key']) {
          sh '''
            set -eu
            mkdir -p ~/.ssh
            ssh-keyscan -H "${REMOTE_HOST}" >> ~/.ssh/known_hosts 2>/dev/null || true

            echo "[REMOTE] Registry reachability (curl): ${REMOTE_SCHEME}://${REMOTE_REGISTRY}"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "curl -fsS ${REMOTE_SCHEME}://${REMOTE_REGISTRY}/v2/_catalog >/dev/null" || {
              echo 'A távoli gép NEM éri el a registry-t. DNS / tűzfal / routing gond?'; exit 1; }

            echo "[REMOTE] Ellenőrzés: insecure-registries tartalmazza-e: ${REMOTE_REGISTRY}"
            if ! ssh -o StrictHostKeyChecking=no "${REMOTE}" "test -f /etc/docker/daemon.json && grep -q '${REMOTE_REGISTRY}' /etc/docker/daemon.json"; then
              echo "❌ A távoli Docker daemon nincs beállítva HTTP registryre: ${REMOTE_REGISTRY}"
              echo "  -> Állítsd be és indítsd újra a Dockert a TÁVOLI gépen:"
              echo "     sudo mkdir -p /etc/docker"
              echo "     sudo tee /etc/docker/daemon.json >/dev/null <<'JSON'"
              echo "     {\"insecure-registries\": [\"${REMOTE_REGISTRY}\"]}"
              echo "     JSON"
              echo "     sudo systemctl daemon-reload && sudo systemctl restart docker"
              exit 1
            else
              echo "OK: /etc/docker/daemon.json tartalmazza: ${REMOTE_REGISTRY}"
            fi
          '''
        }
      }
    }

    // 🆕 DEPLOY DIRECTORY + COMPOSE FILE UPLOAD
    stage('Remote: Upload compose files') {
      steps {
        sshagent(credentials: ['deploy-key']) {
          sh '''
            set -eu
            mkdir -p ~/.ssh
            ssh-keyscan -H "${REMOTE_HOST}" >> ~/.ssh/known_hosts 2>/dev/null || true

            if [ "${REMOTE_USER}" = "root" ]; then SUDO=""; else SUDO="sudo -n"; fi

            echo "[REMOTE] Ensure deploy directory exists: ${REMOTE_DEPLOY_DIR}"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO mkdir -p ${REMOTE_DEPLOY_DIR}"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO mkdir -p ${REMOTE_DEPLOY_DIR}/realm-config"

            echo "[REMOTE] Upload docker-compose.deploy.yml -> docker-compose.yml"
            scp -o StrictHostKeyChecking=no \
              src/main/docker/docker-compose.deploy.yml \
              "${REMOTE}:${REMOTE_DEPLOY_DIR}/docker-compose.yml.tmp"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO mv ${REMOTE_DEPLOY_DIR}/docker-compose.yml.tmp ${COMPOSE_PATH}"

            echo "[REMOTE] Upload Keycloak realm config"
            scp -o StrictHostKeyChecking=no \
              src/main/docker/realm-config/fintech-erp-realm.json \
              "${REMOTE}:${REMOTE_DEPLOY_DIR}/realm-config/fintech-erp-realm.json.tmp"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO mv ${REMOTE_DEPLOY_DIR}/realm-config/fintech-erp-realm.json.tmp ${REMOTE_DEPLOY_DIR}/realm-config/fintech-erp-realm.json"

            echo "[REMOTE] Upload Keycloak health check script"
            scp -o StrictHostKeyChecking=no \
              src/main/docker/realm-config/keycloak-health-check.sh \
              "${REMOTE}:${REMOTE_DEPLOY_DIR}/realm-config/keycloak-health-check.sh.tmp"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO mv ${REMOTE_DEPLOY_DIR}/realm-config/keycloak-health-check.sh.tmp ${REMOTE_DEPLOY_DIR}/realm-config/keycloak-health-check.sh"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO chmod +x ${REMOTE_DEPLOY_DIR}/realm-config/keycloak-health-check.sh"

            echo "[REMOTE] Verify uploaded files"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "ls -lh ${COMPOSE_PATH}"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "ls -lh ${REMOTE_DEPLOY_DIR}/realm-config/"
          '''
        }
      }
    }

    // ⬇️ DEPLOY
    stage('Remote pull & deploy (compose)') {
      steps {
        sshagent(credentials: ['deploy-key']) {
          sh '''
            set -eu
            mkdir -p ~/.ssh
            ssh-keyscan -H "${REMOTE_HOST}" >> ~/.ssh/known_hosts 2>/dev/null || true

            COMPOSE_CMD=$(ssh -o StrictHostKeyChecking=no "${REMOTE}" '
              if docker compose version >/dev/null 2>&1; then
                echo "docker compose";
              elif command -v docker-compose >/dev/null 2>&1; then
                echo "docker-compose";
              else
                echo "";
              fi')
            [ -n "$COMPOSE_CMD" ] || { echo "Nincs docker compose a távoli gépen"; exit 1; }
            echo "Távoli compose: $COMPOSE_CMD"

            if [ "${REMOTE_USER}" = "root" ]; then SUDO=""; else SUDO="sudo -n"; fi
            echo "SUDO prefix: '$SUDO'"

            echo "[REMOTE] Pull ${FULL_IMAGE_REMOTE}"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO docker pull ${FULL_IMAGE_REMOTE}"

            echo "[REMOTE] Compose config check"
            COMPOSE_IMAGE=$(ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO $COMPOSE_CMD -f ${COMPOSE_PATH} config" \
              | awk -v svc="${SERVICE}" ' $0 ~ "^  " svc ":" {f=1} f && /image:/ {print $2; exit}' || true)
            echo "Compose image for ${SERVICE}: '${COMPOSE_IMAGE}'"

            if [ -n "$COMPOSE_IMAGE" ] && [ "$COMPOSE_IMAGE" != "${FULL_IMAGE_REMOTE}" ]; then
              echo "[REMOTE] Retag: ${FULL_IMAGE_REMOTE} -> $COMPOSE_IMAGE"
              ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO docker tag ${FULL_IMAGE_REMOTE} $COMPOSE_IMAGE"
            fi

            echo "[REMOTE] 🔄 Restart ONLY app service (keep Keycloak, Keycloak-DB, Gotenberg running)"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO $COMPOSE_CMD -f ${COMPOSE_PATH} up -d --no-deps --force-recreate app"

            echo "[REMOTE] Ensure Keycloak-DB, Keycloak, Gotenberg are running"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO $COMPOSE_CMD -f ${COMPOSE_PATH} up -d keycloak-db keycloak gotenberg"

            echo "[REMOTE] Wait for services to stabilize"
            sleep 10

            echo "[REMOTE] ✅ Service status:"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO $COMPOSE_CMD -f ${COMPOSE_PATH} ps"

            echo "[REMOTE] ✅ Check running containers:"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO docker ps --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}' | grep -E 'keycloak|gotenberg|app'"

            echo "[REMOTE] ✅ Check volumes:"
            ssh -o StrictHostKeyChecking=no "${REMOTE}" "$SUDO docker volume ls | grep fintecherp"
          '''
        }
      }
    }

  } // stages

  post {
    success {
      echo "✅ Kész: BUILD → LOCAL PUSH → COMPOSE UPLOAD → REMOTE PULL → DEPLOY"
      echo "   • App konténer újraindítva"
      echo "   • Keycloak-DB + Keycloak + Gotenberg adatok megmaradtak"
    }
    failure {
      echo "❌ Hiba történt – a Console Output végén látod, pontosan hol állt meg."
    }
  }
}

#!/usr/bin/env bash
set -e

./scripts/shared/wait-up.sh "https://docker:8765/autuacao/info" 600
#!/usr/bin/env bash
set -e

./scripts/shared/wait-up.sh "https://docker:8765/autuacao/info" 600
./scripts/shared/wait-up.sh "http://docker:4444/wd/hub" 30
#!/usr/bin/env bash
ls -al
ls -al target
docker stop back
docker rm back
docker build -t backend . --no-cache
docker run -d -p 9000:9000 --name back backend 

#!/bin/sh

docker run -it \
  -v $PWD/data:/zeppelin/data \
  -v $PWD/src:/zeppelin/notebook \
  --rm \
  -p 8080:8080 \
  docker.io/apache/zeppelin:0.7.2

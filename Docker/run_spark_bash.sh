#!/bin/sh
docker run -it -v $PWD:/home/jovyan/work --rm docker.io/jupyter/all-spark-notebook start.sh bash

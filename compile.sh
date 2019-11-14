#!/bin/sh
dir=$PWD
cd $dir
rm -rf target/*
mvn clean
mvn package -Dmaven.test.skip=true
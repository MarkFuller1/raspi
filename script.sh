#!/usr/bin/env bash

echo $PWD

git add *

commit_time=$(date+"%T")

echo $commit_time

git commit -am "commit:"${commit_time}

git push origin master


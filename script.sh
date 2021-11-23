#!/usr/bin/env bash

echo $PWD

git add *

commit_time=date+"%T"

git commit -am "commiit:"_$commit_time

git push origin master


#!/usr/bin/env bash

echo %cd%

git add *

commit_time=%TIME%

git commit -am "commiit:"_$commit_time

git push origin master


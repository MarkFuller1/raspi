echo %cd%

git add *

set commit_time=%TIME%

git commit -am "commiit:"_%commit_time%

git push origin master


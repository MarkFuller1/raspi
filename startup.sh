#!/usr/bin/bash
sudo touch lastPulled
sudo echo "$(date)" >> lastPulled

cd /opt/prod/raspi || exit

echo "pulling"
sudo /usr/bin/mvn clean
sudo /usr/bin/git pull 
echo "done pulling"
echo "compile package"
sudo /usr/bin/mvn clean compile package 
echo "done compile package"
echo "start server"
sudo /usr/bin/java -jar target/demo-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=prod

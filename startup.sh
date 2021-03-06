#!/usr/bin/bash
sudo touch lastPulled
sudo echo "$(date)" >> lastPulled

cd /opt/prod/raspi || exit

echo "pulling"
sudo /usr/bin/mvn clean
sudo rm selfie.jpg
sudo /usr/bin/git pull 
echo "done pulling"
echo "compile package"
sudo /usr/bin/mvn clean compile package 
echo "done compile package"
echo "start server"
sudo /usr/bin/java -jar -Dspring.profiles.active=prod target/node-0.0.1-SNAPSHOT.jar

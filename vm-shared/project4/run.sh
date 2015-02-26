ant clean &&
ant build &&
bash cleanCache.sh &&
ant deploy &&
sudo /etc/init.d/tomcat7 restart &&
echo "Done!"

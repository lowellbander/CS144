# run this script after following the instructions under
# 'Creating Your First Web Application Archive' at 
# http://cs.ucla.edu/classes/winter15/cs144/projects/tomcat/index.html

rm -v $CATALINA_BASE/webapps/simple.war
rm -frv $CATALINA_BASE/webapps/simple/
pushd simple_temp/
jar cfM simple.war WEB-INF hello.html
popd
sudo cp simple_temp/simple.war $CATALINA_BASE/webapps/
bash restartTomcat.sh
ls $CATALINA_BASE/webapps/

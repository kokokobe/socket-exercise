#######Æô¶¯ rmid

set classpath=%classpath%;F:\eclipseExercise\java_socket\src\com\liang\rmi\server;

start rmiregistry

start rmid -J-Djava.security.policy=F:\eclipseExercise\java_socket\src\com\liang\rmi\client\rmid.policy -port 1100 -log F:\rmilog

start java -Djava.security.policy=F:\eclipseExercise\java_socket\bin\com\liang\rmi\client\rmid.policy -Djava.rmi.server.codebase=file:///F:\eclipseExercise\java_socket\bin\com\liang\rmi\service\impl

java SimpleClient
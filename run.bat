set JAVA_HOME=%JAVA_11_HOME%
call mvnw clean install
call "%JAVA_11_HOME%\bin\java" -jar target\passwordmanager.war
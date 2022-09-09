Difference between my version and https://www.baeldung.com/spring-cloud-configuration  
in client I use  
spring-cloud-starter-config   
instead of  
spring-cloud-config

To fully use this, create some directory, go there, copy config-client-X.properties to this newly created directory.
In this directory execute command:
git init
git commit
and set path to newly directory for key spring.cloud.config.server.git.uri in application properties for server
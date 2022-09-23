It is almost a copy of this project: https://github.com/spring-petclinic/spring-petclinic-microservices  
My goal was to get to know microservices, dockerization and connecting it all together.  
What i did in this project:

- changed mvn to gradle
- I changed some of the project names

Thanks to this, a lot of things didn't work, but I still got it to the point where everything seems works :)  
What I haven't done:

- I didn't change java code (repositories, services, controllers etc.)

to run dockerized version with docker compose:

gradlew clean build bootBuildImage

the command below should be executed more than once:

docker-compose up -d

Things, which was not present in original project:  
http://localhost:8761/eurekawebui

Thisngs TODO for future:

- learn every element of api gateway
- rewrite api gateway to use events
- rewrite java code
- rewrite frontend
- create own panel in graphana / analyze existing ( according to https://www.youtube.com/watch?v=ypF30j6ni64 )
- create docker which will perform CI / CD

# post
 
Post microservice, responsible for handling and saving posts in Thaqafa Network.
It includes below APIs:
- Create Post
  (POST - http://localhost:9092/posts/post)
- Show all posts / News Feed
  (POST - http://localhost:9092/graphql)
Complete APIs calls in the file: Posts.postman_collection.json

The microservice implemented in Spring Boot, Mongodb and GraphQL.
To run the microservice, do the following:
- Install JDK 17
- Create a blank DB in mongo, name it "social"
- Run the service discovery Eureka server in this repository: https://github.com/Microservices-Restaurant-App/eureka.git
- Open the project from IDE (Intellij or Eclipse STS)
- run PostApplication.java, and access the APIs from above urls

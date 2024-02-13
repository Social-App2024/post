# post
 
Post microservice, responsible for handling and saving posts in Thaqafa Network.
It includes below APIs:
- Create Post
  (http://localhost:9092/posts/post)
- Show all posts / News Feed
  (http://localhost:9092/graphql)

The microservice implemented in Spring Boot, Mongodb and GraphQL.
To run the microservice, do the following:
- Install JDK 17
- Create a blank DB in mongo, name it "social"
- Open the project from IDE (Intellij or Eclipse STS)
- run PostApplication.java, and access the APIs from above urls

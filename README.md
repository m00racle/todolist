About this Workshop
In this workshop, we'll discuss how to get up and running with user authentication and authorization using Spring Security. 
We'll be storing user data, including names and passwords, in a database. Our Spring MVC app connects to the database 
using Hibernate. By the end of this course, you will have added user authentication to a simple task management 
application, allowing users to login, create new tasks, and mark & unmark them as complete.
 
[Introduction](https://teamtreehouse.com/library/user-authentication-in-spring)

In this intro, you'll hear what this workshop is all about!
Authentication in general is the process of an app confirming the identification of a client. The client will be user in 
our case, but it also can be another application if we’re coding an API.
Authorization is assigning users with proper roles which allows us to fine grained control over which resources each 
kind of user can access. For example an admin has the high level of control over the app with even the ability to add, 
edit, and delete other user accounts.
 
[First Look at Out Todo Project](https://teamtreehouse.com/library/a-first-look-at-our-todo-project)

This video introduces you to the project we'll be working through during the workshop. It's a simple task management app 
called TodoToday.
 
 
Query Methods with Spring Data JPA

You can add to CRUD functionality in a Spring Data JPA interface by writing intuitive method stubs. For example, if you 
have a repository for Contact objects that include a persisted email field, you could write a method that queries the d
atastore for contacts given a certain email address as follows:

**public class ContactDao extends CrudRepository<Contact,Long> {
  Contact findByEmail(String email);
}**


For more on query method options, check the [Spring docs](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods)
Git Command to Sync Your Code to the Start of this Video
git checkout -f v2
 
I decided to make the project from scratch since many of the Gradle external libraries are deprecated and the plugin 
declaration is also different. The latest Gradle build plugins required different syntax. 

[A First Look at Our Todo Project](https://teamtreehouse.com/library/a-first-look-at-our-todo-project)

The notable portion of this step is that there are @Bean in the DataConfig.java that must be named specifically as 
**entityManagerFactory** which the Springframework boot will seek this specific name.

Then in this project we are not using the common hibernate.cfg.xml file to configure Hibernate. Instead we build one 
more method inside the DataConfig.java and put the usual properties settings for Hibernate. Then this method must be 
called by the before mentioned entityManagerFactory method and passes those properties to 
**LocalContainerEntityManagerFactoryBean** class’ setJpaProperties method. Jpa here stands for Java Persistence API 
which uses Hibernate to run it to manage CRUD database processing.

There is a reason why we use **LocalContainerEntityManagerFactoryBean** instead of **LocalSessionFactoryBean** like we
did in many previous course. The reason is in this app we are using what is called **Spring data JPA Repositories** 
which @EnableJpaRepositories in the **com/mooracle/todolist/config/DataConfig.java**. This app uses Service DAO pattern 
as usual but what deferentiating it from the previous apps is The DAO interface has no lucid implementation. 

Moreover, unlike any other DAO interfaces in previous courses, the **com/mooracle/todolist/dao/TaskDao.java** has the
@Repository inside the interface code. What we do here is by have Spring Data generate our DAO implementation by using
**smart method naming**. In previous courses we will be anticipating the implementation of the interface 
**com.mooracle.todolist.dao.TaskDao** but here please notice the we **extending the CrudRepository** interface which
comes from the Spring Data library. 

When we do these things, and also enabled JPA Repositories in the **com.mooracle.todolist.config.DataConfig**, Spring 
Data will generate the implementing classes for us. All we ned to do is write interface methods that we would like to
be implemented that aren't included in the **CrudRepository** interface, and Spring Data will does the rest for us.

That Spring Data is generated in upon the application boot, will generate a class that implements this interface
**com.mooracle.todolist.dao.TaskDao**. Back to the **smart method naming**, Spring Data will know by the method named, 
for example findByDescription method, that its implementation that it generates needs to return a single task object that
matches the description passed.

Next thing to be pointed out is the **import.sql** file which will be the source of the initial data we are loading into
the database. In initial commit we configured hibernate.hbm2ddl tool to destroy and recreate the app on boot. You can 
check in **application.properties** we set **hbm2ddl tool = create-drop**. This **import.sql** will ensure that initial 
data will be loaded into the database when we boot our app.

Next we need to add [Spring boot starter security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security/1.5.9.RELEASE)
to our **build.gradle** dependencies. This is the main part of the authentication implementation.
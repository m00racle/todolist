[**About this Workshop**](https://teamtreehouse.com/library/user-authentication-in-spring)

In this workshop, we'll discuss how to get up and running with user authentication and authorization using Spring Security. 
We'll be storing user data, including names and passwords, in a database. Our Spring MVC app connects to the database 
using Hibernate. By the end of this course, you will have added user authentication to a simple task management 
application, allowing users to login, create new tasks, and mark & unmark them as complete.
 
[**Introduction**](https://teamtreehouse.com/library/user-authentication-in-spring)

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

[**A First Look at Our Todo Project**](https://teamtreehouse.com/library/a-first-look-at-our-todo-project)

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

[**Users and Roles**](https://teamtreehouse.com/library/users-and-roles)


To get up and running with user authentication, we'll need to store usernames and passwords, as well as permissions, 
into our database. In Spring, we'll store this data into User and Role objects.

1. Authentication involves verifying that a user is indeed who he or she claims to be.
2. Typically this is done by checking a provided username and password against what is stored in the database.
3. to manage authentication we'll adding a user entity to our app and this class will implement spring's user details 
interface. 
4. the user entity will encapsulate a user's username whether or not the account is enabled as well as a roll.
5. the next term happens after authentication is authorization which checks the authenticated user's permissions to see
if the user is allowed to access a requested resource or performs a requested action.
6. to manage authorization we'll add a role entity.
7. We might define one role for user, one role for user admin, and one role for app admin. These roles are meant to 
grant different authorities or privillages to users. We then assign one or more roles to each user.
8. For one more granular contol over authorities, we could create an entity that encapsulates specific authorities.

Now that we already talk about the concept, we need to start building it: (Start with Entry 2)
1. we begin by making an entity called **Role** class and we'll put it in the **com.mooracle.todolist.model** the steps
are presented in the **com.mooracle.todolist.model.Role**
2. after we build the **com.mooracle.todolist.model.Role** we then add to our role table by editing the **import.sql**
3. Next we create in **com.mooracle.todolist.model** new Java class name it **User** then go there.



**SQL Mistake**

There is a mistake in the SQL INSERT statements for adding users at the end of this video. We catch this later in the 
workshop, but in case you want the fix now, it's the role_id column name that's missing from the two statements. The two 
INSERT statements should look as follows:

-- Insert a couple users
insert into user (username,enabled,password,role_id) values ('user',true,'password',1);
insert into user (username,enabled,password,role_id) values ('user2',true,'password',1);

NOTE: the import.sql in this project also denotes many ERRORS but if we boot run it, it does not shows errors. Maybe it
is due to code style IDEA function.

[**Implementing a UserDetailsService**](https://teamtreehouse.com/library/implementing-a-userdetailsservice)

Connecting Spring Security to user data can be accomplished through several means. In this workshop, we'll take the 
approach of implementing a UserDetailsService, which will supply Spring Security with authentication and authorization 
data.

The next order of business is to integrate Spring Secirity with our user and role entities. In generan what we'll need 
to do is to implement UserDetailService interface. Specifically we want to implement the loadByUsername method which 
locate the user based on the username. [more on UserDetailsService](https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/apidocs/org/springframework/security/core/userdetails/UserDetailsService.html)

When we implement this method we'll call upon our UserDao (we have not written it yet) to grab the user entity from the
database via hibernate and use it as the method return value. Okay leths start coding:
1. Entry 5: create **UserService** interface inside the **com.mooracle.todolist.service** package and go there.
2. Entry 6: create **UserServiceImpl** Class as implementation of Entry 5 in **com.mooracle.todolist.service**
3. Entry 7: create **userDao** interface inside **com.mooracle.todolist.dao** this is interchangeable with Entry 6 above
Note: this only required us to code short syntax and the rest already included inside the CrudRepository extension. when
we boot up the app Spring will automatically build the class implementing this UserDao interface.
4. That UserDao implememntation will includes findByUsername method which required String argument called username passed
onto it. This mewthod will find a User in the database that has a username equals to whatever value passed in 
5. Back to Entry 6: **UserServiceImpl** Class in **com.mooracle.todolist.service**

Thus we finished building our Service and DAO layer. Next we add some application level configuration to the Spring app
that switches on all of our Spring security functionality.

[**Turning On Spring Security with Java Config**](https://teamtreehouse.com/library/turning-on-spring-security-with-java-config)

In this video, we'll switch on security by adding a @Configuration class that specifies the details of which Spring 
Security features we'd like to use.

For more information about [Spring Security Filter Chain in Spring docs](http://docs.spring.io/spring-security/site/docs/current/guides/html5/form.html)

To enable Spring Seciruty we need to add some configuration to our app. as with anything in Spring this can be done in 
two ways:
1. XML based config
2. Java config approach
To keep things neat and organized we will put all security config into a config class of its own. Thus:
1. Entry 8: Inside **com.mooracle.todolist.config** package create new Java Class: **SecurityConfig** and go there
2. Entry 9: goto **com.mooracle.todolist.web.controller.LoginController**

**Sessions for Login Failures**

I've demonstrated the use of session attributes to handle login failure messages, as opposed to using the standard in 
examples you might see around the web. Examples typically show the use of a query string parameter in the URL, such as

*/login?error=true*

I don't like this approach because care must be taken so that **one cannot visit that URL directly** to reproduce the 
flash message. Instead, I prefer to keep the URL clean, and rely on a session attribute to save the flash message. 
Session attributes are server-stored values, and can even include rich objects. When session attributes are used, you'll 
notice that a session cookie (usually called JSESSIONID) is set in the browser and passed with every request, so that the 
server can pair the browsing session with the server-stored session data.

If you choose this approach, be sure to clear the session attribute when it makes sense to do so. In our case, it makes 
sense as soon as we add the flash message to the model map for display in the rendered template.

**Session IDs and Cookies**

When working with session data in web applications using Java or any other server-side language, it is best to 
understand how your data might be compromised. Many applications match server-stored session data with browsing sessions 
using some sort of session cookie. This value is stored in the browser, and when your application receives a request 
that entails using that session ID for authentication or other session data, the application assumes the request 
originated from the same place.

But, what if your session ID cookie becomes compromised, whether through a browser vulnerability, or a non-HTTPS 
(read: plaintext) request intercepted by an attacker? Remember, HTTP requests are sent as plaintext, and if they're 
intercepted, they are directly readable by the attacker. Adding SSL encrypts all requests and responses in a way that 
protects all data (including cookies) in a way that doesn't make it more difficult for an attacker to intercept your 
requests or responses, but rather renders them unreadable since they're encrypted.

In any case, check the Teacher's Notes of later videos for common web app vulnerabilities.

[**Integrating User Data Into Our Application**](https://teamtreehouse.com/library/integrating-user-data-into-our-application)

If an application requires a user to sign in, it makes sense that the data she or he sees is in some way customized. 
In this video we'll make our tasks belong to specific users and discuss how the @Query annotation can be used to fetch 
tasks belonging to the currently signed in user.

Until this point we have configured our app to require authentication. Now it's time to integrate user specific data. 
Now as preambule as our first exercise we will display the currently authenticated user in the nav bar of the app.

One way to do this is by including a security principal argument in the controller method for the task list.:
1. Entry 10: go to **com.mooracle.todolist.web.controller.TaskController**
2. Entry 11: go to **build.gradle**
3. Entry 12: go to **com.mooracle.todolist.config.TemplateConfig**
4. Entry 13: go to **templates/layout.html**

Alright, if the first task is done next job is to associate the username with the data. The main objective is each user
will have its own data of to do list. We'll need to associate each task in the database with a user. Specifically we do 
this using the userId column in the task table. Thus we need to define the join column between task and user entities: 
1. Entry 14: goto **com.mooracle.todolist.model.Task**
2. Entry 15: goto **com.mooracle.todolist.dao.TaskDao** to make sure taskDao is grabbing only the tasks associated with
the currently authenticated use and not all tasks.
3. Entry 16: goto **com.mooracle.todolist.config.SecurityConfig** to configure evaluation context extension to expose 
authentication data.
4. Entry 17: goto: **import.sql** to add some user specific task
5. Entry 18: goto **com.mooracle.todolist.model.User** to add getter for the id so that Spring security can fetch it


Spring Docs for [UsernamePasswordAuthenticationToken](http://docs.spring.io/autorepo/docs/spring-security/4.0.3.RELEASE/apidocs/org/springframework/security/authentication/UsernamePasswordAuthenticationToken.html)

Hiding Thymeleaf Template Errors in IntelliJ
If you don't have the Thymeleaf XML namespaces declared at the top of your HTML, IntelliJ will highlight unknown 
namespaces/attributes. To hide these errors, do the following:
1. In the IntelliJ menubar, go to Preferences
2. Drill down to Editor, then Inspections, then XML
3. Uncheck Unbound XML namespace prefix
4. Uncheck Unknown HTML tag attribute

more links:
1. [Spring Security Thymeleaf Dialect](https://github.com/thymeleaf/thymeleaf-extras-springsecurity)
2. [Spring Authentication Interface](http://docs.spring.io/autorepo/docs/spring-security/4.0.4.RELEASE/apidocs/org/springframework/security/core/Authentication.html)


[**Final Touches and Security Considerations**](https://teamtreehouse.com/library/final-touches-and-security-considerations)

In this video we add the ability to associate an added task with the currently signed in user. We also discuss the 
importance of storing encrypted passwords and add a password encoder to our application. 

If you inspect our code it allows users to add task. In the **com.mooracle.todolist.web.controller.TaskController** we 
have **addTask** method. That method has a call to **com.mooracle.todolist.service.TaskService** which implemented in
**com.mooracle.todolist.service.TaskServiceImpl** to save the added task using a POST request. That service layer then 
calls the DAO layer interface **com.mooracle.todolist.dao.TaskDao** which extends the CRUD repository which makes the 
save method hidden. However, in this case the saved new added task is not yet associated with the current logged user. 

We need to have the user field populated in the task entity before saving. There are at least two methods of saving the
task:
1. Entry 19: goto **com.mooracle.todolist.web.controller.TaskController** 

Now we started the discussion on Security. This is not meant to be exhaustive discussion about security since the topic
is too big to cover in one course. What we are discussing here right now is implementing extra layer of security which
is considered practical. 

First thing first encrypting the password: We'll use BCrypt Password Hashing
1. Entry 20: goto **com.mooracle.todolist.config.SecurityConfig**
2. Entry 21: goto **import.sql** to change the password with hashed (encrypted) version

Lastly we add CSRF (Cross Site request forgery) from the authenticated users. How we do this? One way is to stop any 
GET request that changes the server state. As POST which is really ment to change the server state it will be added with
CSRF protection.
1. Entry 22: goto **com.mooracle.todolist.config.SecurityConfig** to add CSRF protection

**NOTE**: one thing we have not yet talk about here is how to encode the password inputted manually by the user. We 
expect them to put their own hash code when creating a password.

**BCrypt Password Hashing**

BCrypt is a hashing function that is based on the Blowfish cipher. It is designed to allow the ability to be iteratively 
applied a specified number of times (referred to as the cost parameter). One advantage of this hash is that, 
as processors get faster and servers have the ability to process more requests concurrently, the number of iterations 
can be increased so that a "brute-force" attack can be slowed to the point of detection before the attack is successful.

1. [https://en.wikipedia.org/wiki/Bcrypt](https://en.wikipedia.org/wiki/Bcrypt)
2. [https://en.wikipedia.org/wiki/Blowfish](https://en.wikipedia.org/wiki/Blowfish_(cipher))
3. [BCryptPasswordEncoder](http://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)

**Injecting User Data for Modifying Queries**

If you want to inject user-specific data using the authentication object for INSERT statements, in the same way that we 
did for SELECT statements, you'll need to use a native query. Here is an example of a Spring Data JPA interface method 
that you could use:

@Modifying

@Transactional

@Query(nativeQuery = true, value = "insert into task (user_id,description,complete) values 

(:#{principal.id},:#{#task.description},:#{#task.complete})")

void saveForCurrentUser(@Param("task") Task task);

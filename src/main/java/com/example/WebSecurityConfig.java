package com.example;


import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ComponentScan(basePackages = "com.example")
public class WebSecurityConfig {// extends WebSecurityConfigurerAdapter{

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("*//**");
    }*/

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

/*

Integrating Angular 2 with Spring Boot, JWT, and CORS, Part 1

A current trend among developers writing web applications using traditional server-side languages, including Java, is to move the user interface entirely to the browser, and to limit the server-side code to just providing business logic via an API.

One of the most popular ways to implement the front end at the moment is as a Single Page Application (SPA) using the Angular 2 framework (soon to be renamed simply, Angular, and released as version 4).

Here at Chariot, we've been using the Spring Framework to write web applications for quite a long time. While we're also heavily involved with other technologies, such as Scala, Clojure, and on occasion, Ruby, we're not about to give up on Spring any time soon. We're particularly fond of the latest incarnation of Spring - Spring Boot, as it makes it easier than ever to get a Spring application up and running.

In the past, we would typically have used server-side templating (JSP, FreeMarker, etc.), with some Javascript mixed in, to present the user interface. More recently, we might have used Angular 1 to write a Javascript-based user interface, and served that from the static resources directory of our web app.

However, preferences in application architecture tend to change over time, and we now generally like to have our Javascript UI code stand alone. The npm-based tools that we use for Angular development make it convenient to run our Angular front-end app on one port (typically 3000), while our Spring Boot backend runs on another (typically 8080).

Having the two parts of the application (the UI and the API) served from different ports presents a problem, though - by default, the web browser prevents the UI application from accessing the API on a port different from the one on which the UI was served (this is known as the Single-Origin Policy, or SOP).

At first glance, you might think that this is a problem that we're unnecessarily inflicting on ourselves, just for the sake of ease of Angular development. However, we're likely to run into the SOP problem in production as well, for instance, if we want to serve our application's UI from http://example.com and it's API from http://api.example.com, or if we want to provide our API for other people's applications to consume in addition to our own.

A popular solution to this problem is the use of Cross-Origin Resource Sharing (CORS). CORS is a W3C Recommendation, supported by all modern browsers, that involves a set of procedures and HTTP headers that together allow a browser to access data (notably Ajax requests) from a site other than the one from which the current page was served.

The rest of this post (Part 1) will show in detail how to implement a Spring Boot-based REST API for an Angular 2 app, and how to implement CORS so as to allow the Angular UI application to work with the REST API served from a different port and/or a different domain.

Future posts will show how to add authentication and authorization via Spring Security and JSON Web Tokens (JWT).

The Application
Front-End - Tour of Heroes
Let's start with the Tour of Heroes example from the Angular web site.
You may already be familiar with it, but if not, now is a good time to go check it out at https://angular.io/docs/ts/latest/tutorial and work through the tutorial.

Go ahead, I'll wait here for you until you get back.

(If you're the impatient type, and want to just download the finished code for the Tour of Heroes demo, you can clone my git repo at
https://github.com/rfreedman/angular-tour-of-heroes-complete)

Ok, welcome back.

Now that we have a working stand-alone demo front-end (with faked API access), let's create a real API for it.

Back-End - Custom Spring Boot App
The Tour of Heroes front-end expects an API for listing, searching and doing CRUD operations on Hero objects. If you look at the Angular app's hero.ts source file, you'll see that a Hero has two properties - a numeric id and a name:

1
export class Hero {
2
    id: number;
3
    name: string;
4
}
Let's use Spring Boot to create this API.

The easiest way to start is to use the SPRING INITIALIZR app at http://start.spring.io
Go there, select Gradle Project, name the group heroes and name the artifact back-end, add dependencies for JPA, H2 and Rest Repositories, and then hit the Generate Project button. You'll get a zip file containing a starter project. Unzip it, and open it in your favorite IDE / editor. You should now have a build.gradle file in the root folder of the project that looks like this:

01
buildscript {
02
    ext {
03
        springBootVersion = '1.4.3.RELEASE'
04
    }
05
    repositories {
06
        mavenCentral()
07
    }
08
    dependencies {
09
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
10
    }
11
}
12

13
apply plugin: 'java'
14
apply plugin: 'eclipse'
15
apply plugin: 'org.springframework.boot'
16

17
jar {
18
    baseName = 'back-end'
19
    version = '0.0.1-SNAPSHOT'
20
}
21

22
sourceCompatibility = 1.8
23

24
repositories {
25
    mavenCentral()
26
}
27

28

29
dependencies {
30
    compile('org.springframework.boot:spring-boot-starter-data-jpa')
31
    compile('org.springframework.boot:spring-boot-starter-data-rest')
32
    compile("com.h2database:h2")
33
    testCompile('org.springframework.boot:spring-boot-starter-test')
34
}
build.gradle

This gives us a build that includes the required libraries for an H2 embedded database, JPA (Java Persistence API), and Spring Rest in addition to Spring Boot's default embedded Tomcat server.

Now, let's add our Hero domain object. Create a Hero.java file in the src/main/java/heroes folder, and add the following code:

01
package heroes;
02

03
import javax.persistence.Entity;
04
import javax.persistence.GeneratedValue;
05
import javax.persistence.GenerationType;
06
import javax.persistence.Id;
07

08
@Entity
09
public class Hero {
10

11
    @Id
12
    @GeneratedValue(strategy = GenerationType.AUTO)
13
    private long id;
14

15
    private String name;
16

17
    public long getId() {
18
        return id;
19
    }
20

21
    public void setId(long id) {
22
        this.id = id;
23
    }
24

25
    public void setName(String name) {
26
        this.name = name;
27
    }
28

29
    public String getName() {
30
        return name;
31
    }
32
}
Hero.java

This is a standard POJO (plain old java object), a.k.a. a Bean, with JPA annotations to mark this as a JPA Entity and to tell JPA how to generate ids.

The one thing that is slightly different here from a normal Spring Data Entity is the inclusion of the getId and setId methods. These would typically be omitted because JPA doesn't need them, and a true REST API would employ HATEOAS, and not expose the id as part of an object's JSON representation. However, like many REST-ish applications, the Tour of Heroes front-end does not not make use of API-provided hypertext links, and instead uses object ids and well-known endpoint URI patterns to form the URLs used to interact with the API. Since HATEOAS is not the subject of this article, we'll just have the API expose object ids.
This isn't quite enough to start exposing Entity ids, though. By default, Spring will still omit the id when serializing objects to Json. To change this, we need to add a configuration, and in keeping with Spring Boot convention, we'll do so with a configuration class.

Add the following RepositoryConfig.java source file to the src/main/java/com/example folder:

01
package heroes;
02

03
import org.springframework.context.annotation.Configuration;
04
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
05
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
06

07
@Configuration
08
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
09
    @Override
10
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
11
        config.exposeIdsFor(Hero.class);
12
    }
13
}
RepositoryConfig.java

This will configure the Repository (which we haven't created yet) to expose the ids of Hero objects when serializing to Json. Note that if you add more entities, you'll have to add them here as well.

Next, we'll add the Repository, which will be responsible for database operations involving our Heros. With Spring Boot, we can simply provide an interface for our Repository, and Spring Data will implement it via a proxy at runtime. Add the following HeroRepository.java file to src/main/java/com/example:

01
package heroes;
02

03
import java.util.List;
04

05
import org.springframework.data.repository.CrudRepository;
06
import org.springframework.data.repository.query.Param;
07
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
08

09
@RepositoryRestResource(collectionResourceRel = "heroes", path = "heroes")
10
public interface HeroRepository extends CrudRepository<Hero, Long> {
11

12
    @Query("from Hero h where lower(h.name) like CONCAT('%', lower(:name), '%')")
13
    public Iterable<Hero> findByName(@Param("name") String name);
14

15
}
HeroRepository.java

This simple interface, by extending Spring Data's CrudRepository, gives us Create (POST), Update (PUT), Delete (DELETE), and List functionality. The @RepositoryRestResource causes Spring Data to wrap our Repository at runtime with a Controller that handles the HTTP REST requests.

We have added only one method to the interface, beyond what CrudRepository provides by default, to support the Angular app's search function.

This is all of the code that we need to write to get our basic Heroes REST api up and running.

There's one more thing to do. As the application stands right now, when it starts up, there will be no Heroes in the database. We could just add some, but since we're using an in-memory database (H2), we'd lose our Heroes every time we shut down the application.

Fortunately, Spring Data provides an easy facility for loading data into the database at start-up.
If it is creating a new database (which it will do every time we start the app), and it finds a file named data.sql in the root of the classpath, it will execute the contents as sql statements.

Add a data.sql file to the src/main/resources directory with the following contents (or make up your own!):

1
insert into hero(name) values('Black Widow');
2
insert into hero(name) values('Superman');
3
insert into hero(name) values('Rogue');
4
insert into hero(name) values('Batman');
data.sql

Then, to cause the data.sql file to be copied to the classpath at build-time, add the following to the build.gradle file, just after the apply plugin lines:

1
task copySqlImport(type: Copy) {
2
    from 'src/main/resources'
3
    into 'build/classes'
4
}
5

6
build.dependsOn(copySqlImport)
Ok, go ahead and start up the Spring Boot applicaiton using the Gradle task bootRun (either select it from your IDE if it supports Gradle builds, or run ./gradlew bootRun from the command line (or .\gradlew.bat bootRun if you're on Windows).

You can try out the API at http://localhost:8080/heroes. If you point your web browser at that endpoint, you should get a list of the heroes that you specified in your data.sql file. Fire up a REST client (I recommend Postman), and you should also be able to add, update, and delete Heroes.

Note that Spring-Data is still including HATEOAS links (in addition to Hero ids), and there's no easy way to turn them off. We're just going to ignore them.
Integrating the front-end and back-end
To integrate the Angular app with our new Spring Boot - based API, we'll need to make some changes to both the Angular front-end application and the Spring Boot API.

First, we need to disable the Angular app's in-memory-database.

To do this, edit app.module.ts, and comment out or delete the imports for InMemoryWebApiModule and InMemoryDataService at the top of the file, and inside the @NgModule declaration.

Then, in hero.service.ts, change the URLs to point to http://localhost:8080/heroes instead of /app/heroes

These two changes will cause the app to use the api provided by the Spring Boot application instead of the internally mocked api.

Next, we need to deal with the fact that the Spring Boot api responds with data in the HAL format (see https://en.wikipedia.org/wiki/Hypertext_Application_Language). This format is somewhat different from what the original Tour of Heroes app expected, so we need to modify the Angular app's Hero Service slightly to look for data in the right places.

(You could modify the Spring Boot app to provide the format expected by the Angular app instead, but that would be quite a bit more work).

Modify hero.service.ts, changing the getHeros() function from

1
getHeroes(): Promise<Hero[]> {
2
 return this.http.get(this.heroesUrl)
3
  .toPromise()
4
  .then(response => response.json().data as Hero[])
5
  .catch(this.handleError);
6
}
to

1
getHeroes(): Promise<Hero[]> {
2
 return this.http.get(this.heroesUrl)
3
  .toPromise()
4
  .then(response => response.json()._embedded.heroes as Hero[])
5
  .catch(this.handleError);
6
}
(change response.json().data to response.json().embedded.heroes)
and changing

1
create(name: string): Promise<Hero> {
2
return this.http
3
  .post(this.heroesUrl, JSON.stringify({name: name}), {headers: this.headers})
4
  .toPromise()
5
  .then(res => res.json().data)
6
  .catch(this.handleError)
7
}
to

1
create(name: string): Promise<Hero> {
2
return this.http
3
  .post(this.heroesUrl, JSON.stringify({name: name}), {headers: this.headers})
4
  .toPromise()
5
  .then(res => res.json())
6
  .catch(this.handleError)
7
}
(change .then(res => res.json().data) to .then(res => res.json()))
Modify heros-search.service.ts, changing the search function from

1
search(term: string): Observable<Hero[]> {
2
return this.http
3
  .get(`app/heroes/?name=${term}`)
4
  .map((r: Response) => r.json().data as Hero[]);
5
}
to

1
search(term: string): Observable<Hero[]> {
2
return this.http
3
  .get(`http://localhost:8080/heroes/search/findByName?name=${term}`)
4
  .map((r: Response) => r.json()._embedded.heroes as Hero[]);
5
}
(change app/heroes/?name=${term} to http://localhost:8080/heroes/search/findByName?name=${term})
Fire up the apps, and you can see (by using your browser's developer tools that the Angular app does indeed make a GET request to http://localhost:8080/heroes, and that the expected Json is returned with an HTTP 200 response code.

But no heroes show up in the dashboard! What happened?

Look at the web browser's console, and you'll see that there's an error that reads something like:

XMLHttpRequest cannot load http://localhost:8080/heroes. No 'Access-Control-Allow-Origin' header is present on the requested resource. Origin 'http://localhost:3000' is therefore not allowed access.
The problem here is that the browser has a Same-Origin Policy for XMLHttpRequests (or at least most modern browsers do) [see https://developer.mozilla.org/en-US/docs/Web/Security/Same-origin_policy].

This means that without further intervention, XMLHttpRequests can only be made to the same domain that served the initial page. If the protocol, host, or port do not match the original page request, the response won't be received by the Angular code, and you'll see this error.

CORS
There is a way around this, and it's known as the Cross Origin Resource Sharing Protocol, or CORS.
For simple cases like this GET, when your Angular code makes an XMLHttpRequest that the browser determines is cross-origin, the browser looks for an HTTP header named Access-Control-Allow-Origin in the response. If the response header exists, and the value matches the origin domain, then the browser passes the response back to the calling javascript. If the response header does not exist, or it's value does not match the origin domain, then the browser does not pass the response back to the calling code, and you get the error that we just saw.

For more complex cases, like PUTs, DELETEs, or any request involving credentials (which will eventually be all of our requests), the process is slightly more involved. The browser will send an OPTION request to find out what methods are allowed. If the requested method is allowed, then the browser will make the actual request, again passing or blocking the response depending on the Access-Control-Allow-Origin header in the response.

Note that for security resasons, the browser is in complete control of the CORS protocol - it cannot be overridden by the calling code.
The problem right now, as the error message indicates, is that the API is not returning an Access-Control-Allow-Origin header. Let's correct this.

In the Spring Boot app, add the following configuration class to add a Filter that adds the required header.

01
package heroes;
02

03
import org.springframework.context.annotation.Bean;
04
import org.springframework.context.annotation.Configuration;
05
import org.springframework.web.cors.CorsConfiguration;
06
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
07
import org.springframework.web.filter.CorsFilter;
08

09
@Configuration
10
public class RestConfig {
11
    @Bean
12
    public CorsFilter corsFilter() {
13
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
14
        CorsConfiguration config = new CorsConfiguration();
15
        config.setAllowCredentials(true);
16
        config.addAllowedOrigin("*");
17
        config.addAllowedHeader("*");
18
        config.addAllowedMethod("OPTIONS");
19
        config.addAllowedMethod("GET");
20
        config.addAllowedMethod("POST");
21
        config.addAllowedMethod("PUT");
22
        config.addAllowedMethod("DELETE");
23
        source.registerCorsConfiguration("/**", config);
24
        return new CorsFilter(source);
25
    }
26
}
RestConfig.java

In this configuration, we have addAllowedOrigin("*")
- this is a wildcard that will simply copy the value of the request's Origin header into the value of the Response's Access-Control-Allow-Origin header, effectively allowing all origins. You can add specific origins instead if you wish to limit them.
Go back and reload the Angular app, and you should see that the Heros are now listed, and that the rest of the application works as expected.

In this Part 1 post, you have learned how to implement a simple Spring Boot REST API for use by an Angular 2 front-end, and how to allow them to be served from different ports and/or domains via CORS.

In a future post, I'll show you how to add Authentication and Authorization via Spring Security and JWT.


 */

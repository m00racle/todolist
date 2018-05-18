package com.mooracle.todolist.config;

import com.mooracle.todolist.service.UserService;
import com.mooracle.todolist.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/** ENTRY LIST
 *  ENTRY 8: Turning On Spring Security with Java Config
 *
 *  */

@Configuration //8-1: telling Spring this is a config to scan
@EnableWebSecurity //8-2: this is new for this config only and also extends it:
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //8-3: add this in context of @Enable...
    //... all of this is standard practice to what is called Spring Security Filter Chain that allows filtering...
    //... certain requests so that user doesn't have to be authenticated.

    @Autowired
    private UserService userService; //8-4: we need to pull the UserDetailService included in this interface

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        //8-5: make this method and @Autowired it than accepts auth object declared above, and then call that auth...
        //... to make user service that we @Autowired into this class. Oh also please throws Exception since ...
        //... UserDetailsService method potentially throws standard exception

        auth.userDetailsService(userService);
    }

    //8-6: use alt+insert to access override and choose configure(WebSecurity web) to bypass some security checks:

    @Override
    public void configure(WebSecurity web) throws Exception {
        //8-7: in this override we will tell Spring to by pass checking for the static.assets in resources

        web.ignoring().antMatchers("/assets/**"); //8-8: the /assets/** means all inside the assets folder
    }

    //8-9: use alt+insert to override configure(HttpSecurity) to define which things need authorization

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //8-10: we will configure security for authorizing our users requests to their to do list, ...
        //...  configure the login page
        //... configure the log out process.

        //8-11: restricting access to all resources to only those users that are authorized
        http
                .authorizeRequests()
                    .anyRequest().hasRole("USER")
        //8-12: note that we use only "USER" not "Role_User" since .hasRole wiill check it out for us. Now all...
        //... requests other than static resources are will be denied. Thus we need to configure login page:
                    .and()
                .formLogin()
                    .loginPage("/login") //this is the login address
                    .permitAll()
                //8-13: this time we give permit to all. We need to specify when login suceed and failed.
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler())
                //8-14: those handler mehthods are not yet available but DON'T use alt+enter to build those...
                //... we need to build it using specific naming
                //8-18: now we gonna make the log out sequence so Spring knows how to destroy the user login data
                    .and()
                .logout()
                    .permitAll() //we don't need any authentication so just permit all
                    .logoutSuccessHandler(logoutSuccess());
        //NOTE: we can use also the logoutSucessHandler see Teacher's notes
    }

    //8-15: making the success handler method:
    public AuthenticationSuccessHandler loginSuccessHandler() {
        //8-16 AuthentucationSucessHandler is an interface with only one method thus we can use lambda:
        //... remember the spark: if success send it redirect to main page
        return ((request, response, authentication) -> response.sendRedirect("/"));
    }

    //8-16: making the failure handler method:
    public AuthenticationFailureHandler loginFailureHandler() {
        //8-17: this is also single method interface but the lambda will have multi line body
        return (request, response, exception) -> {
            request.getSession().setAttribute("flash", new FlashMessage("incorrect username and/or pass",
                    FlashMessage.Status.FAILURE)); //the name "flash" is woven in the html template
            response.sendRedirect("/login"); //back to login but we need to add flash message
        } ;
    }

    public LogoutSuccessHandler logoutSuccess() {
        //8-20: this is new addition to use logout handler method, it's similar with login failure handler
        return (request, response, authentication) -> {
            request.getSession().setAttribute("flash", new FlashMessage("Logout Successfully",
                    FlashMessage.Status.SUCCESS));
            response.sendRedirect("/login");
        };
    }
}

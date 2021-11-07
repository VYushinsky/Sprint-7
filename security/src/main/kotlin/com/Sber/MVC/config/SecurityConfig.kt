package com.Sber.MVC.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource


@EnableWebSecurity(debug = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val dataSource: DataSource? = null
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(
                "select login, password, 'true' from usr " +
                        "where login=?"
            )
            .authoritiesByUsernameQuery(
                "select login, role from usr " +
                        "where login=?"
            )
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/"," /toHome", "app/{id}/view", "app/list" ).authenticated()
            .antMatchers("app/add", "/app/{id}/edit" ).hasRole("MODERATOR")
            .antMatchers("/app/{id}/delete").hasRole("ADMIN")
            .and().
                formLogin()
            .and()
                .exceptionHandling().accessDeniedPage("/toAccessDenied")
            .and()
                .csrf().disable()
    }
}

/*
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/app/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
    }
    .usersByUsernameQuery(
                "select login, password, 'true' from my_user " +
                        "where login=?"
            )
            .authoritiesByUsernameQuery(
                "select login, authority from my_user " +
                        "where login=?"
            )
 */
*/

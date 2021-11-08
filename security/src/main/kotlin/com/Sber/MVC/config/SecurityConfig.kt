package com.Sber.MVC.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@EnableWebSecurity(debug = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val dataSource: DataSource? = null
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Bean
    fun getPasswordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(8)
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder)
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
            .antMatchers("/"," /toHome", "app/{id}/view", "api/{id}/view", "app/list", "/api/list", "app/list", "api/list" ).authenticated()
            .antMatchers("/app/{id}/edit", "/api/{id}/edit"  ).hasAnyRole("MODERATOR", "ADMIN")
            .antMatchers("/app/{id}/delete", "/api/{id}/delete").hasRole("ADMIN")
            .and().
                formLogin()
            .and()
                .exceptionHandling().accessDeniedPage("/toAccessDenied")
            .and()
                .csrf().disable()
    }
}
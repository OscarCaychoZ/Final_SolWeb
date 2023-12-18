/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb;

import Grupo1.ProyectoSolWeb.Component.AccesoDenegado;
import Grupo1.ProyectoSolWeb.Services.Implements.UserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new UserServices();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
  
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccesoDenegado(); 
    }

    /*Estableciendo filtros de seguridad*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) 
    throws Exception
    {
        http
        .csrf().disable()
        .authorizeHttpRequests()
        .anyRequest().authenticated()
        //iniciar sesion
        .and().formLogin().loginPage("/login")
        //establece donde se enviara las solicitudes de iniciar sesion
        .and().formLogin().loginProcessingUrl("/login")
        //Pagina que redigira despues de iniciar sesion
        .and().formLogin().defaultSuccessUrl("/").permitAll()
        //Pagina que redigira despues de cerrar sesion
        .and().logout().logoutSuccessUrl("/login")
                
        .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        
        return http.build(); 
    }
    
    
    //Configurar un proveedor de autenticacion 
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
    //PasswordEncoder encoder = null;
    DaoAuthenticationProvider authenticationProvider = new
    DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService())
    ;
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
    }
    

}

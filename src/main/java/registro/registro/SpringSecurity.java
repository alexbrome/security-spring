/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package registro.registro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration   //configuración de Spring Bean
@EnableWebSecurity //Habilita el soporte de seguridad web de Spring Security.
public class SpringSecurity {

    //Autoconecta una implementación de la interfaz UserDetailsService. 
    //Este servicio es utilizado por Spring Security para cargar datos específicos del usuario durante la autenticación.
    @Autowired
    private UserDetailsService userDetailsService;

    //Define un codificador de contraseñas
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() //Deshabilitar la protección CSRF
                //Configurar controles de acceso basados en URL usando el método antMatchers
                .authorizeHttpRequests(authorize
                        -> authorize
                        .antMatchers("/register/**", "/index").permitAll()
                        .antMatchers("/users").hasRole("ADMIN")
                )
                //Configurar el inicio de sesión basado en formularios con una página de inicio de sesión personalizada, 
                //una URL de procesamiento de inicio de sesión, 
                //una URL de éxito predeterminada y permitir el acceso a la página de inicio de sesión a todo el mundo.
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                
                                //.usernameParameter("email")
                                //.passwordParameter("pass")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                //Configurar el cierre de sesión con una URL de cierre de sesión personalizada 
                        //y permitir el acceso a ella a todo el mundo.
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    //Configura el gestor de autenticación global. 
    //Establece el servicio de detalles de usuario y el codificador de contraseñas para la autenticación.

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}

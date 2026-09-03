package com.esfe.sistemagimnasio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // RECURSOS PÚBLICOS
                        // =========================
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/error",
                                "/acceso-denegado"
                        ).permitAll()


                        // =========================
                        // LOGIN Y REGISTRO PÚBLICO
                        // =========================
                        .requestMatchers(
                                "/usuarios/login",
                                "/registro"
                        ).permitAll()


                        // =========================
                        // SOLO ADMIN
                        // =========================
                        .requestMatchers(
                                "/usuarios/**",
                                "/clientes/**",
                                "/entrenadores/**",
                                "/membresias-clientes/**",
                                "/tipos-membresia/**",
                                "/pagos/**",
                                "/metodos-pago/**",
                                "/asignaciones-entrenador/**"
                        ).hasAuthority("ADMIN")


                        // =========================
                        // ADMIN O ENTRENADOR
                        // =========================
                        .requestMatchers(
                                "/rutinas/**",
                                "/rutina-ejercicios/**",
                                "/ejercicios/**",
                                "/grupos-musculares/**",
                                "/evaluaciones-fisicas/**",
                                "/asistencias/**"
                        ).hasAnyAuthority(
                                "ADMIN",
                                "ENTRENADOR"
                        )

                        .requestMatchers(
                                "/mi-membresia/**"
                        ).hasAuthority("CLIENTE")

                        // =========================
                        // CUALQUIER USUARIO LOGUEADO
                        // =========================
                        .requestMatchers(
                                "/home"
                        ).authenticated()



                        // =========================
                        // CUALQUIER OTRA RUTA
                        // =========================
                        .anyRequest().authenticated()
                )


                // =========================
                // LOGIN
                // =========================
                .formLogin(form -> form

                        .loginPage("/usuarios/login")

                        .loginProcessingUrl("/usuarios/login")

                        .usernameParameter("username")

                        .passwordParameter("password")

                        .defaultSuccessUrl("/home", true)

                        .failureUrl("/usuarios/login?error")

                        .permitAll()
                )


                // =========================
                // ACCESO DENEGADO
                // =========================
                .exceptionHandling(exception -> exception

                        .accessDeniedPage(
                                "/acceso-denegado"
                        )
                )


                // =========================
                // LOGOUT
                // =========================
                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl(
                                "/usuarios/login?logout"
                        )

                        .permitAll()
                );

        return http.build();
    }
}
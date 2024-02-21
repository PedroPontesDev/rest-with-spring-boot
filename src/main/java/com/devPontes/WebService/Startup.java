package com.devPontes.WebService;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }

    @Bean
    public CommandLineRunner run(PasswordEncoder passwordEncoder) {
        return args -> {
            Map<String, PasswordEncoder> encoders = new HashMap<>();
            Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
                    SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
            encoders.put("pbkdf2", pbkdf2Encoder);
            DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
            delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
            String senha = "admin123";
            String senhaCriptografada = delegatingPasswordEncoder.encode(senha);
            System.out.println("Senha criptografada: " + senhaCriptografada);
        };
    }
}

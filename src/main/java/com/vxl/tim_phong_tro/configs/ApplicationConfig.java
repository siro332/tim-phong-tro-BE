package com.vxl.tim_phong_tro.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Primary
    @Bean
    public void firebaseInit() throws IOException {
        String servicesAccountJsonBase64 = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        byte[] base64Decoded = DatatypeConverter.parseBase64Binary(servicesAccountJsonBase64);;
        String decodedJsonString = new String(base64Decoded, StandardCharsets.UTF_8);
        log.info(decodedJsonString);
        InputStream serviceAccount = new ByteArrayInputStream(decodedJsonString.getBytes(StandardCharsets.UTF_8));
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("gold-fiber-311516.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);

        log.info("OK");
    }
}
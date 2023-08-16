package com.yaloostore.shop.etc;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local-test")
public class ConfigTest {


    @Autowired
    private Environment environment;


    @Test
    void testProfileSettings(){
        String[] activeProfiles = environment.getActiveProfiles();

        for (String activeProfile : activeProfiles) {
            System.out.println(activeProfile);
        }
    }
}

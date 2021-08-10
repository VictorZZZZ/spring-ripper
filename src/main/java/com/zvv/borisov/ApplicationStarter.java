package com.zvv.borisov;

import com.zvv.borisov.playground.Quoter;
import com.zvv.borisov.playground.TerminatorQuoter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationStarter.class);
        context.getBean(Quoter.class).sayGoodByeQuote();
    }

}

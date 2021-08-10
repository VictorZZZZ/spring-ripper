package com.zvv.borisov.playground;

import com.zvv.borisov.playground.annotations.InjectRandomInt;
import com.zvv.borisov.playground.annotations.Profiling;
import com.zvv.borisov.playground.proxy.PostProxy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLOutput;

/**
 * Profiging - самописная аннотация для того, чтобы показать как работает BeanPostProcessor
 */
@Profiling
@Component
public class TerminatorQuoter implements Quoter{
    /**
     * Самописная аннотация Которая в рандоме
     */
    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message = "I'll be back!";

    public TerminatorQuoter() {
        System.out.println("Phase 1: Constructor");
    }

    @PostConstruct
    public void init(){
        System.out.println("Phase 2: Post construct");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    @PostProxy
    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }
    }

    @Override

    public void sayGoodByeQuote(){
        System.out.println("Asta La vista Babe!");
    }
}

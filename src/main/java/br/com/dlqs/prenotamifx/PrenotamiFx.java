package br.com.dlqs.prenotamifx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.dlqs.prenotamifx")
public class PrenotamiFx {
    public static void main(String[] args) {
        Application.launch(LoginApplication.class,args);
    }

}

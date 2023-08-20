package br.com.dlqs.prenotamifx.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LogsService {

    @Bean
    public Logger getLogger(){
        return LoggerFactory.getLogger(this.getClass());
    }
}

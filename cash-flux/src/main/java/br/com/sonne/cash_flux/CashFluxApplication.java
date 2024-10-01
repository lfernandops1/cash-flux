package br.com.sonne.cash_flux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.com.sonne.cash_flux.domain")
@EnableJpaRepositories("br.com.sonne.cash_flux.repository")
public class CashFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashFluxApplication.class, args);
    }

}

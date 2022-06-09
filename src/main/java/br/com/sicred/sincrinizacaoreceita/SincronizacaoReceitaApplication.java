package br.com.sicred.sincrinizacaoreceita;

import br.com.sicred.sincrinizacaoreceita.service.RelatorioServiceInterface;
import br.com.sicred.sincrinizacaoreceita.service.impl.RelatorioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class SincronizacaoReceitaApplication implements CommandLineRunner {

    public static void main(String... args) {
        SpringApplication.run(SincronizacaoReceitaApplication.class, args);
    }

    @Bean
    public RelatorioServiceInterface getRelatorioService() {
        return new RelatorioService();
    }

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        getRelatorioService().iniciaTrabalhoArquivoERetornaArquivoDeResultado(args);
    }
}

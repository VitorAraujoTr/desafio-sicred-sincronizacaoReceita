package br.com.sicred.sincrinizacaoreceita.service;

import java.io.IOException;

public interface RelatorioServiceInterface {

    void iniciaTrabalhoArquivoERetornaArquivoDeResultado(String[] name) throws IOException, InterruptedException;
}

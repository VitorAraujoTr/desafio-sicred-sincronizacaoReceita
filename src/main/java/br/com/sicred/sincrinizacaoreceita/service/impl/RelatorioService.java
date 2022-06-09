package br.com.sicred.sincrinizacaoreceita.service.impl;

import br.com.sicred.sincrinizacaoreceita.entity.Conta;
import br.com.sicred.sincrinizacaoreceita.service.RelatorioServiceInterface;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class RelatorioService implements RelatorioServiceInterface {

    @Override
    public void iniciaTrabalhoArquivoERetornaArquivoDeResultado(String[] name) throws IOException, InterruptedException {

        verificaNomeEExtensaoArquivo(name);

        RelatorioService relatorio = new RelatorioService();

        exportandoArquivoCsv(name[0], relatorio.trabalhandoArquivoCsvImportado(name[0]));
    }

    private void exportandoArquivoCsv(String name, List<Conta> contas) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("resultado-" + name));
        bw.append("agencia")
                .append(";")
                .append("conta")
                .append(";")
                .append("saldo")
                .append(";")
                .append("status")
                .append(";")
                .append("resultado")
                .append("\r\n");

        for (Conta conta : contas) {
            bw.append(conta.getAgencia())
                    .append(";")
                    .append(conta.getConta())
                    .append(";")
                    .append(conta.getSaldo())
                    .append(";")
                    .append(conta.getStatus())
                    .append(";")
                    .append(conta.getResultado())
                    .append("\r\n");
        }

        bw.flush();
        bw.close();
    }

    private List<Conta> trabalhandoArquivoCsvImportado(String name) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new FileReader(name));
        ReceitaService receita = new ReceitaService();
        List<Conta> contas = new ArrayList<>();

        int count = 2;
        br.readLine();
        String strLine = br.readLine();
        while (!isNull(strLine)) {
            String[] vector = strLine.split(";");

            verificaIntegridadeArquivo(vector, count);

            boolean bol = receita.atualizarConta(
                    vector[0],
                    vector[1].replace("-", ""),
                    Double.parseDouble(vector[2].replace(",", ".")),
                    vector[3]);
            contas.add(new Conta(vector[0], vector[1], vector[2], vector[3], Boolean.toString(bol)));

            count++;
            strLine = br.readLine();
        }
        br.close();
        return contas;
    }

    private void verificaNomeEExtensaoArquivo(String[] name) throws FileNotFoundException, FileSystemException {
        if(Objects.equals(name.length, 0)) {
            throw new FileNotFoundException("Favor, adicionar arquivo.");
        } else if(!name[0].contains(".csv")) {
            throw new FileSystemException( "Arquivo inválido!");
        }
    }

    private void verificaIntegridadeArquivo(String[] strs, int count) {
        if (strs.length < 4 || retornaSeHaStringVazia(strs)) {
            throw new IllegalStateException("Dados incorretos no arquivo, linha: " + count);
        }
    }

    private boolean retornaSeHaStringVazia(String[] strs) {
        for (String str: strs) {
            if(isBlank(str)) {
                return true;
            }
        }
        return false;
    }
}
package br.com.sicred.sincrinizacaoreceita.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta implements Serializable {

    private static final long serialVersionUID = -3098234554960470034L;

    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private String resultado;

}

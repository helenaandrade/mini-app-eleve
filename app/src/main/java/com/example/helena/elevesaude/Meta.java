package com.example.helena.elevesaude;

/**
 * Created by MariaHelena on 11/11/2017.
 */

class Meta {
    String titulo;
    String fundamentacao;
    String tipo;
    int numExecucoes;
    int numExecucoesConcluidas;
    String dataLimite;


    public Meta(String titulo, String fundamentacao, String tipo, int numExecucoes, String dataLimite, int numExecucoesConcluidas) {
        this.titulo = titulo;
        this.fundamentacao = fundamentacao;
        this.tipo = tipo;
        this.numExecucoes = numExecucoes;
        this.dataLimite = dataLimite;
        this.numExecucoesConcluidas = numExecucoesConcluidas;
    }
}

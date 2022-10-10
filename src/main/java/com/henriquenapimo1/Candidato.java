package com.henriquenapimo1;

public class Candidato {

    public String nome;
    public int num;
    public Long votos;
    public String porcent;
    public String partido;

    public Candidato(String nome,int num,Long votos,String porcent, String partido) {
        this.nome = nome;
        this.num = num;
        this.votos = votos;
        this.porcent = porcent;
        this.partido = partido;
    }

    public String cor() {
        return nome.equals("LULA") ? "red" : "green";
    }


}

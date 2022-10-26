package com.henriquenapimo1.obj;

public class Candidato {

    public String nome;
    public String partido;
    public int num;

    public int pos;
    public Long votos;
    public int porcent;

    public String urnas;
    public int nulos;

    public Candidato(String nome,int num, int pos,Long votos,int porcent, String partido, String urnas, int perNul) {
        this.nome = nome;
        this.num = num;
        this.pos = pos;
        this.votos = votos;
        this.porcent = porcent;
        this.partido = partido;
        this.urnas = urnas;
        this.nulos = perNul;
    }

    public String cor() {
        return nome.equals("LULA") ? "red" : "blue";
    }


}

package com.example.victorlelis_p2;

import java.io.Serializable;

public class Jogador implements Serializable {
    private int idJogador;
    private int idTime;//chave estrangeira
    private String nomeTime;
    private String nome;
    private String cpf;
    private int anoNascimento;

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getIdTime() {
        return idTime;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "nomeTime='" + nomeTime + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", anoNascimento=" + anoNascimento +
                '}';
    }
}

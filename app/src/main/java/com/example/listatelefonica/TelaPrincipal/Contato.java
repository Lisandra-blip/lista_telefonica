package com.example.listatelefonica.TelaPrincipal;

public class Contato {

    private int id;
    private String nome;
    private int id_tipo;
    private String celular;

    public Contato() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_tipo() { return id_tipo; }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }


    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


}

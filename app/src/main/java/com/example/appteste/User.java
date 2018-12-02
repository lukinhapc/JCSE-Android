package com.example.appteste;

public class User {
    private String nome, email, image, telefone, cnpj, cep, endereco, cidade, uf, gasto, valor, mes;

    public User() {
        //Empty
    }

    public User(String nome, String email, String image, String telefone, String cnpj, String cep, String endereco, String cidade, String uf, String gasto, String valor, String mes) {
        this.nome = nome;
        this.email = email;
        this.image = image;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.cep = cep;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
        this.gasto = gasto;
        this.valor = valor;
        this.mes = mes;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getGasto() {
        return gasto;
    }

    public void setGasto(String gasto) {
        this.gasto = gasto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}

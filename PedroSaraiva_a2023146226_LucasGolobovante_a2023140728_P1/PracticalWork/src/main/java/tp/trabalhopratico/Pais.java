/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp.trabalhopratico;

import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class Pais {
    String nome; // ta
    String capital; // ta
    String continente; // ta
    String img_bandeira; // ta
    ArrayList<String> linguas; // ta
    double area; // ta
    int habitantes; // ta
    int habitantes_porKM; // ta
    String presidente; // ta
    ArrayList<String> religioes; // ta
    ArrayList<String> cidades_importantes; // ta
    ArrayList<String> paises_fronteira; // ta
    int casos_covid; // ta
    
    // nós inventámos
    int mortes_covid; // ta
    String moeda; // ta
    String hino; // ta

    public Pais(String nome, String capital, String continente, String img_bandeira, ArrayList<String> linguas, double area, int habitantes, int habitantes_porKM, String presidente, ArrayList<String> religioes, ArrayList<String> cidades_importantes, ArrayList<String> paises_fronteira, int casos_covid, int mortes_covid, String moeda, String hino) {
        this.nome = nome;
        this.capital = capital;
        this.continente = continente;
        this.img_bandeira = img_bandeira;
        this.linguas = linguas;
        this.area = area;
        this.habitantes = habitantes;
        this.habitantes_porKM = habitantes_porKM;
        this.presidente = presidente;
        this.religioes = religioes;
        this.cidades_importantes = cidades_importantes;
        this.paises_fronteira = paises_fronteira;
        this.casos_covid = casos_covid;
        this.mortes_covid = mortes_covid;
        this.moeda = moeda;
        this.hino = hino;
    }

    public String getNome() {
        return nome;
    }

    public String getCapital() {
        return capital;
    }

    public String getContinente() {
        return continente;
    }

    public String getImg_bandeira() {
        return img_bandeira;
    }

    public ArrayList<String> getLinguas() {
        return linguas;
    }

    public double getArea() {
        return area;
    }

    public int getHabitantes() {
        return habitantes;
    }

    public int getHabitantes_porKM() {
        return habitantes_porKM;
    }

    public String getPresidente() {
        return presidente;
    }

    public ArrayList<String> getReligioes() {
        return religioes;
    }

    public ArrayList<String> getCidades_importantes() {
        return cidades_importantes;
    }

    public ArrayList<String> getPaises_fronteira() {
        return paises_fronteira;
    }

    public int getCasos_covid() {
        return casos_covid;
    }
    
    public int getMortes_covid() {
        return mortes_covid;
    }
    
    public String getMoeda() {
        return moeda;
    }
    
    public String getHino() {
        return hino;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public void setImg_bandeira(String img_bandeira) {
        this.img_bandeira = img_bandeira;
    }

    public void setLinguas(ArrayList<String> linguas) {
        this.linguas = linguas;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setHabitantes(int habitantes) {
        this.habitantes = habitantes;
    }

    public void setHabitantes_porKM(int habitantes_porKM) {
        this.habitantes_porKM = habitantes_porKM;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public void setReligioes(ArrayList<String> religioes) {
        this.religioes = religioes;
    }

    public void setCidades_importantes(ArrayList<String> cidades_importantes) {
        this.cidades_importantes = cidades_importantes;
    }

    public void setPaises_fronteira(ArrayList<String> paises_fronteira) {
        this.paises_fronteira = paises_fronteira;
    }

    public void setCasos_covid(int casos_covid) {
        this.casos_covid = casos_covid;
    }
    
    public void setMortes_covid(int mortes_covid) {
        this.mortes_covid = mortes_covid;
    }
    
    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }
    
    public void setHino(String hino) {
        this.hino = hino;
    }
}

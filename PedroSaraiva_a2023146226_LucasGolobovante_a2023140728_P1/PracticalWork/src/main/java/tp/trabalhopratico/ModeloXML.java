/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp.trabalhopratico;

import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author Pedro
 */
public class ModeloXML {
    public static Document adicionaPais(Pais pais, Document doc) {
        Element raiz;

        // Se o documento for null, cria a raiz <lista>
        if (doc == null) {
            raiz = new Element("lista");
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        // Elemento principal <pais nome="...">
        Element pai = new Element("pais");
        pai.setAttribute(new Attribute("nome", pais.getNome()));

        // Elementos simples
        pai.addContent(new Element("capital").addContent(pais.getCapital()));
        pai.addContent(new Element("continente").addContent(pais.getContinente()));
        pai.addContent(new Element("img_bandeira").addContent(pais.getImg_bandeira()));
        pai.addContent(new Element("area").addContent(Double.toString(pais.getArea())));
        pai.addContent(new Element("habitantes").addContent(Integer.toString(pais.getHabitantes())));
        pai.addContent(new Element("habitantes_porKM").addContent(Integer.toString(pais.getHabitantes_porKM())));
        pai.addContent(new Element("presidente").addContent(pais.getPresidente()));
        pai.addContent(new Element("casos_covid").addContent(Integer.toString(pais.getCasos_covid())));

        // Adicionando os novos atributos
        pai.addContent(new Element("mortes_covid").addContent(Integer.toString(pais.getMortes_covid())));
        pai.addContent(new Element("moeda").addContent(pais.getMoeda()));
        pai.addContent(new Element("hino").addContent(pais.getHino()));

        // Elemento composto: linguas
        Element linguas = new Element("linguas");
        for (String lingua : pais.getLinguas()) {
            linguas.addContent(new Element("lingua").addContent(lingua));
        }
        pai.addContent(linguas);

        // Elemento composto: religioes
        Element religioes = new Element("religioes");
        for (String religiao : pais.getReligioes()) {
            religioes.addContent(new Element("religiao").addContent(religiao));
        }
        pai.addContent(religioes);

        // Elemento composto: cidades_importantes
        Element cidades = new Element("cidades_importantes");
        for (String cidade : pais.getCidades_importantes()) {
            cidades.addContent(new Element("cidade").addContent(cidade));
        }
        pai.addContent(cidades);

        // Elemento composto: paises_fronteira
        Element fronteiras = new Element("paises_fronteira");
        for (String fronteira : pais.getPaises_fronteira()) {
            fronteiras.addContent(new Element("pais_fronteira").addContent(fronteira));
        }
        pai.addContent(fronteiras);

        // Adiciona o <pais> à raiz
        raiz.addContent(pai);
        return doc;
    }


    public static Document removePais(String procura, Document doc) {
        Element raiz;
        boolean found = false;
        if (doc == null) {
            System.out.println("Ficheiro nao existe... nada para remover");
            return null;
        } else {
            raiz = doc.getRootElement();
        }

        List todos = raiz.getChildren("pais");
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i); //obtem pais i da Lista 
            if (esc.getAttributeValue("nome").contains(procura)) {
                esc.getParent().removeContent(esc);
                System.out.println("País removido com sucesso!");
                found = true;
            }
        }

        if (!found) {
            System.out.println("País " + procura + " não foi encontrado");
            return null;
        }
    return doc;
    }
    
    public static Document alteraAtributoNaoLista(String pais, String atributo, String novoAtributo, Document doc) {
        Element raiz;
        boolean found = false;

        if (doc == null) {
            System.out.println("Ficheiro não existe... nada para alterar");
            return null;
        } else {
            raiz = doc.getRootElement();
        }

        List todos = raiz.getChildren("pais");
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i);

            if (esc.getAttributeValue("nome").contains(pais)) {
                Element atrbt = esc.getChild(atributo);
                if (atrbt != null) {
                    atrbt.setText(novoAtributo);
                    System.out.println(atributo+" alterado(a) com sucesso!");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("País " + pais + " não foi encontrado");
            return null;
        }

        return doc;
    }
    
    public static Document adicionaLista(String pais, String lista, String novoValor, Document doc) {
        Element raiz;
        boolean found = false;

        if (doc == null) {
            System.out.println("Ficheiro não existe, nada a adicionar");
            return null;
        } else {
            raiz = doc.getRootElement();
        }

        List todos = raiz.getChildren("pais");
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i);
            if (esc.getAttributeValue("nome").equalsIgnoreCase(pais)) {
                Element ocup = null;
                if (lista.equals("lingua")) {
                    ocup = esc.getChild("linguas");
                } else if (lista.equals("religiao")) {
                    ocup = esc.getChild("religioes");
                } else if (lista.equals("cidade")) {
                    ocup = esc.getChild("cidades_importantes");
                } else if (lista.equals("pais_fronteira")) {
                    ocup = esc.getChild("paises_fronteira");
                }

                if (ocup != null) {
                    Element novo = new Element(lista);
                    novo.setText(novoValor);
                    ocup.addContent(novo);
                    System.out.println(lista + " adicionado(a) com sucesso!");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("País " + pais + " não foi encontrado");
            return null;
        }

        return doc;
    }
    
    public static Document removeLista(String pais, String lista, String novoValor, Document doc) {
        Element raiz;
        if (doc == null) {
            System.out.println("Ficheiro não existe, nada a adicionar.");
            return null;
        } else {
            raiz = doc.getRootElement();
        }

        List todos = raiz.getChildren("pais");
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Element esc = (Element) todos.get(i);
            if (esc.getAttributeValue("nome").contains(pais)) {
                Element ocup = null;
                List lista_oc = null;
                if (lista.equals("lingua")) {
                    ocup = esc.getChild("linguas");
                    lista_oc = ocup.getChildren(lista);
                } else if (lista.equals("religiao")) {
                    ocup = esc.getChild("religioes");
                    lista_oc = ocup.getChildren(lista);
                } else if (lista.equals("cidade")) {
                    ocup = esc.getChild("cidades_importantes");
                    lista_oc = ocup.getChildren(lista);
                } else if (lista.equals("pais_fronteira")) {
                    ocup = esc.getChild("paises_fronteira");
                    lista_oc = ocup.getChildren(lista);
                }

                for (int j = 0; j < lista_oc.size(); j++) {
                    Element oc = (Element) lista_oc.get(j);
                    if (oc.getText().equals(novoValor)) {
                        oc.getParent().removeContent(oc);
                        System.out.println(lista+" removido(a) com sucesso!");
                        found = true;
                    }
                }
            }

        }
        if (!found) {
            System.out.println("não foi encontrado");
            return null;
        }
        return doc;
    }
}

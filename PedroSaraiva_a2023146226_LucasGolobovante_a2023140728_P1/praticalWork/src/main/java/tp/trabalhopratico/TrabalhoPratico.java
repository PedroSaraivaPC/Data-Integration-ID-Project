/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package tp.trabalhopratico;

import java.awt.Image;
import java.io.IOException;

import java.util.ArrayList;
import javax.swing.ImageIcon;

import org.jdom2.Document;

/**
 *
 * @author Pedro
 */
public class TrabalhoPratico {

    public static void main(String[] args) throws IOException {
        System.out.println("\n\n");
        /*
        //Cria Escritor
        Pais pais = Wrappers.cria_Pais("Portugal");
        //Inicializa Doc XML
        Document doc = XMLJDomFunctions.lerDocumentoXML("paises.xml");
        //Chama a função para adicionar o pais ao XML
        doc = ModeloXML.adicionaPais(pais, doc);
        //grava o ficheiro XML em disco
        XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "paises.xml");
        */
        // Cria e exibe a janela
        
        interfaceGrafica janela = new interfaceGrafica();
        janela.setVisible(true);
        
        // Carregar o ícone do classpath
        try {
            Image icon = new ImageIcon(TrabalhoPratico.class.getResource("/world.png")).getImage();
            janela.setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Erro ao carregar o ícone: " + e.getMessage());
        }
        
        // Testes de Strings
        /*
        // Exemplo de chamar as 3 funcoes que estão a ser usadas apenas para a capital
        // Burkina-Faso, Portugal, Arménia, Itália, Malta, Zimbabwe, Malásia
        String pesquisa = "Paquistão";
        String palavra = Wrappers.obtem_hino_wiki(pesquisa);
        System.out.println("palavra: "+palavra);
        
        System.out.println("\n\n");
        
        String pesquisa2 = "Paquistão";
        String palavra2 = Wrappers.obtem_hino_dbcity(pesquisa2);
        System.out.println("palavra2: "+palavra2);
        
        System.out.println("\n\n");
        
        String pesquisa3 = "Paquistão";
        String palavra3 = Wrappers.obtem_hino(pesquisa3);
        System.out.println("palavra3: "+palavra3);
        */
        
        // Testes de Arrays de strings
        /*
        System.out.println("\n\n");
        System.out.println("\n\n");
        
        
        String array1 = "Portugal";
        ArrayList ling1 = Wrappers.obtem_cidades_wiki(array1);
        if(ling1.isEmpty()){
            ling1 = Wrappers.obtem_cidades_wiki(array1);
        }
        System.out.println("Cidades imp: " + ling1);
        
        System.out.println("\n\n");
        
        String array2 = "Portugal";
        ArrayList ling2 = Wrappers.obtem_cidades_dbcity(array2);
        if(ling2.isEmpty()){
            ling2 = Wrappers.obtem_cidades_dbcity(array2);
        }
        System.out.println("Cidades imp: " + ling2);
        
        System.out.println("\n\n");
        
        String array3 = "Portugal";
        ArrayList ling3 = Wrappers.obtem_cidades_importantes(array3);
        if(ling3.isEmpty()){
            ling3 = Wrappers.obtem_cidades_importantes(array3);
        }
        System.out.println("Cidades imp: " + ling3);
        */
        
        // Testes de Doubles
        /*
        System.out.println("\n\n");
        System.out.println("\n\n");

        String pais = "Micronésia";
        double area = Wrappers.obtem_area_wiki(pais);

        System.out.println("Area: " + area + " km2");

        System.out.println("\n\n");
        
        String pais2 = "Micronésia";
        double area2 = Wrappers.obtem_area_dbcity(pais2);

        System.out.println("Area2: " + area2 + " km2");
        
        System.out.println("\n\n");
        
        String pais3 = "Micronésia";
        double area3 = Wrappers.obtem_area(pais3);

        System.out.println("Area3: " + area3 + " km2");
        */
        
        // Testes de Ints
        /*
        System.out.println("\n\n");
        System.out.println("\n\n");

        String pais = "Portugal";
        int habitantes = Wrappers.obtem_mortes_covid(pais);

        System.out.println("cov: " + habitantes + ".");

        System.out.println("\n\n");
        
        String pais2 = "Angola";
        int habitantes2 = Wrappers.obtem_habitantes_dbcity(pais2);

        System.out.println("habitantes2: " + habitantes2 + ".");
        
        System.out.println("\n\n");
        
        String pais3 = "Angola";
        int habitantes3 = Wrappers.obtem_habitantes(pais3);

        System.out.println("habitantes3: " + habitantes3 + ".");
        */
    }
}

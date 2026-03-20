/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp.trabalhopratico;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;

/**
 *
 * @author Pedro
 */
public class Wrappers {
    // Função para criar a classe Pais
    public static Pais cria_Pais(String nomePais) throws SaxonApiException {
        // Inicializa as listas
        ArrayList<String> linguas = new ArrayList<>();
        ArrayList<String> religioes = new ArrayList<>();
        ArrayList<String> cidades = new ArrayList<>();
        ArrayList<String> fronteiras = new ArrayList<>();
        try {
            String xp = "//pais[@nome='" + nomePais + "']";
            XdmValue res = XPathFunctions.executaXpath(xp, "paises.xml");
            if (res != null && res.size() > 0) {
                return null; // País já existe, não e criado novamente
            }
            
            // Extrai os dados do HTML com as funcoes ja criadas
            String capital = obtem_capital(nomePais);
            String continente = obtem_continente(nomePais);
            String img_bandeira = obtem_bandeira(nomePais);
            linguas = obtem_linguas(nomePais);
            double area = obtem_area(nomePais);
            int habitantes = obtem_habitantes(nomePais);
            int densidade = (int)(habitantes / area); // calculado diretamente
            String presidente = obtem_presidente(nomePais);
            religioes = obtem_religioes(nomePais);
            cidades = obtem_cidades_importantes(nomePais);
            fronteiras = obtem_fronteiras(nomePais);
            int casosCovid = obtem_casos_covid(nomePais);
            
            // nós inventámos
            int mortesCovid = obtem_mortes_covid(nomePais);
            String moeda = obtem_moeda(nomePais);
            String hino = obtem_hino(nomePais);
            
            return new Pais(nomePais, capital, continente, img_bandeira, linguas, area, habitantes, densidade, presidente, religioes, cidades, fronteiras, casosCovid, mortesCovid, moeda, hino);
        
        } catch (IOException e) {
            System.out.println("Erro ao obter dados do país: " + nomePais);
            
            linguas.add("desconhecida");
            religioes.add("desconhecida");
            cidades.add("desconhecida");
            fronteiras.add("desconhecida");
            return new Pais(nomePais, "desconhecida", "desconhecido", "sem_bandeira.jpg", linguas, 0.0, 0, 0, "desconhecido", religioes, cidades, fronteiras, 0, 0, "desconhecida", "desconhecido");
        }
    }
    
    // Funções para obter a capital
    static String obtem_capital_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "<b>Capital</b>";
            String er2 = "\">([a-zA-ZÀ-ÿ]+)</a>\\s?(?:<br)?"; // À-ÿ para caracteres com acentos
            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    while (in.hasNextLine()) {
                        linha = in.nextLine();
                        Matcher n2 = p2.matcher(linha);
                        if(n2.find()) {
                            in.close();
                            return n2.group(1);
                        }
                    }
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_capital_dbcity(String pesquisa) {
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "\">Capital</a>\\s<span\\sclass=\"reshid\">[a-zA-ZÀ-ÿ]+</span></th><td><a href=\"[a-zA-ZÀ-ÿ-_/]+\"\\stitle=\"([a-zA-ZÀ-ÿ]+)\"";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_capital(String pais) throws IOException { // está-se a priorizar a pesquisa da wikipedia, sem razão especifica
        String capitalWiki = obtem_capital_wiki(pais);
        String capitalDbCity = obtem_capital_dbcity(pais);

        if (capitalWiki != null && !capitalWiki.isBlank()) { // se a capital vinda da wikipedia nao for null nem tiver vazia ou só com espacos
            return capitalWiki;
        } else if (capitalDbCity != null && !capitalDbCity.isBlank()) { // se a capital vinda da dbcity nao for null nem tiver vazia ou só com espacos
            return capitalDbCity;
        } else { // se ambas as capitais tiverem null ou so tiverem espacos
            return "desconhecida";
        }
    }
    
    // Funções para obter o continente
    static String obtem_continente_wiki(String pesquisa){
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "continente\\s([A-Za-zÀ-ÿ]+)";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_continente_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "<th>Continente</th><td><a href=\"/([a-zA-ZÀ-ÿ]+)\"";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }      
    static String obtem_continente(String pais) throws IOException{ // está-se a priorizar a pesquisa do dbcity, pois a wikipedia em MUITOS paises nao diz qual o seu continente
        String continenteWiki = obtem_continente_wiki(pais);
        String continenteDbCity = obtem_continente_dbcity(pais);

        if (continenteDbCity != null && !continenteDbCity.isBlank()) { // se o continente vindo da dbcity nao for null nem tiver vazia ou só com espacos
            return continenteDbCity;
        } else if (continenteWiki != null && !continenteWiki.isBlank()) { // se o continente vindo da wikipedia nao for null nem tiver vazia ou só com espacos
            return continenteWiki;
        } else { // se ambos os continentes tiverem null ou so tiverem espacos
            return "desconhecido";
        }
    }
    
    // Funções para obter a bandeira
    static String obtem_bandeira_wiki(String pesquisa){
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "<a\\shref=\"([/A-Za-zÀ-ÿ\\(\\)\\.:_]+)\"\\s[\\sA-Za-zÀ-ÿ\"-=]+\\stitle=\"Bandeira\\s[\\sA-Za-zÀ-ÿ]+\"><img alt=\"Bandeira\\s[\\sA-Za-zÀ-ÿ]+\"";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return "https://pt.wikipedia.org" + n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_bandeira_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "title=\"Bandeira\\s[\\sA-Za-zÀ-ÿ]+\"><img src=\"([a-zA-ZÀ-ÿ:_\\./0-9]+)";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }      
    static String obtem_bandeira(String pais) throws IOException{ // está-se a priorizar a pesquisa do dbcity, pois ele retorn o link mesmo da imagem ao contrário da wiki
        String bandeiraWiki = obtem_bandeira_wiki(pais);
        String bandeiraDbCity = obtem_bandeira_dbcity(pais);

        if (bandeiraDbCity != null && !bandeiraDbCity.isBlank()) { // se a bandeira vindo da dbcity nao for null nem tiver vazia ou só com espacos
            return bandeiraDbCity;
        } else if (bandeiraWiki != null && !bandeiraWiki.isBlank()) { // se a bandeiravindo da wikipedia nao for null nem tiver vazia ou só com espacos
            return bandeiraWiki;
        } else { // se ambas as bandeiras tiverem null ou so tiverem espacos
            return "desconhecido";
        }
    }
    
    // Funções para obter as linguas oficiais
    static ArrayList<String> obtem_linguas_wiki(String pesquisa){
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "<b>Língua oficial</b>";
            String er2 = "title=\"(?:Língua)?s?\\s?[a-zA-ZÀ-ÿ\\s]+\">([a-zA-ZÀ-ÿ\\s\\(\\)]+)</a>";
            
            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);
            
            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            Matcher n1, n2;
            ArrayList lista = new ArrayList();
            
            while (in.hasNextLine()) {
                linha = in.nextLine();
                n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.nextLine();
                    linha = in.nextLine();
                    n2 = p2.matcher(linha);
                    while(n2.find()){
                        lista.add(n2.group(1));
                    }
                }
            }
            in.close();
            return lista;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_linguas_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "<th>Língua oficial</th><td>";
            String er2 = "([^<]+)";
            String brTag = "<br/>"; // tag que acaba cada lingua
            String tdTag = "</td>"; // tag para saber que acabou todas as linguas

            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);
            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            Matcher n1, n2;
            ArrayList<String> lista = new ArrayList<>();

            while (in.hasNextLine()) {
                linha = in.nextLine();
                n1 = p1.matcher(linha);
                if(n1.find()) {
                    String sublinha = linha.substring(n1.end()); // começa logo depois do <td>
                    while (true) {
                        n2 = p2.matcher(sublinha);
                        if (n2.find()) {
                            String valor = n2.group(1).trim();
                            lista.add(valor);
                            sublinha = sublinha.substring(n2.end()); // começa logo depois do <br/>

                            if (sublinha.startsWith(brTag)) {
                                sublinha = sublinha.substring(brTag.length()); // continua a ler o próximo valor
                            } else if (sublinha.startsWith(tdTag)) {
                                break; // acabou a lista de línguas
                            } else {
                                break; // apenas por segurança, se não for nenhum dos dois, termina
                            }
                        } else {
                            break; // não encontrou mais valores
                        }
                    }
                    break; // só queremos a primeira ocorrência <th>Língua oficial</th><td>...
                }
            }

            in.close();
            return lista;
        } catch (FileNotFoundException e) {
            System.out.println("\nErro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) {
            System.out.println("\nErro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_linguas(String pais) throws IOException{ // está-se a priorizar a pesquisa do dbcity pelo trabalhao que deu, e tambem porque é mais preciso. Na wikipedia, o html, varia muito de país para país, logo nem sempre funciona
        ArrayList<String> linguasWiki = obtem_linguas_wiki(pais);
        ArrayList<String> linguasDbCity = obtem_linguas_dbcity(pais);

        if (linguasDbCity != null && !linguasDbCity.isEmpty()) {
            return linguasDbCity;
        } else if (linguasWiki != null && !linguasWiki.isEmpty()) {
            return linguasWiki;
        } else {
            ArrayList<String> desconhecido = new ArrayList<>();
            desconhecido.add("desconhecido");
            return desconhecido;
        }
    }
                        
    static double obtem_area_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            
            String er1 = "\">Área(?:</a>)?";
            String er2 = "</th></tr>";
            String er3 = "<td\\sstyle=\"[^\"]+\">\\s*(?:(\\d+\\.\\d+)|(\\d+(?:&#160;\\d+)+)|(\\d[\\d\\s]+))(?:,(\\d+))?.*"; // À-ÿ para caracteres com acentos
            
            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);
            Pattern p3 = Pattern.compile(er3);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));
            Matcher n1, n2, n3;
            while (in.hasNextLine()) {
                linha = in.nextLine();
                n1 = p1.matcher(linha);
                if(n1.find()) {                     
                    linha = in.nextLine();
                    n2 = p2.matcher(linha);
                    if(n2.find()) {
                        while (in.hasNextLine()) {
                            linha = in.nextLine();
                            n3 = p3.matcher(linha); 
                            if (n3.find()) {
                                //System.out.println("linha: " + linha);
                                //System.out.println("1: " + n2.group(1));
                                //System.out.println("2: " + n2.group(2));
                                //System.out.println("3: " + n2.group(3));

                                String parteInteira;
                               
                                if (n3.group(1) != null) { // se o grupo 3 (com '.') tiver algum valor
                                    parteInteira = n3.group(1).replaceAll("\\.", ""); 
                                } else if (n3.group(2) != null) { // se o grupo 1 (com '&#160;') tiver algum valor
                                    parteInteira = n3.group(2).replaceAll("&#160;", "");
                                } else { // se o grupo 2 (com ' ') tiver algum valor
                                    parteInteira = n3.group(3).replaceAll("\\s", "");
                                }
                                
                                String parteDecimal = n3.group(4);

                                String numeroFinal = parteInteira;
                                if (parteDecimal != null) {
                                    numeroFinal += "." + parteDecimal;
                                }

                                in.close();
                                return Double.parseDouble(numeroFinal);
                            }
                        }
                    }
                }
            }
            in.close();
            return -1.0;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return -1.0;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return -1.0;
        }
    }
    static double obtem_area_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "\">Superfície</a></th><td>([0-9\\.]+)\\s";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    //System.out.println("1: " + n1.group(1));
                    String numeroFinal = n1.group(1).replace(".", ""); // remove o ponto
                    double area = Double.parseDouble(numeroFinal);
                    in.close();
                    return area;
                }
            }
            in.close();
            return -1.0;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return -1.0;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return -1.0;
        }
    }
    static double obtem_area(String pais) throws IOException { // priorizamos a wikipedia pelo trabalhão que deu e porque os números lá são mais precisos, isto é, tem até as casas decimais e no dbcity não
        Double areaWiki = obtem_area_wiki(pais);
        Double areaDbCity = obtem_area_dbcity(pais);

        if (areaWiki != null && areaWiki != -1.0) { // se a capital vinda da wikipedia nao for null nem tiver vazia ou só com espacos
            return areaWiki;
        } else if (areaDbCity != null && areaDbCity != -1.0) { // se a capital vinda da dbcity nao for null nem tiver vazia ou só com espacos
            return areaDbCity;
        } else { // se ambas as areas tiverem null ou se tiverem a -1.0
            return -1.0;
        }
    }

    static int obtem_habitantes_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            
            String er1 = "\">População(?:</a>)?";
            String er2 = "<td\\sstyle=\"[^\"]+\">\\s*(?:(\\d+(?:&#160;\\d+)+)|(\\d[\\d\\s]+)).*"; // À-ÿ para caracteres com acentos
            
            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));
            Matcher n1, n2;
            while (in.hasNextLine()) {
                linha = in.nextLine();
                n1 = p1.matcher(linha);
                if (n1.find()) {
                    while (in.hasNextLine()) {
                        linha = in.nextLine();
                        n2 = p2.matcher(linha);
                        if (n2.find()) {
                            String num;
                            if (n2.group(1) != null) {
                                num = n2.group(1).replaceAll("&#160;", "");
                            } else {
                                num = n2.group(2).replaceAll("\\s", "");
                            } // e noutras ocasiões (como Angola), tem a palavra 'milhões', ou seja, vamos priorizar o dbcity porque temos mais que fazer

                            in.close();
                            return Integer.parseInt(num);
                        }
                    }
                }
            }
            in.close();
            return -1;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return -1;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return -1;
        }
    }
    static int obtem_habitantes_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "\">População</a></th><td>([0-9\\.]+)\\s";
            Pattern p1 = Pattern.compile(er1);
            
            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    //System.out.println("1: " + n1.group(1));
                    String num = n1.group(1).replace(".", ""); // remove o ponto
                    int area = Integer.parseInt(num);
                    in.close();
                    return area;
                }
            }
            in.close();
            return -1;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return -1;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return -1;
        }
    }
    static int obtem_habitantes(String pais) throws IOException { // priorizamos o dbcity pq a wikipedia para além de 2 formas diferentes de países para países ainda tem palavras, ex: 10 'milhões' (p ex: Angola)
        Integer habitantesWiki = obtem_habitantes_wiki(pais);
        Integer habitantesDbCity = obtem_habitantes_dbcity(pais);

        if (habitantesDbCity != null && habitantesDbCity != 0) { 
            return habitantesDbCity;
        } else if (habitantesWiki != null && habitantesWiki != 0) {
            return habitantesWiki;
        } else { // se ambos so habitantes tiverem null ou se tiverem a 0
            return 0;
        }
    }
    
    // Funções para obter o presidente
    static String obtem_presidente_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = ">Presidente</a>";
            String er2 = ">([^<]+)<\\/a>";
            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    while (in.hasNextLine()) {
                        linha = in.nextLine();
                        Matcher n2 = p2.matcher(linha);
                        if(n2.find()) {
                            in.close();
                            return n2.group(1);
                        }
                    }
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_presidente_dbcity(String pesquisa) {
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "\">Chefe de Estado <span class=\"reshid\">[^<]+</span></h2><div class=\"h2content\"><table><tr><th>[^<]+</th><td>([^<(]+)";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_presidente(String pais) throws IOException { // está-se a priorizar a pesquisa do dbcity, pois de acordo com as pesquisas realizadas, a wikipedia muda muito o formato da página de país para país enquanto a wikipedia funciona sempre
        String presidenteWiki = obtem_presidente_wiki(pais);
        String presidenteDbCity = obtem_presidente_dbcity(pais);

        if (presidenteDbCity != null && !presidenteDbCity.isBlank()) { 
            return presidenteDbCity;
        } else if (presidenteWiki != null && !presidenteWiki.isBlank()) { 
            return presidenteWiki;
        } else { 
            return "desconhecida";
        }
    }
    
    // Funções para obter as religioes
    static ArrayList<String> obtem_religiao_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        ArrayList<String> lista = new ArrayList<>();

        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "<b>Religião</b>";
            String er2 = ">.+>([^<]+)<\\/a>";

            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"), "UTF-8");

            Matcher n1, n2;
            boolean religiaoEncontrada = false;
            StringBuilder bloco = new StringBuilder();

            while (in.hasNextLine()) {
                linha = in.nextLine();
                if (!religiaoEncontrada) {
                    n1 = p1.matcher(linha);
                    if (n1.find()) {
                        religiaoEncontrada = true;
                    }
                } else {
                    bloco.append(linha).append("\n");
                    if (linha.contains("</ul></div>")) {
                        break;
                    }
                }
            }

            in.close();

            n2 = p2.matcher(bloco.toString());
            while (n2.find()) {
                String religiao = n2.group(1).trim();
                if (!religiao.equalsIgnoreCase("sem religião")) {
                    lista.add(religiao);
                }
            }

            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("\nErro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) {
            System.out.println("\nErro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_religiao_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "\">Religião <span";
            String er2 = "<strong>([^<]+)</strong> <span class=\"floatr mr10\">([0-9%\\s]+)</span></li>";
            String brTag = "<li>"; // tag que acaba cada lingua
            String tdTag = "</ol>"; // tag para saber que acabou todas as linguas

            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);
            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            Matcher n1, n2;
            ArrayList<String> lista = new ArrayList<>();

            while (in.hasNextLine()) {
                linha = in.nextLine();
                n1 = p1.matcher(linha);
                if(n1.find()) {
                    String sublinha = linha.substring(n1.end()); // começa logo depois do <td>
                    while (true) {
                        n2 = p2.matcher(sublinha);
                        if (n2.find()) {
                            String valor1 = n2.group(1).trim();
                            String valor2 = n2.group(2).trim(); // Captura o grupo 2 para sacar tambem a percentagem (fica mais bonito)
                            String valorConcatenado = valor1 + " - " + valor2;
                            lista.add(valorConcatenado);
                            sublinha = sublinha.substring(n2.end()); // começa logo depois do <br/>
                            if (sublinha.startsWith(brTag)) {
                                sublinha = sublinha.substring(brTag.length()); // continua a ler o próximo valor
                            } else if (sublinha.startsWith(tdTag)) {
                                break; // acabou a lista de línguas
                            } else {
                                break; // apenas por segurança, se não for nenhum dos dois, termina
                            }
                        } else {
                            break; // não encontrou mais valores
                        }
                    }
                    break; // só queremos a primeira ocorrência <th>Língua oficial</th><td>...
                }
            }

            in.close();
            return lista;
        } catch (FileNotFoundException e) {
            System.out.println("\nErro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) {
            System.out.println("\nErro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_religioes(String pais) throws IOException { // está-se a priorizar a pesquisa do dbcity, porque a wikipedia muda muito de país para país...
        ArrayList<String> religiaoWiki = obtem_religiao_wiki(pais);
        ArrayList<String> religiaoDbCity = obtem_religiao_dbcity(pais);

        if (religiaoDbCity != null && !religiaoDbCity.isEmpty()) { 
            return religiaoDbCity;
        } else if (religiaoWiki != null && !religiaoWiki.isEmpty()) { 
            return religiaoWiki;
        } else { 
            ArrayList<String> desconhecido = new ArrayList<>();
            desconhecido.add("desconhecida");
            return desconhecido;
        }
    }
    
    // Funções para obter as cidades // VER VER VER VER
    static ArrayList<String> obtem_cidades_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        ArrayList<String> lista = new ArrayList<>();

        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "<td rowspan=\\\"12\\\" style=\\\"background-color:#ddddff; padding-left:7px; padding-right:7px;\\\">";
            String er2 = ">([a-zA-ZÀ-ÿ\\s]+)</a></b></td>";

            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"), "UTF-8");

            Matcher n1, n2;
            boolean religiaoEncontrada = false;
            StringBuilder bloco = new StringBuilder();

            while (in.hasNextLine()) {
                linha = in.nextLine();
                if (!religiaoEncontrada) {
                    n1 = p1.matcher(linha);
                    if (n1.find()) {
                        religiaoEncontrada = true;
                    }
                } else {
                    bloco.append(linha).append("\n");
                    if (linha.contains("</ul></div>")) {
                        break;
                    }
                }
            }

            in.close();

            n2 = p2.matcher(bloco.toString());
            while (n2.find()) {
                String religiao = n2.group(1).trim();
                if (!religiao.equalsIgnoreCase("sem religião")) {
                    lista.add(religiao);
                }
            }

            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("\nErro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) {
            System.out.println("\nErro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_cidades_dbcity(String pesquisa) {
        String link = "https://pt.db-city.com/";
        ArrayList<String> lista = new ArrayList<>();

        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"), "UTF-8");
            StringBuilder conteudo = new StringBuilder();
            while (in.hasNextLine()) {
                conteudo.append(in.nextLine()).append("\n");
            }
            in.close();

            // 1. Encontrar o bloco das cidades importantes
            Pattern p1 = Pattern.compile("<div id=\"block_bigcity\">.*?<table.*?</table>", Pattern.DOTALL);
            Matcher n1 = p1.matcher(conteudo.toString());

            if (n1.find()) {
                String bloco = n1.group();

                // 2. Expressão para extrair os nomes das cidades
                Pattern p2 = Pattern.compile("<td><a[^>]*title=\"([^\"]+)\">[^<]+</a></td>");
                Matcher n2 = p2.matcher(bloco);

                while (n2.find()) {
                    lista.add(n2.group(1).trim());
                }
            }

            return lista;

        } catch (FileNotFoundException e) {
            System.out.println("\nErro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) {
            System.out.println("\nErro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_cidades_importantes(String pais) throws IOException { // está-se a priorizar a pesquisa da wikipedia, pois tem sempre apenas 20 cidades
        ArrayList<String> cidadesWiki = obtem_cidades_wiki(pais);
        ArrayList<String> cidadesDbCity = obtem_cidades_dbcity(pais);

        if (cidadesWiki != null && !cidadesWiki.isEmpty()) {
            return cidadesWiki;
        } else if (cidadesDbCity != null && !cidadesDbCity.isEmpty()) {
            return cidadesDbCity;
        } else {
            ArrayList<String> desconhecido = new ArrayList<>();
            desconhecido.add("desconhecida");
            return desconhecido;
        }
    }
    
    // Funções para obter os paises de fronteira
    static ArrayList<String> obtem_fronteiras_wiki(String pesquisa) {
    String link = "https://pt.wikipedia.org/wiki/";
    ArrayList<String> lista = new ArrayList<>();

    try {
        HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

        Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"), "UTF-8");
        StringBuilder conteudo = new StringBuilder();
        while (in.hasNextLine()) {
            conteudo.append(in.nextLine()).append("\n");
        }
        in.close();

        // 1. Encontrar a célula que contém os países vizinhos (após <a>Fronteira</a>)
        Pattern fronteiras = Pattern.compile("<td[^>]*>\\s*<a[^>]*>Fronteira<\\/a>.*?<td[^>]*>(.*?)<\\/td>", Pattern.DOTALL);
        Matcher mFronteira = fronteiras.matcher(conteudo.toString());

        if (mFronteira.find()) {
            String bloco = mFronteira.group(1);

            // 2. Capturar os títulos dos países dentro do bloco
            Pattern paises = Pattern.compile("<a[^>]*title=\"([^\"]+)\">[^<]+<\\/a>");
            Matcher mPaises = paises.matcher(bloco);

            while (mPaises.find()) {
                String pais = mPaises.group(1).trim();
                if (!lista.contains(pais)) {
                    lista.add(pais);
                }
            }
        }

        return lista;

    } catch (Exception e) {
        System.out.println("Erro: " + e.getMessage());
        return null;
    }
}
    static ArrayList<String> obtem_fronteiras_dbcity(String pesquisa) {
        String link = "https://pt.db-city.com/";
        ArrayList<String> lista = new ArrayList<>();

        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"), "UTF-8");
            StringBuilder conteudo = new StringBuilder();
            while (in.hasNextLine()) {
                conteudo.append(in.nextLine()).append("\n");
            }
            in.close();

            // Extrair o bloco de fronteira
            Pattern blocoFronteira = Pattern.compile("<div id=\"block_border\">.*?<ol class=\"h2l\">(.*?)</ol>", Pattern.DOTALL);
            Matcher mBloco = blocoFronteira.matcher(conteudo.toString());

            if (mBloco.find()) {
                String bloco = mBloco.group(1);

                // Expressão para extrair nomes dos países do atributo title
                Pattern paises = Pattern.compile("<a[^>]+title=\"([^\"]+)\"[^>]*>.*?</a>");
                Matcher mPaises = paises.matcher(bloco);

                while (mPaises.find()) {
                    String pais = mPaises.group(1).trim();
                    if (!lista.contains(pais)) {
                        lista.add(pais);
                    }
                }
            }

            return lista;

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
    static ArrayList<String> obtem_fronteiras(String pais) throws IOException { // está-se a priorizar a pesquisa da wikipedia, pois os países de fronteira no site da dbcity são muito duvidosos (França tem fronteira com Brasil(?))
        ArrayList<String> fronteirasWiki = obtem_fronteiras_wiki(pais);
        ArrayList<String> fronteirasDbCity = obtem_fronteiras_dbcity(pais);

        if (fronteirasWiki != null && !fronteirasWiki.isEmpty()) {
            return fronteirasWiki;
        } else if (fronteirasDbCity != null && !fronteirasDbCity.isEmpty()) {
            return fronteirasDbCity;
        } else {
            ArrayList<String> desconhecido = new ArrayList<>();
            desconhecido.add("desconhecida");
            return desconhecido;
        }
    }
    
    // Função para obter os casos de covid
    static int obtem_casos_covid(String pesquisa){ // estamos apenas a ir buscar ao dbcity que a wikipedia nunca tem o numero de casos de covid
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "<th>Confirmado<\\/th><td>([0-9\\.]+)<";
            Pattern p1 = Pattern.compile(er1);
            
            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    //System.out.println("1: " + n1.group(1));
                    String num = n1.group(1).replace(".", ""); // remove o ponto
                    int area = Integer.parseInt(num);
                    in.close();
                    return area;
                }
            }
            in.close();
            return -1;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return -1;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return -1;
        }
    }
    static int obtem_mortes_covid(String pesquisa){ // estamos apenas a ir buscar ao dbcity que a wikipedia nunca tem o numero de casos de covid
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "<th>Morte<\\/th><td>([0-9\\.]+)<";
            Pattern p1 = Pattern.compile(er1);
            
            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    //System.out.println("1: " + n1.group(1));
                    String num = n1.group(1).replace(".", ""); // remove o ponto
                    int area = Integer.parseInt(num);
                    in.close();
                    return area;
                }
            }
            in.close();
            return -1;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return -1;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return -1;
        }
    }
    
    // Funções para obter as moedas
    static String obtem_moeda_wiki(String pesquisa) {
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "><b>Moeda</b>";
            String er2 = "title=\"[^\"]+\">([^<]+)<\\/a>";
            Pattern p1 = Pattern.compile(er1);
            Pattern p2 = Pattern.compile(er2);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    while (in.hasNextLine()) {
                        linha = in.nextLine();
                        Matcher n2 = p2.matcher(linha);
                        if(n2.find()) {
                            in.close();
                            return n2.group(1);
                        }
                    }
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_moeda_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "title=\"Moeda[^\"]+\">([^<]+)</a>";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }     
    static String obtem_moeda(String pais) throws IOException { // está-se a priorizar a pesquisa da wikipedia só porque sim (ambas encontram perfeitamente
        String moedaWiki = obtem_moeda_wiki(pais);
        String moedaDbCity = obtem_moeda_dbcity(pais);

        if (moedaWiki != null && !moedaWiki.isBlank()) { 
            return moedaWiki;
        } else if (moedaDbCity != null && !moedaDbCity.isBlank()) { 
            return moedaDbCity;
        } else { 
            return "desconhecida";
        }
    }
    
    // Funções para obter os hinos
    static String obtem_hino_wiki(String pesquisa){
        String link = "https://pt.wikipedia.org/wiki/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_wiki.html");

            String linha;
            String er1 = "<b>Hino:</b>(?:.+)data-mwtitle=\"(?:[^\\.]+)\\.(?:.+)<source src=\"([^\"]+)\"";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_wiki.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1);
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: Wikipedia
            System.out.println("\n");
            System.out.println("Erro acessando a Wikipedia com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_hino_dbcity(String pesquisa){
        String link = "https://pt.db-city.com/";
        try {
            HttpRequestFunctions.httpRequest1(link, pesquisa, "./sources/" + pesquisa + "_dbcity.html");

            String linha;
            String er1 = "<th>Hino\\snacional\\s<span(?:.+)<td>(?:[^<]+)<br/><audio\\scontrols><source\\ssrc=\"([^\"]+)\"";
            Pattern p1 = Pattern.compile(er1);

            Scanner in = new Scanner(new FileInputStream("./sources/" + pesquisa + "_dbcity.html"));

            while (in.hasNextLine()) {
                linha = in.nextLine();
                Matcher n1 = p1.matcher(linha);
                if(n1.find()) { 
                    in.close();
                    return n1.group(1); // link do hino
                }
            }
            in.close();
            return null;
        } catch (FileNotFoundException e) { // 'pesquisa' nao existe no URL do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa);
            return null;
        } catch (IOException e) { // outros erros à cerca do site: DBCity
            System.out.println("\n");
            System.out.println("Erro acessando a DBCity com " + pesquisa + ": " + e.getMessage());
            return null;
        }
    }
    static String obtem_hino(String pais) throws IOException { // está-se a priorizar a pesquisa do dbcity pois o mesmo tem os títulos muito mais limpos e simples, mas na wikipedia vai buscar tambem 100%
        String hinoWiki = obtem_hino_wiki(pais);
        String hinoDbCity = obtem_hino_dbcity(pais);

        if (hinoDbCity != null && !hinoDbCity.isBlank()) { 
            return hinoDbCity;
        } else if (hinoWiki != null && !hinoWiki.isBlank()) { 
            return hinoWiki;
        } else { 
            return "desconhecida";
        }
    }
}

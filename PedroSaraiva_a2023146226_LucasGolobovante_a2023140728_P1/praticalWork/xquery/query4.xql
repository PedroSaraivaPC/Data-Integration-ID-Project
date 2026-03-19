xquery version "1.0";

<html>
    <body>
        <h1>Países de fronteira d(a/e/o) {doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/xquery/input.xml")/input/text()}</h1>
        <ul>
            {
            for $x in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")//pais
            let $v := doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/xquery/input.xml")//input
            let $lista := $x/paises_fronteira/pais_fronteira
            where contains($x/@nome, $v)
            return 
                for $front in $lista
                    order by $front
                    return <li>{$front/text()}</li>
            }
        </ul>
    </body>
</html>

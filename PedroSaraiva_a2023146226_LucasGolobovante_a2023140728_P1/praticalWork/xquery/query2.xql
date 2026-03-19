xquery version "1.0";

for $x in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")//pais
let $v := doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/xquery/input.xml")//input
let $lista := $x/cidades_importantes/cidade
where contains($x/@nome, $v)
order by $x/@nome
return (
  "&#10;País: ", data($x/@nome),
  "&#10;&#10;Cidades importantes: &#10;",
                for $i in $lista
                order by $i
                return (data($i), "&#10;")
)
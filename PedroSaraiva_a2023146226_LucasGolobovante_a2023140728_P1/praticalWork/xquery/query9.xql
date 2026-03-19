xquery version "1.0";

let $paises := doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")//pais
let $ordenados := 
  for $p in $paises
  let $numFronteiras := count($p/paises_fronteira/pais_fronteira)
  order by $numFronteiras descending
  return 
    <resultado>
      <nome>{data($p/@nome)}</nome>
      <numeroDeFronteiras>{$numFronteiras}</numeroDeFronteiras>
    </resultado>
return (
  "Top 3 países com mais países de fronteira:&#xa;&#xa;",
  for $r at $posicao in $ordenados
  where $posicao <= 3
  return concat($posicao, ". ", $r/nome, " - ", $r/numeroDeFronteiras, " países de fronteira &#10;")
)
xquery version "1.0";

for $x in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")
let $paises := $x//pais
let $totalPaises := count($paises)
let $totalContinentes := count(distinct-values($paises/continente))
let $populacaoTotal := sum($paises/habitantes)
let $populacaoMedia := $populacaoTotal div $totalPaises
return (
  "&#10;ESTATÍSTICAS GLOBAIS DOS PAÍSES: &#10;&#10;",
  "Total de países: ",$totalPaises,"&#10;",
  "Total de continentes: ",$totalContinentes,"&#10;",
  "População total: ", $populacaoTotal,"&#10;",
  "População média:", $populacaoMedia
)
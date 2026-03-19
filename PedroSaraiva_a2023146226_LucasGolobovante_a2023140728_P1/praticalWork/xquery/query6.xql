xquery version "1.0";

<paisesPorContinente>
{
let $x := doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")
let $continentes := distinct-values($x//continente)
for $c in $continentes
let $paises := $x//pais[continente = $c]
order by $c
return 
  <continente nome="{$c}">
  <numeroPaisesPorContinente valor="{count($paises)}"/>
  {
    for $p in $paises
    return <pais>{data($p/@nome)}</pais>
  }
  </continente>
}
</paisesPorContinente>

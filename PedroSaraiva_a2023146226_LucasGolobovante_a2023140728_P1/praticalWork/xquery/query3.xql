xquery version "1.0";

let $top5 := (
  for $x in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")//pais
  let $n_habit := xs:integer($x/habitantes)
  order by $n_habit descending
  return 
    <pais nome="{data($x/@nome)}">
      <habitantes>{$n_habit}</habitantes>
    </pais>
)[position() <= 5]

return
  <paises>{ $top5 }</paises>






(:
COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

ESTE CÓDIGO COMENTADO ESTÁ MAL E O QUE NÃO ESTÁ COMENTADO (EM CIMA), ESTÁ BEM

xquery version "1.0";

<paises>
{
for $x in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/a2023146226_PedroSaraiva_a2023140728_LucasGolobovante_P1/trabalhoPratico/paises.xml")//pais
let $n_habit := number($x/habitantes)
order by $n_habit descending
return <pais nome="{data($x/@nome)}">
        <habitantes>{$n_habit}</habitantes>
    </pais>
}[position() <= 5]</paises>

^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  COMENTARIO  
:)


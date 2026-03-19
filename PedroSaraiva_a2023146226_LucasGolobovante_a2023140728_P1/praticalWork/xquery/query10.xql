xquery version "1.0";

<informacaoJunta>
{
for $continente in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/xquery/outputs/paisesPorContinente.xml")//continente,
    $pais in $continente/pais
let $numHabitantes := doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")//pais[@nome = $pais]/habitantes
let $existe := doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/xquery/outputs/5maisPopulosos.xml")//pais[@nome = $pais]
where not($existe)
order by xs:integer($numHabitantes) descending
return
    <pais>
        <nome>{data($pais)}</nome>
        <habitantes>{data($numHabitantes)}</habitantes>
        <continente>{data($continente/@nome)}</continente>
    </pais>
}
</informacaoJunta>
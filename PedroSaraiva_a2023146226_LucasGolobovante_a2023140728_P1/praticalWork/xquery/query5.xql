xquery version "1.0";

<html>
    <body>
        <center>
            <h1>Bandeiras dos países</h1>
            <table border="1">
                <tr>
                    <th>País</th>
                    <th>Bandeira</th>
                    <th>Hino</th>
                </tr>
                {
                for $x in doc("C:/Users/Pedro/Desktop/ISEC/2o_Ano/2o_semestre/ID/TrabalhoPratico/PedroSaraiva_a2023146226_LucasGolobovante_a2023140728_P1/trabalhoPratico/paises.xml")//pais
                order by $x/@nome
                return 
                    <tr>
                        <td>{data($x/@nome)}</td>
                        <td><img src="{data($x/img_bandeira)}" width="150"/></td>
                        <td>
                            <audio controls="controls">
                                <source src="{data($x/hino)}" type="audio/mpeg"/>
                                O teu browser não suporta áudio.
                            </audio>
                        </td>
                    </tr>
                }
            </table>
        </center>
    </body>
</html>

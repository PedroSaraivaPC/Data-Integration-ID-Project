<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" indent="yes"/>
  
  <!-- Definir chave para agrupar por continente -->
  <xsl:key name="paisPorContinente" match="pais" use="continente"/>

  <xsl:template match="lista">
    <paisesPorContinente>
      <!-- Selecionar o primeiro país de cada continente (único) -->
      <xsl:for-each select="pais[generate-id() = generate-id(key('paisPorContinente', continente)[1])]">
        <continente nome="{continente}">
          <!-- Listar todos os países com o mesmo continente -->
          <xsl:for-each select="key('paisPorContinente', continente)">
            <pais>
              <xsl:value-of select="@nome"/>
            </pais>
          </xsl:for-each>
        </continente>
      </xsl:for-each>
    </paisesPorContinente>
  </xsl:template>

</xsl:stylesheet>

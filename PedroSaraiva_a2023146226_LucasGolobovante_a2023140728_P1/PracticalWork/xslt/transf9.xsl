<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="text" encoding="UTF-8"/>

  <xsl:template match="lista">
    <xsl:text>Top 3 países com mais países de fronteira:&#xa;&#xa;</xsl:text>

    <xsl:for-each select="pais">
      <!-- Ordenar diretamente pela contagem de países de fronteira -->
      <xsl:sort select="count(paises_fronteira/pais_fronteira)" data-type="number" order="descending"/>

      <!-- Só mostrar os 3 primeiros -->
      <xsl:if test="position() &lt;= 3">
        <xsl:number value="position()"/>. 
        <xsl:value-of select="@nome"/>
        <xsl:text> - </xsl:text>
        <xsl:value-of select="count(paises_fronteira/pais_fronteira)"/>
        <xsl:text> países de fronteira&#xa;</xsl:text>
      </xsl:if>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
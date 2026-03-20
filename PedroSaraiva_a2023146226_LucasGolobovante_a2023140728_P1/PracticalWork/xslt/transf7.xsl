<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="text" encoding="UTF-8"/>

  <xsl:key name="continenteKey" match="pais" use="continente" />

  <xsl:template match="lista">
    <xsl:text>ESTATÍSTICAS GLOBAIS DOS PAÍSES&#xa;&#xa;</xsl:text>

    <!-- Total de países -->
    <xsl:text>Total de países: </xsl:text>
    <xsl:value-of select="count(pais)"/>
    <xsl:text>&#xa;</xsl:text>

    <!-- Total de continentes -->
    <xsl:text>Total de continentes: </xsl:text>
    <xsl:value-of select="count(pais[generate-id() = generate-id(key('continenteKey', continente)[1])])"/>
    <xsl:text>&#xa;</xsl:text>

    <!-- População total -->
    <xsl:text>População total: </xsl:text>
    <xsl:value-of select="format-number(sum(pais/habitantes), '#,###')"/>
    <xsl:text>&#xa;</xsl:text>

    <!-- População média -->
    <xsl:text>População média: </xsl:text>
    <xsl:value-of select="format-number(sum(pais/habitantes) div count(pais), '#,###')"/>
    <xsl:text>&#xa;</xsl:text>
  </xsl:template>

</xsl:stylesheet>

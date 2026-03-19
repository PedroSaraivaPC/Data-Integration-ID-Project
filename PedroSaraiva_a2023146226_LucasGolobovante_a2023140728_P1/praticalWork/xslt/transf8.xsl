<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html" encoding="UTF-8"/>
  <xsl:param name="letraInicial"/>

  <xsl:template match="lista">
    <html>
      <body>
        <h1>Países que começam por '<xsl:value-of select="$letraInicial"/>'</h1>
        <ul>
          <xsl:for-each select="pais[starts-with(translate(@nome, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), translate($letraInicial, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]">
            <xsl:sort select="@nome"/>
            <li><xsl:value-of select="@nome"/></li>
          </xsl:for-each>
        </ul>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>

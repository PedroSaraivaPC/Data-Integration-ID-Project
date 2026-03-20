<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes"/>
	
	<xsl:template match="lista">
		<paises>
			<xsl:for-each select="pais">
				<xsl:sort select="habitantes" data-type="number" order="descending"/>
				  <xsl:if test="position() &lt;= 5"> <!-- '&lt;' é a mesma coisa que '<' -->
				  	<pais>
						<nome>
							<xsl:value-of select="@nome"/>
						</nome>
						<continente>
							<xsl:value-of select="continente/text()"/>
						</continente>
						<populacao>
							<xsl:value-of select="habitantes"/>
						</populacao>	
					</pais>
				  </xsl:if>
			</xsl:for-each>
		</paises>
	</xsl:template>
	
</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8" ?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Fri May 09 18:37:59 BST 2025 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="paisNome"/>

	<xsl:output method="text" indent="yes"/>
	
	<xsl:template match="lista">
		<xsl:for-each select="pais[contains(@nome, $paisNome)]">
		<xsl:text>&#xa;Lista de cidades importantes d(a/e/o) </xsl:text><xsl:value-of select="@nome"/><xsl:text>: &#xa;
</xsl:text>
			<xsl:for-each select="cidades_importantes/cidade">
				<!-- <xsl:sort select="text()"/>
-->
				<xsl:text>&#x9;</xsl:text>
					<xsl:value-of select="text()"/>
				<xsl:text>&#xa;</xsl:text>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>



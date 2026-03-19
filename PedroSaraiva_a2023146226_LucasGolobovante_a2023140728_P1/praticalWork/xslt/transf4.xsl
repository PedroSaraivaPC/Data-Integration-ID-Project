<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Fri May 09 18:15:17 BST 2025 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="paisNome"/>

	<xsl:output method="html"/>
	
	<xsl:template match="lista">
		<html>
			<body>
				<h1>Países de fronteira d(a/e/o) <xsl:value-of select="pais[contains(@nome, $paisNome)]/@nome"/></h1>
				<ul>
					<xsl:for-each select="pais[contains(@nome, $paisNome)]/paises_fronteira/pais_fronteira">
						<xsl:sort select="."/>
						<li><xsl:value-of select="text()"/></li>
					</xsl:for-each>
				</ul>
			</body>
		</html>
	</xsl:template>
	
</xsl:stylesheet>



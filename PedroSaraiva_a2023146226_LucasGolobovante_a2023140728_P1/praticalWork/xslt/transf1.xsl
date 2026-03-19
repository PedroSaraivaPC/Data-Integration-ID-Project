<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) at Fri May 09 17:54:28 BST 2025 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html"/>
	
	<xsl:template match="lista">
		<html>
			<body>
				<center>
					<h2>Listagem de países</h2>
					<table border="1">
						<tr><th>País</th><th>Bandeira</th></tr>
							<xsl:apply-templates select="pais">
								<xsl:sort select="@nome"/>
							</xsl:apply-templates>
					</table>
				</center>
			</body>
		</html>
	</xsl:template>

	<xsl:template match ="pais">
			<tr><td><xsl:value-of select="@nome"/></td>
				<td><img src="{img_bandeira/text()}" width="150"/></td>
			</tr>
	</xsl:template>	
	
</xsl:stylesheet>

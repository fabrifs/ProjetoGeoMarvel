<xsl:stylesheet
   version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
   xmlns="http://www.w3.org/2000/svg">
   
  <xsl:output indent="yes" cdata-section-elements="style" />

  <xsl:template match="/">
    <gpx xmlns="http://www.topografix.com/GPX/1/1">
        <xsl:for-each select="/posicaoes/posicao">
		
             <xsl:variable name="tempo" select="timestamp"/>
             <xsl:variable name="lat" select="lat"/>
			 <xsl:variable name="lon" select="lon"/>
			 <xsl:variable name="nome" select="login"/>		 
			 <wpt lat="{$lat}" lon="{$lon}">			 
				<ele>0</ele>
				<time>2015-11-23 17:32:57</time>
				<type><![CDATA[Dot]]></type>
				<sym>Dot</sym>
				<name>Danilo</name>
			</wpt>
       </xsl:for-each>
	   
	   
    </gpx>
  </xsl:template>

</xsl:stylesheet>
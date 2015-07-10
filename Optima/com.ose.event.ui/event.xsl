<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html>
			<head>
			</head>
			<body>
				<table border="1" frame="border" cellspacing="0" rules="all">
					<tr>
						<th>Entry</th>
						<th>Tick</th>
						<th>Timestamp</th>
						<th>Event</th>
						<th>From</th>
						<th>To</th>
						<th>File/Line</th>
						<th>Number</th>
						<th>Size</th>
						<th>Address</th>
						<th>Error Caller</th>
						<th>Error Code</th>
						<th>Extra Param</th>
						<th>From Exec Unit</th>
						<th>To Exec Unit</th>
						<th>Data</th>
					</tr>
					<xsl:for-each select="events/event">
						<tr>
							<td>
								<xsl:value-of select="reference"/>
							</td>
							<td>
								<xsl:value-of select="tick"/>:<xsl:value-of select="microTick"/>
							</td>
							<td>
								<!-- TODO: Calculate the date? -->
								<xsl:value-of select="seconds"/>
							</td>
							<td>
								<xsl:value-of select="@type"/>
							</td>
							<td>
								<xsl:value-of select="from"/>
							</td>
							<td>
								<xsl:value-of select="to"/>
							</td>
							<td>
								<xsl:if test="line &gt; 0">
									<xsl:value-of select="file"/>:<xsl:value-of select="line"/>
								</xsl:if>
							</td>
							<td>
								<xsl:value-of select="number"/>
							</td>
							<td>
								<xsl:value-of select="size"/>
							</td>
							<td>
								<xsl:value-of select="address"/>
							</td>
							<td>
								<xsl:choose>
									<xsl:when test='kernelCalled = "false"'>
										User
									</xsl:when>
									<xsl:when test='kernelCalled = "true"'>
										Kernel
									</xsl:when>
								</xsl:choose>
							</td>
							<td>
								<xsl:value-of select="error"/>
							</td>
							<td>
								<xsl:value-of select="extra"/>
							</td>
							<td>
								<xsl:value-of select="fromExecUnit"/>
							</td>
							<td>
								<xsl:value-of select="toExecUnit"/>
							</td>
							<td nowrap="true">
								<xsl:choose>
									<xsl:when test="data/struct">
										<xsl:apply-templates select="data/struct"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="data/binary"/>
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="struct">
		<xsl:value-of select="@name"/><br/>
		{<br/>
			<xsl:apply-templates/>
		}<br/>
	</xsl:template>

	<xsl:template match="member">
		<!-- NOTE: Output method "html" will escape member data automatically -->
		<xsl:value-of select="@name"/> = <xsl:value-of select="."/><br/>
	</xsl:template>

</xsl:stylesheet>

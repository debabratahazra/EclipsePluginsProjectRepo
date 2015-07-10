<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>

	<xsl:template match="/">
		<html>
			<head>
			</head>
			<body>
				<xsl:apply-templates select="reports/*"/>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="cpuReports">
		<table border="1" frame="border" cellspacing="0" rules="all">
			<tr>
				<th>Tick</th>
				<th>Interval</th>
				<th>Usage (pri &lt; <xsl:value-of select="@priorityLimit"/>)</th>
			</tr>
			<xsl:for-each select="cpuReport">
				<tr>
					<td>
						<xsl:value-of select="tick"/>:<xsl:value-of select="microTick"/>
					</td>
					<td>
						<xsl:value-of select="interval"/>
					</td>
					<td>
						<xsl:value-of select='format-number(sum div interval, "#0.0%")'/>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template match="cpuPriorityReports">
		<table border="1" frame="border" cellspacing="0" rules="all">
			<tr>
				<th>Tick</th>
				<th>Interval</th>
				<th>Interrupt</th>
				<xsl:call-template name="priorityUsageHeaders">
					<xsl:with-param name="n" select="0"/>
				</xsl:call-template>
				<th>Background</th>
			</tr>
			<xsl:for-each select="cpuPriorityReport">
				<tr>
					<td>
						<xsl:value-of select="tick"/>:<xsl:value-of select="microTick"/>
					</td>
					<td>
						<xsl:value-of select="interval"/>
					</td>
					<td>
						<xsl:value-of select='format-number(sumInterrupt div interval, "#0.0%")'/>
					</td>
					<xsl:for-each select="sumPrioritized">
						<td>
							<xsl:value-of select='format-number(sum div ../interval, "#0.0%")'/>
						</td>
					</xsl:for-each>
					<td>
						<xsl:value-of select='format-number(sumBackground div interval, "#0.0%")'/>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="priorityUsageHeaders">
		<xsl:param name="n"/>
		<xsl:if test="$n &lt; 32">
			<th>Priority <xsl:value-of select="$n"/></th>
			<xsl:call-template name="priorityUsageHeaders">
				<xsl:with-param name="n" select="$n + 1"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="cpuProcessReports">
		<xsl:variable name="maxProcessesPerReport" select="@maxProcessesPerReport"/>

		<table border="1" frame="border" cellspacing="0" rules="all">
			<tr>
				<th>Tick</th>
				<th>Interval</th>
				<th>Other</th>
				<xsl:call-template name="processUsageHeaders">
					<xsl:with-param name="n" select="$maxProcessesPerReport"/>
				</xsl:call-template>
			</tr>
			<xsl:for-each select="cpuProcessReport">
				<tr>
					<td>
						<xsl:value-of select="tick"/>:<xsl:value-of select="microTick"/>
					</td>
					<td>
						<xsl:value-of select="interval"/>
					</td>
					<td>
						<xsl:value-of select='format-number(sumOther div interval, "#0.0%")'/>
					</td>
					<xsl:for-each select="sumProcess">
						<td>
							<xsl:variable name="id" select="id"/>
							<xsl:value-of select="../../process[@id = $id]/@name"/>
							<xsl:text> </xsl:text>
							<xsl:value-of select="id"/>
						</td>
						<td>
							<xsl:value-of select='format-number(sum div ../interval, "#0.0%")'/>
						</td>
					</xsl:for-each>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="processUsageHeaders">
		<xsl:param name="n"/>
		<xsl:if test="$n">
			<th>Process</th>
			<th></th>
			<xsl:call-template name="processUsageHeaders">
				<xsl:with-param name="n" select="$n - 1"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="cpuBlockReports">
		<xsl:variable name="maxBlocksPerReport" select="@maxBlocksPerReport"/>

		<table border="1" frame="border" cellspacing="0" rules="all">
			<tr>
				<th>Tick</th>
				<th>Interval</th>
				<th>Other</th>
				<xsl:call-template name="blockUsageHeaders">
					<xsl:with-param name="n" select="$maxBlocksPerReport"/>
				</xsl:call-template>
			</tr>
			<xsl:for-each select="cpuBlockReport">
				<tr>
					<td>
						<xsl:value-of select="tick"/>:<xsl:value-of select="microTick"/>
					</td>
					<td>
						<xsl:value-of select="interval"/>
					</td>
					<td>
						<xsl:value-of select='format-number(sumOther div interval, "#0.0%")'/>
					</td>
					<xsl:for-each select="sumBlock">
						<td>
							<xsl:variable name="id" select="id"/>
							<xsl:value-of select="../../block[@id = $id]/@name"/>
							<xsl:text> </xsl:text>
							<xsl:value-of select="id"/>
						</td>
						<td>
							<xsl:value-of select='format-number(sum div ../interval, "#0.0%")'/>
						</td>
					</xsl:for-each>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="blockUsageHeaders">
		<xsl:param name="n"/>
		<xsl:if test="$n">
			<th>Block</th>
			<th></th>
			<xsl:call-template name="blockUsageHeaders">
				<xsl:with-param name="n" select="$n - 1"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template match="userReports">
		<xsl:variable name="maxValuesPerReport" select="@maxValuesPerReport"/>
		<xsl:variable name="maxMin" select="@maxMin"/>

		<table border="1" frame="border" cellspacing="0" rules="all">
			<tr>
				<th>Tick</th>
				<th>Interval</th>
				<th>Other<xsl:if test="$maxMin">[Min,Max]</xsl:if></th>
				<xsl:choose>
					<xsl:when test="process">
						<xsl:call-template name="processUsageHeaders">
							<xsl:with-param name="n" select="@maxValuesPerReport"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="idUsageHeaders">
							<xsl:with-param name="n" select="@maxValuesPerReport"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</tr>
			<xsl:for-each select="userReport">
				<tr>
					<td>
						<xsl:value-of select="tick"/>:<xsl:value-of select="microTick"/>
					</td>
					<td>
						<xsl:value-of select="interval"/>
					</td>
					<td>
						<xsl:value-of select="sumOther"/>
						<xsl:if test="$maxMin">
							[<xsl:value-of	select="minOther"/>,<xsl:value-of select="maxOther"/>]
						</xsl:if>
					</td>
					<xsl:for-each select="value">
						<td>
							<xsl:variable name="id" select="id"/>
							<xsl:value-of select="../../process[@id = $id]/@name"/>
							<xsl:text> </xsl:text>
							<xsl:value-of select="id"/>
						</td>
						<td nowrap="true">
							<xsl:value-of select="sum"/>
							<xsl:if test="$maxMin">
								[<xsl:value-of	select="min"/>,<xsl:value-of select="max"/>]
							</xsl:if>
						</td>
					</xsl:for-each>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="idUsageHeaders">
		<xsl:param name="n"/>
		<xsl:if test="$n">
			<th>ID</th>
			<th></th>
			<xsl:call-template name="idUsageHeaders">
				<xsl:with-param name="n" select="$n - 1"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>

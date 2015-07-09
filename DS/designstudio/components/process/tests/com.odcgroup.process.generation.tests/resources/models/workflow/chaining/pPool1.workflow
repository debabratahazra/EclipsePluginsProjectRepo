<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_JcWmsCu4Ed2OvOCcvGjakA" name="pPool1" description="test pool process" displayName="process" filename="modelParsingPool">
    <pools xmi:type="process:Pool" xmi:id="_JcWmsSu4Ed2OvOCcvGjakA" ID="pool1" name="pool1">
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmsiu4Ed2OvOCcvGjakA" name="orga:assigneeA1"/>
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmsyu4Ed2OvOCcvGjakA" name="orga:assigneeA2"/>
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmtCu4Ed2OvOCcvGjakA" name="orga:assigneeA3"/>
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmtSu4Ed2OvOCcvGjakA" name="orga:assigneeA4"/>
      <start xmi:type="process:StartEvent" xmi:id="_JcWmtiu4Ed2OvOCcvGjakA" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_JcWmtyu4Ed2OvOCcvGjakA" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_JcWmuCu4Ed2OvOCcvGjakA" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_JcWmuSu4Ed2OvOCcvGjakA" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_JcWmuiu4Ed2OvOCcvGjakA" URI="http://testB"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_JcWmuyu4Ed2OvOCcvGjakA" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmvCu4Ed2OvOCcvGjakA" name="orga:assigneeA1"/>
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmvSu4Ed2OvOCcvGjakA" name="orga:assigneeA2"/>
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmviu4Ed2OvOCcvGjakA" name="orga:assigneeA3"/>
      <assignee xmi:type="process:Assignee" xmi:id="_JcWmvyu4Ed2OvOCcvGjakA" name="orga:assigneeA4"/>
      <tasks xmi:type="process:UserTask" xmi:id="_JcWmwCu4Ed2OvOCcvGjakA" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_JcWmwSu4Ed2OvOCcvGjakA" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_JcWmwiu4Ed2OvOCcvGjakA" source="_JcWmtiu4Ed2OvOCcvGjakA" target="_JcWmtyu4Ed2OvOCcvGjakA"/>
    <transitions xmi:type="process:Flow" xmi:id="_JcWmwyu4Ed2OvOCcvGjakA" source="_JcWmtyu4Ed2OvOCcvGjakA" target="_JcWmuSu4Ed2OvOCcvGjakA"/>
    <transitions xmi:type="process:Flow" xmi:id="_JcWmxCu4Ed2OvOCcvGjakA" source="_JcWmuSu4Ed2OvOCcvGjakA" target="_JcWmwCu4Ed2OvOCcvGjakA"/>
    <transitions xmi:type="process:Flow" xmi:id="_JcWmxSu4Ed2OvOCcvGjakA" source="_JcWmwCu4Ed2OvOCcvGjakA" target="_JcWmuSu4Ed2OvOCcvGjakA"/>
  </process:Process>
  <notation:Diagram xmi:id="_JcgXsCu4Ed2OvOCcvGjakA" type="Process" element="_JcWmsCu4Ed2OvOCcvGjakA" name="pPool1.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_JtVpACu4Ed2OvOCcvGjakA" type="1001" element="_JcWmsSu4Ed2OvOCcvGjakA">
      <children xmi:type="notation:Node" xmi:id="_JtVpAyu4Ed2OvOCcvGjakA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_JtfaACu4Ed2OvOCcvGjakA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_Ju1d0Cu4Ed2OvOCcvGjakA" type="2001" element="_JcWmtyu4Ed2OvOCcvGjakA">
          <children xmi:type="notation:Node" xmi:id="_Ju1d0yu4Ed2OvOCcvGjakA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_Ju1d0Su4Ed2OvOCcvGjakA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_Ju1d0iu4Ed2OvOCcvGjakA" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_Ju1d1Cu4Ed2OvOCcvGjakA" type="2001" element="_JcWmuSu4Ed2OvOCcvGjakA">
          <children xmi:type="notation:Node" xmi:id="_Ju1d1yu4Ed2OvOCcvGjakA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_Ju1d1Su4Ed2OvOCcvGjakA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_Ju1d1iu4Ed2OvOCcvGjakA" x="55" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_Ju1d2Cu4Ed2OvOCcvGjakA" type="2007" element="_JcWmtiu4Ed2OvOCcvGjakA">
          <children xmi:type="notation:Node" xmi:id="_Ju1d2yu4Ed2OvOCcvGjakA" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_Ju1d3Cu4Ed2OvOCcvGjakA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_Ju1d2Su4Ed2OvOCcvGjakA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_Ju1d2iu4Ed2OvOCcvGjakA" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_JtfaASu4Ed2OvOCcvGjakA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_JtfaAiu4Ed2OvOCcvGjakA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_JtVpASu4Ed2OvOCcvGjakA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtVpAiu4Ed2OvOCcvGjakA" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_JtfaAyu4Ed2OvOCcvGjakA" type="1001" element="_JcWmuyu4Ed2OvOCcvGjakA">
      <children xmi:type="notation:Node" xmi:id="_JtfaBiu4Ed2OvOCcvGjakA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_JtfaByu4Ed2OvOCcvGjakA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_Jv3_oCu4Ed2OvOCcvGjakA" type="2001" element="_JcWmwCu4Ed2OvOCcvGjakA">
          <children xmi:type="notation:Node" xmi:id="_Jv3_oyu4Ed2OvOCcvGjakA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_Jv3_oSu4Ed2OvOCcvGjakA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_Jv3_oiu4Ed2OvOCcvGjakA" x="241" y="84"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_JtfaCCu4Ed2OvOCcvGjakA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_JtfaCSu4Ed2OvOCcvGjakA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_JtfaBCu4Ed2OvOCcvGjakA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtfaBSu4Ed2OvOCcvGjakA" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_JcgXsSu4Ed2OvOCcvGjakA"/>
    <edges xmi:type="notation:Edge" xmi:id="_JwK6kCu4Ed2OvOCcvGjakA" type="3001" element="_JcWmwiu4Ed2OvOCcvGjakA" source="_Ju1d2Cu4Ed2OvOCcvGjakA" target="_Ju1d0Cu4Ed2OvOCcvGjakA">
      <children xmi:type="notation:Node" xmi:id="_JwK6lCu4Ed2OvOCcvGjakA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_JwK6lSu4Ed2OvOCcvGjakA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_JwK6kSu4Ed2OvOCcvGjakA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_JwK6kiu4Ed2OvOCcvGjakA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_JwK6kyu4Ed2OvOCcvGjakA" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_JwxXgCu4Ed2OvOCcvGjakA" type="3001" element="_JcWmwyu4Ed2OvOCcvGjakA" source="_Ju1d0Cu4Ed2OvOCcvGjakA" target="_Ju1d1Cu4Ed2OvOCcvGjakA">
      <children xmi:type="notation:Node" xmi:id="_JwxXhCu4Ed2OvOCcvGjakA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_JwxXhSu4Ed2OvOCcvGjakA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_JwxXgSu4Ed2OvOCcvGjakA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_JwxXgiu4Ed2OvOCcvGjakA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_JwxXgyu4Ed2OvOCcvGjakA" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_JwxXhiu4Ed2OvOCcvGjakA" type="3001" element="_JcWmxCu4Ed2OvOCcvGjakA" source="_Ju1d1Cu4Ed2OvOCcvGjakA" target="_Jv3_oCu4Ed2OvOCcvGjakA">
      <children xmi:type="notation:Node" xmi:id="_JwxXiiu4Ed2OvOCcvGjakA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_JwxXiyu4Ed2OvOCcvGjakA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_JwxXhyu4Ed2OvOCcvGjakA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_JwxXiCu4Ed2OvOCcvGjakA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_JwxXiSu4Ed2OvOCcvGjakA" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_Jw7IgCu4Ed2OvOCcvGjakA" type="3001" element="_JcWmxSu4Ed2OvOCcvGjakA" source="_Jv3_oCu4Ed2OvOCcvGjakA" target="_Ju1d1Cu4Ed2OvOCcvGjakA">
      <children xmi:type="notation:Node" xmi:id="_Jw7IhCu4Ed2OvOCcvGjakA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_Jw7IhSu4Ed2OvOCcvGjakA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_Jw7IgSu4Ed2OvOCcvGjakA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_Jw7Igiu4Ed2OvOCcvGjakA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_Jw7Igyu4Ed2OvOCcvGjakA" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

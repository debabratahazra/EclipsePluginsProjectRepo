<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_9Q3fIC_GEd25z6sLxJOywQ" name="p38" description="test process - 38" displayName="process p38" filename="modelParsing38service">
    <pools xmi:type="process:Pool" xmi:id="_9Q3fIS_GEd25z6sLxJOywQ" ID="Pool1" name="Pool (1)">
      <assigneeByService xmi:type="process:Service" xmi:id="_9Q3fIi_GEd25z6sLxJOywQ" name="assignee-list-2">
        <property xmi:type="process:Property" xmi:id="_9Q3fIy_GEd25z6sLxJOywQ" name="assigneeList" value="orga:assignee1,orga:assignee2,orga:assignee3"/>
      </assigneeByService>
      <start xmi:type="process:StartEvent" xmi:id="_9Q3fJC_GEd25z6sLxJOywQ" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_9Q3fJS_GEd25z6sLxJOywQ" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_9Q3fJi_GEd25z6sLxJOywQ" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_9Q3fJy_GEd25z6sLxJOywQ" ID="Pool2" name="Pool (2)">
      <assigneeByService xmi:type="process:Service" xmi:id="_9Q3fKC_GEd25z6sLxJOywQ" name="exception-assignee"/>
      <end xmi:type="process:EndEvent" xmi:id="_9Q3fKS_GEd25z6sLxJOywQ" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_9Q3fKi_GEd25z6sLxJOywQ" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_9Q3fKy_GEd25z6sLxJOywQ" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_9Q3fLC_GEd25z6sLxJOywQ" source="_9Q3fJC_GEd25z6sLxJOywQ" target="_9Q3fJS_GEd25z6sLxJOywQ"/>
    <transitions xmi:type="process:Flow" xmi:id="_9Q3fLS_GEd25z6sLxJOywQ" source="_9Q3fKi_GEd25z6sLxJOywQ" target="_9Q3fKS_GEd25z6sLxJOywQ"/>
    <transitions xmi:type="process:Flow" xmi:id="_9Q3fLi_GEd25z6sLxJOywQ" source="_9Q3fJS_GEd25z6sLxJOywQ" target="_9Q3fKi_GEd25z6sLxJOywQ"/>
  </process:Process>
  <notation:Diagram xmi:id="_9Q3fLy_GEd25z6sLxJOywQ" type="Process" element="_9Q3fIC_GEd25z6sLxJOywQ" name="p38.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_9lIe8C_GEd25z6sLxJOywQ" type="1001" element="_9Q3fIS_GEd25z6sLxJOywQ">
      <children xmi:type="notation:Node" xmi:id="_9lIe8y_GEd25z6sLxJOywQ" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_9lSP8C_GEd25z6sLxJOywQ" type="5001">
        <children xmi:type="notation:Node" xmi:id="_9mnssC_GEd25z6sLxJOywQ" type="2001" element="_9Q3fJS_GEd25z6sLxJOywQ">
          <children xmi:type="notation:Node" xmi:id="_9mnssy_GEd25z6sLxJOywQ" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_9mnssS_GEd25z6sLxJOywQ" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_9mnssi_GEd25z6sLxJOywQ" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_9mnstC_GEd25z6sLxJOywQ" type="2007" element="_9Q3fJC_GEd25z6sLxJOywQ">
          <children xmi:type="notation:Node" xmi:id="_9mnsty_GEd25z6sLxJOywQ" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_9mnsuC_GEd25z6sLxJOywQ" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_9mnstS_GEd25z6sLxJOywQ"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_9mnsti_GEd25z6sLxJOywQ" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_9lSP8S_GEd25z6sLxJOywQ"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_9lSP8i_GEd25z6sLxJOywQ"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_9lIe8S_GEd25z6sLxJOywQ" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_9lIe8i_GEd25z6sLxJOywQ" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_9lSP8y_GEd25z6sLxJOywQ" type="1001" element="_9Q3fJy_GEd25z6sLxJOywQ">
      <children xmi:type="notation:Node" xmi:id="_9lSP9i_GEd25z6sLxJOywQ" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_9lSP9y_GEd25z6sLxJOywQ" type="5001">
        <children xmi:type="notation:Node" xmi:id="_9nhEkC_GEd25z6sLxJOywQ" type="2001" element="_9Q3fKi_GEd25z6sLxJOywQ">
          <children xmi:type="notation:Node" xmi:id="_9nhEky_GEd25z6sLxJOywQ" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_9nhEkS_GEd25z6sLxJOywQ" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_9nhEki_GEd25z6sLxJOywQ" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_9nhElC_GEd25z6sLxJOywQ" type="2008" element="_9Q3fKS_GEd25z6sLxJOywQ">
          <children xmi:type="notation:Node" xmi:id="_9nhEly_GEd25z6sLxJOywQ" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_9nhEmC_GEd25z6sLxJOywQ" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_9nhElS_GEd25z6sLxJOywQ"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_9nhEli_GEd25z6sLxJOywQ" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_9lSP-C_GEd25z6sLxJOywQ"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_9lSP-S_GEd25z6sLxJOywQ"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_9lSP9C_GEd25z6sLxJOywQ" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_9lSP9S_GEd25z6sLxJOywQ" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_9Q3fMC_GEd25z6sLxJOywQ"/>
    <edges xmi:type="notation:Edge" xmi:id="_9oG6cC_GEd25z6sLxJOywQ" type="3001" element="_9Q3fLC_GEd25z6sLxJOywQ" source="_9mnstC_GEd25z6sLxJOywQ" target="_9mnssC_GEd25z6sLxJOywQ">
      <children xmi:type="notation:Node" xmi:id="_9oG6dC_GEd25z6sLxJOywQ" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_9oG6dS_GEd25z6sLxJOywQ" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_9oG6cS_GEd25z6sLxJOywQ" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_9oG6ci_GEd25z6sLxJOywQ"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_9oG6cy_GEd25z6sLxJOywQ" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_9o3IYC_GEd25z6sLxJOywQ" type="3001" element="_9Q3fLS_GEd25z6sLxJOywQ" source="_9nhEkC_GEd25z6sLxJOywQ" target="_9nhElC_GEd25z6sLxJOywQ">
      <children xmi:type="notation:Node" xmi:id="_9o3IZC_GEd25z6sLxJOywQ" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_9o3IZS_GEd25z6sLxJOywQ" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_9o3IYS_GEd25z6sLxJOywQ" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_9o3IYi_GEd25z6sLxJOywQ"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_9o3IYy_GEd25z6sLxJOywQ" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_9o3IZi_GEd25z6sLxJOywQ" type="3001" element="_9Q3fLi_GEd25z6sLxJOywQ" source="_9mnssC_GEd25z6sLxJOywQ" target="_9nhEkC_GEd25z6sLxJOywQ">
      <children xmi:type="notation:Node" xmi:id="_9pASUy_GEd25z6sLxJOywQ" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_9pASVC_GEd25z6sLxJOywQ" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_9pASUC_GEd25z6sLxJOywQ" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_9pASUS_GEd25z6sLxJOywQ"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_9pASUi_GEd25z6sLxJOywQ" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

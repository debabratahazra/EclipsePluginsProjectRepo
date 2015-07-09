<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_OGFMcDXgEd22A_2b3pOiPw" name="p14" description="test process" displayName="process p14" filename="modelParsing14service">
    <pools xmi:type="process:Pool" xmi:id="_OGFMcTXgEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_OGFMcjXgEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_OGFMczXgEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:ServiceTask" xmi:id="_OGFMdDXgEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <service xmi:type="process:Service" xmi:id="_OGFMdTXgEd22A_2b3pOiPw" name="inc-application">
          <property xmi:type="process:Property" xmi:id="_OGFMdjXgEd22A_2b3pOiPw" name="increment" value="2"/>
        </service>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_OGFMdzXgEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_OGFMeDXgEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <start xmi:type="process:StartEvent" xmi:id="_OGFMeTXgEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:ServiceTask" xmi:id="_OGFMejXgEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="true">
        <service xmi:type="process:Service" xmi:id="_OGFMezXgEd22A_2b3pOiPw" name="inc-application">
          <property xmi:type="process:Property" xmi:id="_OGFMfDXgEd22A_2b3pOiPw" name="increment" value="5"/>
        </service>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_OGFMfTXgEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_OGFMfjXgEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <start xmi:type="process:StartEvent" xmi:id="_OGFMfzXgEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_OGFMgDXgEd22A_2b3pOiPw" ID="EndEventc" name="End Event(activity)"/>
      <tasks xmi:type="process:ServiceTask" xmi:id="_OGFMgTXgEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="true">
        <service xmi:type="process:Service" xmi:id="_OGFMgjXgEd22A_2b3pOiPw" name="inc-application">
          <property xmi:type="process:Property" xmi:id="_OGFMgzXgEd22A_2b3pOiPw" name="increment" value="1"/>
        </service>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_OGFMhDXgEd22A_2b3pOiPw" source="_OGFMczXgEd22A_2b3pOiPw" target="_OGFMdDXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGFMhTXgEd22A_2b3pOiPw" source="_OGFMeTXgEd22A_2b3pOiPw" target="_OGFMejXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGFMhjXgEd22A_2b3pOiPw" source="_OGFMfzXgEd22A_2b3pOiPw" target="_OGFMgTXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGFMhzXgEd22A_2b3pOiPw" source="_OGFMgTXgEd22A_2b3pOiPw" target="_OGFMgDXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGFMiDXgEd22A_2b3pOiPw" source="_OGFMdDXgEd22A_2b3pOiPw" target="_OGFMejXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGFMiTXgEd22A_2b3pOiPw" source="_OGFMejXgEd22A_2b3pOiPw" target="_OGFMgTXgEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_OGFMijXgEd22A_2b3pOiPw" type="Process" element="_OGFMcDXgEd22A_2b3pOiPw" name="p14.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_OHIVUDXgEd22A_2b3pOiPw" type="1001" element="_OGFMcTXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHIVUzXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_OHIVVDXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_OHIVZTXgEd22A_2b3pOiPw" type="2002" element="_OGFMdDXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OHbQQDXgEd22A_2b3pOiPw" type="4002"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OHIVZjXgEd22A_2b3pOiPw" fillColor="7055523" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHIVZzXgEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OHbQQTXgEd22A_2b3pOiPw" type="2007" element="_OGFMczXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OHbQRDXgEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OHbQRTXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OHbQQjXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHbQQzXgEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_OHIVVTXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_OHIVVjXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_OHIVUTXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHIVUjXgEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_OHIVVzXgEd22A_2b3pOiPw" type="1001" element="_OGFMdzXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHIVWjXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_OHIVWzXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_OJDn8DXgEd22A_2b3pOiPw" type="2002" element="_OGFMejXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OJNY8DXgEd22A_2b3pOiPw" type="4002"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OJDn8TXgEd22A_2b3pOiPw" fillColor="7055523" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OJDn8jXgEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OJNY8TXgEd22A_2b3pOiPw" type="2007" element="_OGFMeTXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OJNY9DXgEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OJNY9TXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OJNY8jXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OJNY8zXgEd22A_2b3pOiPw" x="241" y="152"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_OHIVXDXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_OHIVXTXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_OHIVWDXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHIVWTXgEd22A_2b3pOiPw" x="16" y="292" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_OHIVXjXgEd22A_2b3pOiPw" type="1001" element="_OGFMfTXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHIVYTXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_OHIVYjXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_OJNY9jXgEd22A_2b3pOiPw" type="2002" element="_OGFMgTXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OJNY-TXgEd22A_2b3pOiPw" type="4002"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OJNY9zXgEd22A_2b3pOiPw" fillColor="7055523" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OJNY-DXgEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OJNY-jXgEd22A_2b3pOiPw" type="2007" element="_OGFMfzXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OJNY_TXgEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OJNY_jXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OJNY-zXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OJNY_DXgEd22A_2b3pOiPw" x="265" y="150"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OJNY_zXgEd22A_2b3pOiPw" type="2008" element="_OGFMgDXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OJNZAjXgEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OJNZAzXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OJNZADXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OJNZATXgEd22A_2b3pOiPw" x="90" y="265"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_OHIVYzXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_OHIVZDXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_OHIVXzXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHIVYDXgEd22A_2b3pOiPw" x="16" y="558" width="900" height="310"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_OGFMizXgEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_OJNZBDXgEd22A_2b3pOiPw" type="3001" element="_OGFMhDXgEd22A_2b3pOiPw" source="_OHbQQTXgEd22A_2b3pOiPw" target="_OHIVZTXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OJNZCDXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OJNZCTXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OJNZBTXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OJNZBjXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OJNZBzXgEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OJXJ8DXgEd22A_2b3pOiPw" type="3001" element="_OGFMhTXgEd22A_2b3pOiPw" source="_OJNY8TXgEd22A_2b3pOiPw" target="_OJDn8DXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OJXJ9DXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OJXJ9TXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OJXJ8TXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OJXJ8jXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OJXJ8zXgEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OJXJ9jXgEd22A_2b3pOiPw" type="3001" element="_OGFMhjXgEd22A_2b3pOiPw" source="_OJNY-jXgEd22A_2b3pOiPw" target="_OJNY9jXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OJXJ-jXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OJXJ-zXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OJXJ9zXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OJXJ-DXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OJXJ-TXgEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OJXJ_DXgEd22A_2b3pOiPw" type="3001" element="_OGFMhzXgEd22A_2b3pOiPw" source="_OJNY9jXgEd22A_2b3pOiPw" target="_OJNY_zXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OJXKADXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OJXKATXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OJXJ_TXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OJXJ_jXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OJXJ_zXgEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OJXKAjXgEd22A_2b3pOiPw" type="3001" element="_OGFMiDXgEd22A_2b3pOiPw" source="_OHIVZTXgEd22A_2b3pOiPw" target="_OJDn8DXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OJXKBjXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OJXKBzXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OJXKAzXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OJXKBDXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OJXKBTXgEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OJXKCDXgEd22A_2b3pOiPw" type="3001" element="_OGFMiTXgEd22A_2b3pOiPw" source="_OJDn8DXgEd22A_2b3pOiPw" target="_OJNY9jXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OJXKDDXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OJXKDTXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OJXKCTXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OJXKCjXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OJXKCzXgEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

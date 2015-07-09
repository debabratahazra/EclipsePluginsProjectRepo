<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_b0zbsDXfEd22A_2b3pOiPw" name="p8" description="test process" displayName="process p8" filename="modelParsing8service">
    <pools xmi:type="process:Pool" xmi:id="_b0zbsTXfEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assigneeByService xmi:type="process:Service" xmi:id="_b0zbsjXfEd22A_2b3pOiPw" name="assignee-list-1"/>
      <start xmi:type="process:StartEvent" xmi:id="_b0zbszXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_b0zbtDXfEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_b0zbtTXfEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_b0zbtjXfEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assigneeByService xmi:type="process:Service" xmi:id="_b0zbtzXfEd22A_2b3pOiPw" name="assignee-list-2">
        <property xmi:type="process:Property" xmi:id="_b0zbuDXfEd22A_2b3pOiPw" name="assigneeList" value="orga:assigneeB"/>
      </assigneeByService>
      <end xmi:type="process:EndEvent" xmi:id="_b0zbuTXfEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_b0zbujXfEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_b0zbuzXfEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_b0zbvDXfEd22A_2b3pOiPw" source="_b0zbszXfEd22A_2b3pOiPw" target="_b0zbtDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_b0zbvTXfEd22A_2b3pOiPw" source="_b0zbujXfEd22A_2b3pOiPw" target="_b0zbuTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_b0zbvjXfEd22A_2b3pOiPw" source="_b0zbtDXfEd22A_2b3pOiPw" target="_b0zbujXfEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_b0zbvzXfEd22A_2b3pOiPw" type="Process" element="_b0zbsDXfEd22A_2b3pOiPw" name="p8.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_cEI4MDXfEd22A_2b3pOiPw" type="1001" element="_b0zbsTXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_cEI4MzXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_cEI4NDXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_cFLaADXfEd22A_2b3pOiPw" type="2001" element="_b0zbtDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_cFLaAzXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_cFLaATXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_cFLaAjXfEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_cFLaBDXfEd22A_2b3pOiPw" type="2007" element="_b0zbszXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_cFLaBzXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_cFLaCDXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_cFLaBTXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_cFLaBjXfEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_cEI4NTXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_cEI4NjXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_cEI4MTXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_cEI4MjXfEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_cEI4NzXfEd22A_2b3pOiPw" type="1001" element="_b0zbtjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_cEI4OjXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_cEI4OzXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_cFxP4DXfEd22A_2b3pOiPw" type="2001" element="_b0zbujXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_cFxP4zXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_cFxP4TXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_cFxP4jXfEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_cFxP5DXfEd22A_2b3pOiPw" type="2008" element="_b0zbuTXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_cGEK0DXfEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_cGEK0TXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_cFxP5TXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_cFxP5jXfEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_cEI4PDXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_cEI4PTXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_cEI4ODXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_cEI4OTXfEd22A_2b3pOiPw" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_b0zbwDXfEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_cGg2wDXfEd22A_2b3pOiPw" type="3001" element="_b0zbvDXfEd22A_2b3pOiPw" source="_cFLaBDXfEd22A_2b3pOiPw" target="_cFLaADXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_cGg2xDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_cGg2xTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_cGg2wTXfEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_cGg2wjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_cGg2wzXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_cHGsoDXfEd22A_2b3pOiPw" type="3001" element="_b0zbvTXfEd22A_2b3pOiPw" source="_cFxP4DXfEd22A_2b3pOiPw" target="_cFxP5DXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_cHGspDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_cHGspTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_cHGsoTXfEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_cHGsojXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_cHGsozXfEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_cHQdoDXfEd22A_2b3pOiPw" type="3001" element="_b0zbvjXfEd22A_2b3pOiPw" source="_cFLaADXfEd22A_2b3pOiPw" target="_cFxP4DXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_cHQdpDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_cHQdpTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_cHQdoTXfEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_cHQdojXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_cHQdozXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_i9cc4DXgEd22A_2b3pOiPw" name="p41" description="test process" displayName="process p41" filename="modelParsing41service">
    <pools xmi:type="process:Pool" xmi:id="_i9cc4TXgEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assigneeByService xmi:type="process:Service" xmi:id="_i9cc4jXgEd22A_2b3pOiPw" name="input-assignee"/>
      <start xmi:type="process:StartEvent" xmi:id="_i9cc4zXgEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_i9cc5DXgEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_i9cc5TXgEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_i9cc5jXgEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_i9cc5zXgEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_i9cc6DXgEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_i9cc6TXgEd22A_2b3pOiPw" source="_i9cc4zXgEd22A_2b3pOiPw" target="_i9cc5TXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_i9cc6jXgEd22A_2b3pOiPw" source="_i9cc5zXgEd22A_2b3pOiPw" target="_i9cc5DXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_i9cc6zXgEd22A_2b3pOiPw" source="_i9cc5TXgEd22A_2b3pOiPw" target="_i9cc5zXgEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_i9cc7DXgEd22A_2b3pOiPw" type="Process" element="_i9cc4DXgEd22A_2b3pOiPw" name="p41.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_i-e-sDXgEd22A_2b3pOiPw" type="1001" element="_i9cc4TXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_i-e-szXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_i-e-tDXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_i-e-tzXgEd22A_2b3pOiPw" type="2001" element="_i9cc5TXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_i-e-ujXgEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_i-e-uDXgEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_i-e-uTXgEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_i-e-uzXgEd22A_2b3pOiPw" type="2001" element="_i9cc5zXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_i-e-vjXgEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_i-e-vDXgEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_i-e-vTXgEd22A_2b3pOiPw" x="55" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_i-e-vzXgEd22A_2b3pOiPw" type="2007" element="_i9cc4zXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_i-e-wjXgEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_i-e-wzXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_i-e-wDXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_i-e-wTXgEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_i-e-xDXgEd22A_2b3pOiPw" type="2008" element="_i9cc5DXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_i-e-xzXgEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_i-e-yDXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_i-e-xTXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_i-e-xjXgEd22A_2b3pOiPw" x="90" y="385"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_i-e-tTXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_i-e-tjXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_i-e-sTXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_i-e-sjXgEd22A_2b3pOiPw"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_i9cc7TXgEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_i-e-yTXgEd22A_2b3pOiPw" type="3001" element="_i9cc6TXgEd22A_2b3pOiPw" source="_i-e-vzXgEd22A_2b3pOiPw" target="_i-e-tzXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_i-e-zTXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_i-e-zjXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_i-e-yjXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_i-e-yzXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_i-e-zDXgEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_i-e-zzXgEd22A_2b3pOiPw" type="3001" element="_i9cc6jXgEd22A_2b3pOiPw" source="_i-e-uzXgEd22A_2b3pOiPw" target="_i-e-xDXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_i-ovsDXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_i-ovsTXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_i-e-0DXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_i-e-0TXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_i-e-0jXgEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_i-ovsjXgEd22A_2b3pOiPw" type="3001" element="_i9cc6zXgEd22A_2b3pOiPw" source="_i-e-tzXgEd22A_2b3pOiPw" target="_i-e-uzXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_i-ovtjXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_i-ovtzXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_i-ovszXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_i-ovtDXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_i-ovtTXgEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

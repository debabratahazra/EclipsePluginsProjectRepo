<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_e3yPEDXgEd22A_2b3pOiPw" name="p40" description="test process - 40" displayName="process p40" filename="modelParsing40service">
    <pools xmi:type="process:Pool" xmi:id="_e3yPETXgEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assigneeByService xmi:type="process:Service" xmi:id="_e3yPEjXgEd22A_2b3pOiPw" name="assignee-list-2">
        <property xmi:type="process:Property" xmi:id="_e3yPEzXgEd22A_2b3pOiPw" name="assigneeList" value="orga:assignee1,orga:assignee2,orga:assignee3"/>
      </assigneeByService>
      <start xmi:type="process:StartEvent" xmi:id="_e3yPFDXgEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_e3yPFTXgEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_e3yPFjXgEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_e3yPFzXgEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assigneeByService xmi:type="process:Service" xmi:id="_e3yPGDXgEd22A_2b3pOiPw" name="null-assignee"/>
      <end xmi:type="process:EndEvent" xmi:id="_e3yPGTXgEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_e3yPGjXgEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_e3yPGzXgEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_e3yPHDXgEd22A_2b3pOiPw" source="_e3yPFDXgEd22A_2b3pOiPw" target="_e3yPFTXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_e3yPHTXgEd22A_2b3pOiPw" source="_e3yPGjXgEd22A_2b3pOiPw" target="_e3yPGTXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_e3yPHjXgEd22A_2b3pOiPw" source="_e3yPFTXgEd22A_2b3pOiPw" target="_e3yPGjXgEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_e3yPHzXgEd22A_2b3pOiPw" type="Process" element="_e3yPEDXgEd22A_2b3pOiPw" name="p40.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_e4-h4DXgEd22A_2b3pOiPw" type="1001" element="_e3yPETXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_e4-h4zXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_e4-h5DXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_e4-h7jXgEd22A_2b3pOiPw" type="2001" element="_e3yPFTXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_e4-h8TXgEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_e4-h7zXgEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_e4-h8DXgEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_e4-h8jXgEd22A_2b3pOiPw" type="2007" element="_e3yPFDXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_e4-h9TXgEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_e4-h9jXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_e4-h8zXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_e4-h9DXgEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_e4-h5TXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_e4-h5jXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_e4-h4TXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_e4-h4jXgEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_e4-h5zXgEd22A_2b3pOiPw" type="1001" element="_e3yPFzXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_e4-h6jXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_e4-h6zXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_e4-h9zXgEd22A_2b3pOiPw" type="2001" element="_e3yPGjXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_e4-h-jXgEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_e4-h-DXgEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_e4-h-TXgEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_e4-h-zXgEd22A_2b3pOiPw" type="2008" element="_e3yPGTXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_e4-h_jXgEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_e4-h_zXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_e4-h_DXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_e4-h_TXgEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_e4-h7DXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_e4-h7TXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_e4-h6DXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_e4-h6TXgEd22A_2b3pOiPw" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_e3yPIDXgEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_e5IS4DXgEd22A_2b3pOiPw" type="3001" element="_e3yPHDXgEd22A_2b3pOiPw" source="_e4-h8jXgEd22A_2b3pOiPw" target="_e4-h7jXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_e5IS5DXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_e5IS5TXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_e5IS4TXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_e5IS4jXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_e5IS4zXgEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_e5Rc0DXgEd22A_2b3pOiPw" type="3001" element="_e3yPHTXgEd22A_2b3pOiPw" source="_e4-h9zXgEd22A_2b3pOiPw" target="_e4-h-zXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_e5Rc1DXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_e5Rc1TXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_e5Rc0TXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_e5Rc0jXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_e5Rc0zXgEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_e5bN0DXgEd22A_2b3pOiPw" type="3001" element="_e3yPHjXgEd22A_2b3pOiPw" source="_e4-h7jXgEd22A_2b3pOiPw" target="_e4-h9zXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_e5bN1DXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_e5bN1TXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_e5bN0TXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_e5bN0jXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_e5bN0zXgEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

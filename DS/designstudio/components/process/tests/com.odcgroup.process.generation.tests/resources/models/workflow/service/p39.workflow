<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_acGt8DXgEd22A_2b3pOiPw" name="p39" description="test process - 39" displayName="process p39" filename="modelParsing39service">
    <pools xmi:type="process:Pool" xmi:id="_acGt8TXgEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assigneeByService xmi:type="process:Service" xmi:id="_acGt8jXgEd22A_2b3pOiPw" name="assignee-list-2">
        <property xmi:type="process:Property" xmi:id="_acGt8zXgEd22A_2b3pOiPw" name="assigneeList" value="orga:assignee1,orga:assignee2,orga:assignee3"/>
      </assigneeByService>
      <start xmi:type="process:StartEvent" xmi:id="_acGt9DXgEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_acGt9TXgEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_acGt9jXgEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_acGt9zXgEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assigneeByService xmi:type="process:Service" xmi:id="_acGt-DXgEd22A_2b3pOiPw" name="empty-assignee"/>
      <end xmi:type="process:EndEvent" xmi:id="_acGt-TXgEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_acGt-jXgEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_acGt-zXgEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_acGt_DXgEd22A_2b3pOiPw" source="_acGt9DXgEd22A_2b3pOiPw" target="_acGt9TXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_acGt_TXgEd22A_2b3pOiPw" source="_acGt-jXgEd22A_2b3pOiPw" target="_acGt-TXgEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_acGt_jXgEd22A_2b3pOiPw" source="_acGt9TXgEd22A_2b3pOiPw" target="_acGt-jXgEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_acGt_zXgEd22A_2b3pOiPw" type="Process" element="_acGt8DXgEd22A_2b3pOiPw" name="p39.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_adAF0DXgEd22A_2b3pOiPw" type="1001" element="_acGt8TXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_adAF0zXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_adAF1DXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_adAF3jXgEd22A_2b3pOiPw" type="2001" element="_acGt9TXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_adAF4TXgEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_adAF3zXgEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_adAF4DXgEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_adAF4jXgEd22A_2b3pOiPw" type="2007" element="_acGt9DXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_adAF5TXgEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_adAF5jXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_adAF4zXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_adAF5DXgEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_adAF1TXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_adAF1jXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_adAF0TXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_adAF0jXgEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_adAF1zXgEd22A_2b3pOiPw" type="1001" element="_acGt9zXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_adAF2jXgEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_adAF2zXgEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_adAF5zXgEd22A_2b3pOiPw" type="2001" element="_acGt-jXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_adAF6jXgEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_adAF6DXgEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_adAF6TXgEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_adAF6zXgEd22A_2b3pOiPw" type="2008" element="_acGt-TXgEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_adAF7jXgEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_adAF7zXgEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_adAF7DXgEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_adAF7TXgEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_adAF3DXgEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_adAF3TXgEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_adAF2DXgEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_adAF2TXgEd22A_2b3pOiPw" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_acGuADXgEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_adJPwDXgEd22A_2b3pOiPw" type="3001" element="_acGt_DXgEd22A_2b3pOiPw" source="_adAF4jXgEd22A_2b3pOiPw" target="_adAF3jXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_adJPxDXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_adJPxTXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_adJPwTXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_adJPwjXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_adJPwzXgEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_adJPxjXgEd22A_2b3pOiPw" type="3001" element="_acGt_TXgEd22A_2b3pOiPw" source="_adAF5zXgEd22A_2b3pOiPw" target="_adAF6zXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_adJPyjXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_adJPyzXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_adJPxzXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_adJPyDXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_adJPyTXgEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_adJPzDXgEd22A_2b3pOiPw" type="3001" element="_acGt_jXgEd22A_2b3pOiPw" source="_adAF3jXgEd22A_2b3pOiPw" target="_adAF5zXgEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_adJP0DXgEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_adJP0TXgEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_adJPzTXgEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_adJPzjXgEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_adJPzzXgEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

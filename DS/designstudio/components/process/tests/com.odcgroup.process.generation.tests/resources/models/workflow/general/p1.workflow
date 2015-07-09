<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_xUZe4DXhEd22A_2b3pOiPw" name="p1" description="test process" displayName="process p1" filename="modelParsing1">
    <pools xmi:type="process:Pool" xmi:id="_xUZe4TXhEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_xUZe4jXhEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_xUZe4zXhEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_xUZe5DXhEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_xUZe5TXhEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_xUZe5jXhEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_xUZe5zXhEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <end xmi:type="process:EndEvent" xmi:id="_xUZe6DXhEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_xUZe6TXhEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_xUZe6jXhEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_xUZe6zXhEd22A_2b3pOiPw" source="_xUZe4zXhEd22A_2b3pOiPw" target="_xUZe5DXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_xUZe7DXhEd22A_2b3pOiPw" source="_xUZe6TXhEd22A_2b3pOiPw" target="_xUZe6DXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_xUZe7TXhEd22A_2b3pOiPw" source="_xUZe5DXhEd22A_2b3pOiPw" target="_xUZe6TXhEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_xUZe7jXhEd22A_2b3pOiPw" type="Process" element="_xUZe4DXhEd22A_2b3pOiPw" name="p1.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_xVcnwDXhEd22A_2b3pOiPw" type="1001" element="_xUZe4TXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xVcnwzXhEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_xVcnxDXhEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_xVcnzjXhEd22A_2b3pOiPw" type="2001" element="_xUZe5DXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xVcn0TXhEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xVcnzzXhEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xVcn0DXhEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_xVcn0jXhEd22A_2b3pOiPw" type="2007" element="_xUZe4zXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xVcn1TXhEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_xVcn1jXhEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xVcn0zXhEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xVcn1DXhEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_xVcnxTXhEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_xVcnxjXhEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_xVcnwTXhEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xVcnwjXhEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_xVcnxzXhEd22A_2b3pOiPw" type="1001" element="_xUZe5jXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xVcnyjXhEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_xVcnyzXhEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_xVcn1zXhEd22A_2b3pOiPw" type="2001" element="_xUZe6TXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xVcn2jXhEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xVcn2DXhEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xVcn2TXhEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_xVcn2zXhEd22A_2b3pOiPw" type="2008" element="_xUZe6DXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xVcn3jXhEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_xVcn3zXhEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xVcn3DXhEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xVcn3TXhEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_xVcnzDXhEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_xVcnzTXhEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_xVcnyDXhEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xVcnyTXhEd22A_2b3pOiPw" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_xUZe7zXhEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_xVcn4DXhEd22A_2b3pOiPw" type="3001" element="_xUZe6zXhEd22A_2b3pOiPw" source="_xVcn0jXhEd22A_2b3pOiPw" target="_xVcnzjXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xVcn5DXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_xVcn5TXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_xVcn4TXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_xVcn4jXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_xVcn4zXhEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_xVlxsDXhEd22A_2b3pOiPw" type="3001" element="_xUZe7DXhEd22A_2b3pOiPw" source="_xVcn1zXhEd22A_2b3pOiPw" target="_xVcn2zXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xVlxtDXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_xVlxtTXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_xVlxsTXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_xVlxsjXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_xVlxszXhEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_xVlxtjXhEd22A_2b3pOiPw" type="3001" element="_xUZe7TXhEd22A_2b3pOiPw" source="_xVcnzjXhEd22A_2b3pOiPw" target="_xVcn1zXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xVlxujXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_xVlxuzXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_xVlxtzXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_xVlxuDXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_xVlxuTXhEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

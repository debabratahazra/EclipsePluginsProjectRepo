<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_OGJL8DXiEd22A_2b3pOiPw" name="p3" description="test process" displayName="process p3" filename="modelParsing3snd">
    <pools xmi:type="process:Pool" xmi:id="_OGJL8TXiEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_OGJL8jXiEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_OGJL8zXiEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_OGJL9DXiEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_OGJL9TXiEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
      <gateways xmi:type="process:ParallelFork" xmi:id="_OGJL9jXiEd22A_2b3pOiPw" ID="AND-Splita" name="activity Gateway"/>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_OGJL9zXiEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_OGJL-DXiEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <end xmi:type="process:EndEvent" xmi:id="_OGJL-TXiEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_OGJL-jXiEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_OGJL-zXiEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_OGJL_DXiEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_OGJL_TXiEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <end xmi:type="process:EndEvent" xmi:id="_OGJL_jXiEd22A_2b3pOiPw" ID="EndEventc" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_OGJL_zXiEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_OGJMADXiEd22A_2b3pOiPw" URI="http://testC"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_OGJMATXiEd22A_2b3pOiPw" source="_OGJL8zXiEd22A_2b3pOiPw" target="_OGJL9DXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGJMAjXiEd22A_2b3pOiPw" source="_OGJL-jXiEd22A_2b3pOiPw" target="_OGJL-TXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGJMAzXiEd22A_2b3pOiPw" source="_OGJL_zXiEd22A_2b3pOiPw" target="_OGJL_jXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGJMBDXiEd22A_2b3pOiPw" source="_OGJL9DXiEd22A_2b3pOiPw" target="_OGJL9jXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGJMBTXiEd22A_2b3pOiPw" source="_OGJL9jXiEd22A_2b3pOiPw" target="_OGJL-jXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_OGJMBjXiEd22A_2b3pOiPw" source="_OGJL9jXiEd22A_2b3pOiPw" target="_OGJL_zXiEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_OGJMBzXiEd22A_2b3pOiPw" type="Process" element="_OGJL8DXiEd22A_2b3pOiPw" name="p3.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_OG4y0DXiEd22A_2b3pOiPw" type="1001" element="_OGJL8TXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OG4y0zXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_OG4y1DXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_OG4y5TXiEd22A_2b3pOiPw" type="2001" element="_OGJL9DXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OG4y6DXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OG4y5jXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OG4y5zXiEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OG4y6TXiEd22A_2b3pOiPw" type="2005" element="_OGJL9jXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OG4y7DXiEd22A_2b3pOiPw" type="4005">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OG4y7TXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OG4y6jXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OG4y6zXiEd22A_2b3pOiPw" x="75" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OG4y7jXiEd22A_2b3pOiPw" type="2007" element="_OGJL8zXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OG4y8TXiEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OG4y8jXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OG4y7zXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OG4y8DXiEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_OG4y1TXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_OG4y1jXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_OG4y0TXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OG4y0jXiEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_OG4y1zXiEd22A_2b3pOiPw" type="1001" element="_OGJL9zXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OG4y2jXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_OG4y2zXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_OHB8wDXiEd22A_2b3pOiPw" type="2001" element="_OGJL-jXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OHB8wzXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OHB8wTXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHB8wjXiEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OHB8xDXiEd22A_2b3pOiPw" type="2008" element="_OGJL-TXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OHB8xzXiEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OHB8yDXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OHB8xTXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHB8xjXiEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_OG4y3DXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_OG4y3TXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_OG4y2DXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OG4y2TXiEd22A_2b3pOiPw" x="16" y="288" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_OG4y3jXiEd22A_2b3pOiPw" type="1001" element="_OGJL_DXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OG4y4TXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_OG4y4jXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_OHB8yTXiEd22A_2b3pOiPw" type="2001" element="_OGJL_zXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OHB8zDXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OHB8yjXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHB8yzXiEd22A_2b3pOiPw" x="217" y="88"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_OHB8zTXiEd22A_2b3pOiPw" type="2008" element="_OGJL_jXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_OHB80DXiEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_OHB80TXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_OHB8zjXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OHB8zzXiEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_OG4y4zXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_OG4y5DXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_OG4y3zXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OG4y4DXiEd22A_2b3pOiPw" x="16" y="554" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_OGJMCDXiEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_OHB80jXiEd22A_2b3pOiPw" type="3001" element="_OGJMATXiEd22A_2b3pOiPw" source="_OG4y7jXiEd22A_2b3pOiPw" target="_OG4y5TXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHB81jXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OHB81zXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OHB80zXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OHB81DXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OHB81TXiEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OHLtwDXiEd22A_2b3pOiPw" type="3001" element="_OGJMAjXiEd22A_2b3pOiPw" source="_OHB8wDXiEd22A_2b3pOiPw" target="_OHB8xDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHLtxDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OHLtxTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OHLtwTXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OHLtwjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OHLtwzXiEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OHLtxjXiEd22A_2b3pOiPw" type="3001" element="_OGJMAzXiEd22A_2b3pOiPw" source="_OHB8yTXiEd22A_2b3pOiPw" target="_OHB8zTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHLtyjXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OHLtyzXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OHLtxzXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OHLtyDXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OHLtyTXiEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OHLtzDXiEd22A_2b3pOiPw" type="3001" element="_OGJMBDXiEd22A_2b3pOiPw" source="_OG4y5TXiEd22A_2b3pOiPw" target="_OG4y6TXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHLt0DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OHLt0TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OHLtzTXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OHLtzjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OHLtzzXiEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OHLt0jXiEd22A_2b3pOiPw" type="3001" element="_OGJMBTXiEd22A_2b3pOiPw" source="_OG4y6TXiEd22A_2b3pOiPw" target="_OHB8wDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHLt1jXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OHLt1zXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OHLt0zXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OHLt1DXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OHLt1TXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OHLt2DXiEd22A_2b3pOiPw" type="3001" element="_OGJMBjXiEd22A_2b3pOiPw" source="_OG4y6TXiEd22A_2b3pOiPw" target="_OHB8yTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_OHLt3DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_OHLt3TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_OHLt2TXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OHLt2jXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OHLt2zXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

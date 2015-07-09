<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_jOzIEDXfEd22A_2b3pOiPw" name="p13" description="test process" displayName="process p13" filename="modelParsing13service">
    <pools xmi:type="process:Pool" xmi:id="_jOzIETXfEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_jOzIEjXfEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_jOzIEzXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_jOzIFDXfEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jOzIFTXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_jOzIFjXfEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_jOzIFzXfEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <start xmi:type="process:StartEvent" xmi:id="_jOzIGDXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_jOzIGTXfEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jOzIGjXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_jOzIGzXfEd22A_2b3pOiPw" ID="ComplexGatewayb" name="Complex Gateway (activity)">
        <rule xmi:type="process:Rule" xmi:id="_jOzIHDXfEd22A_2b3pOiPw" name="selector-1"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_jOzIHTXfEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_jOzIHjXfEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <end xmi:type="process:EndEvent" xmi:id="_jOzIHzXfEd22A_2b3pOiPw" ID="EndEventc" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_jOzIIDXfEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jOzIITXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_jOzIIjXfEd22A_2b3pOiPw" ID="ComplexMergec" name="activity Gateway">
        <rule xmi:type="process:Rule" xmi:id="_jOzIIzXfEd22A_2b3pOiPw" name="pre-selector-1"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_jOzIJDXfEd22A_2b3pOiPw" ID="Pool4" name="Pool (4)">
      <assignee xmi:type="process:Assignee" xmi:id="_jOzIJTXfEd22A_2b3pOiPw" name="orga:assigneeD"/>
      <tasks xmi:type="process:UserTask" xmi:id="_jOzIJjXfEd22A_2b3pOiPw" ID="d" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jOzIJzXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_jOzIKDXfEd22A_2b3pOiPw" source="_jOzIEzXfEd22A_2b3pOiPw" target="_jOzIFDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzIKTXfEd22A_2b3pOiPw" source="_jOzIGDXfEd22A_2b3pOiPw" target="_jOzIGTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzIKjXfEd22A_2b3pOiPw" source="_jOzIIDXfEd22A_2b3pOiPw" target="_jOzIHzXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzIKzXfEd22A_2b3pOiPw" source="_jOzIFDXfEd22A_2b3pOiPw" target="_jOzIIDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzILDXfEd22A_2b3pOiPw" source="_jOzIGzXfEd22A_2b3pOiPw" target="_jOzIJjXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzILTXfEd22A_2b3pOiPw" source="_jOzIGTXfEd22A_2b3pOiPw" target="_jOzIGzXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzILjXfEd22A_2b3pOiPw" source="_jOzIIjXfEd22A_2b3pOiPw" target="_jOzIIDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jOzILzXfEd22A_2b3pOiPw" source="_jOzIJjXfEd22A_2b3pOiPw" target="_jOzIIjXfEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_jOzIMDXfEd22A_2b3pOiPw" type="Process" element="_jOzIEDXfEd22A_2b3pOiPw" name="p13.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_jPr44DXfEd22A_2b3pOiPw" type="1001" element="_jOzIETXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jPr44zXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_jPr45DXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_jP1p7jXfEd22A_2b3pOiPw" type="2001" element="_jOzIFDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jP1p8TXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1p7zXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1p8DXfEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jP1p8jXfEd22A_2b3pOiPw" type="2007" element="_jOzIEzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jP1p9TXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jP1p9jXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1p8zXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1p9DXfEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_jPr45TXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_jPr45jXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_jPr44TXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jPr44jXfEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_jPr45zXfEd22A_2b3pOiPw" type="1001" element="_jOzIFjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jPr46jXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_jPr46zXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_jP1p9zXfEd22A_2b3pOiPw" type="2001" element="_jOzIGTXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jP1p-jXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1p-DXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1p-TXfEd22A_2b3pOiPw" x="49" y="124"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jP1p-zXfEd22A_2b3pOiPw" type="2003" element="_jOzIGzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jP1p_jXfEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jP1p_zXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1p_DXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1p_TXfEd22A_2b3pOiPw" x="265" y="124"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jP1qADXfEd22A_2b3pOiPw" type="2007" element="_jOzIGDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jP1qAzXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jP1qBDXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1qATXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1qAjXfEd22A_2b3pOiPw" x="1" y="52"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_jPr47DXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_jPr47TXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_jPr46DXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jPr46TXfEd22A_2b3pOiPw" x="16" y="292" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_jP1p4DXfEd22A_2b3pOiPw" type="1001" element="_jOzIHTXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jP1p4zXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_jP1p5DXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_jQIk0DXfEd22A_2b3pOiPw" type="2001" element="_jOzIIDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jQIk0zXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jQIk0TXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jQIk0jXfEd22A_2b3pOiPw" x="55" y="175"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jQIk1DXfEd22A_2b3pOiPw" type="2003" element="_jOzIIjXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jQIk1zXfEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jQIk2DXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jQIk1TXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jQIk1jXfEd22A_2b3pOiPw" x="409" y="170"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jQIk2TXfEd22A_2b3pOiPw" type="2008" element="_jOzIHzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jQIk3DXfEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jQIk3TXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jQIk2jXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jQIk2zXfEd22A_2b3pOiPw" x="90" y="295"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_jP1p5TXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_jP1p5jXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1p4TXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1p4jXfEd22A_2b3pOiPw" x="16" y="558" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_jP1p5zXfEd22A_2b3pOiPw" type="1001" element="_jOzIJDXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jP1p6jXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_jP1p6zXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_jQIk3jXfEd22A_2b3pOiPw" type="2001" element="_jOzIJjXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_jQIk4TXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jQIk3zXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jQIk4DXfEd22A_2b3pOiPw" x="241" y="120"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_jP1p7DXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_jP1p7TXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_jP1p6DXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jP1p6TXfEd22A_2b3pOiPw" x="16" y="824" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_jOzIMTXfEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_jQRuwDXfEd22A_2b3pOiPw" type="3001" element="_jOzIKDXfEd22A_2b3pOiPw" source="_jP1p8jXfEd22A_2b3pOiPw" target="_jP1p7jXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQRuxDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQRuxTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQRuwTXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQRuwjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQRuwzXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQRuxjXfEd22A_2b3pOiPw" type="3001" element="_jOzIKTXfEd22A_2b3pOiPw" source="_jP1qADXfEd22A_2b3pOiPw" target="_jP1p9zXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQRuyjXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQRuyzXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQRuxzXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQRuyDXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQRuyTXfEd22A_2b3pOiPw" points="[0, 15, -83, -72]$[0, 87, -83, 0]$[33, 87, -50, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQRuzDXfEd22A_2b3pOiPw" type="3001" element="_jOzIKjXfEd22A_2b3pOiPw" source="_jQIk0DXfEd22A_2b3pOiPw" target="_jQIk2TXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQRu0DXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQRu0TXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQRuzTXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQRuzjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQRuzzXfEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQRu0jXfEd22A_2b3pOiPw" type="3001" element="_jOzIKzXfEd22A_2b3pOiPw" source="_jP1p7jXfEd22A_2b3pOiPw" target="_jQIk0DXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQRu1jXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQRu1zXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQRu0zXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQRu1DXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQRu1TXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQRu2DXfEd22A_2b3pOiPw" type="3001" element="_jOzILDXfEd22A_2b3pOiPw" source="_jP1p-zXfEd22A_2b3pOiPw" target="_jQIk3jXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQkpsDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQkpsTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQRu2TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQRu2jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQRu2zXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQuasDXfEd22A_2b3pOiPw" type="3001" element="_jOzILTXfEd22A_2b3pOiPw" source="_jP1p9zXfEd22A_2b3pOiPw" target="_jP1p-zXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQuatDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQuatTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQuasTXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQuasjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQuaszXfEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_uWiRgDXfEd22A_2b3pOiPw" id="(0.033333335,0.93333334)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQuatjXfEd22A_2b3pOiPw" type="3001" element="_jOzILjXfEd22A_2b3pOiPw" source="_jQIk1DXfEd22A_2b3pOiPw" target="_jQIk0DXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQuaujXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQuauzXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQuatzXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQuauDXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQuauTXfEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jQuavDXfEd22A_2b3pOiPw" type="3001" element="_jOzILzXfEd22A_2b3pOiPw" source="_jQIk3jXfEd22A_2b3pOiPw" target="_jQIk1DXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_jQuawDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jQuawTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jQuavTXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jQuavjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jQuavzXfEd22A_2b3pOiPw" points="[50, 0, -98, 300]$[148, 0, 0, 300]$[148, -270, 0, 30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

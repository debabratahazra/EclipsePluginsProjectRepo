<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_TVZfEDXiEd22A_2b3pOiPw" name="p4" description="test process" displayName="process p4" filename="modelParsing4">
    <pools xmi:type="process:Pool" xmi:id="_TVZfETXiEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_TVZfEjXiEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_TVZfEzXiEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_TVZfFDXiEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_TVZfFTXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_TVZfFjXiEd22A_2b3pOiPw" ID="ComplexGatewaya" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_TVZfFzXiEd22A_2b3pOiPw"/>
        <script xmi:type="process:Script" xmi:id="_TVZfGDXiEd22A_2b3pOiPw" value="&#xA;if output == 2: &#xA;  selection.add(&quot;b&quot;) &#xA;elif output == 3: &#xA;  selection.add(&quot;c&quot;) &#xA;elif output==4: &#xA;  selection.add(&quot;b&quot;) &#xA;  selection.add(&quot;c&quot;) &#xA;else: &#xA;  print &quot;wrong output&quot;&#xA;"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_TVZfGTXiEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_TVZfGjXiEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <assignee xmi:type="process:Assignee" xmi:id="_TVZfGzXiEd22A_2b3pOiPw" name="orga:assigneeB2"/>
      <end xmi:type="process:EndEvent" xmi:id="_TVZfHDXiEd22A_2b3pOiPw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_TVZfHTXiEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_TVZfHjXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_TVZfHzXiEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_TVZfIDXiEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <end xmi:type="process:EndEvent" xmi:id="_TVZfITXiEd22A_2b3pOiPw" ID="EndEventc" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_TVZfIjXiEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_TVZfIzXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_TVZfJDXiEd22A_2b3pOiPw" source="_TVZfEzXiEd22A_2b3pOiPw" target="_TVZfFDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_TVZfJTXiEd22A_2b3pOiPw" source="_TVZfHTXiEd22A_2b3pOiPw" target="_TVZfHDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_TVZfJjXiEd22A_2b3pOiPw" source="_TVZfIjXiEd22A_2b3pOiPw" target="_TVZfITXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_TVZfJzXiEd22A_2b3pOiPw" source="_TVZfFjXiEd22A_2b3pOiPw" target="_TVZfHTXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_TVZfKDXiEd22A_2b3pOiPw" source="_TVZfFjXiEd22A_2b3pOiPw" target="_TVZfIjXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_TVZfKTXiEd22A_2b3pOiPw" source="_TVZfFDXiEd22A_2b3pOiPw" target="_TVZfFjXiEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_TVZfKjXiEd22A_2b3pOiPw" type="Process" element="_TVZfEDXiEd22A_2b3pOiPw" name="p4.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_TWcA4DXiEd22A_2b3pOiPw" type="1001" element="_TVZfETXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWcA4zXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_TWcA5DXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_TWcA9TXiEd22A_2b3pOiPw" type="2001" element="_TVZfFDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWcA-DXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcA9jXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcA9zXiEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_TWcA-TXiEd22A_2b3pOiPw" type="2003" element="_TVZfFjXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWcA_DXiEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_TWcA_TXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcA-jXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcA-zXiEd22A_2b3pOiPw" x="75" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_TWcA_jXiEd22A_2b3pOiPw" type="2007" element="_TVZfEzXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWcBATXiEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_TWcBAjXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcA_zXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcBADXiEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_TWcA5TXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_TWcA5jXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcA4TXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcA4jXiEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_TWcA5zXiEd22A_2b3pOiPw" type="1001" element="_TVZfGTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWcA6jXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_TWcA6zXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_TWcBAzXiEd22A_2b3pOiPw" type="2001" element="_TVZfHTXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWcBBjXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcBBDXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcBBTXiEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_TWcBBzXiEd22A_2b3pOiPw" type="2008" element="_TVZfHDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWlx4DXiEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_TWlx4TXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcBCDXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcBCTXiEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_TWcA7DXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_TWcA7TXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcA6DXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcA6TXiEd22A_2b3pOiPw" x="16" y="288" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_TWcA7jXiEd22A_2b3pOiPw" type="1001" element="_TVZfHzXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWcA8TXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_TWcA8jXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_TWlx4jXiEd22A_2b3pOiPw" type="2001" element="_TVZfIjXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWlx5TXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWlx4zXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWlx5DXiEd22A_2b3pOiPw" x="241" y="88"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_TWlx5jXiEd22A_2b3pOiPw" type="2008" element="_TVZfITXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_TWlx6TXiEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_TWlx6jXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_TWlx5zXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWlx6DXiEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_TWcA8zXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_TWcA9DXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_TWcA7zXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_TWcA8DXiEd22A_2b3pOiPw" x="16" y="554" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_TVZfKzXiEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_TWu70DXiEd22A_2b3pOiPw" type="3001" element="_TVZfJDXiEd22A_2b3pOiPw" source="_TWcA_jXiEd22A_2b3pOiPw" target="_TWcA9TXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWu71DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_TWu71TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_TWu70TXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_TWu70jXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_TWu70zXiEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_TWu71jXiEd22A_2b3pOiPw" type="3001" element="_TVZfJTXiEd22A_2b3pOiPw" source="_TWcBAzXiEd22A_2b3pOiPw" target="_TWcBBzXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWu72jXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_TWu72zXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_TWu71zXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_TWu72DXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_TWu72TXiEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_TWu73DXiEd22A_2b3pOiPw" type="3001" element="_TVZfJjXiEd22A_2b3pOiPw" source="_TWlx4jXiEd22A_2b3pOiPw" target="_TWlx5jXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWu74DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_TWu74TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_TWu73TXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_TWu73jXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_TWu73zXiEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_TWu74jXiEd22A_2b3pOiPw" type="3001" element="_TVZfJzXiEd22A_2b3pOiPw" source="_TWcA-TXiEd22A_2b3pOiPw" target="_TWcBAzXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWu75jXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_TWu75zXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_TWu74zXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_TWu75DXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_TWu75TXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_TWu76DXiEd22A_2b3pOiPw" type="3001" element="_TVZfKDXiEd22A_2b3pOiPw" source="_TWcA-TXiEd22A_2b3pOiPw" target="_TWlx4jXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TWu77DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_TWu77TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_TWu76TXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_TWu76jXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_TWu76zXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_TW4s0DXiEd22A_2b3pOiPw" type="3001" element="_TVZfKTXiEd22A_2b3pOiPw" source="_TWcA9TXiEd22A_2b3pOiPw" target="_TWcA-TXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_TW4s1DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_TW4s1TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_TW4s0TXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_TW4s0jXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_TW4s0zXiEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

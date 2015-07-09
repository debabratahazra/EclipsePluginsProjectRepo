<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_nujwkDXiEd22A_2b3pOiPw" name="p6" description="test process" displayName="process p6" filename="modelParsing6">
    <pools xmi:type="process:Pool" xmi:id="_nujwkTXiEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_nujwkjXiEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_nujwkzXiEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_nujwlDXiEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_nujwlTXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_nujwljXiEd22A_2b3pOiPw" ID="ComplexGatewaya" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_nujwlzXiEd22A_2b3pOiPw"/>
        <script xmi:type="process:Script" xmi:id="_nujwmDXiEd22A_2b3pOiPw" value="#test&#xA;if output == 2: &#xA;  selection.add(&quot;b&quot;) &#xA;elif output == 3: &#xA;  selection.add(&quot;c&quot;) &#xA;elif output==4: &#xA;  selection.add(&quot;b&quot;) &#xA;  selection.add(&quot;c&quot;) &#xA;else: &#xA;  print &quot;wrong output&quot;&#xA;"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_nujwmTXiEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_nujwmjXiEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <tasks xmi:type="process:UserTask" xmi:id="_nujwmzXiEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_nujwnDXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_nujwnTXiEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_nujwnjXiEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <end xmi:type="process:EndEvent" xmi:id="_nujwnzXiEd22A_2b3pOiPw" ID="EndEventd" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_nujwoDXiEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_nujwoTXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_nujwojXiEd22A_2b3pOiPw" ID="d" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_nujwozXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ExclusiveMerge" xmi:id="_nujwpDXiEd22A_2b3pOiPw" ID="OR-Joind" name="activity Gateway"/>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_nujwpTXiEd22A_2b3pOiPw" source="_nujwkzXiEd22A_2b3pOiPw" target="_nujwlDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwpjXiEd22A_2b3pOiPw" source="_nujwojXiEd22A_2b3pOiPw" target="_nujwnzXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwpzXiEd22A_2b3pOiPw" source="_nujwljXiEd22A_2b3pOiPw" target="_nujwmzXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwqDXiEd22A_2b3pOiPw" source="_nujwljXiEd22A_2b3pOiPw" target="_nujwoDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwqTXiEd22A_2b3pOiPw" source="_nujwlDXiEd22A_2b3pOiPw" target="_nujwljXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwqjXiEd22A_2b3pOiPw" source="_nujwmzXiEd22A_2b3pOiPw" target="_nujwpDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwqzXiEd22A_2b3pOiPw" source="_nujwoDXiEd22A_2b3pOiPw" target="_nujwpDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_nujwrDXiEd22A_2b3pOiPw" source="_nujwpDXiEd22A_2b3pOiPw" target="_nujwojXiEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_nujwrTXiEd22A_2b3pOiPw" type="Process" element="_nujwkDXiEd22A_2b3pOiPw" name="p6.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_nvTXcDXiEd22A_2b3pOiPw" type="1001" element="_nujwkTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nvTXczXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_nvTXdDXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_nvTXhTXiEd22A_2b3pOiPw" type="2001" element="_nujwlDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvTXiDXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvTXhjXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvTXhzXiEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_nvTXiTXiEd22A_2b3pOiPw" type="2003" element="_nujwljXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvTXjDXiEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_nvTXjTXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvTXijXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvTXizXiEd22A_2b3pOiPw" x="75" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_nvTXjjXiEd22A_2b3pOiPw" type="2007" element="_nujwkzXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvTXkTXiEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_nvTXkjXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvTXjzXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvTXkDXiEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_nvTXdTXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_nvTXdjXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_nvTXcTXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvTXcjXiEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_nvTXdzXiEd22A_2b3pOiPw" type="1001" element="_nujwmTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nvTXejXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_nvTXezXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_nvdIcDXiEd22A_2b3pOiPw" type="2001" element="_nujwmzXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvdIczXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvdIcTXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvdIcjXiEd22A_2b3pOiPw" x="193" y="84"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_nvTXfDXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_nvTXfTXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_nvTXeDXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvTXeTXiEd22A_2b3pOiPw" x="16" y="288" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_nvTXfjXiEd22A_2b3pOiPw" type="1001" element="_nujwnTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nvTXgTXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_nvTXgjXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_nvdIdDXiEd22A_2b3pOiPw" type="2001" element="_nujwoDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvdIdzXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvdIdTXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvdIdjXiEd22A_2b3pOiPw" x="49" y="64"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_nvdIeDXiEd22A_2b3pOiPw" type="2001" element="_nujwojXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvdIezXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvdIeTXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvdIejXiEd22A_2b3pOiPw" x="55" y="295"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_nvdIfDXiEd22A_2b3pOiPw" type="2004" element="_nujwpDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvmSYDXiEd22A_2b3pOiPw" type="4004">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_nvmSYTXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvdIfTXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvdIfjXiEd22A_2b3pOiPw" x="217" y="64"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_nvmSYjXiEd22A_2b3pOiPw" type="2008" element="_nujwnzXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_nvmSZTXiEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_nvmSZjXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_nvmSYzXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvmSZDXiEd22A_2b3pOiPw" x="90" y="415"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_nvTXgzXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_nvTXhDXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_nvTXfzXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_nvTXgDXiEd22A_2b3pOiPw" x="16" y="554" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_nujwrjXiEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_nxPRIDXiEd22A_2b3pOiPw" type="3001" element="_nujwpTXiEd22A_2b3pOiPw" source="_nvTXjjXiEd22A_2b3pOiPw" target="_nvTXhTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxPRJDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxPRJTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxPRITXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxPRIjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxPRIzXiEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxZCIDXiEd22A_2b3pOiPw" type="3001" element="_nujwpjXiEd22A_2b3pOiPw" source="_nvdIeDXiEd22A_2b3pOiPw" target="_nvmSYjXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxZCJDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxZCJTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxZCITXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxZCIjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxZCIzXiEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxZCJjXiEd22A_2b3pOiPw" type="3001" element="_nujwpzXiEd22A_2b3pOiPw" source="_nvTXiTXiEd22A_2b3pOiPw" target="_nvdIcDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxZCKjXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxZCKzXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxZCJzXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxZCKDXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxZCKTXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxZCLDXiEd22A_2b3pOiPw" type="3001" element="_nujwqDXiEd22A_2b3pOiPw" source="_nvTXiTXiEd22A_2b3pOiPw" target="_nvdIdDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxZCMDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxZCMTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxZCLTXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxZCLjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxZCLzXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxZCMjXiEd22A_2b3pOiPw" type="3001" element="_nujwqTXiEd22A_2b3pOiPw" source="_nvTXhTXiEd22A_2b3pOiPw" target="_nvTXiTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxZCNjXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxZCNzXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxZCMzXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxZCNDXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxZCNTXiEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxZCODXiEd22A_2b3pOiPw" type="3001" element="_nujwqjXiEd22A_2b3pOiPw" source="_nvdIcDXiEd22A_2b3pOiPw" target="_nvdIfDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxZCPDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxZCPTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxZCOTXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxZCOjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxZCOzXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxizIDXiEd22A_2b3pOiPw" type="3001" element="_nujwqzXiEd22A_2b3pOiPw" source="_nvdIdDXiEd22A_2b3pOiPw" target="_nvdIfDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxizJDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxizJTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxizITXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxizIjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxizIzXiEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_nxizJjXiEd22A_2b3pOiPw" type="3001" element="_nujwrDXiEd22A_2b3pOiPw" source="_nvdIfDXiEd22A_2b3pOiPw" target="_nvdIeDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_nxizKjXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_nxizKzXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_nxizJzXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_nxizKDXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_nxizKTXiEd22A_2b3pOiPw" points="[0, 30, 166, -225]$[0, 255, 166, 0]$[-116, 255, 50, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

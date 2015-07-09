<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_1c4-QDXhEd22A_2b3pOiPw" name="p2" description="test process" displayName="process p2" filename="modelParsing2">
    <pools xmi:type="process:Pool" xmi:id="_1c4-QTXhEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_1c4-QjXhEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_1c4-QzXhEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_1c4-RDXhEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_1c4-RTXhEd22A_2b3pOiPw" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_1c4-RjXhEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_1c4-RzXhEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <tasks xmi:type="process:UserTask" xmi:id="_1c4-SDXhEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_1c4-STXhEd22A_2b3pOiPw" URI="http://testB"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_1c4-SjXhEd22A_2b3pOiPw" ID="ComplexGatewayb" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_1c4-SzXhEd22A_2b3pOiPw"/>
        <script xmi:type="process:Script" xmi:id="_1c4-TDXhEd22A_2b3pOiPw" value="if output==2: selection.add(&quot;c&quot;) elif output==3: selection.add(&quot;d&quot;) else: print &quot;wrong output&quot;"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_1c4-TTXhEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_1c4-TjXhEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <tasks xmi:type="process:UserTask" xmi:id="_1c4-TzXhEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_1c4-UDXhEd22A_2b3pOiPw" URI="http://testC"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_1c4-UTXhEd22A_2b3pOiPw" ID="Pool4" name="Pool (4)">
      <assignee xmi:type="process:Assignee" xmi:id="_1c4-UjXhEd22A_2b3pOiPw" name="orga:assigneeD"/>
      <end xmi:type="process:EndEvent" xmi:id="_1c4-UzXhEd22A_2b3pOiPw" ID="EndEventd" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_1c4-VDXhEd22A_2b3pOiPw" ID="d" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_1c4-VTXhEd22A_2b3pOiPw" URI="http://testD"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-VjXhEd22A_2b3pOiPw" source="_1c4-QzXhEd22A_2b3pOiPw" target="_1c4-RDXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-VzXhEd22A_2b3pOiPw" source="_1c4-VDXhEd22A_2b3pOiPw" target="_1c4-UzXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-WDXhEd22A_2b3pOiPw" source="_1c4-RDXhEd22A_2b3pOiPw" target="_1c4-SDXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-WTXhEd22A_2b3pOiPw" source="_1c4-SjXhEd22A_2b3pOiPw" target="_1c4-TzXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-WjXhEd22A_2b3pOiPw" source="_1c4-SjXhEd22A_2b3pOiPw" target="_1c4-VDXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-WzXhEd22A_2b3pOiPw" source="_1c4-SDXhEd22A_2b3pOiPw" target="_1c4-SjXhEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_1c4-XDXhEd22A_2b3pOiPw" source="_1c4-TzXhEd22A_2b3pOiPw" target="_1c4-VDXhEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_1c4-XTXhEd22A_2b3pOiPw" type="Process" element="_1c4-QDXhEd22A_2b3pOiPw" name="p2.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_1dpMMDXhEd22A_2b3pOiPw" type="1001" element="_1c4-QTXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1dpMMzXhEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_1dpMNDXhEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_1dyWIDXhEd22A_2b3pOiPw" type="2001" element="_1c4-RDXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWIzXhEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWITXhEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWIjXhEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_1dyWJDXhEd22A_2b3pOiPw" type="2007" element="_1c4-QzXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWJzXhEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_1dyWKDXhEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWJTXhEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWJjXhEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_1dpMNTXhEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_1dpMNjXhEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_1dpMMTXhEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dpMMjXhEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_1dpMNzXhEd22A_2b3pOiPw" type="1001" element="_1c4-RjXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1dpMOjXhEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_1dpMOzXhEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_1dyWKTXhEd22A_2b3pOiPw" type="2001" element="_1c4-SDXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWLDXhEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWKjXhEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWKzXhEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_1dyWLTXhEd22A_2b3pOiPw" type="2003" element="_1c4-SjXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWMDXhEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_1dyWMTXhEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWLjXhEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWLzXhEd22A_2b3pOiPw" x="75" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_1dpMPDXhEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_1dpMPTXhEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_1dpMODXhEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dpMOTXhEd22A_2b3pOiPw" x="16" y="298" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_1dpMPjXhEd22A_2b3pOiPw" type="1001" element="_1c4-TTXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1dpMQTXhEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_1dpMQjXhEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_1dyWMjXhEd22A_2b3pOiPw" type="2001" element="_1c4-TzXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWNTXhEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWMzXhEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWNDXhEd22A_2b3pOiPw" x="55" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_1dpMQzXhEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_1dpMRDXhEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_1dpMPzXhEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dpMQDXhEd22A_2b3pOiPw" x="16" y="564" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_1dpMRTXhEd22A_2b3pOiPw" type="1001" element="_1c4-UTXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1dpMSDXhEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_1dpMSTXhEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_1dyWNjXhEd22A_2b3pOiPw" type="2001" element="_1c4-VDXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWOTXhEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWNzXhEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWODXhEd22A_2b3pOiPw" x="337" y="66"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_1dyWOjXhEd22A_2b3pOiPw" type="2008" element="_1c4-UzXhEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_1dyWPTXhEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_1dyWPjXhEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_1dyWOzXhEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dyWPDXhEd22A_2b3pOiPw" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_1dpMSjXhEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_1dpMSzXhEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_1dpMRjXhEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_1dpMRzXhEd22A_2b3pOiPw" x="16" y="830" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_1c4-XjXhEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_1d8HIDXhEd22A_2b3pOiPw" type="3001" element="_1c4-VjXhEd22A_2b3pOiPw" source="_1dyWJDXhEd22A_2b3pOiPw" target="_1dyWIDXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1d8HJDXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1d8HJTXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1d8HITXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1d8HIjXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1d8HIzXhEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_1d8HJjXhEd22A_2b3pOiPw" type="3001" element="_1c4-VzXhEd22A_2b3pOiPw" source="_1dyWNjXhEd22A_2b3pOiPw" target="_1dyWOjXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1d8HKjXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1d8HKzXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1d8HJzXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1d8HKDXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1d8HKTXhEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_1d8HLDXhEd22A_2b3pOiPw" type="3001" element="_1c4-WDXhEd22A_2b3pOiPw" source="_1dyWIDXhEd22A_2b3pOiPw" target="_1dyWKTXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1d8HMDXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1d8HMTXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1d8HLTXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1d8HLjXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1d8HLzXhEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_1d8HMjXhEd22A_2b3pOiPw" type="3001" element="_1c4-WTXhEd22A_2b3pOiPw" source="_1dyWLTXhEd22A_2b3pOiPw" target="_1dyWMjXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1d8HNjXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1d8HNzXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1d8HMzXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1d8HNDXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1d8HNTXhEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_1d8HODXhEd22A_2b3pOiPw" type="3001" element="_1c4-WjXhEd22A_2b3pOiPw" source="_1dyWLTXhEd22A_2b3pOiPw" target="_1dyWNjXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1d8HPDXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1d8HPTXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1d8HOTXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1d8HOjXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1d8HOzXhEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_1eFREDXhEd22A_2b3pOiPw" type="3001" element="_1c4-WzXhEd22A_2b3pOiPw" source="_1dyWKTXhEd22A_2b3pOiPw" target="_1dyWLTXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1eFRFDXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1eFRFTXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1eFRETXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1eFREjXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1eFREzXhEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_1eFRFjXhEd22A_2b3pOiPw" type="3001" element="_1c4-XDXhEd22A_2b3pOiPw" source="_1dyWMjXhEd22A_2b3pOiPw" target="_1dyWNjXhEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_1eFRGjXhEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_1eFRGzXhEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_1eFRFzXhEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_1eFRGDXhEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_1eFRGTXhEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

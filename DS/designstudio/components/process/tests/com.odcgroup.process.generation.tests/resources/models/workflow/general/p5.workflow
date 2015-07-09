<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_X3hmcDXiEd22A_2b3pOiPw" name="p5" description="test process" displayName="process p5" filename="modelParsing5">
    <pools xmi:type="process:Pool" xmi:id="_X3hmcTXiEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_X3hmcjXiEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_X3hmczXiEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_X3hmdDXiEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_X3hmdTXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_X3hmdjXiEd22A_2b3pOiPw" ID="ComplexGatewaya" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_X3hmdzXiEd22A_2b3pOiPw"/>
        <script xmi:type="process:Script" xmi:id="_X3hmeDXiEd22A_2b3pOiPw" value="#test&#xA;if output == 2: &#xA;  selection.add(&quot;b&quot;) &#xA;elif output == 3: &#xA;  selection.add(&quot;c&quot;) &#xA;elif output==4: &#xA;  selection.add(&quot;b&quot;) &#xA;  selection.add(&quot;c&quot;) &#xA;else: &#xA;  print &quot;wrong output&quot;&#xA;"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_X3hmeTXiEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_X3hmejXiEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <tasks xmi:type="process:UserTask" xmi:id="_X3hmezXiEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_X3hmfDXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_X3hmfTXiEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_X3hmfjXiEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <end xmi:type="process:EndEvent" xmi:id="_X3hmfzXiEd22A_2b3pOiPw" ID="EndEventd" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_X3hmgDXiEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_X3hmgTXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_X3hmgjXiEd22A_2b3pOiPw" ID="d" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_X3hmgzXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ParallelMerge" xmi:id="_X3hmhDXiEd22A_2b3pOiPw" ID="AND-Joind" name="activity Gateway"/>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmhTXiEd22A_2b3pOiPw" source="_X3hmczXiEd22A_2b3pOiPw" target="_X3hmdDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmhjXiEd22A_2b3pOiPw" source="_X3hmgjXiEd22A_2b3pOiPw" target="_X3hmfzXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmhzXiEd22A_2b3pOiPw" source="_X3hmdjXiEd22A_2b3pOiPw" target="_X3hmezXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmiDXiEd22A_2b3pOiPw" source="_X3hmdjXiEd22A_2b3pOiPw" target="_X3hmgDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmiTXiEd22A_2b3pOiPw" source="_X3hmdDXiEd22A_2b3pOiPw" target="_X3hmdjXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmijXiEd22A_2b3pOiPw" source="_X3hmezXiEd22A_2b3pOiPw" target="_X3hmhDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmizXiEd22A_2b3pOiPw" source="_X3hmgDXiEd22A_2b3pOiPw" target="_X3hmhDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_X3hmjDXiEd22A_2b3pOiPw" source="_X3hmhDXiEd22A_2b3pOiPw" target="_X3hmgjXiEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_X3hmjTXiEd22A_2b3pOiPw" type="Process" element="_X3hmcDXiEd22A_2b3pOiPw" name="p5.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_X4RNUDXiEd22A_2b3pOiPw" type="1001" element="_X3hmcTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X4RNUzXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_X4RNVDXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_X4a-UDXiEd22A_2b3pOiPw" type="2001" element="_X3hmdDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4a-UzXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-UTXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-UjXiEd22A_2b3pOiPw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_X4a-VDXiEd22A_2b3pOiPw" type="2003" element="_X3hmdjXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4a-VzXiEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_X4a-WDXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-VTXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-VjXiEd22A_2b3pOiPw" x="75" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_X4a-WTXiEd22A_2b3pOiPw" type="2007" element="_X3hmczXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4a-XDXiEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_X4a-XTXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-WjXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-WzXiEd22A_2b3pOiPw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_X4RNVTXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_X4RNVjXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_X4RNUTXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4RNUjXiEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_X4RNVzXiEd22A_2b3pOiPw" type="1001" element="_X3hmeTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X4RNWjXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_X4RNWzXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_X4a-XjXiEd22A_2b3pOiPw" type="2001" element="_X3hmezXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4a-YTXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-XzXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-YDXiEd22A_2b3pOiPw" x="193" y="12"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_X4RNXDXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_X4RNXTXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_X4RNWDXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4RNWTXiEd22A_2b3pOiPw" x="16" y="288" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_X4RNXjXiEd22A_2b3pOiPw" type="1001" element="_X3hmfTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X4RNYTXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_X4RNYjXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_X4a-YjXiEd22A_2b3pOiPw" type="2001" element="_X3hmgDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4a-ZTXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-YzXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-ZDXiEd22A_2b3pOiPw" x="73" y="64"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_X4a-ZjXiEd22A_2b3pOiPw" type="2001" element="_X3hmgjXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4a-aTXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-ZzXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-aDXiEd22A_2b3pOiPw" x="361" y="184"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_X4a-ajXiEd22A_2b3pOiPw" type="2006" element="_X3hmhDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4kvUDXiEd22A_2b3pOiPw" type="4006">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_X4kvUTXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4a-azXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4a-bDXiEd22A_2b3pOiPw" x="193" y="208"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_X4kvUjXiEd22A_2b3pOiPw" type="2008" element="_X3hmfzXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_X4kvVTXiEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_X4kvVjXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_X4kvUzXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4kvVDXiEd22A_2b3pOiPw" x="385" y="355"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_X4RNYzXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_X4RNZDXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_X4RNXzXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_X4RNYDXiEd22A_2b3pOiPw" x="16" y="554" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_X3hmjjXiEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_X6W4ADXiEd22A_2b3pOiPw" type="3001" element="_X3hmhTXiEd22A_2b3pOiPw" source="_X4a-WTXiEd22A_2b3pOiPw" target="_X4a-UDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6W4BDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6W4BTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6W4ATXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6W4AjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6W4AzXiEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6gpADXiEd22A_2b3pOiPw" type="3001" element="_X3hmhjXiEd22A_2b3pOiPw" source="_X4a-ZjXiEd22A_2b3pOiPw" target="_X4kvUjXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6gpBDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6gpBTXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6gpATXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6gpAjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6gpAzXiEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6gpBjXiEd22A_2b3pOiPw" type="3001" element="_X3hmhzXiEd22A_2b3pOiPw" source="_X4a-VDXiEd22A_2b3pOiPw" target="_X4a-XjXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6gpCjXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6gpCzXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6gpBzXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6gpCDXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6gpCTXiEd22A_2b3pOiPw" points="[30, 0, -108, -100]$[140, 0, 2, -100]$[140, 70, 2, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6gpDDXiEd22A_2b3pOiPw" type="3001" element="_X3hmiDXiEd22A_2b3pOiPw" source="_X4a-VDXiEd22A_2b3pOiPw" target="_X4a-YjXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6gpEDXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6gpETXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6gpDTXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6gpDjXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6gpDzXiEd22A_2b3pOiPw" points="[0, 30, -48, -371]$[0, 379, -48, -22]"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_g07mUDXiEd22A_2b3pOiPw" id="(0.8,0.36666667)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6gpEjXiEd22A_2b3pOiPw" type="3001" element="_X3hmiTXiEd22A_2b3pOiPw" source="_X4a-UDXiEd22A_2b3pOiPw" target="_X4a-VDXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6py8DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6py8TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6gpEzXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6gpFDXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6gpFTXiEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6py8jXiEd22A_2b3pOiPw" type="3001" element="_X3hmijXiEd22A_2b3pOiPw" source="_X4a-XjXiEd22A_2b3pOiPw" target="_X4a-ajXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6py9jXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6py9zXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6py8zXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6py9DXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6py9TXiEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6py-DXiEd22A_2b3pOiPw" type="3001" element="_X3hmizXiEd22A_2b3pOiPw" source="_X4a-YjXiEd22A_2b3pOiPw" target="_X4a-ajXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6py_DXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6py_TXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6py-TXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6py-jXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6py-zXiEd22A_2b3pOiPw" points="[-5, 30, -105, -114]$[-5, 144, -105, 0]$[70, 144, -30, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_X6py_jXiEd22A_2b3pOiPw" type="3001" element="_X3hmjDXiEd22A_2b3pOiPw" source="_X4a-ajXiEd22A_2b3pOiPw" target="_X4a-ZjXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_X6pzAjXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_X6pzAzXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_X6py_zXiEd22A_2b3pOiPw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_X6pzADXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_X6pzATXiEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_xggLwDXiEd22A_2b3pOiPw" name="p15" description="test process" displayName="process p15" filename="modelParsing15">
    <pools xmi:type="process:Pool" xmi:id="_xggLwTXiEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_xggLwjXiEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_xggLwzXiEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_xggLxDXiEd22A_2b3pOiPw" ID="a" name="activity" description="an activity that loops on itself" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_xggLxTXiEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_xggLxjXiEd22A_2b3pOiPw" ID="ComplexGatewaya" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_xggLxzXiEd22A_2b3pOiPw"/>
        <script xmi:type="process:Script" xmi:id="_xggLyDXiEd22A_2b3pOiPw" value="&#xA;if output == 2: &#xA;  selection.add(&quot;a&quot;) &#xA;elif output == 3: &#xA;  selection.add(&quot;a&quot;) &#xA;elif output==4: &#xA;  selection.add(&quot;a&quot;) &#xA;else: &#xA;  print &quot;finished&quot;&#xA;"/>
      </gateways>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_xggLyTXiEd22A_2b3pOiPw" source="_xggLwzXiEd22A_2b3pOiPw" target="_xggLxDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_xggLyjXiEd22A_2b3pOiPw" source="_xggLxjXiEd22A_2b3pOiPw" target="_xggLxDXiEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_xggLyzXiEd22A_2b3pOiPw" source="_xggLxDXiEd22A_2b3pOiPw" target="_xggLxjXiEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_xggLzDXiEd22A_2b3pOiPw" type="Process" element="_xggLwDXiEd22A_2b3pOiPw" name="p15.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_xhPyoDXiEd22A_2b3pOiPw" type="1001" element="_xggLwTXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xhPyozXiEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_xhPypDXiEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_xhPypzXiEd22A_2b3pOiPw" type="2001" element="_xggLxDXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xhPyqjXiEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xhPyqDXiEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xhPyqTXiEd22A_2b3pOiPw" x="161" y="104"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_xhPyqzXiEd22A_2b3pOiPw" type="2003" element="_xggLxjXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xhPyrjXiEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_xhPyrzXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xhPyrDXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xhPyrTXiEd22A_2b3pOiPw" x="449" y="104"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_xhPysDXiEd22A_2b3pOiPw" type="2007" element="_xggLwzXiEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_xhPyszXiEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_xhPytDXiEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_xhPysTXiEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xhPysjXiEd22A_2b3pOiPw" x="185" y="8"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_xhPypTXiEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_xhPypjXiEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_xhPyoTXiEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_xhPyojXiEd22A_2b3pOiPw"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_xggLzTXiEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_xhPytTXiEd22A_2b3pOiPw" type="3001" element="_xggLyTXiEd22A_2b3pOiPw" source="_xhPysDXiEd22A_2b3pOiPw" target="_xhPypzXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xhPyuTXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_xhPyujXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_xhPytjXiEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_xhPytzXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_xhPyuDXiEd22A_2b3pOiPw" points="[0, 30, 105, -90]$[-105, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_xhPyuzXiEd22A_2b3pOiPw" type="3001" element="_xggLyjXiEd22A_2b3pOiPw" source="_xhPyqzXiEd22A_2b3pOiPw" target="_xhPypzXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xhPyvzXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_xhPywDXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_xhPyvDXiEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_xhPyvTXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_xhPyvjXiEd22A_2b3pOiPw" points="[-30, 0, 238, 0]$[-125, 60, 143, 60]$[-218, 21, 50, 21]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_xhPywTXiEd22A_2b3pOiPw" type="3001" element="_xggLyzXiEd22A_2b3pOiPw" source="_xhPypzXiEd22A_2b3pOiPw" target="_xhPyqzXiEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_xhPyxTXiEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_xhPyxjXiEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_xhPywjXiEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_xhPywzXiEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_xhPyxDXiEd22A_2b3pOiPw" points="[50, -17, -218, -17]$[147, -50, -121, -50]$[238, 0, -30, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

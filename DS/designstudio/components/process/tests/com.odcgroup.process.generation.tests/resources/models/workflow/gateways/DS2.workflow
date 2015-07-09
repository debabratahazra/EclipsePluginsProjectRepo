<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_jkHKQCZIEd2mY5zBWmxoQw" name="DS2" description="test" displayName="DS2" filename="workflow-model-DS2">
    <pools xmi:type="process:Pool" xmi:id="_jkHKQSZIEd2mY5zBWmxoQw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_jkHKQiZIEd2mY5zBWmxoQw" name="orga:assignee"/>
      <start xmi:type="process:StartEvent" xmi:id="_jkHKQyZIEd2mY5zBWmxoQw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_jkHKRCZIEd2mY5zBWmxoQw" ID="EndEventD" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_jkHKRSZIEd2mY5zBWmxoQw" ID="A" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jkHKRiZIEd2mY5zBWmxoQw" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_jkHKRyZIEd2mY5zBWmxoQw" ID="B" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jkHKSCZIEd2mY5zBWmxoQw" URI="http://testB"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_jkHKSSZIEd2mY5zBWmxoQw" ID="C" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jkHKSiZIEd2mY5zBWmxoQw" URI="http://testC"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_jkHKSyZIEd2mY5zBWmxoQw" ID="D" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_jkHKTCZIEd2mY5zBWmxoQw" URI="http://testD"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_jkHKTSZIEd2mY5zBWmxoQw" ID="ComplexGatewayA" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_jkHKTiZIEd2mY5zBWmxoQw" name="a-service-selector-with-context">
          <property xmi:type="process:Property" xmi:id="_jkHKTyZIEd2mY5zBWmxoQw" name="test1" value="a property"/>
        </service>
      </gateways>
      <gateways xmi:type="process:ExclusiveMerge" xmi:id="_jkHKUCZIEd2mY5zBWmxoQw" ID="OR-JoinD" name="activity Gateway"/>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKUSZIEd2mY5zBWmxoQw" source="_jkHKQyZIEd2mY5zBWmxoQw" target="_jkHKRSZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKUiZIEd2mY5zBWmxoQw" source="_jkHKSyZIEd2mY5zBWmxoQw" target="_jkHKRCZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKUyZIEd2mY5zBWmxoQw" source="_jkHKTSZIEd2mY5zBWmxoQw" target="_jkHKRyZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKVCZIEd2mY5zBWmxoQw" source="_jkHKTSZIEd2mY5zBWmxoQw" target="_jkHKSSZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKVSZIEd2mY5zBWmxoQw" source="_jkHKRSZIEd2mY5zBWmxoQw" target="_jkHKTSZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKViZIEd2mY5zBWmxoQw" source="_jkHKRyZIEd2mY5zBWmxoQw" target="_jkHKUCZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKVyZIEd2mY5zBWmxoQw" source="_jkHKSSZIEd2mY5zBWmxoQw" target="_jkHKUCZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_jkHKWCZIEd2mY5zBWmxoQw" source="_jkHKUCZIEd2mY5zBWmxoQw" target="_jkHKSyZIEd2mY5zBWmxoQw"/>
  </process:Process>
  <notation:Diagram xmi:id="_jkHKWSZIEd2mY5zBWmxoQw" type="Process" element="_jkHKQCZIEd2mY5zBWmxoQw" name="DS2.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_jlTdECZIEd2mY5zBWmxoQw" type="1001" element="_jkHKQSZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlTdEyZIEd2mY5zBWmxoQw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_jlTdFCZIEd2mY5zBWmxoQw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_jlTdFyZIEd2mY5zBWmxoQw" type="2001" element="_jkHKRSZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jlTdGiZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jlTdGCZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jlTdGSZIEd2mY5zBWmxoQw" x="135" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jlTdGyZIEd2mY5zBWmxoQw" type="2001" element="_jkHKRyZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jlTdHiZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jlTdHCZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jlTdHSZIEd2mY5zBWmxoQw" x="55" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jlTdHyZIEd2mY5zBWmxoQw" type="2001" element="_jkHKSSZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jlTdIiZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jlTdICZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jlTdISZIEd2mY5zBWmxoQw" x="215" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jlTdIyZIEd2mY5zBWmxoQw" type="2001" element="_jkHKSyZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jlTdJiZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jlTdJCZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jlTdJSZIEd2mY5zBWmxoQw" x="135" y="625"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jldOECZIEd2mY5zBWmxoQw" type="2003" element="_jkHKTSZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jldOEyZIEd2mY5zBWmxoQw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jldOFCZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jldOESZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jldOEiZIEd2mY5zBWmxoQw" x="155" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jldOFSZIEd2mY5zBWmxoQw" type="2004" element="_jkHKUCZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jldOGCZIEd2mY5zBWmxoQw" type="4004">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jldOGSZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jldOFiZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jldOFyZIEd2mY5zBWmxoQw" x="155" y="505"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jldOGiZIEd2mY5zBWmxoQw" type="2007" element="_jkHKQyZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jldOHSZIEd2mY5zBWmxoQw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jldOHiZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jldOGyZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jldOHCZIEd2mY5zBWmxoQw" x="170" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_jldOHyZIEd2mY5zBWmxoQw" type="2008" element="_jkHKRCZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_jldOIiZIEd2mY5zBWmxoQw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_jldOIyZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_jldOICZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jldOISZIEd2mY5zBWmxoQw" x="170" y="745"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_jlTdFSZIEd2mY5zBWmxoQw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_jlTdFiZIEd2mY5zBWmxoQw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_jlTdESZIEd2mY5zBWmxoQw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_jlTdEiZIEd2mY5zBWmxoQw" x="16" y="55" width="1060" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_jkHKWiZIEd2mY5zBWmxoQw"/>
    <edges xmi:type="notation:Edge" xmi:id="_jldOJCZIEd2mY5zBWmxoQw" type="3001" element="_jkHKUSZIEd2mY5zBWmxoQw" source="_jldOGiZIEd2mY5zBWmxoQw" target="_jlTdFyZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlmYACZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlmYASZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jldOJSZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jldOJiZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jldOJyZIEd2mY5zBWmxoQw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlmYAiZIEd2mY5zBWmxoQw" type="3001" element="_jkHKUiZIEd2mY5zBWmxoQw" source="_jlTdIyZIEd2mY5zBWmxoQw" target="_jldOHyZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlmYBiZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlmYByZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlmYAyZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlmYBCZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlmYBSZIEd2mY5zBWmxoQw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlmYCCZIEd2mY5zBWmxoQw" type="3001" element="_jkHKUyZIEd2mY5zBWmxoQw" source="_jldOECZIEd2mY5zBWmxoQw" target="_jlTdGyZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlmYDCZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlmYDSZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlmYCSZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlmYCiZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlmYCyZIEd2mY5zBWmxoQw" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlmYDiZIEd2mY5zBWmxoQw" type="3001" element="_jkHKVCZIEd2mY5zBWmxoQw" source="_jldOECZIEd2mY5zBWmxoQw" target="_jlTdHyZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlmYEiZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlmYEyZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlmYDyZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlmYECZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlmYESZIEd2mY5zBWmxoQw" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlmYFCZIEd2mY5zBWmxoQw" type="3001" element="_jkHKVSZIEd2mY5zBWmxoQw" source="_jlTdFyZIEd2mY5zBWmxoQw" target="_jldOECZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlmYGCZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlmYGSZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlmYFSZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlmYFiZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlmYFyZIEd2mY5zBWmxoQw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlmYGiZIEd2mY5zBWmxoQw" type="3001" element="_jkHKViZIEd2mY5zBWmxoQw" source="_jlTdGyZIEd2mY5zBWmxoQw" target="_jldOFSZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlmYHiZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlmYHyZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlmYGyZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlmYHCZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlmYHSZIEd2mY5zBWmxoQw" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlmYICZIEd2mY5zBWmxoQw" type="3001" element="_jkHKVyZIEd2mY5zBWmxoQw" source="_jlTdHyZIEd2mY5zBWmxoQw" target="_jldOFSZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlwJAyZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlwJBCZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlwJACZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlwJASZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlwJAiZIEd2mY5zBWmxoQw" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_jlwJBSZIEd2mY5zBWmxoQw" type="3001" element="_jkHKWCZIEd2mY5zBWmxoQw" source="_jldOFSZIEd2mY5zBWmxoQw" target="_jlTdIyZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_jlwJCSZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_jlwJCiZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_jlwJBiZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_jlwJByZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_jlwJCCZIEd2mY5zBWmxoQw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

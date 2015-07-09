<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_yzKuYCZIEd2mY5zBWmxoQw" name="DS3" description="test" displayName="DS3" filename="workflow-model-DS3">
    <pools xmi:type="process:Pool" xmi:id="_yzKuYSZIEd2mY5zBWmxoQw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_yzKuYiZIEd2mY5zBWmxoQw" name="orga:assignee"/>
      <start xmi:type="process:StartEvent" xmi:id="_yzKuYyZIEd2mY5zBWmxoQw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_yzKuZCZIEd2mY5zBWmxoQw" ID="EndEventD" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_yzKuZSZIEd2mY5zBWmxoQw" ID="A" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_yzKuZiZIEd2mY5zBWmxoQw" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_yzKuZyZIEd2mY5zBWmxoQw" ID="B" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_yzKuaCZIEd2mY5zBWmxoQw" URI="http://testB"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_yzKuaSZIEd2mY5zBWmxoQw" ID="C" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_yzKuaiZIEd2mY5zBWmxoQw" URI="http://testC"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_yzKuayZIEd2mY5zBWmxoQw" ID="D" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_yzKubCZIEd2mY5zBWmxoQw" URI="http://testD"/>
      </tasks>
      <gateways xmi:type="process:ParallelFork" xmi:id="_yzKubSZIEd2mY5zBWmxoQw" ID="AND-SplitA" name="activity Gateway"/>
      <gateways xmi:type="process:ParallelMerge" xmi:id="_yzKubiZIEd2mY5zBWmxoQw" ID="AND-JoinD" name="activity Gateway"/>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_yzKubyZIEd2mY5zBWmxoQw" source="_yzKuYyZIEd2mY5zBWmxoQw" target="_yzKuZSZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKucCZIEd2mY5zBWmxoQw" source="_yzKuayZIEd2mY5zBWmxoQw" target="_yzKuZCZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKucSZIEd2mY5zBWmxoQw" source="_yzKuZSZIEd2mY5zBWmxoQw" target="_yzKubSZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKuciZIEd2mY5zBWmxoQw" source="_yzKubSZIEd2mY5zBWmxoQw" target="_yzKuZyZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKucyZIEd2mY5zBWmxoQw" source="_yzKubSZIEd2mY5zBWmxoQw" target="_yzKuaSZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKudCZIEd2mY5zBWmxoQw" source="_yzKuZyZIEd2mY5zBWmxoQw" target="_yzKubiZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKudSZIEd2mY5zBWmxoQw" source="_yzKuaSZIEd2mY5zBWmxoQw" target="_yzKubiZIEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_yzKudiZIEd2mY5zBWmxoQw" source="_yzKubiZIEd2mY5zBWmxoQw" target="_yzKuayZIEd2mY5zBWmxoQw"/>
  </process:Process>
  <notation:Diagram xmi:id="_yzKudyZIEd2mY5zBWmxoQw" type="Process" element="_yzKuYCZIEd2mY5zBWmxoQw" name="DS3.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_y0gyMCZIEd2mY5zBWmxoQw" type="1001" element="_yzKuYSZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y0gyMyZIEd2mY5zBWmxoQw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_y0qjMCZIEd2mY5zBWmxoQw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_y1aKECZIEd2mY5zBWmxoQw" type="2001" element="_yzKuZSZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1aKEyZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1aKESZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1aKEiZIEd2mY5zBWmxoQw" x="135" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1aKFCZIEd2mY5zBWmxoQw" type="2001" element="_yzKuZyZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1aKFyZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1aKFSZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1aKFiZIEd2mY5zBWmxoQw" x="55" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1aKGCZIEd2mY5zBWmxoQw" type="2001" element="_yzKuaSZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1aKGyZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1aKGSZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1aKGiZIEd2mY5zBWmxoQw" x="215" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1aKHCZIEd2mY5zBWmxoQw" type="2001" element="_yzKuayZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1aKHyZIEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1aKHSZIEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1aKHiZIEd2mY5zBWmxoQw" x="135" y="625"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1aKICZIEd2mY5zBWmxoQw" type="2005" element="_yzKubSZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1aKIyZIEd2mY5zBWmxoQw" type="4005">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_y1aKJCZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1aKISZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1aKIiZIEd2mY5zBWmxoQw" x="155" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1jUACZIEd2mY5zBWmxoQw" type="2006" element="_yzKubiZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1jUAyZIEd2mY5zBWmxoQw" type="4006">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_y1jUBCZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1jUASZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1jUAiZIEd2mY5zBWmxoQw" x="155" y="505"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1jUBSZIEd2mY5zBWmxoQw" type="2007" element="_yzKuYyZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1jUCCZIEd2mY5zBWmxoQw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_y1jUCSZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1jUBiZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1jUByZIEd2mY5zBWmxoQw" x="170" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_y1jUCiZIEd2mY5zBWmxoQw" type="2008" element="_yzKuZCZIEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_y1jUDSZIEd2mY5zBWmxoQw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_y1jUDiZIEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_y1jUCyZIEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y1jUDCZIEd2mY5zBWmxoQw" x="170" y="745"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_y0qjMSZIEd2mY5zBWmxoQw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_y0qjMiZIEd2mY5zBWmxoQw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_y0gyMSZIEd2mY5zBWmxoQw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_y0gyMiZIEd2mY5zBWmxoQw" x="16" y="55" width="1060" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_yzKueCZIEd2mY5zBWmxoQw"/>
    <edges xmi:type="notation:Edge" xmi:id="_y2vm0CZIEd2mY5zBWmxoQw" type="3001" element="_yzKubyZIEd2mY5zBWmxoQw" source="_y1jUBSZIEd2mY5zBWmxoQw" target="_y1aKECZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y2vm1CZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y2vm1SZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y2vm0SZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y2vm0iZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y2vm0yZIEd2mY5zBWmxoQw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y2vm1iZIEd2mY5zBWmxoQw" type="3001" element="_yzKucCZIEd2mY5zBWmxoQw" source="_y1aKHCZIEd2mY5zBWmxoQw" target="_y1jUCiZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y2vm2iZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y2vm2yZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y2vm1yZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y2vm2CZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y2vm2SZIEd2mY5zBWmxoQw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y2vm3CZIEd2mY5zBWmxoQw" type="3001" element="_yzKucSZIEd2mY5zBWmxoQw" source="_y1aKECZIEd2mY5zBWmxoQw" target="_y1aKICZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y2vm4CZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y2vm4SZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y2vm3SZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y2vm3iZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y2vm3yZIEd2mY5zBWmxoQw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y2vm4iZIEd2mY5zBWmxoQw" type="3001" element="_yzKuciZIEd2mY5zBWmxoQw" source="_y1aKICZIEd2mY5zBWmxoQw" target="_y1aKFCZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y2vm5iZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y2vm5yZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y2vm4yZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y2vm5CZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y2vm5SZIEd2mY5zBWmxoQw" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y25X0CZIEd2mY5zBWmxoQw" type="3001" element="_yzKucyZIEd2mY5zBWmxoQw" source="_y1aKICZIEd2mY5zBWmxoQw" target="_y1aKGCZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y25X1CZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y25X1SZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y25X0SZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y25X0iZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y25X0yZIEd2mY5zBWmxoQw" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y25X1iZIEd2mY5zBWmxoQw" type="3001" element="_yzKudCZIEd2mY5zBWmxoQw" source="_y1aKFCZIEd2mY5zBWmxoQw" target="_y1jUACZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y25X2iZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y25X2yZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y25X1yZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y25X2CZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y25X2SZIEd2mY5zBWmxoQw" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y25X3CZIEd2mY5zBWmxoQw" type="3001" element="_yzKudSZIEd2mY5zBWmxoQw" source="_y1aKGCZIEd2mY5zBWmxoQw" target="_y1jUACZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y25X4CZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y25X4SZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y25X3SZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y25X3iZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y25X3yZIEd2mY5zBWmxoQw" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_y25X4iZIEd2mY5zBWmxoQw" type="3001" element="_yzKudiZIEd2mY5zBWmxoQw" source="_y1jUACZIEd2mY5zBWmxoQw" target="_y1aKHCZIEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_y25X5iZIEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_y25X5yZIEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_y25X4yZIEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_y25X5CZIEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_y25X5SZIEd2mY5zBWmxoQw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

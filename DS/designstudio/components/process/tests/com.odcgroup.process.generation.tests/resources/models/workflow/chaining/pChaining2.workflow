<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_2txMMCutEd22R8pdt3nQbA" name="pChaining2" description="test process" displayName="process p1" filename="modelChaining2Pools">
    <pools xmi:type="process:Pool" xmi:id="_2txMMSutEd22R8pdt3nQbA" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_2txMMiutEd22R8pdt3nQbA" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_2txMMyutEd22R8pdt3nQbA" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_2txMNCutEd22R8pdt3nQbA" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_2txMNSutEd22R8pdt3nQbA" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_2txMNiutEd22R8pdt3nQbA" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_2txMNyutEd22R8pdt3nQbA" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_2txMOCutEd22R8pdt3nQbA" URI="http://testB"/>
      </tasks>
      <gateways xmi:type="process:ParallelFork" xmi:id="_2txMOSutEd22R8pdt3nQbA" ID="AND-Splita" name="activity Gateway"/>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_2txMOiutEd22R8pdt3nQbA" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_2txMOyutEd22R8pdt3nQbA" name="orga:assigneeC"/>
      <end xmi:type="process:EndEvent" xmi:id="_2txMPCutEd22R8pdt3nQbA" ID="EndEventc" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_2txMPSutEd22R8pdt3nQbA" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_2txMPiutEd22R8pdt3nQbA" URI="http://testC"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_2txMPyutEd22R8pdt3nQbA" source="_2txMMyutEd22R8pdt3nQbA" target="_2txMNSutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_2txMQCutEd22R8pdt3nQbA" source="_2txMNyutEd22R8pdt3nQbA" target="_2txMNCutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_2txMQSutEd22R8pdt3nQbA" source="_2txMPSutEd22R8pdt3nQbA" target="_2txMPCutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_2txMQiutEd22R8pdt3nQbA" source="_2txMNSutEd22R8pdt3nQbA" target="_2txMOSutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_2txMQyutEd22R8pdt3nQbA" source="_2txMOSutEd22R8pdt3nQbA" target="_2txMNyutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_2txMRCutEd22R8pdt3nQbA" source="_2txMOSutEd22R8pdt3nQbA" target="_2txMPSutEd22R8pdt3nQbA"/>
  </process:Process>
  <notation:Diagram xmi:id="_2txMRSutEd22R8pdt3nQbA" type="Process" element="_2txMMCutEd22R8pdt3nQbA" name="pChaining2.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_2uhaICutEd22R8pdt3nQbA" type="1001" element="_2txMMSutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2uhaIyutEd22R8pdt3nQbA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_2uhaJCutEd22R8pdt3nQbA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_2uhaLiutEd22R8pdt3nQbA" type="2001" element="_2txMNSutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uhaMSutEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uhaLyutEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uhaMCutEd22R8pdt3nQbA" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_2uhaMiutEd22R8pdt3nQbA" type="2001" element="_2txMNyutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uhaNSutEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uhaMyutEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uhaNCutEd22R8pdt3nQbA" x="289" y="266"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_2uhaNiutEd22R8pdt3nQbA" type="2005" element="_2txMOSutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uhaOSutEd22R8pdt3nQbA" type="4005">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_2uhaOiutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uhaNyutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uhaOCutEd22R8pdt3nQbA" x="75" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_2uqkECutEd22R8pdt3nQbA" type="2007" element="_2txMMyutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uqkEyutEd22R8pdt3nQbA" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_2uqkFCutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uqkESutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uqkEiutEd22R8pdt3nQbA" x="90" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_2uqkFSutEd22R8pdt3nQbA" type="2008" element="_2txMNCutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uqkGCutEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_2uqkGSutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uqkFiutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uqkFyutEd22R8pdt3nQbA" x="481" y="194"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_2uhaJSutEd22R8pdt3nQbA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_2uhaJiutEd22R8pdt3nQbA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_2uhaISutEd22R8pdt3nQbA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uhaIiutEd22R8pdt3nQbA" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_2uhaJyutEd22R8pdt3nQbA" type="1001" element="_2txMOiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2uhaKiutEd22R8pdt3nQbA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_2uhaKyutEd22R8pdt3nQbA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_2uqkGiutEd22R8pdt3nQbA" type="2001" element="_2txMPSutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uqkHSutEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uqkGyutEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uqkHCutEd22R8pdt3nQbA" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_2uqkHiutEd22R8pdt3nQbA" type="2008" element="_2txMPCutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_2uqkISutEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_2uqkIiutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_2uqkHyutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uqkICutEd22R8pdt3nQbA" x="90" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_2uhaLCutEd22R8pdt3nQbA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_2uhaLSutEd22R8pdt3nQbA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_2uhaKCutEd22R8pdt3nQbA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_2uhaKSutEd22R8pdt3nQbA" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_2txMRiutEd22R8pdt3nQbA"/>
    <edges xmi:type="notation:Edge" xmi:id="_2uqkIyutEd22R8pdt3nQbA" type="3001" element="_2txMPyutEd22R8pdt3nQbA" source="_2uqkECutEd22R8pdt3nQbA" target="_2uhaLiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2uqkJyutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_2uqkKCutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_2uqkJCutEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2uqkJSutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2uqkJiutEd22R8pdt3nQbA" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_2u0VECutEd22R8pdt3nQbA" type="3001" element="_2txMQCutEd22R8pdt3nQbA" source="_2uhaMiutEd22R8pdt3nQbA" target="_2uqkFSutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2u0VFCutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_2u0VFSutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_2u0VESutEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2u0VEiutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2u0VEyutEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_2u0VFiutEd22R8pdt3nQbA" type="3001" element="_2txMQSutEd22R8pdt3nQbA" source="_2uqkGiutEd22R8pdt3nQbA" target="_2uqkHiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2u0VGiutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_2u0VGyutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_2u0VFyutEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2u0VGCutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2u0VGSutEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_2u0VHCutEd22R8pdt3nQbA" type="3001" element="_2txMQiutEd22R8pdt3nQbA" source="_2uhaLiutEd22R8pdt3nQbA" target="_2uhaNiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2u0VICutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_2u0VISutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_2u0VHSutEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2u0VHiutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2u0VHyutEd22R8pdt3nQbA" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_2u0VIiutEd22R8pdt3nQbA" type="3001" element="_2txMQyutEd22R8pdt3nQbA" source="_2uhaNiutEd22R8pdt3nQbA" target="_2uhaMiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2u0VJiutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_2u0VJyutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_2u0VIyutEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2u0VJCutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2u0VJSutEd22R8pdt3nQbA" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_2u0VKCutEd22R8pdt3nQbA" type="3001" element="_2txMRCutEd22R8pdt3nQbA" source="_2uhaNiutEd22R8pdt3nQbA" target="_2uqkGiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_2u0VLCutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_2u0VLSutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_2u0VKSutEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2u0VKiutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2u0VKyutEd22R8pdt3nQbA" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

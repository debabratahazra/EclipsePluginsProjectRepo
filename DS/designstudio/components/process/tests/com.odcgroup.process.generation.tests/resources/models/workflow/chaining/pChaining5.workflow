<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_JsZHcCuwEd2zdf6-rjEAtw" name="pChaining5" description="test process" displayName="process p1" filename="modelChainingNot2Pools">
    <pools xmi:type="process:Pool" xmi:id="_JsZHcSuwEd2zdf6-rjEAtw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_JsZHciuwEd2zdf6-rjEAtw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_JsZHcyuwEd2zdf6-rjEAtw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_JsZHdCuwEd2zdf6-rjEAtw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_JsZHdSuwEd2zdf6-rjEAtw" URI="http://testA"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_JsZHdiuwEd2zdf6-rjEAtw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_JsZHdyuwEd2zdf6-rjEAtw" name="orga:assigneeB"/>
      <end xmi:type="process:EndEvent" xmi:id="_JsZHeCuwEd2zdf6-rjEAtw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_JsZHeSuwEd2zdf6-rjEAtw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_JsZHeiuwEd2zdf6-rjEAtw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_JsZHeyuwEd2zdf6-rjEAtw" source="_JsZHcyuwEd2zdf6-rjEAtw" target="_JsZHdCuwEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_JsZHfCuwEd2zdf6-rjEAtw" source="_JsZHeSuwEd2zdf6-rjEAtw" target="_JsZHeCuwEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_JsZHfSuwEd2zdf6-rjEAtw" source="_JsZHdCuwEd2zdf6-rjEAtw" target="_JsZHeSuwEd2zdf6-rjEAtw"/>
  </process:Process>
  <notation:Diagram xmi:id="_JsZHfiuwEd2zdf6-rjEAtw" type="Process" element="_JsZHcCuwEd2zdf6-rjEAtw" name="pChaining5.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_JtbpQCuwEd2zdf6-rjEAtw" type="1001" element="_JsZHcSuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_JtbpQyuwEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_JtbpRCuwEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_JtbpTiuwEd2zdf6-rjEAtw" type="2001" element="_JsZHdCuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_JtlaQCuwEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_JtbpTyuwEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtbpUCuwEd2zdf6-rjEAtw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_JtlaQSuwEd2zdf6-rjEAtw" type="2007" element="_JsZHcyuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_JtlaRCuwEd2zdf6-rjEAtw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_JtlaRSuwEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_JtlaQiuwEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtlaQyuwEd2zdf6-rjEAtw" x="90" y="55"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_JtbpRSuwEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_JtbpRiuwEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_JtbpQSuwEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtbpQiuwEd2zdf6-rjEAtw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_JtbpRyuwEd2zdf6-rjEAtw" type="1001" element="_JsZHdiuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_JtbpSiuwEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_JtbpSyuwEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_JtlaRiuwEd2zdf6-rjEAtw" type="2001" element="_JsZHeSuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_JtlaSSuwEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_JtlaRyuwEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtlaSCuwEd2zdf6-rjEAtw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_JtlaSiuwEd2zdf6-rjEAtw" type="2008" element="_JsZHeCuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_JtlaTSuwEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_JtlaTiuwEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_JtlaSyuwEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtlaTCuwEd2zdf6-rjEAtw" x="241" y="74"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_JtbpTCuwEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_JtbpTSuwEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_JtbpSCuwEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_JtbpSSuwEd2zdf6-rjEAtw" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_JsZHfyuwEd2zdf6-rjEAtw"/>
    <edges xmi:type="notation:Edge" xmi:id="_JtukMCuwEd2zdf6-rjEAtw" type="3001" element="_JsZHeyuwEd2zdf6-rjEAtw" source="_JtlaQSuwEd2zdf6-rjEAtw" target="_JtbpTiuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_JtukNCuwEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_JtukNSuwEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_JtukMSuwEd2zdf6-rjEAtw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_JtukMiuwEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_JtukMyuwEd2zdf6-rjEAtw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_JtukNiuwEd2zdf6-rjEAtw" type="3001" element="_JsZHfCuwEd2zdf6-rjEAtw" source="_JtlaRiuwEd2zdf6-rjEAtw" target="_JtlaSiuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_JtukOiuwEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_JtukOyuwEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_JtukNyuwEd2zdf6-rjEAtw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_JtukOCuwEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_JtukOSuwEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_JtukPCuwEd2zdf6-rjEAtw" type="3001" element="_JsZHfSuwEd2zdf6-rjEAtw" source="_JtbpTiuwEd2zdf6-rjEAtw" target="_JtlaRiuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_JtukQCuwEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_JtukQSuwEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_JtukPSuwEd2zdf6-rjEAtw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_JtukPiuwEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_JtukPyuwEd2zdf6-rjEAtw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

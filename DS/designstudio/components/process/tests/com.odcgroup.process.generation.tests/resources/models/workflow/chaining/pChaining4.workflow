<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_zzgvoCuvEd2zdf6-rjEAtw" name="pChaining4" description="test process" displayName="process p1" filename="modelChaining4PoolsMany">
    <pools xmi:type="process:Pool" xmi:id="_zzgvoSuvEd2zdf6-rjEAtw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_zzgvoiuvEd2zdf6-rjEAtw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_zzgvoyuvEd2zdf6-rjEAtw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_zzgvpCuvEd2zdf6-rjEAtw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_zzgvpSuvEd2zdf6-rjEAtw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_zzgvpiuvEd2zdf6-rjEAtw" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_zzgvpyuvEd2zdf6-rjEAtw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_zzgvqCuvEd2zdf6-rjEAtw" URI="http://testB"/>
      </tasks>
      <gateways xmi:type="process:ParallelFork" xmi:id="_zzgvqSuvEd2zdf6-rjEAtw" ID="AND-Splita" name="activity Gateway"/>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_zzgvqiuvEd2zdf6-rjEAtw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_zzgvqyuvEd2zdf6-rjEAtw" name="orga:assigneeCD"/>
      <end xmi:type="process:EndEvent" xmi:id="_zzgvrCuvEd2zdf6-rjEAtw" ID="EndEventc" name="End Event(activity)"/>
      <end xmi:type="process:EndEvent" xmi:id="_zzgvrSuvEd2zdf6-rjEAtw" ID="EndEventd" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_zzgvriuvEd2zdf6-rjEAtw" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_zzgvryuvEd2zdf6-rjEAtw" URI="http://testC"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_zzgvsCuvEd2zdf6-rjEAtw" ID="d" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_zzgvsSuvEd2zdf6-rjEAtw" URI="http://testD"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_zzgvsiuvEd2zdf6-rjEAtw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_zzgvsyuvEd2zdf6-rjEAtw" name="orga:assigneeE"/>
      <end xmi:type="process:EndEvent" xmi:id="_zzgvtCuvEd2zdf6-rjEAtw" ID="EndEvente" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_zzgvtSuvEd2zdf6-rjEAtw" ID="e" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_zzgvtiuvEd2zdf6-rjEAtw" URI="http://testE"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_zzgvtyuvEd2zdf6-rjEAtw" ID="Pool4" name="Pool (4)">
      <assignee xmi:type="process:Assignee" xmi:id="_zzgvuCuvEd2zdf6-rjEAtw" name="orga:assigneeF"/>
      <end xmi:type="process:EndEvent" xmi:id="_zzgvuSuvEd2zdf6-rjEAtw" ID="EndEventf" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_zzgvuiuvEd2zdf6-rjEAtw" ID="f" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_zzgvuyuvEd2zdf6-rjEAtw" URI="http://testF"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvvCuvEd2zdf6-rjEAtw" source="_zzgvoyuvEd2zdf6-rjEAtw" target="_zzgvpSuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvvSuvEd2zdf6-rjEAtw" source="_zzgvpyuvEd2zdf6-rjEAtw" target="_zzgvpCuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvviuvEd2zdf6-rjEAtw" source="_zzgvriuvEd2zdf6-rjEAtw" target="_zzgvrCuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvvyuvEd2zdf6-rjEAtw" source="_zzgvsCuvEd2zdf6-rjEAtw" target="_zzgvrSuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvwCuvEd2zdf6-rjEAtw" source="_zzgvtSuvEd2zdf6-rjEAtw" target="_zzgvtCuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvwSuvEd2zdf6-rjEAtw" source="_zzgvuiuvEd2zdf6-rjEAtw" target="_zzgvuSuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvwiuvEd2zdf6-rjEAtw" source="_zzgvpSuvEd2zdf6-rjEAtw" target="_zzgvqSuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvwyuvEd2zdf6-rjEAtw" source="_zzgvqSuvEd2zdf6-rjEAtw" target="_zzgvpyuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvxCuvEd2zdf6-rjEAtw" source="_zzgvqSuvEd2zdf6-rjEAtw" target="_zzgvriuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvxSuvEd2zdf6-rjEAtw" source="_zzgvqSuvEd2zdf6-rjEAtw" target="_zzgvsCuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvxiuvEd2zdf6-rjEAtw" source="_zzgvqSuvEd2zdf6-rjEAtw" target="_zzgvtSuvEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_zzgvxyuvEd2zdf6-rjEAtw" source="_zzgvqSuvEd2zdf6-rjEAtw" target="_zzgvuiuvEd2zdf6-rjEAtw"/>
  </process:Process>
  <notation:Diagram xmi:id="_zzgvyCuvEd2zdf6-rjEAtw" type="Process" element="_zzgvoCuvEd2zdf6-rjEAtw" name="pChaining4.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_0EWA8CuvEd2zdf6-rjEAtw" type="1001" element="_zzgvoSuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0EWA8yuvEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0EWA9CuvEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0F1OsCuvEd2zdf6-rjEAtw" type="2001" element="_zzgvpSuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0F1OsyuvEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0F1OsSuvEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0F1OsiuvEd2zdf6-rjEAtw" x="289" y="122"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0F1OtCuvEd2zdf6-rjEAtw" type="2001" element="_zzgvpyuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0F1OtyuvEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0F1OtSuvEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0F1OtiuvEd2zdf6-rjEAtw" x="523" y="243"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0F1OuCuvEd2zdf6-rjEAtw" type="2005" element="_zzgvqSuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0F1OuyuvEd2zdf6-rjEAtw" type="4005">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0F1OvCuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0F1OuSuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0F1OuiuvEd2zdf6-rjEAtw" x="309" y="242"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0F1OvSuvEd2zdf6-rjEAtw" type="2007" element="_zzgvoyuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0F1OwCuvEd2zdf6-rjEAtw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0F1OwSuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0F1OviuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0F1OvyuvEd2zdf6-rjEAtw" x="324" y="32"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0F1OwiuvEd2zdf6-rjEAtw" type="2008" element="_zzgvpCuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0F-_sCuvEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0F-_sSuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0F1OwyuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0F1OxCuvEd2zdf6-rjEAtw" x="691" y="267"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0EWA9SuvEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0EWA9iuvEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0EWA8SuvEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0EWA8iuvEd2zdf6-rjEAtw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0EWA9yuvEd2zdf6-rjEAtw" type="1001" element="_zzgvqiuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0EWA-iuvEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0EWA-yuvEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0HBhgCuvEd2zdf6-rjEAtw" type="2001" element="_zzgvriuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HBhgyuvEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HBhgSuvEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HBhgiuvEd2zdf6-rjEAtw" x="55" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0HBhhCuvEd2zdf6-rjEAtw" type="2001" element="_zzgvsCuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HBhhyuvEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HBhhSuvEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HBhhiuvEd2zdf6-rjEAtw" x="215" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0HBhiCuvEd2zdf6-rjEAtw" type="2008" element="_zzgvrCuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HBhiyuvEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0HBhjCuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HBhiSuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HBhiiuvEd2zdf6-rjEAtw" x="90" y="175"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0HBhjSuvEd2zdf6-rjEAtw" type="2008" element="_zzgvrSuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HBhkCuvEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0HBhkSuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HBhjiuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HBhjyuvEd2zdf6-rjEAtw" x="250" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0EWA_CuvEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0EWA_SuvEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0EWA-CuvEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0EWA-SuvEd2zdf6-rjEAtw" x="16" y="298" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0EWA_iuvEd2zdf6-rjEAtw" type="1001" element="_zzgvsiuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0EWBASuvEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0EWBAiuvEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0HLSgCuvEd2zdf6-rjEAtw" type="2001" element="_zzgvtSuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HLSgyuvEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HLSgSuvEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HLSgiuvEd2zdf6-rjEAtw" x="361" y="62"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0HLShCuvEd2zdf6-rjEAtw" type="2008" element="_zzgvtCuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HLShyuvEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0HLSiCuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HLShSuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HLShiuvEd2zdf6-rjEAtw" x="385" y="158"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0Efx8CuvEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0Efx8SuvEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0EWA_yuvEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0EWBACuvEd2zdf6-rjEAtw" x="16" y="564" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0Efx8iuvEd2zdf6-rjEAtw" type="1001" element="_zzgvtyuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0Efx9SuvEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0Efx9iuvEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0HVDgCuvEd2zdf6-rjEAtw" type="2001" element="_zzgvuiuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HVDgyuvEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HVDgSuvEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HVDgiuvEd2zdf6-rjEAtw" x="649" y="18"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0HVDhCuvEd2zdf6-rjEAtw" type="2008" element="_zzgvuSuvEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_0HVDhyuvEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0HVDiCuvEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0HVDhSuvEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0HVDhiuvEd2zdf6-rjEAtw" x="673" y="138"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0Efx9yuvEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0Efx-CuvEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0Efx8yuvEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0Efx9CuvEd2zdf6-rjEAtw" x="16" y="830" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_zzgvySuvEd2zdf6-rjEAtw"/>
    <edges xmi:type="notation:Edge" xmi:id="_0HxvcCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvvCuvEd2zdf6-rjEAtw" source="_0F1OvSuvEd2zdf6-rjEAtw" target="_0F1OsCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0H65YCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0H65YSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0HxvcSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0HxvciuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0HxvcyuvEd2zdf6-rjEAtw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0IhWUCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvvSuvEd2zdf6-rjEAtw" source="_0F1OtCuvEd2zdf6-rjEAtw" target="_0F1OwiuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0IhWVCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0IhWVSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0IhWUSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0IhWUiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0IhWUyuvEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0IqgQCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvviuvEd2zdf6-rjEAtw" source="_0HBhgCuvEd2zdf6-rjEAtw" target="_0HBhiCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0IqgRCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0IqgRSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0IqgQSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0IqgQiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0IqgQyuvEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0IqgRiuvEd2zdf6-rjEAtw" type="3001" element="_zzgvvyuvEd2zdf6-rjEAtw" source="_0HBhhCuvEd2zdf6-rjEAtw" target="_0HBhjSuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0IqgSiuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0IqgSyuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0IqgRyuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0IqgSCuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0IqgSSuvEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0IqgTCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvwCuvEd2zdf6-rjEAtw" source="_0HLSgCuvEd2zdf6-rjEAtw" target="_0HLShCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0IqgUCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0IqgUSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0IqgTSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0IqgTiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0IqgTyuvEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I0RQCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvwSuvEd2zdf6-rjEAtw" source="_0HVDgCuvEd2zdf6-rjEAtw" target="_0HVDhCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0I0RRCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I0RRSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I0RQSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I0RQiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I0RQyuvEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I0RRiuvEd2zdf6-rjEAtw" type="3001" element="_zzgvwiuvEd2zdf6-rjEAtw" source="_0F1OsCuvEd2zdf6-rjEAtw" target="_0F1OuCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0I0RSiuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I0RSyuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I0RRyuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I0RSCuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I0RSSuvEd2zdf6-rjEAtw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I0RTCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvwyuvEd2zdf6-rjEAtw" source="_0F1OuCuvEd2zdf6-rjEAtw" target="_0F1OtCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0I-CQCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-CQSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I0RTSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I0RTiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I0RTyuvEd2zdf6-rjEAtw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I-CQiuvEd2zdf6-rjEAtw" type="3001" element="_zzgvxCuvEd2zdf6-rjEAtw" source="_0F1OuCuvEd2zdf6-rjEAtw" target="_0HBhgCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0I-CRiuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-CRyuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-CQyuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-CRCuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-CRSuvEd2zdf6-rjEAtw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I-CSCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvxSuvEd2zdf6-rjEAtw" source="_0F1OuCuvEd2zdf6-rjEAtw" target="_0HBhhCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0I-CTCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-CTSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-CSSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-CSiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-CSyuvEd2zdf6-rjEAtw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I-CTiuvEd2zdf6-rjEAtw" type="3001" element="_zzgvxiuvEd2zdf6-rjEAtw" source="_0F1OuCuvEd2zdf6-rjEAtw" target="_0HLSgCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0I-CUiuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-CUyuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-CTyuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-CUCuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-CUSuvEd2zdf6-rjEAtw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0JHMMCuvEd2zdf6-rjEAtw" type="3001" element="_zzgvxyuvEd2zdf6-rjEAtw" source="_0F1OuCuvEd2zdf6-rjEAtw" target="_0HVDgCuvEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_0JHMNCuvEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0JHMNSuvEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0JHMMSuvEd2zdf6-rjEAtw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0JHMMiuvEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0JHMMyuvEd2zdf6-rjEAtw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

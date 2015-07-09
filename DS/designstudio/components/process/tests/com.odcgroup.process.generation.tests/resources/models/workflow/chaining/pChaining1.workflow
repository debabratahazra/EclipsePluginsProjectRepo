<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_0IFjYCutEd22R8pdt3nQbA" name="pChaining1" description="test process" displayName="process p1" filename="modelChaining2ForNext">
    <pools xmi:type="process:Pool" xmi:id="_0IFjYSutEd22R8pdt3nQbA" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_0IFjYiutEd22R8pdt3nQbA" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_0IFjYyutEd22R8pdt3nQbA" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_0IFjZCutEd22R8pdt3nQbA" ID="EndEventb" name="End Event(activity)"/>
      <end xmi:type="process:EndEvent" xmi:id="_0IFjZSutEd22R8pdt3nQbA" ID="EndEventc" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0IFjZiutEd22R8pdt3nQbA" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0IFjZyutEd22R8pdt3nQbA" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_0IFjaCutEd22R8pdt3nQbA" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0IFjaSutEd22R8pdt3nQbA" URI="http://testB"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_0IFjaiutEd22R8pdt3nQbA" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0IFjayutEd22R8pdt3nQbA" URI="http://testC"/>
      </tasks>
      <gateways xmi:type="process:ParallelFork" xmi:id="_0IFjbCutEd22R8pdt3nQbA" ID="AND-Splita" name="activity Gateway"/>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_0IFjbSutEd22R8pdt3nQbA" source="_0IFjYyutEd22R8pdt3nQbA" target="_0IFjZiutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_0IFjbiutEd22R8pdt3nQbA" source="_0IFjaCutEd22R8pdt3nQbA" target="_0IFjZCutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_0IFjbyutEd22R8pdt3nQbA" source="_0IFjaiutEd22R8pdt3nQbA" target="_0IFjZSutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_0IFjcCutEd22R8pdt3nQbA" source="_0IFjZiutEd22R8pdt3nQbA" target="_0IFjbCutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_0IFjcSutEd22R8pdt3nQbA" source="_0IFjbCutEd22R8pdt3nQbA" target="_0IFjaCutEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_0IFjciutEd22R8pdt3nQbA" source="_0IFjbCutEd22R8pdt3nQbA" target="_0IFjaiutEd22R8pdt3nQbA"/>
  </process:Process>
  <notation:Diagram xmi:id="_0IFjcyutEd22R8pdt3nQbA" type="Process" element="_0IFjYCutEd22R8pdt3nQbA" name="pChaining1.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_0I1KQCutEd22R8pdt3nQbA" type="1001" element="_0IFjYSutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0I1KQyutEd22R8pdt3nQbA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0I1KRCutEd22R8pdt3nQbA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0I1KRyutEd22R8pdt3nQbA" type="2001" element="_0IFjZiutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KSiutEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KSCutEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KSSutEd22R8pdt3nQbA" x="135" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0I1KSyutEd22R8pdt3nQbA" type="2001" element="_0IFjaCutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KTiutEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KTCutEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KTSutEd22R8pdt3nQbA" x="55" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0I1KTyutEd22R8pdt3nQbA" type="2001" element="_0IFjaiutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KUiutEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KUCutEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KUSutEd22R8pdt3nQbA" x="215" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0I1KUyutEd22R8pdt3nQbA" type="2005" element="_0IFjbCutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KViutEd22R8pdt3nQbA" type="4005">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0I1KVyutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KVCutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KVSutEd22R8pdt3nQbA" x="155" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0I1KWCutEd22R8pdt3nQbA" type="2007" element="_0IFjYyutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KWyutEd22R8pdt3nQbA" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0I1KXCutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KWSutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KWiutEd22R8pdt3nQbA" x="170" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0I1KXSutEd22R8pdt3nQbA" type="2008" element="_0IFjZCutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KYCutEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0I1KYSutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KXiutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KXyutEd22R8pdt3nQbA" x="90" y="505"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0I1KYiutEd22R8pdt3nQbA" type="2008" element="_0IFjZSutEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_0I1KZSutEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0I1KZiutEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KYyutEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KZCutEd22R8pdt3nQbA" x="250" y="505"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0I1KRSutEd22R8pdt3nQbA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0I1KRiutEd22R8pdt3nQbA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0I1KQSutEd22R8pdt3nQbA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0I1KQiutEd22R8pdt3nQbA"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_0IFjdCutEd22R8pdt3nQbA"/>
    <edges xmi:type="notation:Edge" xmi:id="_0I-7QCutEd22R8pdt3nQbA" type="3001" element="_0IFjbSutEd22R8pdt3nQbA" source="_0I1KWCutEd22R8pdt3nQbA" target="_0I1KRyutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0I-7RCutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-7RSutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-7QSutEd22R8pdt3nQbA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-7QiutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-7QyutEd22R8pdt3nQbA" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I-7RiutEd22R8pdt3nQbA" type="3001" element="_0IFjbiutEd22R8pdt3nQbA" source="_0I1KSyutEd22R8pdt3nQbA" target="_0I1KXSutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0I-7SiutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-7SyutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-7RyutEd22R8pdt3nQbA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-7SCutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-7SSutEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I-7TCutEd22R8pdt3nQbA" type="3001" element="_0IFjbyutEd22R8pdt3nQbA" source="_0I1KTyutEd22R8pdt3nQbA" target="_0I1KYiutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0I-7UCutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-7USutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-7TSutEd22R8pdt3nQbA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-7TiutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-7TyutEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0I-7UiutEd22R8pdt3nQbA" type="3001" element="_0IFjcCutEd22R8pdt3nQbA" source="_0I1KRyutEd22R8pdt3nQbA" target="_0I1KUyutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0I-7ViutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0I-7VyutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0I-7UyutEd22R8pdt3nQbA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0I-7VCutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0I-7VSutEd22R8pdt3nQbA" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0JIsQCutEd22R8pdt3nQbA" type="3001" element="_0IFjcSutEd22R8pdt3nQbA" source="_0I1KUyutEd22R8pdt3nQbA" target="_0I1KSyutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0JIsRCutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0JIsRSutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0JIsQSutEd22R8pdt3nQbA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0JIsQiutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0JIsQyutEd22R8pdt3nQbA" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0JIsRiutEd22R8pdt3nQbA" type="3001" element="_0IFjciutEd22R8pdt3nQbA" source="_0I1KUyutEd22R8pdt3nQbA" target="_0I1KTyutEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_0JIsSiutEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0JIsSyutEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0JIsRyutEd22R8pdt3nQbA" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0JIsSCutEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0JIsSSutEd22R8pdt3nQbA" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

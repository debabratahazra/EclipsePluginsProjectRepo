<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_FNpFoCZJEd2mY5zBWmxoQw" name="DS1" description="test" displayName="DS1" filename="workflow-model-DS1">
    <pools xmi:type="process:Pool" xmi:id="_FNpFoSZJEd2mY5zBWmxoQw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_FNpFoiZJEd2mY5zBWmxoQw" name="orga:assignee"/>
      <start xmi:type="process:StartEvent" xmi:id="_FNpFoyZJEd2mY5zBWmxoQw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_FNpFpCZJEd2mY5zBWmxoQw" ID="EndEventD" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_FNpFpSZJEd2mY5zBWmxoQw" ID="A" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_FNpFpiZJEd2mY5zBWmxoQw" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_FNpFpyZJEd2mY5zBWmxoQw" ID="B" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_FNpFqCZJEd2mY5zBWmxoQw" URI="http://testB"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_FNpFqSZJEd2mY5zBWmxoQw" ID="C" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_FNpFqiZJEd2mY5zBWmxoQw" URI="http://testC"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_FNpFqyZJEd2mY5zBWmxoQw" ID="D" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_FNpFrCZJEd2mY5zBWmxoQw" URI="http://testD"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_FNpFrSZJEd2mY5zBWmxoQw" ID="ComplexGatewayA" name="Complex Gateway (activity)">
        <service xmi:type="process:Service" xmi:id="_FNpFriZJEd2mY5zBWmxoQw" name="a-service-selector-with-context">
          <property xmi:type="process:Property" xmi:id="_FNpFryZJEd2mY5zBWmxoQw" name="test1" value="a property"/>
        </service>
      </gateways>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_FNpFsCZJEd2mY5zBWmxoQw" ID="ComplexMergeD" name="activity Gateway">
        <service xmi:type="process:Service" xmi:id="_FNpFsSZJEd2mY5zBWmxoQw" name="a-service-pre-selector-with-context">
          <property xmi:type="process:Property" xmi:id="_FNpFsiZJEd2mY5zBWmxoQw" name="someProperty" value="A property"/>
        </service>
      </gateways>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFsyZJEd2mY5zBWmxoQw" source="_FNpFoyZJEd2mY5zBWmxoQw" target="_FNpFpSZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFtCZJEd2mY5zBWmxoQw" source="_FNpFqyZJEd2mY5zBWmxoQw" target="_FNpFpCZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFtSZJEd2mY5zBWmxoQw" source="_FNpFrSZJEd2mY5zBWmxoQw" target="_FNpFpyZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFtiZJEd2mY5zBWmxoQw" source="_FNpFrSZJEd2mY5zBWmxoQw" target="_FNpFqSZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFtyZJEd2mY5zBWmxoQw" source="_FNpFpSZJEd2mY5zBWmxoQw" target="_FNpFrSZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFuCZJEd2mY5zBWmxoQw" source="_FNpFsCZJEd2mY5zBWmxoQw" target="_FNpFqyZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFuSZJEd2mY5zBWmxoQw" source="_FNpFpyZJEd2mY5zBWmxoQw" target="_FNpFsCZJEd2mY5zBWmxoQw"/>
    <transitions xmi:type="process:Flow" xmi:id="_FNpFuiZJEd2mY5zBWmxoQw" source="_FNpFqSZJEd2mY5zBWmxoQw" target="_FNpFsCZJEd2mY5zBWmxoQw"/>
  </process:Process>
  <notation:Diagram xmi:id="_FNy2oCZJEd2mY5zBWmxoQw" type="Process" element="_FNpFoCZJEd2mY5zBWmxoQw" name="DS1.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_FO_JcCZJEd2mY5zBWmxoQw" type="1001" element="_FNpFoSZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FO_JcyZJEd2mY5zBWmxoQw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_FO_JdCZJEd2mY5zBWmxoQw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_FO_JdyZJEd2mY5zBWmxoQw" type="2001" element="_FNpFpSZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JeiZJEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JeCZJEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JeSZJEd2mY5zBWmxoQw" x="135" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JeyZJEd2mY5zBWmxoQw" type="2001" element="_FNpFpyZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JfiZJEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JfCZJEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JfSZJEd2mY5zBWmxoQw" x="55" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JfyZJEd2mY5zBWmxoQw" type="2001" element="_FNpFqSZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JgiZJEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JgCZJEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JgSZJEd2mY5zBWmxoQw" x="215" y="385"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JgyZJEd2mY5zBWmxoQw" type="2001" element="_FNpFqyZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JhiZJEd2mY5zBWmxoQw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JhCZJEd2mY5zBWmxoQw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JhSZJEd2mY5zBWmxoQw" x="135" y="625"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JhyZJEd2mY5zBWmxoQw" type="2003" element="_FNpFrSZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JiiZJEd2mY5zBWmxoQw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_FO_JiyZJEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JiCZJEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JiSZJEd2mY5zBWmxoQw" x="155" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JjCZJEd2mY5zBWmxoQw" type="2003" element="_FNpFsCZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JjyZJEd2mY5zBWmxoQw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_FO_JkCZJEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JjSZJEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JjiZJEd2mY5zBWmxoQw" x="155" y="505"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JkSZJEd2mY5zBWmxoQw" type="2007" element="_FNpFoyZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JlCZJEd2mY5zBWmxoQw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_FO_JlSZJEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JkiZJEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JkyZJEd2mY5zBWmxoQw" x="170" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_FO_JliZJEd2mY5zBWmxoQw" type="2008" element="_FNpFpCZJEd2mY5zBWmxoQw">
          <children xmi:type="notation:Node" xmi:id="_FO_JmSZJEd2mY5zBWmxoQw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_FO_JmiZJEd2mY5zBWmxoQw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JlyZJEd2mY5zBWmxoQw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JmCZJEd2mY5zBWmxoQw" x="170" y="745"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_FO_JdSZJEd2mY5zBWmxoQw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_FO_JdiZJEd2mY5zBWmxoQw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_FO_JcSZJEd2mY5zBWmxoQw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FO_JciZJEd2mY5zBWmxoQw" x="16" y="55" width="1060" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_FNy2oSZJEd2mY5zBWmxoQw"/>
    <edges xmi:type="notation:Edge" xmi:id="_FPI6cCZJEd2mY5zBWmxoQw" type="3001" element="_FNpFsyZJEd2mY5zBWmxoQw" source="_FO_JkSZJEd2mY5zBWmxoQw" target="_FO_JdyZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPI6dCZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPI6dSZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPI6cSZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPI6ciZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPI6cyZJEd2mY5zBWmxoQw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPI6diZJEd2mY5zBWmxoQw" type="3001" element="_FNpFtCZJEd2mY5zBWmxoQw" source="_FO_JgyZJEd2mY5zBWmxoQw" target="_FO_JliZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPI6eiZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPI6eyZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPI6dyZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPI6eCZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPI6eSZJEd2mY5zBWmxoQw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPI6fCZJEd2mY5zBWmxoQw" type="3001" element="_FNpFtSZJEd2mY5zBWmxoQw" source="_FO_JhyZJEd2mY5zBWmxoQw" target="_FO_JeyZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPI6gCZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPI6gSZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPI6fSZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPI6fiZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPI6fyZJEd2mY5zBWmxoQw" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPk_UCZJEd2mY5zBWmxoQw" type="3001" element="_FNpFtiZJEd2mY5zBWmxoQw" source="_FO_JhyZJEd2mY5zBWmxoQw" target="_FO_JfyZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPuwUCZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPuwUSZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPk_USZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPk_UiZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPk_UyZJEd2mY5zBWmxoQw" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPuwUiZJEd2mY5zBWmxoQw" type="3001" element="_FNpFtyZJEd2mY5zBWmxoQw" source="_FO_JdyZJEd2mY5zBWmxoQw" target="_FO_JhyZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPuwViZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPuwVyZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPuwUyZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPuwVCZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPuwVSZJEd2mY5zBWmxoQw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPuwWCZJEd2mY5zBWmxoQw" type="3001" element="_FNpFuCZJEd2mY5zBWmxoQw" source="_FO_JjCZJEd2mY5zBWmxoQw" target="_FO_JgyZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPuwXCZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPuwXSZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPuwWSZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPuwWiZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPuwWyZJEd2mY5zBWmxoQw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPuwXiZJEd2mY5zBWmxoQw" type="3001" element="_FNpFuSZJEd2mY5zBWmxoQw" source="_FO_JeyZJEd2mY5zBWmxoQw" target="_FO_JjCZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPuwYiZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPuwYyZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPuwXyZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPuwYCZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPuwYSZJEd2mY5zBWmxoQw" points="[0, 30, -80, -90]$[80, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FPuwZCZJEd2mY5zBWmxoQw" type="3001" element="_FNpFuiZJEd2mY5zBWmxoQw" source="_FO_JfyZJEd2mY5zBWmxoQw" target="_FO_JjCZJEd2mY5zBWmxoQw">
      <children xmi:type="notation:Node" xmi:id="_FPuwaCZJEd2mY5zBWmxoQw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_FPuwaSZJEd2mY5zBWmxoQw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_FPuwZSZJEd2mY5zBWmxoQw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FPuwZiZJEd2mY5zBWmxoQw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FPuwZyZJEd2mY5zBWmxoQw" points="[0, 30, 80, -90]$[-80, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

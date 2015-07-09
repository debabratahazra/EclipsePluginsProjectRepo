<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_CBva0CuuEd22R8pdt3nQbA" name="pChaining3" description="test process" displayName="process p1" filename="modelChaining2PoolsMany">
    <pools xmi:type="process:Pool" xmi:id="_CBva0SuuEd22R8pdt3nQbA" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_CBva0iuuEd22R8pdt3nQbA" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_CBva0yuuEd22R8pdt3nQbA" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_CBva1CuuEd22R8pdt3nQbA" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_CBva1SuuEd22R8pdt3nQbA" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_CBva1iuuEd22R8pdt3nQbA" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_CBva1yuuEd22R8pdt3nQbA" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_CBva2CuuEd22R8pdt3nQbA" URI="http://testB"/>
      </tasks>
      <gateways xmi:type="process:ParallelFork" xmi:id="_CBva2SuuEd22R8pdt3nQbA" ID="AND-Splita" name="activity Gateway"/>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_CBva2iuuEd22R8pdt3nQbA" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_CBva2yuuEd22R8pdt3nQbA" name="orga:assigneeCD"/>
      <end xmi:type="process:EndEvent" xmi:id="_CBva3CuuEd22R8pdt3nQbA" ID="EndEventc" name="End Event(activity)"/>
      <end xmi:type="process:EndEvent" xmi:id="_CBva3SuuEd22R8pdt3nQbA" ID="EndEventd" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_CBva3iuuEd22R8pdt3nQbA" ID="c" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_CBva3yuuEd22R8pdt3nQbA" URI="http://testC"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_CBva4CuuEd22R8pdt3nQbA" ID="d" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_CBva4SuuEd22R8pdt3nQbA" URI="http://testD"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_CBva4iuuEd22R8pdt3nQbA" source="_CBva0yuuEd22R8pdt3nQbA" target="_CBva1SuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva4yuuEd22R8pdt3nQbA" source="_CBva1yuuEd22R8pdt3nQbA" target="_CBva1CuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva5CuuEd22R8pdt3nQbA" source="_CBva3iuuEd22R8pdt3nQbA" target="_CBva3CuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva5SuuEd22R8pdt3nQbA" source="_CBva4CuuEd22R8pdt3nQbA" target="_CBva3SuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva5iuuEd22R8pdt3nQbA" source="_CBva1SuuEd22R8pdt3nQbA" target="_CBva2SuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva5yuuEd22R8pdt3nQbA" source="_CBva2SuuEd22R8pdt3nQbA" target="_CBva1yuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva6CuuEd22R8pdt3nQbA" source="_CBva2SuuEd22R8pdt3nQbA" target="_CBva3iuuEd22R8pdt3nQbA"/>
    <transitions xmi:type="process:Flow" xmi:id="_CBva6SuuEd22R8pdt3nQbA" source="_CBva2SuuEd22R8pdt3nQbA" target="_CBva4CuuEd22R8pdt3nQbA"/>
  </process:Process>
  <notation:Diagram xmi:id="_CBva6iuuEd22R8pdt3nQbA" type="Process" element="_CBva0CuuEd22R8pdt3nQbA" name="pChaining3.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_CCfBsCuuEd22R8pdt3nQbA" type="1001" element="_CBva0SuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCfBsyuuEd22R8pdt3nQbA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_CCfBtCuuEd22R8pdt3nQbA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_CCfBviuuEd22R8pdt3nQbA" type="2001" element="_CBva1SuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCfBwSuuEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfBvyuuEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfBwCuuEd22R8pdt3nQbA" x="121" y="98"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCfBwiuuEd22R8pdt3nQbA" type="2001" element="_CBva1yuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCfBxSuuEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfBwyuuEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfBxCuuEd22R8pdt3nQbA" x="313" y="218"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCfBxiuuEd22R8pdt3nQbA" type="2005" element="_CBva2SuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCfBySuuEd22R8pdt3nQbA" type="4005">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_CCfByiuuEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfBxyuuEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfByCuuEd22R8pdt3nQbA" x="141" y="218"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCfByyuuEd22R8pdt3nQbA" type="2007" element="_CBva0yuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCfBziuuEd22R8pdt3nQbA" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_CCfBzyuuEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfBzCuuEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfBzSuuEd22R8pdt3nQbA" x="156" y="15"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCfB0CuuEd22R8pdt3nQbA" type="2008" element="_CBva1CuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCfB0yuuEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_CCfB1CuuEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfB0SuuEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfB0iuuEd22R8pdt3nQbA" x="523" y="243"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_CCfBtSuuEd22R8pdt3nQbA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_CCfBtiuuEd22R8pdt3nQbA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfBsSuuEd22R8pdt3nQbA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfBsiuuEd22R8pdt3nQbA" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_CCfBtyuuEd22R8pdt3nQbA" type="1001" element="_CBva2iuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCfBuiuuEd22R8pdt3nQbA" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_CCfBuyuuEd22R8pdt3nQbA" type="5001">
        <children xmi:type="notation:Node" xmi:id="_CCoysCuuEd22R8pdt3nQbA" type="2001" element="_CBva3iuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCoysyuuEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCoysSuuEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCoysiuuEd22R8pdt3nQbA" x="25" y="59"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCoytCuuEd22R8pdt3nQbA" type="2001" element="_CBva4CuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCoytyuuEd22R8pdt3nQbA" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCoytSuuEd22R8pdt3nQbA" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCoytiuuEd22R8pdt3nQbA" x="215" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCoyuCuuEd22R8pdt3nQbA" type="2008" element="_CBva3CuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCoyuyuuEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_CCoyvCuuEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCoyuSuuEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCoyuiuuEd22R8pdt3nQbA" x="73" y="179"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_CCoyvSuuEd22R8pdt3nQbA" type="2008" element="_CBva3SuuEd22R8pdt3nQbA">
          <children xmi:type="notation:Node" xmi:id="_CCoywCuuEd22R8pdt3nQbA" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_CCoywSuuEd22R8pdt3nQbA" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_CCoyviuuEd22R8pdt3nQbA"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCoyvyuuEd22R8pdt3nQbA" x="250" y="175"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_CCfBvCuuEd22R8pdt3nQbA"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_CCfBvSuuEd22R8pdt3nQbA"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_CCfBuCuuEd22R8pdt3nQbA" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_CCfBuSuuEd22R8pdt3nQbA" x="16" y="282" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_CBva6yuuEd22R8pdt3nQbA"/>
    <edges xmi:type="notation:Edge" xmi:id="_CCx8oCuuEd22R8pdt3nQbA" type="3001" element="_CBva4iuuEd22R8pdt3nQbA" source="_CCfByyuuEd22R8pdt3nQbA" target="_CCfBviuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCx8pCuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CCx8pSuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CCx8oSuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CCx8oiuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CCx8oyuuEd22R8pdt3nQbA" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CCx8piuuEd22R8pdt3nQbA" type="3001" element="_CBva4yuuEd22R8pdt3nQbA" source="_CCfBwiuuEd22R8pdt3nQbA" target="_CCfB0CuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCx8qiuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CCx8qyuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CCx8pyuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CCx8qCuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CCx8qSuuEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CCx8rCuuEd22R8pdt3nQbA" type="3001" element="_CBva5CuuEd22R8pdt3nQbA" source="_CCoysCuuEd22R8pdt3nQbA" target="_CCoyuCuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCx8sCuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CCx8sSuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CCx8rSuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CCx8riuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CCx8ryuuEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CCx8siuuEd22R8pdt3nQbA" type="3001" element="_CBva5SuuEd22R8pdt3nQbA" source="_CCoytCuuEd22R8pdt3nQbA" target="_CCoyvSuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCx8tiuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CCx8tyuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CCx8syuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CCx8tCuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CCx8tSuuEd22R8pdt3nQbA" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CCx8uCuuEd22R8pdt3nQbA" type="3001" element="_CBva5iuuEd22R8pdt3nQbA" source="_CCfBviuuEd22R8pdt3nQbA" target="_CCfBxiuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CCx8vCuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CCx8vSuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CCx8uSuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CCx8uiuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CCx8uyuuEd22R8pdt3nQbA" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CCx8viuuEd22R8pdt3nQbA" type="3001" element="_CBva5yuuEd22R8pdt3nQbA" source="_CCfBxiuuEd22R8pdt3nQbA" target="_CCfBwiuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CC7toCuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CC7toSuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CCx8vyuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CCx8wCuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CCx8wSuuEd22R8pdt3nQbA" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CC7toiuuEd22R8pdt3nQbA" type="3001" element="_CBva6CuuEd22R8pdt3nQbA" source="_CCfBxiuuEd22R8pdt3nQbA" target="_CCoysCuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CC7tpiuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CC7tpyuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CC7toyuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CC7tpCuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CC7tpSuuEd22R8pdt3nQbA" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CC7tqCuuEd22R8pdt3nQbA" type="3001" element="_CBva6SuuEd22R8pdt3nQbA" source="_CCfBxiuuEd22R8pdt3nQbA" target="_CCoytCuuEd22R8pdt3nQbA">
      <children xmi:type="notation:Node" xmi:id="_CC7trCuuEd22R8pdt3nQbA" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_CC7trSuuEd22R8pdt3nQbA" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_CC7tqSuuEd22R8pdt3nQbA" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CC7tqiuuEd22R8pdt3nQbA"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CC7tqyuuEd22R8pdt3nQbA" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

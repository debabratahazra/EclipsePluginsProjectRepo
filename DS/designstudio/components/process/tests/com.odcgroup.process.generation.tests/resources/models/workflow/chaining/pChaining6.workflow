<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_PG198CuwEd2zdf6-rjEAtw" name="pChaining6" description="test process" displayName="process p1" filename="modelParsingSimpleChaining">
    <pools xmi:type="process:Pool" xmi:id="_PG198SuwEd2zdf6-rjEAtw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_PG198iuwEd2zdf6-rjEAtw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_PG198yuwEd2zdf6-rjEAtw" ID="StartEvent" name="Start Event"/>
      <end xmi:type="process:EndEvent" xmi:id="_PG199CuwEd2zdf6-rjEAtw" ID="EndEventb" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_PG199SuwEd2zdf6-rjEAtw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_PG199iuwEd2zdf6-rjEAtw" URI="http://testA"/>
      </tasks>
      <tasks xmi:type="process:UserTask" xmi:id="_PG199yuwEd2zdf6-rjEAtw" ID="b" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_PG19-CuwEd2zdf6-rjEAtw" URI="http://testB"/>
      </tasks>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_PG19-SuwEd2zdf6-rjEAtw" source="_PG198yuwEd2zdf6-rjEAtw" target="_PG199SuwEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_PG19-iuwEd2zdf6-rjEAtw" source="_PG199yuwEd2zdf6-rjEAtw" target="_PG199CuwEd2zdf6-rjEAtw"/>
    <transitions xmi:type="process:Flow" xmi:id="_PG19-yuwEd2zdf6-rjEAtw" source="_PG199SuwEd2zdf6-rjEAtw" target="_PG199yuwEd2zdf6-rjEAtw"/>
  </process:Process>
  <notation:Diagram xmi:id="_PG19_CuwEd2zdf6-rjEAtw" type="Process" element="_PG198CuwEd2zdf6-rjEAtw" name="pChaining6.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_PHca4CuwEd2zdf6-rjEAtw" type="1001" element="_PG198SuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_PHca4yuwEd2zdf6-rjEAtw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_PHca5CuwEd2zdf6-rjEAtw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_PHmL4CuwEd2zdf6-rjEAtw" type="2001" element="_PG199SuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_PHmL4yuwEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_PHmL4SuwEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_PHmL4iuwEd2zdf6-rjEAtw" x="55" y="145"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_PHmL5CuwEd2zdf6-rjEAtw" type="2001" element="_PG199yuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_PHmL5yuwEd2zdf6-rjEAtw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_PHmL5SuwEd2zdf6-rjEAtw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_PHmL5iuwEd2zdf6-rjEAtw" x="55" y="265"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_PHmL6CuwEd2zdf6-rjEAtw" type="2007" element="_PG198yuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_PHmL6yuwEd2zdf6-rjEAtw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_PHmL7CuwEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_PHmL6SuwEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_PHmL6iuwEd2zdf6-rjEAtw" x="90" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_PHmL7SuwEd2zdf6-rjEAtw" type="2008" element="_PG199CuwEd2zdf6-rjEAtw">
          <children xmi:type="notation:Node" xmi:id="_PHmL8CuwEd2zdf6-rjEAtw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_PHmL8SuwEd2zdf6-rjEAtw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_PHmL7iuwEd2zdf6-rjEAtw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_PHmL7yuwEd2zdf6-rjEAtw" x="90" y="385"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_PHca5SuwEd2zdf6-rjEAtw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_PHca5iuwEd2zdf6-rjEAtw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_PHca4SuwEd2zdf6-rjEAtw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_PHca4iuwEd2zdf6-rjEAtw"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_PG19_SuwEd2zdf6-rjEAtw"/>
    <edges xmi:type="notation:Edge" xmi:id="_PHmL8iuwEd2zdf6-rjEAtw" type="3001" element="_PG19-SuwEd2zdf6-rjEAtw" source="_PHmL6CuwEd2zdf6-rjEAtw" target="_PHmL4CuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_PHmL9iuwEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_PHmL9yuwEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_PHmL8yuwEd2zdf6-rjEAtw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_PHmL9CuwEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_PHmL9SuwEd2zdf6-rjEAtw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_PHmL-CuwEd2zdf6-rjEAtw" type="3001" element="_PG19-iuwEd2zdf6-rjEAtw" source="_PHmL5CuwEd2zdf6-rjEAtw" target="_PHmL7SuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_PHmL_CuwEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_PHmL_SuwEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_PHmL-SuwEd2zdf6-rjEAtw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_PHmL-iuwEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_PHmL-yuwEd2zdf6-rjEAtw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_PHvV0CuwEd2zdf6-rjEAtw" type="3001" element="_PG19-yuwEd2zdf6-rjEAtw" source="_PHmL4CuwEd2zdf6-rjEAtw" target="_PHmL5CuwEd2zdf6-rjEAtw">
      <children xmi:type="notation:Node" xmi:id="_PHvV1CuwEd2zdf6-rjEAtw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_PHvV1SuwEd2zdf6-rjEAtw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_PHvV0SuwEd2zdf6-rjEAtw" routing="Rectilinear" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_PHvV0iuwEd2zdf6-rjEAtw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_PHvV0yuwEd2zdf6-rjEAtw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

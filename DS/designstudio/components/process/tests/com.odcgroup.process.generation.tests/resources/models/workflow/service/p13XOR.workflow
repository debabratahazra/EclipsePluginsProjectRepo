<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.1/notation" xmlns:process="http://www.odcgroup.com/process">
  <process:Process xmi:id="_0PtEUDXfEd22A_2b3pOiPw" name="p13XOR" description="test process with XOR split and join as service" displayName="process p13XOR" filename="modelParsing13serviceXOR">
    <pools xmi:type="process:Pool" xmi:id="_0PtEUTXfEd22A_2b3pOiPw" ID="Pool1" name="Pool (1)">
      <assignee xmi:type="process:Assignee" xmi:id="_0PtEUjXfEd22A_2b3pOiPw" name="orga:assigneeA"/>
      <start xmi:type="process:StartEvent" xmi:id="_0PtEUzXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0PtEVDXfEd22A_2b3pOiPw" ID="a" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0PtEVTXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_0PtEVjXfEd22A_2b3pOiPw" ID="ComplexGatewaya" name="Complex Gateway (activity)">
        <rule xmi:type="process:Rule" xmi:id="_0PtEVzXfEd22A_2b3pOiPw" name="selector-xor-1"/>
      </gateways>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_0PtEWDXfEd22A_2b3pOiPw" ID="Pool2" name="Pool (2)">
      <assignee xmi:type="process:Assignee" xmi:id="_0PtEWTXfEd22A_2b3pOiPw" name="orga:assigneeB"/>
      <start xmi:type="process:StartEvent" xmi:id="_0PtEWjXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0PtEWzXfEd22A_2b3pOiPw" ID="b" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0PtEXDXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_0PtEXTXfEd22A_2b3pOiPw" ID="Pool3" name="Pool (3)">
      <assignee xmi:type="process:Assignee" xmi:id="_0PtEXjXfEd22A_2b3pOiPw" name="orga:assigneeC"/>
      <start xmi:type="process:StartEvent" xmi:id="_0PtEXzXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0PtEYDXfEd22A_2b3pOiPw" ID="c" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0PtEYTXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_0PtEYjXfEd22A_2b3pOiPw" ID="Pool4" name="Pool (4)">
      <assignee xmi:type="process:Assignee" xmi:id="_0PtEYzXfEd22A_2b3pOiPw" name="orga:assigneeD"/>
      <start xmi:type="process:StartEvent" xmi:id="_0PtEZDXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0PtEZTXfEd22A_2b3pOiPw" ID="d" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0PtEZjXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_0PtEZzXfEd22A_2b3pOiPw" ID="Pool5" name="Pool (5)">
      <assignee xmi:type="process:Assignee" xmi:id="_0PtEaDXfEd22A_2b3pOiPw" name="orga:assigneeE"/>
      <start xmi:type="process:StartEvent" xmi:id="_0PtEaTXfEd22A_2b3pOiPw" ID="StartEvent" name="Start Event"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0PtEajXfEd22A_2b3pOiPw" ID="e" name="activity" description="an activity" initial="true">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0PtEazXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
    </pools>
    <pools xmi:type="process:Pool" xmi:id="_0PtEbDXfEd22A_2b3pOiPw" ID="Pool6" name="Pool (6)">
      <assignee xmi:type="process:Assignee" xmi:id="_0PtEbTXfEd22A_2b3pOiPw" name="orga:assigneeF"/>
      <end xmi:type="process:EndEvent" xmi:id="_0PtEbjXfEd22A_2b3pOiPw" ID="EndEventf" name="End Event(activity)"/>
      <tasks xmi:type="process:UserTask" xmi:id="_0PtEbzXfEd22A_2b3pOiPw" ID="f" name="activity" description="an activity" initial="false">
        <pageflow xmi:type="process:Pageflow" xmi:id="_0PtEcDXfEd22A_2b3pOiPw" URI="inc"/>
      </tasks>
      <gateways xmi:type="process:ComplexGateway" xmi:id="_0PtEcTXfEd22A_2b3pOiPw" ID="ComplexMergef" name="activity Gateway">
        <rule xmi:type="process:Rule" xmi:id="_0PtEcjXfEd22A_2b3pOiPw" name="pre-selector-xor-1"/>
      </gateways>
    </pools>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEczXfEd22A_2b3pOiPw" source="_0PtEUzXfEd22A_2b3pOiPw" target="_0PtEVDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEdDXfEd22A_2b3pOiPw" source="_0PtEWjXfEd22A_2b3pOiPw" target="_0PtEWzXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEdTXfEd22A_2b3pOiPw" source="_0PtEXzXfEd22A_2b3pOiPw" target="_0PtEYDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEdjXfEd22A_2b3pOiPw" source="_0PtEZDXfEd22A_2b3pOiPw" target="_0PtEZTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEdzXfEd22A_2b3pOiPw" source="_0PtEaTXfEd22A_2b3pOiPw" target="_0PtEajXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEeDXfEd22A_2b3pOiPw" source="_0PtEbzXfEd22A_2b3pOiPw" target="_0PtEbjXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEeTXfEd22A_2b3pOiPw" source="_0PtEVjXfEd22A_2b3pOiPw" target="_0PtEWzXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEejXfEd22A_2b3pOiPw" source="_0PtEVjXfEd22A_2b3pOiPw" target="_0PtEYDXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEezXfEd22A_2b3pOiPw" source="_0PtEVjXfEd22A_2b3pOiPw" target="_0PtEZTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEfDXfEd22A_2b3pOiPw" source="_0PtEVjXfEd22A_2b3pOiPw" target="_0PtEajXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEfTXfEd22A_2b3pOiPw" source="_0PtEVDXfEd22A_2b3pOiPw" target="_0PtEVjXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEfjXfEd22A_2b3pOiPw" source="_0PtEcTXfEd22A_2b3pOiPw" target="_0PtEbzXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEfzXfEd22A_2b3pOiPw" source="_0PtEWzXfEd22A_2b3pOiPw" target="_0PtEcTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEgDXfEd22A_2b3pOiPw" source="_0PtEYDXfEd22A_2b3pOiPw" target="_0PtEcTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEgTXfEd22A_2b3pOiPw" source="_0PtEZTXfEd22A_2b3pOiPw" target="_0PtEcTXfEd22A_2b3pOiPw"/>
    <transitions xmi:type="process:Flow" xmi:id="_0PtEgjXfEd22A_2b3pOiPw" source="_0PtEajXfEd22A_2b3pOiPw" target="_0PtEcTXfEd22A_2b3pOiPw"/>
  </process:Process>
  <notation:Diagram xmi:id="_0PtEgzXfEd22A_2b3pOiPw" type="Process" element="_0PtEUDXfEd22A_2b3pOiPw" name="p13XOR.workflow" measurementUnit="Pixel">
    <children xmi:type="notation:Node" xmi:id="_0QmcMDXfEd22A_2b3pOiPw" type="1001" element="_0PtEUTXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0QmcMzXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0QmcNDXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0QmcWjXfEd22A_2b3pOiPw" type="2001" element="_0PtEVDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QmcXTXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcWzXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcXDXfEd22A_2b3pOiPw" x="49" y="98"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0QmcXjXfEd22A_2b3pOiPw" type="2003" element="_0PtEVjXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QmcYTXfEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0QmcYjXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcXzXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcYDXfEd22A_2b3pOiPw" x="241" y="98"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0QmcYzXfEd22A_2b3pOiPw" type="2007" element="_0PtEUzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QmcZjXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0QmcZzXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcZDXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcZTXfEd22A_2b3pOiPw" x="73" y="26"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0QmcNTXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0QmcNjXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcMTXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcMjXfEd22A_2b3pOiPw" x="16" y="16" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0QmcNzXfEd22A_2b3pOiPw" type="1001" element="_0PtEWDXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0QmcOjXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0QmcOzXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0QvmIDXfEd22A_2b3pOiPw" type="2001" element="_0PtEWzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QvmIzXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmITXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmIjXfEd22A_2b3pOiPw" x="409" y="80"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0QvmJDXfEd22A_2b3pOiPw" type="2007" element="_0PtEWjXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QvmJzXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0QvmKDXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmJTXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmJjXfEd22A_2b3pOiPw" x="601" y="104"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0QmcPDXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0QmcPTXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcODXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcOTXfEd22A_2b3pOiPw" x="16" y="312" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0QmcPjXfEd22A_2b3pOiPw" type="1001" element="_0PtEXTXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0QmcQTXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0QmcQjXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0QvmKTXfEd22A_2b3pOiPw" type="2001" element="_0PtEYDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QvmLDXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmKjXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmKzXfEd22A_2b3pOiPw" x="409" y="102"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0QvmLTXfEd22A_2b3pOiPw" type="2007" element="_0PtEXzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QvmMDXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0QvmMTXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmLjXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmLzXfEd22A_2b3pOiPw" x="457" y="40"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0QmcQzXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0QmcRDXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcPzXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcQDXfEd22A_2b3pOiPw" x="16" y="578" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0QmcRTXfEd22A_2b3pOiPw" type="1001" element="_0PtEYjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0QmcSDXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0QmcSTXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0QvmMjXfEd22A_2b3pOiPw" type="2001" element="_0PtEZTXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QvmNTXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmMzXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmNDXfEd22A_2b3pOiPw" x="409" y="124"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0QvmNjXfEd22A_2b3pOiPw" type="2007" element="_0PtEZDXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0QvmOTXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0QvmOjXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmNzXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmODXfEd22A_2b3pOiPw" x="505" y="20"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0QmcSjXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0QmcSzXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcRjXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcRzXfEd22A_2b3pOiPw" x="16" y="844" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0QmcTDXfEd22A_2b3pOiPw" type="1001" element="_0PtEZzXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0QmcTzXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0QmcUDXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0QvmOzXfEd22A_2b3pOiPw" type="2001" element="_0PtEajXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0Q5XIDXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0QvmPDXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QvmPTXfEd22A_2b3pOiPw" x="337" y="122"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0Q5XITXfEd22A_2b3pOiPw" type="2007" element="_0PtEaTXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0Q5XJDXfEd22A_2b3pOiPw" type="4007">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0Q5XJTXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0Q5XIjXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0Q5XIzXfEd22A_2b3pOiPw" x="481" y="24"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0QmcUTXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0QmcUjXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcTTXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcTjXfEd22A_2b3pOiPw" x="16" y="1110" width="900" height="250"/>
    </children>
    <children xmi:type="notation:Node" xmi:id="_0QmcUzXfEd22A_2b3pOiPw" type="1001" element="_0PtEbDXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0QmcVjXfEd22A_2b3pOiPw" type="4009"/>
      <children xmi:type="notation:Node" xmi:id="_0QmcVzXfEd22A_2b3pOiPw" type="5001">
        <children xmi:type="notation:Node" xmi:id="_0Q5XJjXfEd22A_2b3pOiPw" type="2001" element="_0PtEbzXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0Q5XKTXfEd22A_2b3pOiPw" type="4001"/>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0Q5XJzXfEd22A_2b3pOiPw" fillColor="7266040" lineColor="0"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0Q5XKDXfEd22A_2b3pOiPw" x="55" y="175"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0Q5XKjXfEd22A_2b3pOiPw" type="2003" element="_0PtEcTXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0Q5XLTXfEd22A_2b3pOiPw" type="4003">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0Q5XLjXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0Q5XKzXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0Q5XLDXfEd22A_2b3pOiPw" x="75" y="55"/>
        </children>
        <children xmi:type="notation:Node" xmi:id="_0Q5XLzXfEd22A_2b3pOiPw" type="2008" element="_0PtEbjXfEd22A_2b3pOiPw">
          <children xmi:type="notation:Node" xmi:id="_0Q5XMjXfEd22A_2b3pOiPw" type="4008">
            <layoutConstraint xmi:type="notation:Location" xmi:id="_0Q5XMzXfEd22A_2b3pOiPw" y="5"/>
          </children>
          <styles xmi:type="notation:ShapeStyle" xmi:id="_0Q5XMDXfEd22A_2b3pOiPw"/>
          <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0Q5XMTXfEd22A_2b3pOiPw" x="90" y="295"/>
        </children>
        <styles xmi:type="notation:SortingStyle" xmi:id="_0QmcWDXfEd22A_2b3pOiPw"/>
        <styles xmi:type="notation:FilteringStyle" xmi:id="_0QmcWTXfEd22A_2b3pOiPw"/>
      </children>
      <styles xmi:type="notation:ShapeStyle" xmi:id="_0QmcVDXfEd22A_2b3pOiPw" fillColor="12312300" lineColor="0"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0QmcVTXfEd22A_2b3pOiPw" x="16" y="1376" width="900" height="250"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_0PtEhDXfEd22A_2b3pOiPw"/>
    <edges xmi:type="notation:Edge" xmi:id="_0RMSEDXfEd22A_2b3pOiPw" type="3001" element="_0PtEczXfEd22A_2b3pOiPw" source="_0QmcYzXfEd22A_2b3pOiPw" target="_0QmcWjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RMSFDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RMSFTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RMSETXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RMSEjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RMSEzXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RMSFjXfEd22A_2b3pOiPw" type="3001" element="_0PtEdDXfEd22A_2b3pOiPw" source="_0QvmJDXfEd22A_2b3pOiPw" target="_0QvmIDXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RMSGjXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RMSGzXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RMSFzXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RMSGDXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RMSGTXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RoW8DXfEd22A_2b3pOiPw" type="3001" element="_0PtEdTXfEd22A_2b3pOiPw" source="_0QvmLTXfEd22A_2b3pOiPw" target="_0QvmKTXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RoW9DXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RoW9TXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RoW8TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RoW8jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RoW8zXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RoW9jXfEd22A_2b3pOiPw" type="3001" element="_0PtEdjXfEd22A_2b3pOiPw" source="_0QvmNjXfEd22A_2b3pOiPw" target="_0QvmMjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RoW-jXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RoW-zXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RoW9zXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RoW-DXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RoW-TXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RoW_DXfEd22A_2b3pOiPw" type="3001" element="_0PtEdzXfEd22A_2b3pOiPw" source="_0Q5XITXfEd22A_2b3pOiPw" target="_0QvmOzXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RoXADXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RoXATXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RoW_TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RoW_jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RoW_zXfEd22A_2b3pOiPw" points="[0, 15, 0, -90]$[0, 75, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RoXAjXfEd22A_2b3pOiPw" type="3001" element="_0PtEeDXfEd22A_2b3pOiPw" source="_0Q5XJjXfEd22A_2b3pOiPw" target="_0Q5XLzXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RoXBjXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RoXBzXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RoXAzXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RoXBDXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RoXBTXfEd22A_2b3pOiPw" points="[0, 30, 0, -75]$[0, 90, 0, -15]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RyH8DXfEd22A_2b3pOiPw" type="3001" element="_0PtEeTXfEd22A_2b3pOiPw" source="_0QmcXjXfEd22A_2b3pOiPw" target="_0QvmIDXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RyH9DXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RyH9TXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RyH8TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RyH8jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RyH8zXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RyH9jXfEd22A_2b3pOiPw" type="3001" element="_0PtEejXfEd22A_2b3pOiPw" source="_0QmcXjXfEd22A_2b3pOiPw" target="_0QvmKTXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RyH-jXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RyH-zXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RyH9zXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RyH-DXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RyH-TXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RyH_DXfEd22A_2b3pOiPw" type="3001" element="_0PtEezXfEd22A_2b3pOiPw" source="_0QmcXjXfEd22A_2b3pOiPw" target="_0QvmMjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RyIADXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RyIATXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RyH_TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RyH_jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RyH_zXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RyIAjXfEd22A_2b3pOiPw" type="3001" element="_0PtEfDXfEd22A_2b3pOiPw" source="_0QmcXjXfEd22A_2b3pOiPw" target="_0QvmOzXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RyIBjXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RyIBzXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RyIAzXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RyIBDXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RyIBTXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RyICDXfEd22A_2b3pOiPw" type="3001" element="_0PtEfTXfEd22A_2b3pOiPw" source="_0QmcWjXfEd22A_2b3pOiPw" target="_0QmcXjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RyIDDXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RyIDTXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RyICTXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RyICjXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RyICzXfEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0RyIDjXfEd22A_2b3pOiPw" type="3001" element="_0PtEfjXfEd22A_2b3pOiPw" source="_0Q5XKjXfEd22A_2b3pOiPw" target="_0Q5XJjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0RyIEjXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0RyIEzXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0RyIDzXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0RyIEDXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0RyIETXfEd22A_2b3pOiPw" points="[0, 30, 0, -90]$[0, 90, 0, -30]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0R748DXfEd22A_2b3pOiPw" type="3001" element="_0PtEfzXfEd22A_2b3pOiPw" source="_0QvmIDXfEd22A_2b3pOiPw" target="_0Q5XKjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0R749DXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0R749TXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0R748TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0R748jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0R748zXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0R749jXfEd22A_2b3pOiPw" type="3001" element="_0PtEgDXfEd22A_2b3pOiPw" source="_0QvmKTXfEd22A_2b3pOiPw" target="_0Q5XKjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0R74-jXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0R74-zXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0R749zXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0R74-DXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0R74-TXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0R74_DXfEd22A_2b3pOiPw" type="3001" element="_0PtEgTXfEd22A_2b3pOiPw" source="_0QvmMjXfEd22A_2b3pOiPw" target="_0Q5XKjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0R75ADXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0R75ATXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0R74_TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0R74_jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0R74_zXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_0SFC4DXfEd22A_2b3pOiPw" type="3001" element="_0PtEgjXfEd22A_2b3pOiPw" source="_0QvmOzXfEd22A_2b3pOiPw" target="_0Q5XKjXfEd22A_2b3pOiPw">
      <children xmi:type="notation:Node" xmi:id="_0SFC5DXfEd22A_2b3pOiPw" type="4010">
        <layoutConstraint xmi:type="notation:Location" xmi:id="_0SFC5TXfEd22A_2b3pOiPw" y="40"/>
      </children>
      <styles xmi:type="notation:ConnectorStyle" xmi:id="_0SFC4TXfEd22A_2b3pOiPw" jumpLinkStatus="Above" lineColor="0"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_0SFC4jXfEd22A_2b3pOiPw"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_0SFC4zXfEd22A_2b3pOiPw" points="[0, 0, 0, 0]$[0, 0, 0, 0]"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>

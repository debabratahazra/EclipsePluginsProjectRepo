<%--
  #%L
  useragent-odata-html5
  %%
  Copyright (C) 2012 - 2013 Temenos Holdings N.V.
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  #L%
  --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>IRIS navigator</title>
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico">
    <link href="Styles/Site.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="Stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/themes/redmond/jquery-ui.css" /> 

	<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
	<script type="text/javascript" src="Scripts/underscore-1.4.3-min.js"></script>
    <script type="text/javascript" src="Scripts/datajs-1.1.0-iris.js"></script>
</head>
<body>
    
    <div class="page">
        <div class="header">
            <div class="title">
                <h1>ODataJS Browser</h1>
            </div>
            <div class="loginDisplay"></div>
            <div class="clear hideSkiplink"></div>
            <div class="navBar">
                <form>
                    <input disabled="true" type="text" name="odata-svc" id="where" value="<%=request.getParameter("odata-svc")%>" class="text ui-widget-content ui-corner-all" />
                </form>
            </div>
        </div>
		<div class="leftCol">
            <div id="service-root" class="ui-widget">
	            <table id="services" class="ui-widget ui-widget-content">
			            <tr class="ui-widget-header ">
				            <th>EntitySet</th>
			            </tr>
	            </table>
                <span id="loadingServices" style="display:none">Loading services...</span>
                <span id="loadingMetadata" style="display:none">Loading metadata...</span>
            </div>
		</div>
        <div class="main">
            <div id="dialog-form" title="Create new user">
	            <form>
	            <fieldset id="dialog-form-fields">
	            </fieldset>
	            </form>
            </div>

            <div id="entityset-contain" class="ui-widget">
	            <h1 id="EntitySetName">Select EntitySet</h1>
                <button id="createEntity" style="display:none">Create new item</button>
	            <table id="entities" class="ui-widget ui-widget-content">
                    <tr class="ui-widget-header "></tr>
	            </table>
                <span id="loadingEntities" style="display:none">Loading...</span>
            </div>
        </div>
        <div class="clear"></div>
        <div class="footer"></div>
    </div>

    <script type="text/javascript" src="Scripts/DataJSCRUD.js"></script>
    <script type="text/javascript">
        $(OnPageLoad)
    </script>
</body>
</html>

























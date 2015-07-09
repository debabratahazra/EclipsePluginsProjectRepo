/*
 * #%L
 * useragent-odata-html5
 * %%
 * Copyright (C) 2012 - 2013 Temenos Holdings N.V.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
// Test
// OData Root Service URI
var ODATA_SVC = $("#where");
var tips = $(".validateTips");

// Page Load Actions

function OnPageLoad() 
{
    $("#dialog:ui-dialog").dialog("destroy");

    $("#dialog-form").dialog({
        autoOpen: false,
        height: 375,
        width: 450,
        modal: true,
        close: function () {
        }
    });

    $("#createEntity").button()
			.click(OpenCreateUserDialog);

    GetMetadata();
    GetServices();    
} 

//Page Events:

//Get the metadata document
function GetMetadata() 
{
    $("#loadingMetadata").show();
	var uriMetadata = ODATA_SVC.val() + "$metadata";
	OData.read(uriMetadata, function (metadata) {
		OData.defaultMetadata.push(metadata);		//Set result as the default metadata document
	    $("#loadingMetadata").hide();
	}, ErrorCallback, OData.metadataHandler);
}
//Gets all the services
function GetServices() 
{
    $("#loadingServices").show();
    OData.read({ 
    		requestUri: ODATA_SVC.val(),
    		headers: { Accept: "application/atomsvc+xml" }
    	}, GetServicesCallback, ErrorCallback);
}
//GetServices Success Callback
function GetServicesCallback(data, request) 
{
    $("#loadingServices").hide();
    $("#services").find("tr:gt(0)").remove();
    ApplyServiceTemplate(data.workspaces[0].collections);
}

//***********************Get Notes (READ)***************************
//Gets all the Entities for an EntitySet
function GetEntitySet(uri) 
{
    $("#loadingEntities").show();
	$("#createEntity").hide();
    $("#entities").find("tr:gt(0)").remove();
    uri = _.unescape(uri);
    OData.read({ 
		requestUri: uri,
		headers: { Accept: "application/atom+xml" }
	}, GetEntitySetCallback, ErrorCallback);
}

//GetEntitySet Success Callback
function GetEntitySetCallback(data, request) 
{
    $("#loadingEntities").hide();
    $("#EntitySetName").text(data.__metadata.title)
    
    // OData js doesn't provide us with the link relations, parse them ourselves :-(
    
    if (data.results != null) {
        var entriesLinks = parseFeedEntryLinks(request);
        RenderEntitySet(data.results, entriesLinks);
    } else {
        RenderEntity(data, parseEntryLinks(request));
    }
}

//given an Atom feed response, parse each entry and their links
function parseFeedEntryLinks(request) {
	var entriesLinks = [];
    var xmlDoc = $.parseXML( request.body ),
	$xml = $( xmlDoc ),
	$feed = $xml.find( "feed" );

    var entries = $feed.children("entry");
	if (entries.length > 0) {
		entries.each(function (){
    		var $this = $(this);
    		var id = $this.children("id").text();
    		entriesLinks[id] = parseEntry($this);
		});
		
	}
	return entriesLinks;
}

// given an Atom entry response, parse the links
function parseEntryLinks(request) {
    var xmlDoc = $.parseXML( request.body ),
		$xml = $( xmlDoc ),
		$entry = $xml.find( "entry" );
    return parseEntry($entry);
}

function parseEntry($entry) {
	var mapLinksKeyRel = new Object();
    var atomLinks = $entry.children('link');
    if (atomLinks.length > 0) {
    	atomLinks.each(function (){
    		var $this = $(this);
    		var link = new Link($this.attr("title"), $this.attr("rel"), $this.attr("href"), $this.attr("type"));
    		mapLinksKeyRel[link.rel] = link;
    	});
    }
    return mapLinksKeyRel;
}

function Link(title, rel, href, type) {
	this.title = title;
	this.rel = rel;
	this.href = href;
	this.type = type;
}

//***********************End: Get GetEntitySet***************************

//*****************************Add Entity (CREATE)***************************
//Handle Create User Entity button click
function OpenCreateUserDialog() 
{
    $("#dialog-form").dialog("option", "title", "Create An Account");
    $("#dialog-form").dialog("option", "buttons", [
                                                            {
                                                                text: "Save",
                                                                click: function () {
                                                                    var bValid = false;
                                                                    bValid = ValidateUserData();
                                                                    if (bValid) {
                                                                        AddUser();
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                text: "Cancel",
                                                                click: function () {
                                                                    $("#dialog-form").dialog("close");
                                                                }
                                                            }
                                                        ]
                                    );
    $("#dialog-form").dialog("open");
}

//Handle the DataJS call for new Entity creation
function AddUser() 
{
    $("#loading").show();
    var newUserdata = { username: $("#name").val(), email: $("#email").val(), password: $("#password").val() };
    var requestOptions = {
        requestUri: USERS_ODATA_SVC,
        method: "POST",
        data: newUserdata
    };

    OData.request(requestOptions, AddSuccessCallback, ErrorCallback);

}

//AddEntity Success Callback
function AddSuccessCallback(data, request) 
{
    $("#loading").hide('slow');
    $("#dialog-form").dialog("close");
    GetUsers();
}

//*************************End Add Entity***************************

//*************************Update Entity (UPDATE)***************************
//Handle Update hyper link click
function OpenUpdateDialog(entityName, row, href) 
{
    $("#loading").hide();
    
    //Add form fields
    var metadata = OData.defaultMetadata[0];
    AddFormFields(entityName, metadata, "dialog-form-fields");

    //Populate fields
	var entityType = OData.lookupEntityType(entityName, metadata);
	if(entityType != undefined) {
  	    for (i in entityType.property) {
  	   		var property = entityType.property[i];
    	    var entityCell = $("#entityCell_" + row + "_" + property.name);
    		$("#inputForm_" + property.name).val(entityCell.eq(0).text());
  	    }
	}

    $("#dialog-form").dialog("option", "buttons", [
                        {
                            text: "Save",
                            click: function () {
                                var bValid = ValidateData();
                                if (bValid) {
                                    UpdateResource(entityName, href);
                                }
                            }
                        },
                        {
                            text: "Cancel",
                            click: function () {
                                $("#dialog-form").dialog("close");
                            }
                        }
                    ]);
    $("#dialog-form").dialog("open");
}

//Handle DataJS calls to Update user data
function UpdateResource(entityName, href) 
{
    $("#loading").show();
    var requestURI = ODATA_SVC.val() + _.unescape(href);
	var data = [];
	$('#dialog-form').find('fieldset input').each(function(){
		var key = $(this).eq(0).attr('name'),
	        val = $(this).eq(0).val();
		data[key] = val;
	})
	
	var oHeaders = {};
	oHeaders['Content-Type'] = "application/atom+xml; charset=utf-8";
	oHeaders['DataServiceVersion'] = "2.0";
	OData.defaultHandler = OData.atomHandler;
	OData.request( {
			headers : oHeaders, // object that contains HTTP headers as name value pairs 
			requestUri: requestURI,
			method: "PUT",
			data: data
			}, UpdateSuccessCallback, ErrorCallback);	
}

//UpdateResource Suceess callback
function UpdateSuccessCallback(data, response) {
    $("#loading").hide('slow');
    $("#dialog-form").dialog("close");
    GetEntitySet(response.requestUri);
}

//*************************End : Update User (UPDATE)***************************

//*************************Delete User (DELETE)***************************

var $dialog = null;

//Handle Delete hyperlink click
function OpenDeleteDialog(entityUri) 
{
    $("#loading").hide();

    entityUri = _.unescape(entityUri);
    // append baseuri if the link is relative
    if (entityUri.indexOf("http://") == -1) {
    	entityUri = ODATA_SVC.val() + entityUri;
    }
    $dialog = $('<div></div>')
		            .html('You are about to delete entity at "' + entityUri + '". Do you want to continue? ')
		            .dialog({
		                autoOpen: false,
                        width:400,
		                modal: true,
		                buttons: {
		                    "Yes": function () {
		                    	DeleteEntity(entityUri);
		                    },
		                    "No": function () {
		                        $(this).dialog("close");
		                    }
		                },
		                title: 'Delete Account'
		            });
        $dialog.dialog('open');
}

//Handles DataJS calls for delete user
function DeleteEntity(entityUri) 
{
	entityUri = _.unescape(entityUri);
	var requestOptions = {
                            requestUri: entityUri,
                            method: "DELETE",
                            headers: { Accept: "application/atom+xml" }
                        };

    OData.request(requestOptions, DeleteSuccessCallback, ErrorCallback);
}

//DeleteUser Success callback
function DeleteSuccessCallback(data, request)
{
    $dialog.dialog('close');
    // Handle response from redirect (303), or will need to redisplay current view (205)
    if (request.statusCode == 200) {
        GetEntitySetCallback(data, request);
    } else {
        alert("Unhandled statusCode, is it a redisplay? " + request.statusCode);
    }
}

//*************************End : Delete User (DELETE)***************************

//*************************Helper Functions***************************

//Helper function to apply UI template for list of services
function ApplyServiceTemplate(links) 
{
	for (obj in links) {
		var link = links[obj];
		var content = "<tr id=\"entitySet_" + link.title + "\">" +
							"<td><a href=\"javascript:GetEntitySet('" + escapeURI(link.href) + "')\">" + link.title + "</a></td>" +
						"</tr>";
        $(content).appendTo("#services tbody");
	}
}

// Render the table for displaying an EntitySet
function RenderEntitySet(data, entitiesLinks) 
{
    $("#entities").find("tr:gt(0)").remove();
	if (data.length == 0) {
		var msg = "<tr><td>No entities found.</td></tr>";
		$(msg).appendTo("#entities tbody");
	} else {
		var header = "<tr class=\"ui-widget-header\">";
		for (obj in data[0].__metadata.properties) {
			header += "<th>" + obj + "</th>";
		}
		header += "<th>Actions</th>"
		header += "</tr>";
		$(header).appendTo("#entities tbody");

		var body = "";
		for (row in data) {
			if (row != "__metadata") {
				body += "<tr id=\"entityRow_" + row + "\">";
				for (obj in data[0].__metadata.properties) {
					var prop = data[row][obj];
					if (prop != null && prop.__deferred != null && prop.__deferred.uri != null) {
						body += "<td id=\"entityCell_" + row + "_"+ obj +"\"><a href=\"javascript:GetEntitySet('" + escapeURI(prop.__deferred.uri) + "')\">" + obj + "</a></td>";
					} else if(prop != null) {
						body += "<td id=\"entityCell_" + row + "_"+ obj +"\">" + prop + "</td>";
					}
					else {
						body += "<td id=\"entityCell_" + row + "_"+ obj +"\"></td>";
					}
				}
				body += "<td>";
				var links = entitiesLinks[data[row].__metadata.uri];
				var editRel = "edit";
				if (links[editRel] != null) {
					body +=
						"<a href=\"javascript:OpenUpdateDialog('" + data[row].__metadata.type + "', '" + row + "', '" + escapeURI(links[editRel].href) + "')\">Update</a>" +
						" " +
						"<a href=\"javascript:OpenDeleteDialog('" + escapeURI(links[editRel].href) + "')\">Delete</a>";
				}
				body += "</td></tr>";
			}                            
		}
	    $(body).appendTo("#entities tbody");
	}
}

//Render the table for displaying an Entity
function RenderEntity(data, links) 
{
	var header = "<tr class=\"ui-widget-header\">";
	header += "<th>Name</th>"
	header += "<th>Value</th>"
	header += "</tr>";
	$(header).appendTo("#entities tbody");

	var body = "";
	for (obj in data.__metadata.properties) {
		body += "<tr id=\"entityRow_0\">";
		body += "<td>" + obj + "</td>";
		var prop = data[obj];
		if (prop != null && prop.__deferred != null && prop.__deferred.uri != null) {
			body += "<td id=\"entityCell_0_"+ obj +"\"><a href=\"javascript:GetEntitySet('" + escapeURI(prop.__deferred.uri) + "')\">" + obj + "</a></td>";
		} else if(prop != null) {
			body += "<td id=\"entityCell_0_"+ obj +"\">" + prop + "</td>";
		}
		else {
			body += "<td id=\"entityCell_0_"+ obj +"\"></td>";
		}
		body += "</tr>";
	}
	body += "<tr><td>";
	var editRel = "edit";
	if (links[editRel] != null) {
		body += 
			"<a href=\"javascript:OpenUpdateDialog('" + data.__metadata.type + "', '0', '" + escapeURI(links[editRel].href) + "')\">Update</a>" +
			" " +
			"<a href=\"javascript:OpenDeleteDialog('" + escapeURI(links[editRel].href) + "')\">Delete</a>";
	}
	body += "</td></tr>";
    $(body).appendTo("#entities tbody");
}

//Validation Helper, validates the user edit form
function ValidateData() 
{
    var bValid = true;
//    allFields.removeClass("ui-state-error");

 //   bValid = bValid && checkLength(name, "username", 3, 16);
 //   bValid = bValid && checkLength(email, "email", 6, 80);
 //   bValid = bValid && checkLength(password, "password", 5, 16);

//    bValid = bValid && checkRegexp(name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter.");
//    bValid = bValid && checkRegexp(email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com");
//    bValid = bValid && checkRegexp(password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9");
    return bValid;
}

//Helper function used to show validation errors
function updateTips(t) 
{
    var tips = $(".validateTips");
    tips
				.text(t)
				.addClass("ui-state-highlight");
    setTimeout(function () {
        tips.removeClass("ui-state-highlight", 1500);
    },
                        500);
}

//Helper function to validate length requirements
function checkLength(o, n, min, max) {
    if (o.val().length > max || o.val().length < min) {
        o.addClass("ui-state-error");
        updateTips("Length of " + n + " must be between " +
					min + " and " + max + ".");
        return false;
    }
    else {
        return true;
    }
}

//Helper function to validate using regular expression
function checkRegexp(o, regexp, n) {
    if (!(regexp.test(o.val()))) {
        o.addClass("ui-state-error");
        updateTips(n);
        return false;
    }
    else {
        return true;
    }
}

// function to html escape reserved characters in uri
// plain old encodeUri, encodeComponentUri, and escape don't work here because we are actually escaping html
function escapeURI(uri) {
	// we need to double escape the uri because we are trying to put an escaped uri into html
	uri = _.escape(uri);
	uri = _.escape(uri);
    return uri
}

//Add form fields a form's fieldset element 
function AddFormFields(entityName, metadata, formFieldSetElement) 
{
	var entityType = OData.lookupEntityType(entityName, metadata);
	if(entityType != undefined) {
  	    $("#dialog-form").dialog("option", "title", "Update " + entityType.name);
        document.getElementById("dialog-form-fields").innerHTML = "<div id=\"loading\" style=\"display:none\">Loading...</div>";
  	    for (i in entityType.property) {
  	   		var property = entityType.property[i];
    	    var name = property.name;
    	    
    	    //Field type attribute
    	    var type = property.type;
    	    if(type == "Edm.String")
    	    	type = "text";
    	    else if(type.substring(0, "Edm.Int".length) == "Edm.Int" || type == "Edm.Double")
    	    	type = "number";
    	    else if(type == "Edm.DateTime")
    	    	type = "datetime";
    	    else if(type == "Edm.Time")
    	    	type = "time";
    	    else if(type == "Edm.Boolean")
    	    	type = "checkbox";
    	    else 
    	    	type = "text";

    	    //Field required attribute
    	    var nullable = property.nullable;
    	    var requiredAttribute = "";
    	    if(nullable == false) {
    	    	requiredAttribute = "required";
    	    }
    	    
    	    //Add field
            document.getElementById(formFieldSetElement).innerHTML += "\
            	<label for=\"inputForm_" + name + "\">" + name + "</label><br/> \
            	<input id=\"inputForm_" + name + "\" name=\"" + name + "\" type=\"" + type + "\" " + requiredAttribute + " class=\"text ui-widget-content ui-corner-all\" /><br/>";
    	}
	}
	else {
    	alert("Failed to find entity " + entityName + " in metadata document.");
	}
}

//OData Error callback
function ErrorCallback(error) {
    alert(error.message + ": " + error.response.statusCode + "[" + error.response.statusText + "]");
    $("#dialog-form").dialog("close");
}

//*************************End : Helper Functions***************************
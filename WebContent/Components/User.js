

$(document).ready(function() {
	
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateUserForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hideUserIdSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "UserAPI",
		type : type,
		data : $("#formUser").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onUserComplete(response.responseText, status);
		}
	});
});

function onUserSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hideUserIdSave").val("");
	$("#formUser")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hideUserIdSave").val(
					$(this).closest("tr").find('#hideUserIdUpdate').val());
			$("#name").val($(this).closest("tr").find('td:eq(0)').text());
			$("#nic").val($(this).closest("tr").find('td:eq(1)').text());
			$("#address").val($(this).closest("tr").find('td:eq(2)').text());
			$("#phone").val($(this).closest("tr").find('td:eq(3)').text());
			$("#email").val($(this).closest("tr").find('td:eq(4)').text());
		});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	
	$.ajax({
		url : "UserAPI",
		type : "DELETE",
		data : "id=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status) {
			onUserDeleteComplete(response.responseText, status);
		}
	});
});
function onUserDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validateUserForm() {
	// Name
	if ($("#name").val().trim() == "") {
		return "Insert Name";
	}
	// NIC
	if ($("#nic").val().trim() == "") {
		return "Insert NIC";
	}
	// Address
	if ($("#address").val().trim() == "") {
		return "Insert Address";
	}
	// Phone
	if ($("#phone").val().trim() == "") {
		return "Insert Phone Number";
	}
	
	// Email
	if ($("#email").val().trim() == "") {
		return "Insert Email";
	}
	

	
	
	return true;

}

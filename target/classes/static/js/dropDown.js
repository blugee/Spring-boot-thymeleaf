$( document ).ready(function(e) {
	$('#search_txt').keypress(function(event){

		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13'){
			getSearch();
		}
	});
	 $('.search-panel .dropdown-menu').find('a').click(function(e) {
			e.preventDefault();
			var param = $(this).attr("href").replace("#","");
			var concept = $(this).text();
			$('.search-panel span#search_concept').text(concept);
			$('.input-group #search_param').val(param);
		});
});

function getList(val) {
	$.ajax({
		type : "GET",
		url : "/dropdownList/" + val,

		success : function(data) {
			$('#result').html(data);
		}
	});
}

function handleSelect(elm) {
	window.location = "/user/list?gender=" + elm;
}

function onChangeEvent(isAjax, val) {
	if (isAjax == true) {
		getList(val);
	
	} else {
		handleSelect(val);
	}
}

function getSearch() {

	var search = $('#search_txt').val();
	$.ajax({
		type : "GET",
		url : "/search?text=" + search,

		success : function(data) {
			$('#result').html(data);
		}
	});
}

function getConfirmation(id) {
	var retVal = confirm("Do you want to continue ?");
	if (retVal == true) {
		window.location = "/user/delete/" + id;
	} else {
		return false;
	}
}


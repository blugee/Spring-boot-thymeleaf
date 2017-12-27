function getSport(val) {
	$.ajax({
	type: "GET",
	url: "/getSport?id="+val,
	success: function(data){
		$('#sports').html(data);
	}
	});
}
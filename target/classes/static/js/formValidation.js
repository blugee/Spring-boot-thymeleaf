
$(document).ready(function(){
	$.validator.addMethod("dateFormat",function(value, element) {
		        // put your own logic here, this is just a (crappy) example
		        return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
		    },
		    "Please enter a date in the format dd/mm/yyyy."
		);
	var val=$('#email').val();
	
	$('#userForm').validate({
				rules: {
					firstName:{
						required:true,
						minlength:4
					},
					lastName:{
						required:true,
						minlength:4
					},
					birthDate : {
				        required : true,
				        dateFormat : true
				    },
					gender :{
						required:true
					},
					email:{
						required:true,
						email:true,
						remote:{
							type:"GET",
							url:"/emailFinder?email="+val
						}
						
					},
					studentImagePath :{
						required:false
					}
				},
				message:{
					firstName:{
						required:"Please Enter a valid name",
						minlength:"Sorry, Enter more Charachter"
					},
					lastName :{
						required:"Please Enter your Username",
						minlength:"Please increase the length of your Username"
					},
					birthDate :{
						required:"Please Enter your BirthDate"
					},
					gender :{
						required:"Please Enter your Gender"
					},
					email:{
						required:"Please Enter your Email",
						email:"Your Email is not True",
						remote: 'Email already used. Log in to your existing account.'
					},
					studentImagePath :{
						required:"Please Choose your Photo From the Gallery"
					}
				}
			});
		});

<!DOCTYPE html>
<html>
<head>
	<title>MetaTag</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
	<style type="text/css">
		body{
			font-family: 'Lato', helvetica, arial, verdana, sans-serif;
		}
		.center_div{
		    margin-top: 20px;
		}
	</style>
</head>
<body style="background: #FBFBFB">

	<div class="container center_div">
		<form class="form-inline" id="suggest-form" >
			<div class="form-group">
		    	<label for="content" class="control-label" style="color:#2883C1;font-weight: bold">Content :</label>
		    	<g:textArea name="content"  class="form-control" rows="10" cols="100" style="width:auto" placeholder="Content"/>
		  	</div>
		  	<button type="button" class="btn btn-primary" onclick="suggest()">Suggest</button>
		  	<img src="/img/gears.gif" style="display:none" id="gears">
		</form>


		<div style="margin-top: 10px;margin-left:66px">
			<div class="form-group">
				<label for="summary" class="control-label" style="color:#2883C1;font-weight: bold">Summary :</label>
				<g:textArea name="summary"  class="form-control" rows="10" cols="100" style="width:auto" placeholder="Summary"/>
			</div>
			<div class="form-group">
				<label for="keywords" class="control-label" style="color:#2883C1;font-weight: bold">Keywords :</label>
				<g:textField name="keywords" class="form-control" placeholder="Keywords"/>		
			</div>
		</div>
		
	</div>
	
		
</body>
<script type="text/javascript">
	function suggest(){ 
		$("#gears").css('display','inline')
		$("#keywords").val("")
		$("#summary").val("")
		$.ajax({
			url: '/metaTag/suggestMetaKeyWords',
			type: 'POST',
			data: new FormData($('#suggest-form')[0]),
				error: function(XMLHttpRequest,textStatus,errorThrown){},
				success: function(data,textStatus){
					$("#gears").css('display','none');
					console.log(data);
					$("#summary").val(data.summary);
					$("#keywords").val(data.keywords);
				},
			cache: false, 
			contentType: false, 
			processData: false
		});
	}
	

</script>
</html>
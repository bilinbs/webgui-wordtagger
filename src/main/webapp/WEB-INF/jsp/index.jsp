<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home - മലയാളം ശബ്ദഭേദങ്ങൾ </title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel='stylesheet' href='css/main.css'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

    <link href="https://fonts.googleapis.com/css?family=Oleo+Script:400,700" rel="stylesheet">
   	<link href="https://fonts.googleapis.com/css?family=Teko:400,700" rel="stylesheet">
   	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<section id="contact">
			<%@include file="nav-header.jsp" %>
			<div class="contact-section">
			<div class="container-fluid">
				<form action="/webgui/tag" method="post">
			  		<div class="col-md-6">
			  			<div class="form-group">
			  				<label for ="inputText">Enter your malayalam text (in unicode) below</label>
			  			 	<textarea  class="form-control" id="inputText" name="inputText" placeholder="Enter the text to be tagged"></textarea>
			  			</div>
			  			<div>

			  				<button type="submit" class="btn btn-default submit"><i class="fa fa-paper-plane" aria-hidden="true"></i> tag the Text</button>
			  			</div>
			  			
					</div>
				</form>
			</div>
            <div class="contact-section">
            <div class=" container-fluid">
            <form action="/webgui/tag" method="post" enctype="multipart/form-data">
            <div class="col-md-6">
            <label for="fileUpload" class="text-padding">Or alternatively upload a file</label>
            <input class="button-inline btn btn-default" type="file" name="uploadFile" id="uploadFile" accept="text/*"/>
            <button type="submit" class="btn btn-default submit float-left button-inline"><i class="fa fa-paper-plane" aria-hidden="true"></i> upload and tag</button>
            </div>
            </form>
            </div>
            </div>
		</section>
		
		<%@include file="footer.jsp" %>
</body>
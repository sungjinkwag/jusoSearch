<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="loginForm">
	ID : <input type="text" id="id" name="id" value=""><br>
	PW : <input type="password" id="pw" name="pw" value="" onkeypress="if(event.keyCode==13) {login(); return false;}"><br>
	<button type="button" onclick="login();">로그인</button>
</form>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
  <script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />" ></script>

  <title>Users</title>
  <script type="text/javascript">
    function openAddUserDialog() {
      $('#formAddUser').show();
    }

    function closeAddUserDialog() {
      $('#formUpdateUser').hide();
    }

    function openUpdateUserDialog(userName, userPasswod) {
      var formUpdateUser = $('#formUpdateUser');
      formUpdateUser.find('#userName').val(userName);
      formUpdateUser.find('#userPassword').val(userPasswod);
      formUpdateUser.show();
    }

    function closeUpdateUserDialog() {
      var formUpdateUser = $('#formUpdateUser');
      formUpdateUser.find('#userName').val('');
      formUpdateUser.find('#userPassword').val('');
      formUpdateUser.hide();
    }

    function deleteUserButtonClick(userName) {
      if (confirm("Are you sure?")){
        document.location.href = "/remove-user/" + userName;
      }
    }


  </script>
</head>
<body>
<a href="<c:url value="books"/>">Books</a>
<a href="<c:url value="users"/>">Users</a> <br />
<h2>User list</h2>
<form:form id="formAddUser" method="post" commandName="user" action="add-user" class="modal">
  <fieldset class="modal-content">
    <form:label path="userName">Name: </form:label>
    <form:input path="userName"/>
    <br />

    <form:label path="userPassword">Password: </form:label>
    <form:password path="userPassword"/>
    <br />
    <button type="submit">Add user</button>
    <button type="reset" onclick="closeAddUserDialog()">Cancel</button>
  </fieldset>
</form:form>
<button id="addUserButton" onclick="openAddUserDialog()">Add user</button>

<form:form id="formUpdateUser" method="post" commandName="user" action="update-user" class="modal">
  <fieldset class="modal-content">
    <form:label path="userName">Name: </form:label>
    <form:input path="userName" readonly="true"/>
    <br />

    <form:label path="userPassword">Password: </form:label>
    <form:password path="userPassword"/>
    <br />
    <button type="submit">Update user</button>
    <button type="reset" onclick="closeUpdateUserDialog()">Cancel</button>
  </fieldset>
</form:form>

  <c:if test="${!empty listUsers}">
  <table class="tg">
    <tr>
      <th>Login</th>
      <th>Delete</th>
    </tr>
    <c:forEach items="${listUsers}" var="user">
      <tr>
        <td><a href ="#formUpdateUser" onclick="openUpdateUserDialog('${user.userName}','${user.userPassword}')">${user.userName}</a></td>
        <td><button onclick="deleteUserButtonClick('${user.userName}')">Delete</button></td>
      </tr>
    </c:forEach>
  </table>
  </c:if>



</body>
</html>

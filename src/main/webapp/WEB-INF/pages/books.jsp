<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
	<security:authentication property="principal.username" var ="loginName"/>
	<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
	<script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />" ></script>

	<title>Books</title>

	<script type="text/javascript">

		function loadBooks(){
			var login = $("body").attr("data-login");
			$.ajax({
				url: 'moreBooks',
				method: 'GET',
				dataType: 'json',
				data: ({offset: $('.tg tr:not(:has(th))').length}),
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function(data){
					for (var i = 0; i < data.length; i++){
						var row = $("<tr></tr>");
						var bookIsn = $("<td><a href=\"#formUpdateBook\" onclick = \"openUpdateBookDialog(" + data[i].isn
								+", '" + data[i].bookName + "', '" + data[i].bookAuthor + "')\">" + data[i].isn + "</a></td>");
						var bookName = $("<td class=\"book-name\">" + data[i].bookName + "</td>");
						var bookAuthor = $("<td class=\"book-author\">" + data[i].bookAuthor + "</td>");
						var holder;
						var deleteButton;
						if (data[i].userName == login) {
							holder = $("<td><button onclick= \"returnButtonClick(" + data[i].isn + ")\">Return</button></td>");
							deleteButton = $("<td><button disabled=\"true\"\\>Delete</button></td>");

						} else if(data[i].userName == null) {
							holder = $("<td><button onclick= \"location.href=\'/take-book/" + login + "/" + data[i].isn + "\'\">Take</button></td>");
							deleteButton = $("<td><button onclick=\"deleteButtonClick("+ data[i].isn + ")\">Delete</button></td>");
						} else {
							holder = $("<td>" + data[i].userName + "</td>");
							deleteButton = $("<td><button disabled=\"true\"\\>Delete</button></td>");
						}

						var bookTable = $("#bookTable");
						bookIsn.appendTo(row);
						bookName.appendTo(row);
						bookAuthor.appendTo(row);
						holder.appendTo(row);
						deleteButton.appendTo(row);
						row.appendTo(bookTable);
					}
				}
			});
		}


		function openUpdateBookDialog(isn, bookName, bookAuthor) {
			var formUpdateBook = $('#formUpdateBook');
			formUpdateBook.find('#isn').val(isn);
			formUpdateBook.find('#bookName').val(bookName);
			formUpdateBook.find('#bookAuthor').val(bookAuthor);
			formUpdateBook.show();

		}

		function closeUpdateBookDialog() {
			var formUpdateBook = $('#formUpdateBook');
			formUpdateBook.find('#isn').val('');
			formUpdateBook.find('#bookName').val('');
			formUpdateBook.find('#bookAuthor').val('');
			formUpdateBook.hide();
		}

		function openAddBookDialog() {
			$('#formAddBook').show();
		}

		function closeAddBookDialog() {
			$('#formAddBook').hide();
		}

		function deleteButtonClick(isn) {
			if (confirm("Are you sure?")){
				document.location.href = "/remove-book/" + isn;
			}
		}

		function returnButtonClick(isn) {
			document.location.href = "/return-book/" + isn;
		}

		function takeButtonClick(name, isn) {
			document.location.href = "/take-book/" + name + "/" + isn;
		}

		function sort(field) {
		    var jQField = $(field);
		    var bookTable = $('#bookTable');
		    var sortField = jQField.attr('sort-field');
		    var rowArray = bookTable.find('tr:not(:has(th))');
		    var strActive = 'active';

		    if (jQField.hasClass(strActive) == false) {
		        bookTable.find('.' + strActive).removeClass(strActive);
		        jQField.addClass(strActive);
		    }

		    rowArray.sort( function(a, b){
		        var aValue = $(a).find('.' + sortField).text();
		        var bValue = $(b).find('.' + sortField).text();

		        return aValue > bValue;
		    });

		    if (jQField.attr('sort-type') == 'ASC') {
		        rowArray = rowArray.get().reverse();
		        jQField.attr('sort-type', 'DESC');
		    } else {
		        jQField.attr('sort-type', 'ASC');
		    }

		    bookTable.append(rowArray);
		}
	</script>

</head>
<body data-login="${loginName}">

	<a href="<c:url value="books"/>">Books</a>
	<a href="<c:url value="users"/>">Users</a>

	<h2>Book List</h2>

	<form:form id = "formAddBook" method="post" commandName="book" action="add-book" class="modal">
		<fieldset class="modal-content">
			<form:label path="isn">ISN: </form:label>
			<form:input path="isn"/>

			<br/>
			<form:label path="bookName">Name: </form:label>
			<form:input path="bookName"/>
			<br/>
			<form:label path="bookAuthor">Author: </form:label>
			<form:input path="bookAuthor"/>
			<br/>
			<button type="submit">Add</button>
			<button type="reset" onclick="closeAddBookDialog()">Cancel</button>
		</fieldset>
	</form:form>

    <form:form id = "formUpdateBook" method="post" commandName="book" action="update-book" class="modal">
        <fieldset class="modal-content">
            <form:label path="isn">ISN: </form:label>
            <form:input path="isn" readonly="true"/>

            <br/>
            <form:label path="bookName">Name: </form:label>
            <form:input path="bookName"/>
            <br/>
            <form:label path="bookAuthor">Author: </form:label>
            <form:input path="bookAuthor"/>
            <br/>
            <button type="submit">Update</button>
            <button type="reset" onclick="closeUpdateBookDialog()">Cancel</button>
        </fieldset>
    </form:form>


	<button id="addBookButton" onclick="openAddBookDialog()">Add book</button>
	<c:if test="${!empty listBooks}">
		<table id="bookTable" class="tg">
			<tr>
				<th>ISN</th>
				<th width="120" class="sort-header" sort-field="book-name" sort-type="" onclick="sort(this)">Title</th>
				<th width="120" class="sort-header active" sort-field="book-author" sort-type="ASC" onclick="sort(this)">Author</th>
				<th width="60">Holder</th>
				<th width="60">Delete</th>
			</tr>
			<c:forEach items="${listBooks}" var="book">
				<tr>
					<td><a href="#formUpdateBook" onclick = "openUpdateBookDialog(${book.isn},'${book.bookName}','${book.bookAuthor}')">${book.isn}</a></td>
					<td class="book-name">${book.bookName}</td>
					<td class="book-author">${book.bookAuthor}</td>
					<td>
						<c:choose>
							<c:when test="${book.userName eq loginName}">
								<button onclick="returnButtonClick(${book.isn})">Return</button>
							</c:when>
							<c:when test="${empty book.userName}">
								<button onclick="takeButtonClick('${loginName}',${book.isn})">Take</button>
							</c:when>
							<c:otherwise>
								${book.userName}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:if test="${empty book.userName}">
							<button onclick="deleteButtonClick(${book.isn})" >Delete</button>
						</c:if>
						<c:if test="${!empty book.userName}">
							<button disabled="true">Delete</button>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<button onclick="loadBooks()">More</button>
</body>
</html>
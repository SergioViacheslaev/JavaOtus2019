<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>

<head>
    <title>List Users</title>

    <!-- reference our style sheet -->

    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css"/>

</head>

<body>

<div id="wrapper">
    <div id="header">
        <h2>USER LIST WEB PAGE</h2>
    </div>
</div>

<div id="container">

    <div id="content">

        <!--  add Add button-->
        <input type="button" value="Add Customer"
               onclick="window.location.href='showFormForAdd'; return false;"
               class="add-button"
        />


        <!--  add our html table here -->

        <table>
            <tr>
                <th>Name</th>
                <th>Age</th>
            </tr>

            <!-- loop over and print our customers -->
            <c:forEach var="tempUser" items="${users}">

                <tr>
                    <td> ${tempUser.name} </td>
                    <td> ${tempUser.age} </td>
                </tr>

            </c:forEach>

        </table>

    </div>

</div>


</body>

</html>










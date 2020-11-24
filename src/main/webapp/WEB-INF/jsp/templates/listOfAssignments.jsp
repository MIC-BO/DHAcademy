<div class="table-responsive">
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">First Name</th>
          <th scope="col">Last Name</th>
          <th scope="col">Class</th>
          <th scope="col" class="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:set var="count" value="0" scope="page" />
        <c:forEach var="currentAssignment" items="${assignments}">
            <tr>
                <th scope="row"><c:set var="count" value="${count + 1}" scope="page"/>${count}</th>
                <td>${currentAssignment.firstName}</td>
                <td>${currentAssignment.lastName}</td>
                <td>${currentAssignment.title}</td>
                <td align="center">
                    <c:forEach items="${controls}" var = "control">
                        <a class="${control.controlClass}"
                           href="/${control.url}?id=${currentAssignment.id}&firstName=${student.firstName}&lastName=${student.lastName}"
                           <c:if test="${control.needConfirmation}">
                                onclick="return confirm('Are you sure you want to perform this action?');"
                           </c:if>
                           role="button">${control.name}</a>

                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
      </tbody>
    </table>
</div>
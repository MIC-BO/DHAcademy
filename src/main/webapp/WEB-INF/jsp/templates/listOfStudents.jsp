<div class="table-responsive">
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">First Name</th>
          <th scope="col">Last Name</th>
          <th scope="col" class="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="currentStudent" items="${students}">
            <tr>
                <th scope="row">${currentStudent.studentID}</th>
                <td>${currentStudent.firstName}</td>
                <td>${currentStudent.lastName}</td>
                <td align="center">
                    <c:forEach items="${controls}" var = "control">
                        <a class="${control.controlClass}"
                           href="/${control.url}?studentID=${currentStudent.studentID}&firstName=${student.firstName}&lastName=${student.lastName}"
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
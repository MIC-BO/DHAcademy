<div class="table-responsive">
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Title</th>
          <th scope="col">Description</th>
          <th scope="col" class="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="currentAcademyClass" items="${academyClasses}">
            <tr>
                <th scope="row">${currentAcademyClass.code}</th>
                <td>${currentAcademyClass.title}</td>
                <td>${currentAcademyClass.description}</td>
                <td align="center">
                    <c:forEach items="${controls}" var = "control">
                        <a class="${control.controlClass}"
                           href="/${control.url}?code=${currentAcademyClass.code}&title=${academyClass.title}&description=${academyClass.description}"
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
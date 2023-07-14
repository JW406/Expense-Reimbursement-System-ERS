<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="mt-3 container">
    <table class="table" class="display" cellspacing="0" width="100%">
      <thead class="thead-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Email</th>
          <th scope="col">Name</th>
          <th scope="col">GitHub Username</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  <script>
    $(() => {
      const tbody = $('.table tbody')
      fetch(window.__ctx + '/api/getManagedEmployees').then((resp) => resp.json()).then((d) => {
        for (const idx in d) {
          const item = d[idx]
          tbody.append(`
            <tr>
              <th scope="row">\${+idx+1}</th>
              <td>\${item['email'] || ''}</td>
              <td>\${item['name'] || ''}</td>
              <td>\${item['gitHubAddress'] || ''}</td>
            </tr>
          `)
        }
        $('.table').dataTable()
      })
    })
  </script>

  <jsp:include page="footer.jsp" />

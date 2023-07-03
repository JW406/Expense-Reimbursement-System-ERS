<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="container">
    <div class="row-no-gutters">
      <div class="col-xs-12">
        <ul class="nav nav-tabs mt-3">
          <li class="nav-item">
            <a class="nav-link active" href="#">Active</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Approved</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Declined</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Recalled</a>
          </li>
        </ul>
        <div class="table-responsive">
          <table class="table table-sm reimbursement-table">
            <thead>
              <tr></tr>
            </thead>
            <tbody></tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  <script>
    function mock(data, time = 0) {
      return new Promise((resolve) => {
        setTimeout(() => {
          resolve(data)
        }, time)
      })
    }

    let isAdmin = false

    ;(() => {
      const thead = $('.reimbursement-table thead tr')
        ;['Request Date', 'Request Amount', 'Actions'].forEach((name) => {
          thead.append(`<th class="w-33">\${name}</th>`)
        })
      const table = $('.reimbursement-table')
      mock(
        [
          {
            requestDate: '2016/10/1',
            requestAmt: 500,
          },
          {
            requestDate: '2016/10/1',
            requestAmt: 1000,
          },
        ]
        , 800).then((data) => {
          const tbody = $('.reimbursement-table tbody')
          for (const d of data) {
            const row = $('<tr>')
            row.append(`<td class="active w-33">\${d['requestDate']}</td>`)
            row.append(`<td class="success w-33">\${d['requestAmt']}</td>`)
            const actionCell = $('<td class="w-33">')
            if (isAdmin) {
              actionCell.append(
                '<button type="button" class="btn btn-primary btn-sm mr-2">Approve</button>'
              )
              actionCell.append(
                '<button type="button" class="btn btn-danger btn-sm">Decline</button>'
              )
            } else {
              actionCell.append(
                '<button type="button" class="btn btn-primary btn-sm">Recall</button>'
              )
            }
            row.append(actionCell)
            tbody.append(row)
          }
        })

    })()
  </script>
  <jsp:include page="footer.jsp" />

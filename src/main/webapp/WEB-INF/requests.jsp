<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="mt-3 container">
    <div class="row-no-gutters">
      <div class="col-xs-12">
        <div class="d-flex justify-content-between">
          <ul class="nav nav-tabs left">
            <li class="nav-item">
              <a class="nav-link" href="#active">Active</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#approved">Approved</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#declined">Declined</a>
            </li>
          </ul>
        </div>
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
    let tbody

    function employeeRequestActions(state) {
      return (e) => {
        const id = e.target.dataset['id']
        fetch(window.__ctx + '/api/request-update', {
          method: 'POST',
          headers: {
            "Content-type": "application/json; charset=UTF-8",
          },
          body: JSON.stringify({ id, state }),
        }
        ).then((resp) => resp.json()).then((data) => {
          if (data['isSuccess']) {
            window.location.reload()
          }
        })
      }
    }

    function managerRequestActions(state) {
      return (e) => {
        const id = e.target.dataset['id']
        fetch(window.__ctx + '/api/manager-actions', {
          method: 'POST',
          headers: {
            "Content-type": "application/json; charset=UTF-8",
          },
          body: JSON.stringify({ id, state }),
        }
        ).then((resp) => resp.json()).then((data) => {
          if (data['isSuccess']) {
            window.location.reload()
          }
        })
      }
    }

    function populateManagerTable(state) {
      tbody.find('.btn-approve').unbind()
      tbody.find('.btn-decline').unbind()
      tbody.find('.btn-resend').unbind()
      tbody.html('')
      fetch(window.__ctx + `/api/requestManagedEmployeeRequests?state=\${state}`, {
        method: 'GET',
      }).then((resp) => resp.json()).then((data) => {
        if (data == null || !data.length) { return }
        tbody.html('')
        for (const d of data) {
          const row = $('<tr>')
          row.append(`<td class="w-25">\${dateFmt("yyyy-MM-dd hh:mm:ss", new Date(d['tsDate']))}</td>`)
          row.append(`<td class="w-25">\${d.requestedByEmployee.email}</td>`)
          row.append(`<td class="w-25">\$\${d['reqAmnt']}</td>`)
          const actionCell = $('<td class="w-25">')
          if (state === 'active') {
            actionCell.append(
              `<button type="button" class="btn btn-primary btn-sm mr-2 btn-approve" data-id="\${d['id']}">Approve</button>`
            )
            actionCell.append(
              `<button type="button" class="btn btn-danger btn-sm btn-decline" data-id="\${d['id']}">Decline</button>`
            )
          } else if (state === 'approved' || state === 'declined') {
            actionCell.append(
              `<button type="button" class="btn btn-primary btn-sm btn-resend" data-id="\${d['id']}">Comment</button>`
            )
          }
          row.append(actionCell)
          tbody.append(row)
        }
        tbody.find('.btn-approve').click(managerRequestActions('approved'))
        tbody.find('.btn-decline').click(managerRequestActions('declined'))
        tbody.find('.btn-resend').click(managerRequestActions('active'))
      })
    }

    function populateEmployeeTable(state) {
      tbody.find('.btn-approve').unbind()
      tbody.find('.btn-decline').unbind()
      tbody.find('.btn-resend').unbind()
      tbody.find('.btn-recall').unbind()
      tbody.html('')
      fetch(window.__ctx + `/api/requestsTable?state=\${state}`, {
        method: 'GET',
      }
      ).then((resp) => resp.json()).then((data) => {
        if (data == null || !data.length) { return }
        tbody.html('')
        for (const d of data) {
          const row = $('<tr>')
          row.append(`<td class="active w-33">\${dateFmt("yyyy-MM-dd hh:mm:ss", new Date(d['tsDate']))}</td>`)
          row.append(`<td class="success w-33">\$\${d['reqAmnt']}</td>`)
          const actionCell = $('<td class="w-33">')
          if (state === 'active') {
            actionCell.append(
              `<button type="button" class="btn btn-primary btn-sm btn-recall" data-id="\${d['id']}">Recall</button>`
            )
          } else if (state === 'recalled') {
            actionCell.append(
              `<button type="button" class="btn btn-primary btn-sm btn-resend" data-id="\${d['id']}">Resend</button>`
            )
          }
          row.append(actionCell)
          tbody.append(row)
        }
        tbody.find('.btn-approve').click(employeeRequestActions('approved'))
        tbody.find('.btn-decline').click(employeeRequestActions('declined'))
        tbody.find('.btn-resend').click(employeeRequestActions('active'))
        tbody.find('.btn-recall').click(employeeRequestActions('recalled'))
      })
    }

    $(() => {
      tbody = $('.reimbursement-table tbody')

      if (!getIsAdmin()) {
        const thead = $('.reimbursement-table thead tr')
          ;['Request Date', 'Request Amount', 'Actions'].forEach((name) => {
            thead.append(`<th class="w-33">\${name}</th>`)
          })
        $('.nav-tabs.left').append(`
                  <li class="nav-item">
                    <a class="nav-link" href="#recalled">Recalled</a>
                  </li>
              `)
        $('.nav-tabs.left').parent().append(`
          <ul class="nav nav-tabs right">
            <li class="nav-item">
              <a class="nav-link btn btn-success btn-sm" href="<%=request.getContextPath()%>/new_request">New</a>
            </li>
          </ul>
              `)

      } else {
        const thead = $('.reimbursement-table thead tr')
          ;['Request Date', 'Requested By', 'Request Amount', 'Actions'].forEach((name) => {
            thead.append(`<th class="w-25">\${name}</th>`)
          })
      }

      let currTab = window.location.hash && window.location.hash.slice(1) || 'active'
      $(`.nav-item a[href="#\${currTab}"]`).addClass('active')
      if (getIsAdmin()) {
        populateManagerTable(currTab)
      } else {
        populateEmployeeTable(currTab)
      }

      $('.nav-item a').click(function () {
        const $this = $(this)
        setTimeout(() => {
          if ($this[0].href === window.location.href) {
            $this.parent().parent().find('li > a').removeClass('active')
            $this.addClass('active')
            if (getIsAdmin()) {
              populateManagerTable($this[0].hash.slice(1))
            } else {
              populateEmployeeTable($this[0].hash.slice(1))
            }
          }
        }, 0)
      })
    })
  </script>
  <jsp:include page="footer.jsp" />
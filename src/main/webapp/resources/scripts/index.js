function mock(data, time = 0) {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(data)
    }, time)
  })
}

let isAdmin = false

function populateTable() {
  const thead = $('.reimbursement-table thead tr')
  ;['Employee Name', 'Request Amount', 'Actions'].forEach((name) => {
    thead.append(`<th class="w-33">${name}</th>`)
  })
  const table = $('.reimbursement-table')
  mock(
  [
    {
      employeeName: 'foo',
      requestAmt: 500,
    },
    {
      employeeName: 'bar',
      requestAmt: 1000,
    },
  ]
  , 800).then((data) => {
    const tbody = $('.reimbursement-table tbody')
    for (const d of data) {
      const row = $('<tr>')
      row.append(`<td class="active w-33">${d['employeeName']}</td>`)
      row.append(`<td class="success w-33">${d['requestAmt']}</td>`)
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

}

$(() => {
  populateTable()
  $('.logout-btn').on('click', () => {
    window.localStorage.removeItem('username')
    fetch(window.__ctx + '/api/logout').then(() => {
      window.location.reload()
    })
  })
})

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="mt-3 container">
    <div class="card">
      <div class="card-header">
        Your Information
      </div>
      <div class="card-body">
        <h5 class="card-title">Your Email</h5>
        <p class="card-text email"></p>
        <h5 class="card-title">Your Name</h5>
        <p class="card-text name"></p>
        <h5 class="card-title">Your GitHub</h5>
        <p class="card-text github"></p>
        <h5 class="card-title">Your Phone Number</h5>
        <p class="card-text phonenumber"></p>
      </div>
    </div>
  </div>
  <script>
    $(() => {
      ;(async () => {
        const d = await fetch(window.__ctx + '/api/get-accountinfo').then((d) => d.json())
        $('.card-body > .card-text.email').text(d['email'])
        $('.card-body > .card-text.name').text(d['name'])
        $('.card-body > .card-text.github').text(d['gitHubAddress'])
        $('.card-body > .card-text.phonenumber').text(d['phoneNumber'])
        if (!d['isManager']) {
          $(`.card-body`).append(
            `<h5 class="card-title">Your Manager</h5>
              <p class="card-text manager"></p>`
          )
          $('.card-body > .card-text.manager').text(`\${d['manager']?.name ?? 'No Manager'} (\${d['manager']?.email ?? ''})`)
          $('.card-body').append(`
            <div class="dropdown">
              <button class="btn btn-secondary mr-5 dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" >
                Select Your Manager
              </button>
              <div class="dropdown-menu">
              </div>
              <button type="button" class="btn btn-success submit-manager-change">
                Submit Manager Change
              </button>
            </div>
          `)
          const managers = await fetch(window.__ctx + '/api/get-all-managers').then((d) => d.json())
          const menu = $('.card-body').find('.dropdown-menu')
          for (const item of managers) {
            const button = $(`<button class="dropdown-item" type="button">\${item['name']} (\${item['email']})</button>`)
            button.data({ email: item['email'] })
            menu.append(button)
          }
          const $toggle = $('.card-body .dropdown > .dropdown-toggle')
          $('.dropdown-item').click(function () {
            const $this = $(this)
            $toggle.text($this.text())
            $toggle.data({ email: $this.data()['email'] })
          })
          $('.submit-manager-change').click(() => {
            const managerEmail = $toggle.data()['email']
            fetch(window.__ctx + '/api/employee-change-manager', {
              method: 'POST',
              headers: {
                "Content-type": "application/json; charset=UTF-8",
              },
              body: JSON.stringify({ managerEmail }),
            }).then((d) => d.json()).then((d) => {
              if (d['isSuccess']) {
                modalShow({ body: d['msg'], onclose: () => window.location.reload() })
              }
            })
          })
        }
      })()
    })
  </script>
  <jsp:include page="footer.jsp" />

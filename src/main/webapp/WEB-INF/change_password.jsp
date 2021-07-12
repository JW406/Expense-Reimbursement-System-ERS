<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="mt-3 container">
    <div class="row-no-gutters">
      <form id="password-change">
        <div class="form-row">
          <div class="col-md-6 mb-3">
            <label for="password0">Old Password</label>
            <input type="password" class="form-control" id="password0" required autocomplete="current-password">
          </div>
        </div>
        <div class="form-row">
          <div class="col-md-6 mb-3">
            <label for="password1">New Password</label>
            <input type="password" class="form-control" id="password1" required autocomplete="current-password">
            <div class="valid-feedback">
              Looks good!
            </div>
            <div class="invalid-feedback">
              Password must be at least 2 character or more
            </div>
          </div>
        </div>
        <div class="form-row">
          <div class="col-md-6 mb-3">
            <label for="password2">Repeat Password</label>
            <input type="password" class="form-control" id="password2" required autocomplete="current-password">
            <div class="valid-feedback">
              Looks good!
            </div>
            <div class="invalid-feedback">
              Sorry, the passwords didn't match.
            </div>
          </div>
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Submit Changes</button>
    </form>
  </div>
  <script>
    $(() => {
      $('#password1').on('input', _.debounce(function (e) {
        const value = e.target.value
        const $this = $(this)
        if (value == null || value.length < 2) {
          $this.removeClass('is-valid').addClass('is-invalid')
        } else {
          $this.removeClass('is-invalid').addClass('is-valid')
        }
      }, 800))

      $('#password2').on('input', _.debounce(function (e) {
        const value = e.target.value
        if (value == null || value === "") { return }
        const $this = $(this)
        if (value !== $('#password1').val()) {
          $this.removeClass('is-valid').addClass('is-invalid')
        } else {
          $this.removeClass('is-invalid').addClass('is-valid')
        }
      }, 800))
    })

    $('#password-change').submit(function (e) {
      e.preventDefault()
      const $this = $(this)

      const oldPassword = $this.find('#password0').val()
      const newPassword = $this.find('#password1').val()
      const newPassword2 = $this.find('#password2').val()
      fetch(window.__ctx + '/api/password-change', {
        method: 'POST',
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify({ oldPassword, newPassword, newPassword2 }),
      }).then((d) => d.json()).then((d) => {
        $this.find('input').val('')
        $this.find('input').removeClass('is-invalid').removeClass('is-valid')
        modalShow({ body: d['msg'] })
      })

    })
  </script>
  <jsp:include page="footer.jsp" />

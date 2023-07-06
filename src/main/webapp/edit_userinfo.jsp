<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="mt-3 container">
    <div class="row-no-gutters">
      <form id="edit-userinfo">
        <div class="form-row">
          <div class="col-md-6 mb-3">
            <label for="full-name">Full name</label>
            <input type="text" class="form-control" id="full-name" required autofocus>
            <div class="valid-feedback">
              Looks good!
            </div>
            <div class="invalid-feedback">
              Please provide a valid name.
            </div>
          </div>
        </div>
        <div class="form-row">
          <div class="col-md-6 mb-3">
            <label for="phone-number">Phone Number</label>
            <input type="text" class="form-control" id="phone-number" required>
            <div class="valid-feedback">
              Looks good!
            </div>
            <div class="invalid-feedback">
              Please provide a valid phone number.
            </div>
          </div>
        </div>
        <div class="form-row">
          <div class="col-md-6 mb-3">
            <label for="github-username">GitHub Username</label>
            <input type="text" class="form-control" id="github-username" required>
            <div class="valid-feedback">
              Looks good!
            </div>
            <div class="invalid-feedback">
              Please provide a valid GitHub Username.
            </div>
          </div>
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Submit Changes</button>
    </form>
  </div>
  <script>
    $(() => {
      $('.form-row input').on('input', _.debounce(function (e) {
        const $this = $(this)
        const value = e.target.value
        if (value == null || value === '') {
          $this.removeClass('is-valid').addClass('is-invalid')
        } else {
          $this.removeClass('is-invalid').addClass('is-valid')
        }
      }, 800))

      fetch(window.__ctx + '/api/get-accountinfo').then((d) => d.json()).then((d) => {
        $('#full-name').attr('value', d['name']).addClass('is-valid')
        $('#phone-number').attr('value', d['phoneNumber']).addClass('is-valid')
        $('#github-username').attr('value', d['gitHubAddress']).addClass('is-valid')
      })
    })

    $('#edit-userinfo').submit((e) => {
      e.preventDefault()
      const fullName = e.target['full-name'].value
      const phoneNumber = e.target['phone-number'].value
      const gitHubUsername = e.target['github-username'].value
      fetch(window.__ctx + '/api/info-update', {
        method: 'POST',
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify({ fullName, phoneNumber, gitHubUsername }),
      }).then((d) => d.json()).then((d) => {
        if (d['isSuccess']) {
          alert('success')
        }
      })

    })
  </script>

  <jsp:include page="footer.jsp" />

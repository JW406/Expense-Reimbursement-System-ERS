  <div class="overlay">
    <div class="spinner-border text-light"></div>
  </div>
  <footer class="footer mt-auto py-3" style="background-color: #f5f5f5;">
    <div class="container">
      <span class="text-muted">Footer says Hello World.</span>
    </div>
  </footer>

  <script>
    if (getIsAdmin()) {
      $('#navbarSupportedContent .navbar-nav:first-child').append(`
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/view-all-employees">View All Employees</a>
          </li>
        `)
      $('body').append(`
    <div class="modal" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Comment</h5>
            <button type="button" class="close close-btn" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <textarea class="modal-text" type="text" style="width:100%;height:100%;">
            </textarea>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary close-btn" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Submit</button>
          </div>
        </div>
      </div>
    </div>`)
      $('.modal-content .close-btn').click((e) => {
        $('body > .modal').hide()
      })
      $('.modal-body > textarea').keydown((e) => {
        const oe = e.originalEvent
        if (!oe['shiftKey'] && oe['key'] === 'Enter') {
          e.preventDefault()
          $('.modal-footer > .btn-primary').click()
        }
      })
      $('.modal-footer > .btn-primary').click((e) => {
        const modalText = $('.modal-body > .modal-text')
        const comment = modalText.val()
        const id = modalText.data()['id']
        fetch(window.__ctx + '/api/manager-update-request-comment', {
          method: 'POST',
          headers: {
            "Content-type": "application/json; charset=UTF-8",
          },
          body: JSON.stringify({ id, payload: comment }),
        }).then((d) => d.json()).then((d) => {
          if (d['isSuccess']) {
            alert(d['msg'])
            window.location.reload()
          }
        })
        $('body > .modal').hide()
      })
    }
  </script>
  <script src="<%=request.getContextPath()%>/resources/libraries/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/scripts/index.js"></script>
</body>

</html>

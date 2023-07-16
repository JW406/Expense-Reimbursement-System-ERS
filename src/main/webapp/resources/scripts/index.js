function modalShow(obj = {}) {
  const title = obj.title || 'Message'
  const body = obj.body || ''
  const onclose = obj.onclose || function () {}
  $('body').append(`
    <div class="modal generic-modal" style="display:block" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">${title}</h5>
            <button type="button" class="close close-btn" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            ${body}
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary close-btn" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    `)
  $('.generic-modal .close-btn').click((e) => {
    onclose()
    $('.generic-modal').remove()
  })
  const closeEvent = (e) => {
    const oe = e.originalEvent
    if (oe['key'] === 'Enter' || oe['key'] === 'Escape') {
      console.log(e)
      $('.generic-modal .close-btn').click()
      $('body').unbind('keydown', closeEvent)
      return false
    }
  }
  $('body').keydown(closeEvent)
}

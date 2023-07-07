
$(() => {
  $('.logout-btn').on('click', () => {
    window.localStorage.removeItem('username')
    window.localStorage.removeItem('ismanager')
    fetch(window.__ctx + '/api/logout').then(() => {
      window.location.reload()
    })
  })
  $('.navbar-nav a').each(function () {
    const $this = $(this)
    if ($this[0].href === window.location.href) {
      $this.addClass('active')
    }
  })
})

const __fetch = window.fetch
window.fetch = function fetch() {
  window.dispatchEvent(new Event('fetchStart'))
  return __fetch.apply(this, arguments).then((a) => {
    window.dispatchEvent(new Event('fetchDone'))
    return a
  })
}
;(($overlay) => {
  window.addEventListener('fetchStart', () => {
    $overlay.css({ display: 'flex' })
  })
  window.addEventListener('fetchDone', () => {
    $overlay.css({ display: 'none' })
  })
})($('div.overlay'))

function dateFmt(fmt, date) {
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
    'q+': Math.floor((date.getMonth() + 3) / 3),
    S: date.getMilliseconds(),
  }
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(
      RegExp.$1,
      (date.getFullYear() + '').substr(4 - RegExp.$1.length)
    )
  for (const k in o)
    if (new RegExp('(' + k + ')').test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length)
      )
  return fmt
}

const getIsAdmin = ((isAdmin) => {
  return function () {
    if (isAdmin == null) {
      isAdmin = window.localStorage.getItem('ismanager') === 'true'
    }
    return isAdmin
  }
})(null)

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
      $('.generic-modal .close-btn').click()
      $('body').unbind('keydown', closeEvent)
      return false
    }
  }
  $('body').keydown(closeEvent)
}

$(() => {
  if (window.location.pathname === window.__ctx + '/login') {
    return
  }
  const username = window.localStorage.getItem('username')
  if (username != null && username !== '') {
    const navUserName = $('.user-name')
    navUserName.addClass('dropdown-toggle')
    navUserName.attr('data-toggle', 'dropdown')
    navUserName.attr('aria-haspopup', 'true')
    navUserName.attr('aria-expanded', 'false')
    navUserName.html(
      `<span>${
        getIsAdmin() ? '(Manger)  ' : ''
      }${username}</span><span class="caret"></span>`
    )
    navUserName.parent().append(`
<div class="dropdown-menu aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="${window.__ctx}/edit_userinfo">Edit User Info</a>
<a class="dropdown-item" href="${window.__ctx}/change_password">Change Password</a>
<div class="dropdown-divider"></div>
<button class="dropdown-item logout-btn">Logout</button>
</div>
`)
  }

  $('.logout-btn').on('click', () => {
    window.localStorage.removeItem('username')
    window.localStorage.removeItem('ismanager')
    fetch(window.__ctx + '/api/logout').then(() => {
      window.location.reload()
    })
  })

  // header
  $('.navbar-nav a').each(function () {
    const $this = $(this)
    if ($this[0].href === window.location.href) {
      $this.addClass('active')
    }
  })

  // comment button
  if (getIsAdmin()) {
    $('#navbarSupportedContent .navbar-nav:first-child').append(`
                  <li class="nav-item">
                    <a class="nav-link" href="${window.__ctx}/view-all-employees">View All Employees</a>
                  </li>
                `)
    $('.comment-modal .modal-body').append(`
            <textarea class="modal-text" style="width:100%;height:100%;">
              </textarea>
            </div>
          `)
    $('.comment-modal .modal-footer').append(`
            <button type="button" class="btn btn-primary">Submit</button>
          `)
    $('.comment-modal .modal-body > textarea').keydown((e) => {
      const oe = e.originalEvent
      if (!oe['shiftKey'] && oe['key'] === 'Enter') {
        e.preventDefault()
        $('.modal-footer > .btn-primary').click()
      }
    })
    $('.comment-modal .modal-footer > .btn-primary').click((e) => {
      const modalText = $('.modal-body > .modal-text')
      const comment = modalText.val()
      const id = modalText.data()['id']
      fetch(window.__ctx + '/api/manager-update-request-comment', {
        method: 'POST',
        headers: {
          'Content-type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify({ id, payload: comment }),
      })
        .then((d) => d.json())
        .then((d) => {
          if (d['isSuccess']) {
            modalShow({
              body: d['msg'],
              onclose: () => window.location.reload(),
            })
          }
        })
      $('body > .comment-modal').hide()
    })
  } else {
    $('.comment-modal .modal-body').append('<p>')
  }
  $('.comment-modal .close-btn').click((e) => {
    $('.comment-modal').hide()
  })
})

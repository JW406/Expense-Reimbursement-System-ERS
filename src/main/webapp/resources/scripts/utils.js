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
    $overlay = $('div.overlay')
    $overlay.css({ display: 'flex' })
  })
  window.addEventListener('fetchDone', () => {
    $overlay.css({ display: 'none' })
  })
})(null)


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

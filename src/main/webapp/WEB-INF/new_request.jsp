<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp" />

<div class="new-request-window mt-5">
  <form id="new-request">
    <div class="form-row">
      <div class="form-group col-md-6">
        <label for="request-amnt">Request Amount</label>
        <div class="d-flex">
          <span style="height:38px;line-height:38px;margin-right:5px;">$</span>
          <input type="text" class="form-control" id="request-amnt" autofocus>
        </div>
        <label for="request-reason">Reason</label>
        <div>
          <textarea id="request-reason" style="width:100%;height:200px;"></textarea>
        </div>
      </div>
    </div>
    <div class="err-msg"> </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>
<script>
  document.getElementById('new-request').addEventListener('submit', (ev) => {
    ev.preventDefault()
    $('.err-msg').text('')
    const requestAmnt = ev.target['request-amnt'].value
    const requestReason = ev.target['request-reason'].value
    let decimalSignIdx = -1
    if (!$.isNumeric(requestAmnt)) {
      $('.err-msg').text('Numbers only')
    } else if ((decimalSignIdx = requestAmnt.indexOf('.')) > -1 && requestAmnt.length - decimalSignIdx > 3) {
      $('.err-msg').text('Only 2 digits after the dot')
    } else {
      fetch(window.__ctx + '/api/submit-request', {
        method: 'POST',
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify({ requestAmnt, requestReason, timestamp: new Date().getTime() }),
      }
      ).then((resp) => resp.json()).then((d) => {
        if (d['isSuccess']) {
          alert(d['msg'])
          window.location.href = window.__ctx + '/requests'
        } else {
          $('.err-msg').text(d.msg)
        }
      })
    }
  })
</script>

<jsp:include page="footer.jsp" />

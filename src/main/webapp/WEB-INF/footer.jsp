  <div class="overlay">
    <div class="spinner-border text-light"></div>
  </div>
  <div class="modal comment-modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Comment</h5>
          <button type="button" class="close close-btn" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary close-btn" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <footer class="footer mt-auto py-3" style="background-color: #f5f5f5;">
    <div class="container">
      <span class="text-muted">Footer says Hello World.</span>
    </div>
  </footer>

  <script src="<%=request.getContextPath()%>/resources/libraries/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/libraries/js/underscore-min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/libraries/js/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/scripts/index.js"></script>
</body>

</html>

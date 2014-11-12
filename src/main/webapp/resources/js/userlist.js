$('#delete').on('show.bs.modal', function (e) {
    document.getElementById('modal-delete-form').action = '/user/' + e.relatedTarget.id + '/delete';
})
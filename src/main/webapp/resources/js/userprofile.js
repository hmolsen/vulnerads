$('#tfa_enabled').change(function () {
    if ($('#tfa_enabled').prop('checked') == true) {
        $('#showtfasecret').modal('show');
    }
});

$('#showtfasecret').on("show.bs.modal", function () {
    $(this).find(".lazy").each(function () {
        $(this).attr('src', $(this).attr('data-src-lazy'));
    });
});
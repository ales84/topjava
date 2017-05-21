var ajaxUrl = 'ajax/meals/';
var datatableApi;
var startDate, endDate, startTime, endTime;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function filterTable() {
    startDate = $('#startDate').val();
    endDate = $('#endDate').val();
    startTime = $('#startTime').val();
    endTime = $('#endTime').val();

    $.get(ajaxUrl+'filter?startDate='+startDate+'&startTime='+startTime+'&endDate='+endDate+'&endTime='+endTime, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}
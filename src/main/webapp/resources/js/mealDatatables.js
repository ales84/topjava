var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type === 'display') {
                        return data.substring(0, 10) + ' ' + data.substring(11, 19);
                    }
                    return data;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data["exceed"] === true) {
                $(row).addClass('exceeded');
            } else {
                $(row).addClass('normal');
            }
        },
        "initComplete": makeEditable
    });
    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
    $('#startDate').datetimepicker({
        format: 'Y-m-d'
    });

    $('#endDate').datetimepicker({
        format: 'Y-m-d'
    });
    $('#startTime').datetimepicker({
        format: 'H:i'
    });
    $('#endTime').datetimepicker({
        format: 'H:i'
    });
});
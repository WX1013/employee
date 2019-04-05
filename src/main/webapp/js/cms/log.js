$(function () {
    search();
});

function search(username, page, size) {
    $.ajax({
        url: "/log/getLogList",   //url
        type: "post",   //请求类型 ,
        dataType: "json", // 返回参数类型
        data: {
            username: username,
            page: page,
            size: size
        },
        success: function (res) {
            var logs = res.rows;
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for (var i = 0; i < logs.length; i++) {
                html += '<tr>';
                html += ' <td>' + logs[i].id + '</td>';
                html += ' <td>' + logs[i].username + '</td>';
                html += ' <td>' + logs[i].module + '</td>';
                html += ' <td>' + logs[i].method + '</td>';
                html += ' <td>' + logs[i].ip + '</td>';
                html += ' <td>' + logs[i].addTimeStr + '</td>';
                html += '</tr>';
            }
            $("#table_data").html(html);

            // 分页数据
            $("#pageAndTotal").html("总共 " + res.pages + " 页，共 " + res.total + " 条数据");
            var pagenation = '<li><a href="#" onclick="changePage(1,10)" aria-label="Previous">首页</a></li>\n' +
                '             <li><a href="#" onclick="changePage(' + (page - 1) + ',' + pages + ')">上一页</a></li>';
            var begin;
            var end;
            debugger;
            if(pages <= 5){
                begin = 1;
                end = pages;
            }
            if(pages > 5){
                begin = 1;
                end = 5;
                if(page > 3 && page < pages - 2){
                    begin = page - 2;
                    end = page + 2;
                }else if(page >= pages - 2){
                    begin = pages - 4;
                    end = pages;
                }
            }
            for (var i = begin; i <= end; i++) {
                if (page == i) {
                    pagenation += '<li class="active"><a href="#" onclick="changePage(' + ('+i+') + ',' + pages + ')"  >' + i + '</a></li>';
                } else {
                    pagenation += '<li><a href="#" onclick="changePage(' + i + ',' + pages + ')">' + i + '</a></li>';
                }
            }
            pagenation += '<li><a href="#" onclick="changePage(' + (page + 1) + ',' + pages + ')">下一页</a></li>\n' +
                '          <li><a href="#" onclick="changePage(' + pages + ',' + pages + ')" aria-label="Next">尾页</a></li>';
            $("#pagenation").html(pagenation);
        }
    });
}

function changePage(page, pages) {
    if (page < 1) {
        page = 1;
    } else if (page > pages) {
        page = pages;
    }
    var username = $("#username").val();
    search(username, page, 10);
}


function searchByName() {
    var username = $("#username").val();
    search(username, 1, 10);
}

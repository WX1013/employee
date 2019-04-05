$(function () {
    search();
});

function search(username, page, size) {
    $.ajax({
        url: "/feedback/search",   //url
        type: "post",   //请求类型 ,
        dataType: "json", // 返回参数类型
        data: {
            username: username,
            page: page,
            size: size
        },
        success: function (res) {
            var feeds = res.rows;
            console.log(feeds);
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for(var i = 0; i < feeds.length; i++){
                html += '<tr>';
                html += ' <td>'+ feeds[i].id +'</td>';
                html += ' <td>'+ feeds[i].username +'</td>';
                html += ' <td>'+ feeds[i].content +'</td>';
                html += ' <td>'+ feeds[i].addTimeStr +'</td>';
                html += ' <td>'+ state(feeds[i].state) +'</td>';
                html += ' <td>';
                if(feeds[i].state == 0) {
                    html += '<button type="button" class="btn bg-olive btn-xs" onclick="updateState(' + feeds[i].id + ',1)" >采纳</button>&nbsp; ' +
                        '<button type="button" class="btn bg-olive btn-xs" onclick="updateState(' + feeds[i].id + ',2)" >不采纳</button>';
                }
                html += ' <a  class="btn bg-maroon btn-xs" onclick="delFeed('+feeds[i].id+')"">删除</a></td>';
                html += '</tr>';
            }
            $("#table_data").html(html);

            // 分页数据
            $("#pageAndTotal").html("总共 " + res.pages + " 页，共 " + res.total + " 条数据");
            var pagenation = '<li><a href="#" onclick="changePage(1,10)" aria-label="Previous">首页</a></li>\n' +
                '             <li><a href="#" onclick="changePage(' + (page - 1) + ',' + pages + ')">上一页</a></li>';
            var begin;
            var end;
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

function changePage(page,pages) {
    if(page < 1){
        page = 1;
    }else if(page > pages){
        page = pages;
    }
    var username = $("#username").val();
    search(username,page,10);
}

function delFeed(id) {
    var flag = confirm("确认删除该反馈信息？删除不可恢复");
    if(flag){
        $.ajax({
            url: "/feedback/delete",   //url
            type: "post",   //请求类型 ,
            dataType: "json",
            data: {
                id: id
            },
            success: function (res) {
                alert(res.message);
                if(res.code == '200'){
                    location.reload();
                }
            }
        });
    }
    return;
}

// 启用、禁用用户
function updateState(id,state){
    $.ajax({
        url: "/feedback/updateState",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            id: id,
            state: state
        },
        success: function (res) {
            alert(res.message);
            if(res.code == '200'){
                location.reload();
            }
        }
    });

}

// 状态显示
function state(state) {
    if(state == 0){
        return "未采纳";
    }else if(state == 1){
        return "已采纳";
    }else{
        return "不采纳";
    }
}

function searchByName(){
    var username = $("#username").val();
    search(username,1,10);
}

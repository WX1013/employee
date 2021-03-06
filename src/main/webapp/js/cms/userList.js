$(function () {
    search();
});

function search(username, page, size) {
    $.ajax({
        url: "/user/search",   //url
        type: "post",   //请求类型 ,
        dataType: "json", // 返回参数类型
        data: {
            username: username,
            page: page,
            size: size
        },
        success: function (res) {
            var users = res.rows;
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for (var i = 0; i < users.length; i++) {
                html += '<tr>';
                html += ' <td>' + users[i].id + '</td>';
                html += ' <td>' + users[i].username + '</td>';
                if(users[i].delFlg === 0){
                    html += ' <td>' + state(users[i].delFlg) + '</td>';
                }else {
                    html += '<td style="color: red">' + state(users[i].delFlg) + '</td>';
                }
                html += ' <td>' + users[i].addTimeStr + '</td>';
                html += ' <td><button type="button" class="btn bg-olive btn-xs" onclick="updateState(' + users[i].id + ',' + users[i].delFlg + ')" >' + opration(users[i].delFlg) + '</button>';
                html += ' <a  class="btn bg-olive btn-xs" href="userDetail.html?id=' + users[i].empId + '">查看</a>';
                html += ' <a  class="btn bg-maroon btn-xs" onclick="delUser(' + users[i].id + ')"">删除</a>';
                html += ' <a  class="btn bg-olive btn-xs" onclick="resetPass(' + users[i].id + ')" >重置密码</a></td>';
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

function changePage(page, pages) {
    if (page < 1) {
        page = 1;
    } else if (page > pages) {
        page = pages;
    }
    var username = $("#username").val();
    search(username, page, 10);
}

function delUser(id) {
    var flag = confirm("确认删除该用户？删除不可恢复");
    if (flag) {
        $.ajax({
            url: "/user/delete",   //url
            type: "post",   //请求类型 ,
            dataType: "json",
            data: {
                id: id
            },
            success: function (res) {
                alert(res.message);
                if (res.code == '200') {
                    location.reload();
                }
            }
        });
    }
    return;
}

// 启用、禁用用户
function updateState(id, state) {
    var delFlg = 0;
    if (state == 0) { // 禁用操作
        delFlg = 1;
    } else { // 启用操作
        delFlg = 0;
    }
    if (delFlg == 1) {
        var flag = confirm("确认禁用此用户？禁用后用户不可登录");
        if (!flag) {
            return;
        }
    }
    $.ajax({
        url: "/user/updateState",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            id: id,
            state: delFlg
        },
        success: function (res) {
            alert(res.message);
            if (res.code == '200') {
                location.reload();
            }
        }
    });

}

// 操作按钮显示
function opration(state) {
    if (state == 0) {
        return "禁用";
    } else {
        return "启用";
    }
}

function resetPass(id) {
    var flag = confirm("确定重置该用户的密码？");
    if (flag) {
        $.ajax({
            url: "/user/resetPass",   //url
            type: "post",   //请求类型 ,
            dataType: "json",
            data: {
                id: id
            },
            success: function (res) {
                alert(res.message);
                if (res.code == '200') {
                    location.reload();
                }
            }
        });
    } else {
        return;
    }

}

// 状态显示
function state(state) {
    if (state == 0) {
        return "启用";
    } else {
        return "禁用";
    }
}

function searchByName() {
    var username = $("#username").val();
    search(username, 1, 10);
}

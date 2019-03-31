$(function () {
    search();
});

function search(deptName, page, size) {
    $.ajax({
        url: "/dept/search",   //url
        type: "post",   //请求类型 ,
        dataType: "json", // 返回参数类型
        data: {
            deptName: deptName,
            page: page,
            size: size
        },
        success: function (res) {
            var users = res.rows;
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for(var i = 0; i < users.length; i++){
                html += '<tr>';
                html += ' <td>'+ users[i].id +'</td>';
                html += ' <td>'+ users[i].deptName +'</td>';
                html += ' <td>'+ users[i].addTimeStr +'</td>';
                html += ' <td>'+ state(users[i].delFlg) +'</td>';
                html += ' <td><button type="button" class="btn bg-olive btn-xs" onclick="updateState('+users[i].id+','+users[i].delFlg+')" >'+opration(users[i].delFlg)+'</button>';
                html += ' <a  class="btn bg-olive btn-xs" href="userDetail.html?id='+users[i].empId+'">查看</a>';
                html += ' <a  class="btn bg-olive btn-xs" onclick="delUser('+users[i].id+')"">删除</a></td>';
                html += '</tr>';
            }
            $("#table_data").html(html);

            // 分页数据
            $("#pageAndTotal").html("总共 "+ res.pages +" 页，共 "+ res.total +" 条数据");
            var pagenation = '<li><a href="#" onclick="search()" aria-label="Previous">首页</a></li>\n' +
                '             <li><a href="#" onclick="changePage('+(page - 1)+','+pages+')">上一页</a></li>';
            for(var i = 1; i <= pages; i++){
                if(page == i){
                    pagenation += '<li class="active"><a href="#" >'+ i +'</a></li>';
                }else{
                    pagenation += '<li><a href="#">'+ i +'</a></li>';
                }
            }
            pagenation += '<li><a href="#" onclick="changePage('+(page + 1)+','+ pages+')">下一页</a></li>\n' +
                '          <li><a href="#" onclick="search(null,'+pages+',10)" aria-label="Next">尾页</a></li>';
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
    search(null,page,10);
}

function delUser(id) {
    var flag = confirm("确认删除该部门？");
    if(flag){
        $.ajax({
            url: "/dept/delete",   //url
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

function searchByName(){
    var deptName = $("#deptName").val();
    search(deptName,1,10);
}

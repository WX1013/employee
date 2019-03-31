$(function () {
    search();
});

function search(deptName, page, size) {
    $.ajax({
        url: "/dept/search",   //url
        type: "post",   //请求类型 ,
        dataType: "json", // 返回参数类型
        data: {
            name: deptName,
            page: page,
            size: size
        },
        success: function (res) {
            var depts = res.rows;
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for(var i = 0; i < depts.length; i++){
                html += '<tr>';
                html += ' <td>'+ depts[i].id +'</td>';
                html += ' <td>'+ depts[i].name +'</td>';
                html += ' <td>'+ depts[i].addTimeStr +'</td>';
                html += ' <td><a  class="btn bg-olive btn-xs" href="#" onclick="edit('+depts[i].empId+')">编辑</a>';
                html += ' <a  class="btn bg-olive btn-xs" href="userDetail.html?id='+depts[i].empId+'">查看部门人员</a>';
                html += ' <a  class="btn bg-olive btn-xs" onclick="delDept('+depts[i].id+')"">删除</a></td>';
                html += '</tr>';
            }
            $("#table_data").html(html);

            // 分页数据
            $("#pageAndTotal").html("总共 "+ res.pages +" 页，共 "+ res.total +" 条数据");
            var pagenation = '<li><a href="#" onclick="changePage(1,10)" aria-label="Previous">首页</a></li>\n' +
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
    var deptName = $("#deptName");
    search(deptName,page,10);
}

function delDept(id) {
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

function saveDept() {
    var id = $("#deptId").val();
    var name = $("#name").val();
    $.ajax({
        url: "/dept/save",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            id: id,
            name: name
        },
        success: function (res) {
            alert(res.message);
            if(res.code == '200'){
                location.reload();
                cancel();
            }
        }
    });
}

function searchByName(){
    var deptName = $("#deptName").val();
    search(deptName,1,10);
}
function myalert() {
    $("#bg").css({
        display: "block",
        height: "100%",
        position: "fixed"
    });
    var $box = $('.box');
    $box.css({
        //设置弹出层距离左边的位置
        left: ($("body").width() - $box.width()) / 2 + "px",
        //设置弹出层距离上面的位置
        top: ($(window).height() - $box.height()) / 2 - $(window).scrollTop() - $box.height() + "px",
        display: "block"
    }).find("p").html();
}

function edit(deptId){

}

function cancel() {
    $("#divResult").hide();
    $("#bg").hide();
    $(".box").hide();
}

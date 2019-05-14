$(function () {
    search();
});

function search(username, page, size) {
    $.ajax({
        url: "/emp/search",   //url
        type: "post",   //请求类型 ,
        dataType: "json", // 返回参数类型
        data: {
            name: username,
            page: page,
            size: size
        },
        success: function (res) {
            var entities = res.rows;
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for(var i = 0; i < entities.length; i++){
                html += '<tr>';
                html += ' <td>'+ entities[i].id +'</td>';
                html += ' <td>'+ entities[i].name +'</td>';
                html += ' <td>'+ getSex(entities[i].sex) +'</td>';
                html += ' <td>'+ entities[i].phone +'</td>';
                html += ' <td>'+ entities[i].salary +'</td>';
                html += ' <td>'+ entities[i].position +'</td>';
                html += ' <td>'+ entities[i].addTimeStr +'</td>';
                html += ' <td><a  class="btn bg-olive btn-xs" onclick="showDialog('+entities[i].id+','+entities[i].salary+')">设置工资</a>';
                html += ' <a  class="btn bg-olive btn-xs" href="userDetail.html?id='+entities[i].id+'">查看</a>';
                html += ' <a  class="btn bg-maroon btn-xs" onclick="delEmp('+entities[i].id+')">删除</a></td>';
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

function getSex(sex) {
    if(sex == 1){
        return "男";
    }else{
        return "女";
    }

}

function delEmp(id) {
    var flag = confirm("·删除该职工？删除不可恢复");
    if(flag){
        $.ajax({
            url: "/emp/delete",   //url
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
    var empname = $("#name").val();
    search(empname,1,10);
}

function showDialog(id,salary){
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
    $("#empId").val(id);
    $("#salary").val(salary);
}

function updateSalary() {
    var empId = $("#empId").val();
    var salary = $("#salary").val();
    $.ajax({
        url: "/emp/updateSalary",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            id: empId,
            salary: salary
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

function cancel() {
    $("#divResult").hide();
    $("#bg").hide();
    $(".box").hide();
}
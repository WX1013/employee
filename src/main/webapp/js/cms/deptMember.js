$(function () {
    var deptId = getUrlData("deptId");
    $.ajax({
        url: "/member/getMembers",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            deptId: deptId
        },
        success: function (res) {
            var members = res.rows;
            var page = res.page;
            var pages = res.pages;
            // 列表数据
            var html = '';
            for (var i = 0; i < members.length; i++) {
                html += '<tr>';
                html += ' <td>' + members[i].id + '</td>';
                html += ' <td>' + members[i].empname + '</td>';
                html += ' <td>' + members[i].salary + '</td>';
                html += ' <td>' + members[i].position + '</td>';
                html += ' <td>' + members[i].addTimeStr + '</td>';
                html += ' <td><a  class="btn bg-olive btn-xs" onclick="tobeLeader('+members[i].id+')">置为部长</a>';
                html += ' <a  class="btn bg-maroon btn-xs" onclick="delMember(' + members[i].id + ')"">删除</a></td>';
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
});

function delMember(id) {
    var flag = confirm("确认删除该职工？删除不可恢复");
    if (flag) {
        $.ajax({
            url: "/member/delMember",   //url
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

// 获取url参数
function getUrlData(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    } else {
        return null;
    }
}


function addMember() {
    var deptId = getUrlData("deptId");
    var empid = $("#empList").val();
    $.ajax({
        url: "/member/addMember",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            empid: empid,
            deptid: deptId
        },
        success: function (res) {
            alert(res.message);
            if (res.code == '200') {
                location.reload();
            }
        }
    });
}

function myalert() {
    $.ajax({
        url: "/emp/getEmps",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {},
        success: function (res) {
            var list = res.result;
            var html = '<option value="">请选择职工</option>';
            for(var i = 0; i < list.length; i++){
                html += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
            }
            $("#empList").html(html);
        }
    });

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

function cancel() {
    $("#divResult").hide();
    $("#bg").hide();
    $(".box").hide();
}


function tobeLeader(id) {
    if(confirm("确定设置此职工为本部门部长吗？")){
        $.ajax({
            url: "/member/tobeLeader",   //url
            type: "post",   //请求类型 ,
            dataType: "json",
            data: {
                id: id
            },
            success: function (res) {
                alert(res.message);
                if(res.code == 200){
                    location.reload();
                }
            }
        });
    }
}
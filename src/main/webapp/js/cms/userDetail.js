$(function () {
    var id = getUrlData("id");
    $.ajax({
        url: "/emp/findOne",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            id: id
        },
        success: function (res) {
            var emp = res.result;
            $("#name").text(emp.name);
            $("#age").text(emp.age);
            if(emp.sex == 0){
                $("#sex").text("女");
            }else{
                $("#sex").text("男");
            }
            $("#identity").text(emp.identity);
            $("#phone").text(emp.phone);
            $("#email").text(emp.email);
            $("#deptName").text(emp.deptName);
            $("#address").text(emp.address);
            if(emp.delFlg == 0){
                $("#state").text("启用");
            }else{
                $("#state").text("禁用");
            }
            $("#addTime").text(emp.addTimeStr);
        }
    });
});

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

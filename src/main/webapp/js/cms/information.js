$(function () {
    $.ajax({
        url: "/user/getUserInfo",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: { },
        success: function (res) {
            var user = res.result;
            if(user.age == null){
                alert("请尽快完善个人信息");
            }
            fullData(user);
        }
    });
});

function updateInfo() {
    var id = $("#empId").val();
    var name = $("#name").val();
    if(name == null){
        alert("姓名不能为空");
        return;
    }
    var age = $("#age").val();
    if(age == null){
        alert("年龄不能为空");
        return;
    }
    var sexStr = $("#sex").val();
    if(sexStr == null){
        alert("性别不能为空");
        return;
    }
    if(sexStr != "男" && sex != "女"){
        alert("请输入正确的性别");
        return;
    }
    var sex = 0;
    if(sexStr == "男"){
        sex = 1;
    }
    var identity = $("#identity").val();
    if(identity == null){
        alert("身份证不能为空");
        return;
    }
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (!reg.test(identity)) {
        alert("请输入正确的身份证号码");
        return;
    }

    var phone = $("#phone").val();
    if(phone == null){
        alert("电话不能为空");
        return;
    }
    if(!(/^1[34578]\d{9}$/.test(phone))){
        alert("请输入正确的手机号码");
        return;
    }
    var email = $("#email").val();
    if(email == null){
        alert("邮箱不能为空");
        return;
    }
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    if(!reg.test(email)){ //正则验证不通过，格式不对
        alert("请输入有效的邮箱");
        return ;
    }
    var address = $("#address").val();
    if(address == null){
        alert("地址不能为空");
        return;
    }
    $.ajax({
        url: "/user/updateInfo",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            id: id,
            name: name,
            age: age,
            sex: sex,
            identity: identity,
            phone: phone,
            email: email,
            address: address
        },
        success: function (res) {
            alert(res.message);
            if(res.code == 200){
                location.reload();
            }
        }
    });


}

function fullData(user) {
    $("#empId").val(user.id);
    $("#name").val(user.name);
    $("#age").val(user.age);
    if(user.sex == 0){
        $("#sex").val("女");
    }else if(user.sex == 1){
        $("#sex").val("男");
    }else{
        $("#sex").val("");
    }
    $("#identity").val(user.identity);
    $("#phone").val(user.phone);
    $("#email").val(user.email);
    $("#deptName").val(user.deptName);
    $("#address").val(user.address);
    if(user.delFlg == 0){
        $("#state").val("启用");
    }else{
        $("#state").val("禁用");
    }
    $("#addTime").val(user.addTimeStr);
}

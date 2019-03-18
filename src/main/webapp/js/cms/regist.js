// 注册方法
function regist() {
    var name = $("#name").val();
    if(name == "" || name == null){
        $("#error").html("真实姓名不能为空");
        return;
    }
    var username = $("#username").val();
    if(username == "" || username == null){
        $("#error").html("用户名不能为空");
        return;
    }
    var phone = $("#phone").val();
    if(phone == "" || phone == null){
        $("#error").html("电话不能为空");
        return;
    }
    var password = $("#password").val();
    if(password == "" || password == null){
        $("#error").html("密码不能为空");
        return;
    }
    var repassword = $("#repassword").val();
    if(repassword != password){
        $("#error").html("两次密码不一样");
        return;
    }
    $("#error").html(" ");
    $.ajax({
        url: "/user/regist",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            username: username,
            password: password,
            phone: phone
        },
        success: function (msg) {
            alert(msg.message);
            if (msg.code == 200) {
                location.href = "../../login.html";
            }
        }
    });

}



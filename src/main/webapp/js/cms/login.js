$('input[type=radio][name=options]').change(function () {
    if (this.value == '0') {
        $("#userContron").removeAttr("hidden");
    }
    else if (this.value == '1') {
        $("#userContron").attr("hidden", "hidden");
    }
});

function keyLogin(){
    debugger;
    if(event.keyCode == 13){
        login();
    }
}

// 登录方法
function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    var object = {username: username, password: password};
    if (!username) {
        alert("名称不能为空");
    } else if (!password) {
        alert("请输入密码");
    }
    if ($('input:radio:checked').val() == '1') {  // 是管理员
        $.ajax({
            url: "/admin/login",   //url
            type: "post",   //请求类型 ,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(object),
            success: function (data) {
                alert(data.message);
                if (data.code == 200) {
                    location.href = "../../cms/adminIndex.html";
                }
            }
        });
    } else { // 是用户
        $.ajax({
            url: "/user/login",   //url
            type: "post",   //请求类型 ,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(object),
            success: function (data) {
                alert(data.message);
                if (data.code == 200) {
                    location.href = "./cms/userIndex.html";
                }
            }
        });
    }
}

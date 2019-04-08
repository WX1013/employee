var vercode = 0;
var time = 60;
var flag = true;   //设置点击标记，防止60内再次点击生效
//发送验证码
$('#code').click(function () {
    debugger;
    $(this).attr("disabled", true);
    var username = $('#username').val();
    var email = $('#email').val();
    if(username == null){
        alert("请输入用户名");
        return;
    }
    if(email == null){
        alert("请输入邮箱");
        return;
    }
    if (flag) {
        var timer = setInterval(function () {
            if (time == 60 && flag) {
                flag = false;
                $.ajax({
                    url: '/user/getCode',
                    type: 'get',
                    dataType: "json",
                    data: {
                        username: username,
                        email: email
                    },
                    success: function (res) {
                        debugger;
                        if (res == '200') {
                            vercode = res.result;
                            $("#code").html("已发送");
                        } else {
                            alert(res.message);
                            flag = true;
                            time = 60;
                            clearInterval(timer);
                        }
                    }
                });
            } else if (time == 0) {
                $("#code").removeAttr("disabled");
                $("#code").html("获取验证码");
                clearInterval(timer);
                time = 60;
                flag = true;
            } else {
                $("#code").html(time + " s 重新发送");
                time--;
            }
        }, 1000);
    }

});

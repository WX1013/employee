function changePass(){
    var password = $("#password").val();
    var newpassword = $("#newpassword").val();
    var repassword = $("#repassword").val();
    $.ajax({
        url: "/user/changePass",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {
            password: password,
            newpassword: newpassword,
            repassword: repassword
        },
        success: function (res) {
            alert(res.message);
            if (res.code == '200') {
                parent.location.href = '../login.html';
            }
        }
    });

}

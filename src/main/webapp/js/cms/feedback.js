function saveFeedback() {
    var content = $("#content").val();
    if(content.length > 100){
        alert("反馈内容字数不可超过100");
        return;
    }
    $.ajax({
        url: "/feedback/add",   //url
        type: "post",   //请求类型 ,
        dataType: "json",
        data: {content: content},
        success: function (data) {
            alert(data.message);
            if (data.code == 200) {
                location.reload();
            }else if(data.code == 300){
                location.href = '../login.html';
            }
        }
    });
}


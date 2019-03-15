//window.alert = function(txt)
//{
//    var winSize = function(){
//        var xScroll, yScroll, windowWidth, windowHeight, pageWidth, pageHeight;
//        // innerHeight获取的是可视窗口的高度，IE不支持此属性
//        if (window.innerHeight && window.scrollMaxY) {
//            xScroll = document.body.scrollWidth;
//            yScroll = window.innerHeight + window.scrollMaxY;
//        } else if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac
//            xScroll = document.body.scrollWidth;
//            yScroll = document.body.scrollHeight;
//        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
//            xScroll = document.body.offsetWidth;
//            yScroll = document.body.offsetHeight;
//        }
//
//        if (self.innerHeight) {    // all except Explorer
//            windowWidth = self.innerWidth;
//            windowHeight = self.innerHeight;
//        } else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
//            windowWidth = document.documentElement.clientWidth;
//            windowHeight = document.documentElement.clientHeight;
//        } else if (document.body) { // other Explorers
//            windowWidth = document.body.clientWidth;
//            windowHeight = document.body.clientHeight;
//        }
//
//        // for small pages with total height less then height of the viewport
//        if (yScroll < windowHeight) {
//            pageHeight = windowHeight;
//        } else {
//            pageHeight = yScroll;
//        }
//
//        // for small pages with total width less then width of the viewport
//        if (xScroll < windowWidth) {
//            pageWidth = windowWidth;
//        } else {
//            pageWidth = xScroll;
//        }
//
//        return{
//            'pageWidth':pageWidth,
//            'pageHeight':pageHeight,
//            'windowWidth':windowWidth,
//            'windowHeight':windowHeight
//        }
//    }();
//
//    var shield = document.createElement("DIV");
//    shield.id = "shield";
//    shield.style.position = "absolute";
//    shield.style.left = "0px";
//    shield.style.top = "0px";
//    shield.style.width =  winSize.pageWidth+"px";
//    shield.style.height = document.body.scrollHeight+"px";
//    shield.style.background = "rgba(51,51,51,0.6)";
//    shield.style.textAlign = "center";
//    shield.style.zIndex = "10000";
//    shield.style.filter = "alpha(opacity=0)";
//    var alertFram = document.createElement("DIV");
//    alertFram.id="alertFram";
//    alertFram.style.position = "fixed";
//    alertFram.style.left = "35%";
//    alertFram.style.top = '-66px';
//    var top = (winSize.windowHeight / 2 - 300);
//    alertFram.style.width =  "30%";
//    alertFram.style.height = "33px";
//    alertFram.style.textAlign = "left";
//    alertFram.style.lineHeight = "33px";
//    alertFram.style.zIndex = "10001";
//    alertFram.style.fontSize = "16px";
//    strHtml = "<div class='alert alert-info' role='alert'>\n";
//    strHtml += "<span>"+txt+"</span>";
//    strHtml += "<button type='button' class='close' onclick='doOk()'><span style='font-size: 30px'>×</span></button>"
//    strHtml += "</div>";
//    alertFram.innerHTML = strHtml;
//    document.body.appendChild(alertFram);
//    document.body.appendChild(shield);
//    $(alertFram).animate({top:top+"px"},400);
//    var c = 0;
//    this.doAlpha = function(){
//        if (c++ > 20){clearInterval(ad);return 0;}
//        shield.style.filter = "alpha(opacity="+c+");";
//    };
//    var ad = setInterval("doAlpha()",5);
//    this.doOk = function(){
//        $(alertFram).animate({top:-top+"px"},300,function(){
//            alertFram.remove();
//            shield.remove();
//        });
//        // alertFram.style.display = "none";
//        // shield.style.display = "none";
//    };
//    setTimeout("doOk()",2000);
//    alertFram.focus();
//    document.body.onselectstart = function(){return false;};
//};
// window.confirm = function (txt) {
//     var _confirm = $('<div class="modal fade" id="_confirm" tabindex="-1" role="dialog" aria-labelledby="importmodal" data-backdrop="static"  aria-hidden="true"> ' +
//         '<div class="modal-dialog"> ' +
//         '<div class="modal-content" >' +
//         '<div class="modal-header">' +
//         '<h4 class="modal-title" id="commonModalLabel"> 提示' +
//         '</h4>' +
//         '</div>' +
//         '<div class="modal-body"><span style="margin-left: 20px;font-size: 20px;">'+txt+'</span>'+
//         '</div> ' +
//         '<div class="modal-footer" style="margin-top:20px;"> ' +
//         '<button type="button" class="btn btn-default" data-dismiss="modal">取消 ' +
//         '</button> ' +
//         '<button type="button" class="btn btn-primary" >确定 ' +
//         '</button> ' +
//         '</div> ' +
//         '</div> ' +
//         '</div> ' +
//         '</div>');
//     $(document.body).append(_confirm);
//     _confirm.modal('show');
// };


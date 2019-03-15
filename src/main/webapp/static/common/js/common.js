function onlyNum() {
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
    event.returnValue=false;
}
/**
 * 纯数字
 * @param obj
 */
function justNum(obj){
	obj.value=obj.value.replace(/\D/g,'');
}
/**
 * 金额的格式限制
 * @param obj
 */
function clearNoNum(obj){  
  //修复第一个字符是小数点 的情况.  
  if(obj.value !=''&& obj.value.substr(0,1) == '.'){  
      obj.value="";  
  }  
  obj.value = obj.value.replace(/^0*(0\.|[1-9])/, '$1');//解决 粘贴不生效  
  obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的       
  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");      
  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数       
  if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类于 01、02的金额  
      if(obj.value.substr(0,1) == '0' && obj.value.length == 2){  
          obj.value= obj.value.substr(1,obj.value.length);      
      }  
  }      
}
/**
 *
 * @param value
 * @param min 最小长度
 * @param max 最大长度
 * @returns {boolean}
 */
function charValidate(value,min,max) {
    var exp = '^[A-Za-z0-9]{'+min+','+max+'}$';
    var reg = new RegExp(exp);
    return reg.test(value);
}

function emptyValidate(o){
	var form = $(o);
	var notempty = true;
	form.find(".required").each(function(){
		var value = parent.val();
		if(!value){
			var id = parent.attr("id");
			var text = form.find("label[for='"+id+"']").text();
			alert(text+"不能为空!");
			parent.focus();
			notempty = false;
			return false;
		}
	});
	
	form.find(".phone-num").each(function(){
		var value = parent.val();
		if(value){
			var reg = /^1[0-9]{10}$/;
			if (!reg.test(value)) {
				alert("请输入正确的手机号");
				parent.focus();
				notempty = false;
				return false;
			}
		}
	});
	return true;
}

function formatterDate(yyyymmdd){
	yyyymmdd = String(yyyymmdd);
	var yyyy = yyyymmdd.substring(0,4);
	var mm = yyyymmdd.substring(4,6);
	var dd = yyyymmdd.substring(6,8);
	return yyyy+"-"+mm+"-"+dd;
}

function validatePhone(obj) {
	var value = $(obj).val();
	if(value){
		var reg = /^1[0-9]{10}$/;
		if (!reg.test(value)) {
			alert("请输入正确的手机号");
			$(obj).focus();
		}
	}
}

function reloadchild(){
	var href = window.location.href;
	if(href.indexOf('#') != -1){
		var href = href.substring(href.indexOf('#')+1);
	}
	loadchild(href);
}

function loadchild(controller,param){
	 $(".container-fluid").html("");
	 var href = window.location.href;
	 if(controller.indexOf("parent=")==-1){
         // if (href.indexOf("#") != -1
	    	//  var urlsearch = new UrlSearch();
	    	//  var parent = urlsearch.parent;
	     //  	if(typeof(parent)!="undefined"){
	     //     	history.pushState({}, controller,"#"+controller+"&parent="+encodeURIComponent(parent));
	     //  	}else{
	     //  		var num=href.indexOf("#")
	     //     	href=href.substr(num+1);
	     //     	history.pushState({}, controller,"#"+controller+"&parent="+encodeURIComponent(href));
	     //  	}
	     // }else{
	    	 history.pushState({}, controller,"#"+controller);
	     // }
	 }else{
		 history.pushState({}, controller,"#"+controller); 
	 }
	 var url = controller;
	 if(controller.indexOf('?') != -1){
	     url = controller.substring(0, controller.indexOf('?'));
     }
    $(".menu-href").each(function(){
//        if($(this).attr("menu-controller").indexOf(url) != -1){
    	if($(this).attr("menu-controller") == url){
            $( ".sidebar-menu .treeview li").removeClass("active");
            $(this).parent().addClass("active");
            $(this).parent().parent().show();
            $(this).parent().parent().parent().addClass("menu-open");
            if($(this).parent().parent().parent().parent().parent('.treeview').length != 0){
                $(this).parent().parent().parent().parent().parent('.treeview').addClass("active");
                $(this).parent().parent().parent().parent().parent('.treeview').addClass("menu-open");
            }
            return false;
        }
    });
	 var parent =  $( ".sidebar-menu .treeview .treeview-menu .active");
     var padd = parent.attr("data-flagadd");
     var pdel = parent.attr("data-flagdel");
     var pexport = parent.attr("data-flagexport");
     var pedit = parent.attr("data-flagedit");
     var pimport = parent.attr("data-flagimport");
     $.ajax({
         url: controller,
         async: false,
         data:param?param:{},
         beforeSend:function (xmlReq) {
             $('.overlay').css('display','block');
         },
         success: function (d) {
             $('.overlay').hide();
//             var html = $(d);
             $(".container-fluid").html(d);
             if(padd==0){
           	  $(".padd").remove();
             }
             if(pdel==0){
           	  $(".pdel").remove(); 
             }
             if(pexport==0){
           	  $(".pexport").remove();       	  
             }
             if(pedit==0){
           	  $(".pedit").remove();     
             }
             if(pimport==0){
           	  $(".pimport").remove();        	  
             }
         }
     });
}

function UrlSearch() 
{
   var name,value; 
   //decodeURI解决乱码问题
   var str=decodeURI(location.href); //取得整个地址栏
   var num=str.indexOf("#") 
   str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
   var datanum = str.indexOf("?");
   str=str.substr(datanum+1);
   var arr=str.split("&"); //各个参数放到数组里
   for(var i=0;i < arr.length;i++){ 
    num=arr[i].indexOf("="); 
    if(num>0){ 
     name=arr[i].substring(0,num);
     value=arr[i].substr(num+1);
     this[name]=value;
     } 
    } 
}
/**
 * code根据code返回下拉框
 * @param code 字典code
 * @param name 需要默认选择的text
 * @param async 是否异步
 * @returns {combox}
 */
$.fn.combox = function (code,name,async) {
    return new combox($(this),code,name,async);
};

function combox(elem,code,name, async) {
    if(typeof async === "undefined"){
        async = true;
    }
    $.ajax({
        url:'sys/selData',
        type:'GET',
        async: async,
        data: {code:code},
        success: function (data) {
            if(data.code != '200'){
                return ;
            }
            $.each(data.data,function(index,dic){
                if(name && name == dic.name){
                    elem.append('<option value="' + dic.code + '" selected>' + dic.name + '</option>');
                }else {
                    elem.append('<option value="' + dic.code + '">' + dic.name + '</option>');
                }
            });
        }
    });
}
/**
 * 序列化form为对象
 * @returns {{}}
 */
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
var icons = [
    "icon iconfont beamicon-renyuanguanli",
    "icon iconfont beamicon-wuzi",
    "icon iconfont beamicon-shebeipeizhi",
    "icon iconfont beamicon-shengchanguanli",
    "icon iconfont beamicon-AIshiyanshi",
    "icon iconfont beamicon-msnui-portal",
    "icon iconfont beamicon-quanxianguanli",
    "icon iconfont beamicon-xitongshezhi",
];

function getSelOperator() {
    var operators = [];
    if(typeof $('#selectedOperator') === "undefined"){
        return [];
    }
    $('#selectedOperator').children("ul").children().each(function (i, elem) {
        var operator = {};
        operator['ecode'] = $(elem).attr('d-ecode');
        operator['identity'] = $(elem).attr('d-identity');
        operator['name'] = $(elem).attr('d-name');
        operator['presasg'] = $(elem).attr('d-presasg');
        operator['workdept'] = $(elem).attr('d-workdept');
        operator['group'] = $(elem).attr('d-group');
        operators.push(operator);
    });
    return operators;
}
function removeOp(node) {
    $(node).parent().parent().remove();
}
function removeAllOp() {
    $('#selectedOperator').children("ul").remove();
}
window.alert=function(e,callback){
    // $.alert({
    //     icon: 'fa fa-warning',
    //     title:'系统提示:',
    //     content: e,
    //     type: 'red',
    //     buttons: {
    //         cancel: {
    //             btnClass:'btn-red',
    //             text: '确认',
    //             action: function(){
    //                 if (callback){
    //                     setTimeout(callback(),100);
    //                 }
    //             }
    //         }
    //     }
    // });

    $.confirm({
        icon: 'fa fa-warning',
        title:'系统提示:',
        content: e,
        type: 'blue',
        buttons: {
            cancel: {
                btnClass:'btn-blue',
                text: '确认',
                action: function(){

                }
            }
        },
        onClose: function () {
        },
        onDestroy: function () {
            if (callback){
                setTimeout(callback(),100);
            }
        }
    });
}


window.confirm=function(txt,callback,cancelback){
    $.confirm({
        title: '操作确认',
        content: txt,
        type: 'blue',
        typeAnimated: true,
        buttons: {
            success: {
                text: '确认',
                btnClass: 'btn-primary',
                action: function(){
                    if(callback){
                        callback();
                    }
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn-red',
                action: function(){
                    if(cancelback){
                        cancelback();
                    }
                }
            },
        }
    });
};
$('.notifications-menu').on('show.bs.dropdown', function(){
    fetchMessage();
});
$('.notifications-menu').on('hidden.bs.dropdown', function(){
    $('#message_list').empty();
});


$.ajaxSetup({
    beforeSend: function (){
        var btnId = this.btnId;
        // console.log(btnId);
        // console.log(this.url);
        return decideAuth(btnId);
    }
});

function decideAuth(btnId) {
    if(!btnId){
        return true;
    }
    var $activeMenu = $(".menu-href").parent('.active');
    if($activeMenu.hasClass("nothis")){
    	$activeMenu = $activeMenu.parent('ul').parent('li');
    }
    if($activeMenu.length === 0){
        // console.log("获取不到active menu");
        return true;
    }
    var modelId = $activeMenu.attr('model-id');
    if(!modelId){
        // console.log("获取不到modelId");
        return true;
    }
    var roleBtnInfo = fetchRoleBtn(modelId);
    if(!roleBtnInfo){
        return true;
    }
    var canRequest = false;
    $.each(roleBtnInfo, function () {
        if(this.btnPageId === btnId && this.roleBtnId){
            canRequest = true;
            return false;
        }

    });
//    if(!canRequest){
//        alert("无权限访问");
//    }
    return canRequest;
}

function fetchRoleBtn(modelId) {
    var roleBtnInfo;
    $.ajax({
        url: './admin/fetchLoginUserBtnPer',
        async: false,
        data: {modelId: modelId},
        success: function (data) {
            if(data.code != '200' || data.data.length == 0){
                return ;
            }
            roleBtnInfo = data.data;
        },
        error: function () {
            alert('系统错误');
        }
    });
    return roleBtnInfo;
}

function fetchMenuData() {
    $.ajax({
        url:'./admin/getMainMenu',
        type:'GET',
        async: false,
        success:function (data) {
            if(data.code != '200'){
                alert(data.message);
                return ;
            }
            if(typeof data.data === "undefined"){
                alert("本账号角色未配置菜单项");
                return ;
            }
            initMenu(data.data);
        },
        error:function () {
            alert("系统错误");
        }
    })
}

function initMenu(data) {
    var $menu = $('#sidebar-menu');
    $.each(data, function (i1, item) {
        var $tree_li = "";
        if(item.url){
            $tree_li =  $('<li class="treeview"> <a href="#'+item.url+'" menu-controller="'+item.url+'" class="li_column"><i class="icon iconfont beamicon-renyuanguanli"></i>'+item.menuname+'<span></span> <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i> </span> </a></li>');
        }else{
            $tree_li =  $('<li class="treeview"> <a href="#" class="li_column"><i class="icon iconfont beamicon-renyuanguanli"></i>'+item.menuname+'<span></span> <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i> </span> </a></li>');
        }
        if(item.nodes.length === 0){
            $menu.append($tree_li);
            return true;
        }
        var $tree_ul = $('<ul class="treeview-menu"></ul>');
        //二级菜单
        $.each(item.nodes, function (i2, item2) {
        	if(item2.url){
        		var $tree_li_2 = $('<li model-id="'+item2.id+'" role-id="'+item2.roleId+'"><a href="#'+item2.url+'" class="menu-href li_column2" menu-controller="'+item2.url+'" menu-name="'+item2.menuname+'"><i class="'+item2.wineighticon+'"></i>'+item2.menuname+'</a> </li>');
                $tree_ul.append($tree_li_2);
        	}else{
        		var $tree_li_3 = '';
        		$.each(item2.nodes, function (i3, item3) {
                	if(item3.url){
                		$tree_li_3 += '<li model-id="'+item3.id+'" role-id="'+item3.roleId+'"><a href="#'+item3.url+'" class="menu-href li_column2" menu-controller="'+item3.url+'" menu-name="'+item3.menuname+'"><i class="'+item3.wineighticon+'"></i>'+item3.menuname+'</a> </li>';
                	}
                });
        		
    			var $tree_li_2 = $('<li class="treeview" model-id="'+item2.id+'" role-id="'+item2.roleId+'"><a href="#" class="menu-href li_column2" menu-name="'+item2.menuname+'" > <span>'+item2.menuname+'</span> <span class="pull-right-container">'+
    					'<i class="fa fa-angle-left pull-right"></i> </span> </a>' + 
    					'<ul class="treeview-menu" style="display: none;">'+$tree_li_3+'</ul></li>');
    			
                $tree_ul.append($tree_li_2);
                
        	}
        });
        $tree_li.append($tree_ul);
        $menu.append($tree_li);
    })
}



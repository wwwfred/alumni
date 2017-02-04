var schoolCode;
var schoolWX;
var schoolLogoUrl;
var baseUrl = location.protocol + "//" + location.host;
/*var baseUrl="http://alumni.wwwfred.net";*/

var myHome = function(schoolCode)
{
//	console.info(baseUrl);
	location.href = baseUrl + "/page/schoolList.html"+"?schoolCode="+ (schoolCode?schoolCode:"42_20_0001");
};

var request = {
		QueryString : function(val) {
			var uri = window.location.search;
			var re = new RegExp("" + val + "\=([^\&\?]*)", "ig");
			return ((uri.match(re)) ? decodeURI((uri.match(re)[0].substr(val.length + 1)))
					: null);
		},
		QueryStrings : function() {
			var uri = window.location.search;
			var re = /\w*\=([^\&\?]*)/ig;
			var retval = [];
			while ((arr = re.exec(uri)) != null)
				retval.push(decodeURI(arr[0]));
			return retval;
		},
		setQuery : function(val1, val2) {
			var a = this.QueryStrings();
			var retval = "";
			var seted = false;
			var re = new RegExp("^" + val1 + "\=([^\&\?]*)$", "ig");
			for (var i = 0; i < a.length; i++) {
				if (re.test(a[i])) {
					seted = true;
					a[i] = val1 + "=" + val2;
				}
			}
			retval = a.join("&");
			return "?" + retval
					+ (seted ? "" : (retval ? "&" : "") + val1 + "=" + val2);
		}
};

var ajax_weui_loading_html = '<div id="ajax_weui_loading"><div class="weui-loadmore"><i class="weui-loading"></i><span class="weui-loadmore__tips">正在加载</span></div></div>';  
var ajax_weui_loading_node;
var ajax = function(method,url,async,data,headers,successHandler,errorHandler)
{
	if(async)
	{
		ajax_weui_loading_node = document.createElement("div");
		ajax_weui_loading_node.innerHTML = ajax_weui_loading_html;
		document.getElementsByTagName("body")[0].appendChild(ajax_weui_loading_node);
	}
	var xmlHttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlHttp=new XMLHttpRequest();
	}	
	else
	{// code for IE6, IE5
		xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200)
	    {
			if(async)
			{
				document.getElementsByTagName("body")[0].removeChild(ajax_weui_loading_node);  
			}
			if(xmlHttp.status==200&&successHandler)
			{
				successHandler(xmlHttp.responseText);
			}
			else if(errorHandler)
			{
				errorHandler(xmlHttp.status);
			}
	    }
	};
	xmlHttp.open(method,url,async);
	if(headers)
	{
		for (var i = 0; i < headers.length; i++) {
//			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.setRequestHeader(headers[i][0],headers[i][1]);
		}
	}
	if(data)
	{
		xmlHttp.send(data);
	}
	else
	{
		xmlHttp.send();
	}
};

var ajaxGet = function(url,successHandler){
	ajax("GET", url, true, null, null, successHandler, null);
};
var ajaxPost = function(url,data,successHandler){
	ajax("POST", url, true, data, null, successHandler, null);
};

var ajaxFile = function(url,form,inputFile,successHandler){
	ajax_weui_loading_node = document.createElement("div");
	ajax_weui_loading_node.innerHTML = ajax_weui_loading_html;
	document.getElementsByTagName("body")[0].appendChild(ajax_weui_loading_node);
	/* FormData 是表单数据类 */
	var formData = new FormData(form);
	//var formData = form.getFormData();
	var xmlHttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlHttp=new XMLHttpRequest();
	}	
	else
	{// code for IE6, IE5
		xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
//	formData.append("upload", 1);
	/* 把文件添加到表单里 */
	formData.append(inputFile.name, inputFile.files[0]);
	xmlHttp.open("POST", url, true);

	xmlHttp.onload = function () {
		document.getElementsByTagName("body")[0].removeChild(ajax_weui_loading_node);  
		successHandler(xmlHttp.responseText);
	};

	xmlHttp.send(formData);
};

var getMap = function(){//初始化map_，给map_对象增加方法，使map_像个Map  
    var map_=new Object();  
    //属性加个特殊字符，以区别方法名，统一加下划线_  
    map_.put=function(key,value){    map_[key]=value;}   
    map_.get=function(key){    return map_[key];}  
    map_.remove=function(key){    delete map_[key];}      
    map_.keyset=function(){  
        var ret="";  
        for(var p in map_){      
            if(typeof p =='string' && p.substring(p.length-1)=="_"){   
                ret+=",";  
                ret+=p;  
            }  
        }             
        if(ret==""){  
            return ret.split(","); //empty array  
        }else{  
            return ret.substring(1).split(",");   
        }  
    }     
    return map_;  
};  

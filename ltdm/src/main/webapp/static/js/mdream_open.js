

function  Ws(){
	this.alive =  true;
	if(window.XMLHttpRequest){
		this.req = new XMLHttpRequest();
	}else{
		this.req = new ActiveXObject("Msxml2.XMLHTTP");  //ie
	}
}

var ws =  new Ws();
var AjaxArr =  new Array();
var m_data = {};

Ws.prototype.record =  {data:null,url:null,container_iframe:null,result_view:null,second_view:null,view_data:null}

Ws.prototype.delback =function(target,page_view){
	if(!arguments[0] || arguments[0]== '') page_view = "#second_view";
	if (confirm("确认要删除?")) {
		var $this = $(target),_clickTab = $this.attr('link'); 
		$.get(_clickTab,function (data ,status){
			$("#container_iframe").html(ws.record.container_iframe);
			ws.recover_view_data(ws.record.view_data);
			console.log(ws.record.data);
			$.ajax({  
		        url : ws.record.url,  
		        type : 'POST',  
		        data : $.toJSON(ws.record.data),           
		        contentType : 'application/json',  
		        success : function(data, status, xhr) {  
		        	$(page_view).html(data);
		        	
		        }
			});
		});
	 }
}


Ws.prototype.recback =  function (target,page_view){
	if(!arguments[0] || arguments[0]== '') page_view = "#second_view";
	if (confirm("确认要恢复?")) {
           
		var $this = $(target),_clickTab = $this.attr('link'); 
		//ws.record.container_iframe = $("#container_iframe").html;
		$.get(_clickTab,function (data ,status){
			$("#container_iframe").html(ws.record.container_iframe);
			ws.recover_view_data(ws.record.view_data);
			$.ajax({  
		        url : ws.record.url,  
		        type : 'POST',  
		        data : $.toJSON(ws.record.data),           
		        contentType : 'application/json',  
		        success : function(data, status, xhr) {  
		        	$(page_view).html(data);
		        	
		        }
			});
		});
	 }
}

Ws.prototype.callback =  function(page_view){
	if(!arguments[0]) page_view = "#second_view";
//	alert(ws.record.container_iframe)
	$("#container_iframe").html(ws.record.container_iframe);
	this.recover_view_data(ws.record.view_data);
	$.ajax({  
        url : ws.record.url,  
        type : 'POST',  
        data : $.toJSON(ws.record.data),           
        contentType : 'application/json',  
        success : function(data, status, xhr) {  
        	$(page_view).html(data);
        	
        }
	});
	
}

Ws.prototype.ajax = function (url,type,data,content_type){
	
	this.req.open(type,url,true);
	this.req.setRequestHeader("Content-Type", content_type);
	this.req.send($.toJSON(data));
	this.req.onreadystatechange=function(){
		if (req.readyState==4 && req.status==200){			
			return eval("("+req.responseText+")");
		}
	};
	
}	

Ws.prototype.ajaxform =  function (target){
	
	target.ajaxSubmit({
        //定义返回JSON数据，还包括xml和script格式
        dataType:'json',
		type:'POST',
        beforeSend: function() {
            //表单提交前做表单验证
        },
        success: function(data) {
        	var view = "";
        	if(data.rcode = "1"){
        		
            	view += "<div class='alert alert-success fade in'>";
            	view += "<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
            	view += "<strong>"+data.message+"</strong></div>";	
        	}else{
        		view += "<div class='alert alert-danger fade in'>";
            	view += "<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
            	view += "<strong>"+data.message+"</strong></div>";	
        	}
        	       	
			$('#update_alert_view').html(view);
			
			setTimeout(function(){
				$('#update_alert_view').html("");
			}, 3000)
			
        }
    	});
				
		return false;
	}

Ws.prototype.ajaxrefcreate =  function (target,page_view){
	if(!arguments[1]) page_view = "#container_iframe";
	target.ajaxSubmit({
        //定义返回JSON数据，还包括xml和script格式
		type:'POST',
        beforeSend: function() {
            //表单提交前做表单验证
        },
        success: function(data) {
        //	$("#container_iframe").html(data);
        	$(page_view).html(data);
        }
    	});				
		return false;
	}

Ws.prototype.get = function(target,_blank,record){
	if(!arguments[2]) record = false;
	var $this = $(target),_clickTab = $this.attr('link'); 
	$.get(_clickTab,function (data ,status){
		if(_blank!=""){
			$(_blank).html(data); 
		}else{
			$("#container_iframe").html(data); 
		}
	});
}

Ws.prototype.post = function(target,_blank,record){
	if(!arguments[2]) record = false;
	//alert(record);
	var $this = $(target),_clickTab = $this.attr('link'); 
	/*
	$.post(_clickTab,function (data ,status){
		$("#container_iframe").html(data); 
	},"json");
	*/
	$.ajax({  
        url : _clickTab,  
        type : 'POST',  
        data : $.toJSON(new Array()),           
        contentType : 'application/json',  
        success : function(data, status, xhr) {  
        	if(_blank!=""){
        		$("#"+_blank).html(data); 
        	}else{
        		$("#container_iframe").html(data); 
        		
        		if(record){
            		ws.record.data = new Array();            		
//            			alert(_clickTab.replace("/home","/list"));
//            			var re = new RegExp("/home","gi");
//            			re.exec(_clickTab);
//            			alert("sss"+re.lastIndex);
            		ws.record.url = _clickTab.replace("/home","/list");
            		ws.record.container_iframe = data;
            	}
        	}
//        	if(record){
//        		ws.record.data = new Array();
//        		ws.record.url = _clickTab;
//        		
//        	}else{
//        	//	alert(ws.record);
//        	}
        }
	});
	
	
}

Ws.prototype.postForm =  function (target,_blank){
	
	var  form = $(target);
	
 	jQuery.ajax({
	    url: form.attr("action"),
	    data:form.serialize(),
	    type:"POST",  
	    success:function(data)
	    {	
	        $(_blank).html(data); 
	    }
	    });
	    return false;	
}

Ws.prototype.navpost = function(target,_blank){
	
	var $this = $(target),_clickTab = $this.attr('link'),sign=$this.attr('sign');
	var  term =  "[sign='"+sign+"']";
	var items =  $(term);

	for(var i=0;i<items.length;i++){
		this.getval(items[i]);
	}
	var values = m_data[sign];
	if(values==null){
		values= new Array();
	}
	$.ajax({  
        url : _clickTab,  
        type : 'POST',  
        data : $.toJSON(values),           
        contentType : 'application/json',  
        success : function(data, status, xhr) {  
        	if(_blank!=""){
        		//ws.record.container_iframe = $("#container_iframe").html;
        		$("#"+_blank).html(data); 
        		ws.record.data =  values;
        		ws.record.url =  _clickTab;
        		
        	}else{
        		$("#container_iframe").html(data); 
        	}
        }
	});
}

Ws.prototype.search =  function (target,sign,form){

	var $target =  $(target);
	
	var  term =  "[sign='"+sign+"']";
//	var items =  $(term);
	var  url =  $target.attr("url");
	var inputs = $(form).find("input");
	ws.record.view_data = new Array();
	for(var i=0;i<inputs.length;i++){
		this.getval(inputs[i]);
		//alert(this.getval(inputs[i]));
	}
//	for(var i=0;i<items.length;i++){
//		this.getval(items[i]);
//	}

	ws.record.data =  m_data[sign];
	alert("=========="+$.toJSON(m_data[sign]));
	ws.record.url = url.replace("/search","/list");	
//	ws.record.url =  url;	
//	console.log(m_data[sign])
	
	$.ajax({  
        url : url,  
        type : 'POST',  
        async: false,
        data : $.toJSON(m_data[sign]),          
        contentType : 'application/json',  
        success : function(data, status, xhr) {  
        	m_data[sign] = null;
        	$("#search_view").html(data);
        }
	});	
	ws.record.container_iframe = $("#container_iframe").html();
}

Ws.prototype.recover_view_data =  function (){
	
	var vdata = ws.record.view_data;
	var id;
	var value;
	var type ;
	if(vdata!=null && vdata.length>0){
		for(var i=0;i<vdata.length;i++){
			 id = vdata[i].id;
			 value = vdata[i].value;
			 type = vdata[i].type;
			 if(type=="select"){
				 $('#'+id).val(value);
				 
			 }else if(type=="text"){
				 
				 $('#'+id).val(value);
				 
			 }else if(type=="radio"){
				 
				 $('#'+id).attr("checked","checked");
				 
			 }else if(type=="checkbox"){
				 $('#'+id).attr("checked","checked");
			 }
		}
	}
	
	
}

Ws.prototype.check = function (target){
	
	
	var $target =  $(target);
	//ws.getval(target);
	//console.log($target);
	var sign =  $target.attr("sign");
	var  term =  "[sign='"+sign+"']";
	var items =  $(term);
	var  url =  $target.attr("url");
	ws.record.view_data = new Array();
	for(var i=0;i<items.length;i++){
		this.getval(items[i]);
	}

	ws.record.data =  m_data[sign];
	ws.record.url =  url;	
	$.ajax({  
        url : url,  
        type : 'POST',
        async: false,
        data : $.toJSON(m_data[sign]),          
        contentType : 'application/json',  
        success : function(data, status, xhr) {  
        	m_data[sign] = null;
        	$("#second_view").html(data);
        }
	});
	ws.record.container_iframe = $("#container_iframe").html();
	
}

Ws.prototype.select = function (target){
	
	var $target =  $(target);	
//	console.log($target[0].nodeName);
	var sign =  $target.attr("sign");	
	var  term =  "[sign='"+sign+"']";
	var items =  $(term);
	var  url =  $target.attr("url");
	ws.record.view_data = new Array();
	for(var i=0;i<items.length;i++){
		this.getval(items[i]);
	}

	ws.record.data =  m_data[sign];
	
	ws.record.url =  url;	
	$.ajax({  
        url : url,  
        type : 'POST',
        async: false,
        data : $.toJSON(m_data[sign]),          
        contentType : 'application/json',  
        success : function(data, status, xhr) {  
        	m_data[sign] = null;
        	$("#second_view").html(data);
        }
	});	
	ws.record.container_iframe = $("#container_iframe").html();
}



Ws.prototype.getval = function (target){
	
	var $target =  $(target);	
	var id = $target.attr("id");
	var value = $target.val()
	var view_op = {};
	if($target[0].nodeName=="SELECT"){
		view_op['id'] = id;
		view_op['value'] = value;
		view_op["type"] = "select";
		ws.record.view_data.push(view_op);
		
		this.selectval(target);
	}else if($target[0].nodeName=="INPUT"){
		
		if($target.attr("type")=="text"){
			if(value!=null && value!=""){
				view_op['id'] = id;
				view_op['value'] = value;
				view_op["type"] = "text";
				//alert(id);
				ws.record.view_data.push(view_op);
			}
			
			this.expressval(target);
						
		}else if($target.attr("type")=="radio"){
			
			if($target.is(':checked')){
				view_op['id'] = id;
				view_op['value'] = "checked";
				view_op["type"] = "radio";
				ws.record.view_data.push(view_op);
				this.expressval(target);
				
			}			
		}else if($target.attr("type")=="checkbox"){
			
			if($target.is(':checked')){
				view_op['id'] = id;
				view_op['value'] = "checked";
				view_op["type"] = "checkbox";
				ws.record.view_data.push(view_op);
				this.expressval(target);
			}
		}		
	}
	
	
	
	
	
} 


Ws.prototype.expressval = function (target){
	
	var $target =  $(target);	
	var name = $target.attr("name");
	var opgroup = $target.attr("opgroup");
	var group = $target.attr("group");
	var upalias = $target.attr("upalias");
	var alias = $target.attr("alias");	
	var sign =  $target.attr("sign");
	var value = $target.val();
	var operate = $target.attr("operate");
	
	//alert(operate+value);
	
	if(value!=null && value!=""){
			
		if(m_data[sign]!=null && m_data[sign].length>0){
			for(var i=0;i<m_data[sign].length;i++){
				var obj =  m_data[sign][i];
				if(alias == obj.alias && opgroup == obj.opgroup){						
					for(var j=0;j<obj.expressions.length;j++){
						var express = obj.expressions[j];
						if(name==express.name &&  operate==express.operate){
							express.value = value;
							console.log(m_data[sign]);
							return ;
						}
					}
					
					var nexpress = {};
					nexpress["name"]=name;
					nexpress["value"]=value;
					nexpress["operate"]=operate;
					obj.expressions.push(nexpress);
					console.log(m_data[sign]);
					return ;
					
				}
					
			}
			var op = {};
			op["opgroup"] = opgroup;
			op["group"] = group;
			op["opgroup"] = opgroup;
			op["alias"] = alias;
			op["upalias"] = upalias;
			op["expressions"]= new Array();
			var nexpress = {};
			nexpress["name"]=name;
			nexpress["value"]=value;
			nexpress["operate"]=operate;
			op["expressions"].push(nexpress);
			m_data[sign].push(op);
			return ;
			
		}else{
			
			m_data[sign] = new Array();
			var op = {};
			op["opgroup"] = opgroup;
			op["group"] = group;
			op["opgroup"] = opgroup;
			op["alias"] = alias;
			op["upalias"] = upalias;
			op["expressions"]= new Array();
			var nexpress = {};
			nexpress["name"]=name;
			nexpress["value"]=value;
			nexpress["operate"]=operate;
			op["expressions"].push(nexpress);
			m_data[sign].push(op);
			
		}
//		console.log(m_data[sign]);
	}
	
	
} 

Ws.prototype.selectval = function (target){
	
	var $target = $(target);
	var name = $target.attr("name");
	var opgroup = $target.attr("opgroup");
	var group = $target.attr("group");
	var upalias = $target.attr("upalias");
	var alias = $target.attr("alias");	
	var sign =  $target.attr("sign");
	var value = $target.val()!=null?$target.val():$target.attr("value");
	var  operate = $target.find("option:selected").attr("operate")

	
	if(value!=null && value!=""){
			
		if(m_data[sign]!=null && m_data[sign].length>0){
			for(var i=0;i<m_data[sign].length;i++){
				var obj =  m_data[sign][i];
				if(alias == obj.alias && opgroup == obj.opgroup){						
					for(var j=0;j<obj.expressions.length;j++){
						var express = obj.expressions[j];
						if(name==express.name &&  operate==express.operate){
							express.value = value;
							console.log(m_data[sign]);
							return ;
						}
					}
					
					var nexpress = {};
					nexpress["name"]=name;
					nexpress["value"]=value;
					nexpress["operate"]=operate;
					obj.expressions.push(nexpress);
					console.log(m_data[sign]);
					return ;
					
				}
					
			}
			var op = {};
			op["opgroup"] = opgroup;
			op["group"] = group;
			op["opgroup"] = opgroup;
			op["alias"] = alias;
			op["upalias"] = upalias;
			op["expressions"]= new Array();
			var nexpress = {};
			nexpress["name"]=name;
			nexpress["value"]=value;
			nexpress["operate"]=operate;
			op["expressions"].push(nexpress);
			m_data[sign].push(op);
			return ;
			
		}else{
			
			m_data[sign] = new Array();
			var op = {};
			op["opgroup"] = opgroup;
			op["group"] = group;
			op["opgroup"] = opgroup;
			op["alias"] = alias;
			op["upalias"] = upalias;
			op["expressions"]= new Array();
			var nexpress = {};
			nexpress["name"]=name;
			nexpress["value"]=value;
			nexpress["operate"]=operate;
			op["expressions"].push(nexpress);
			m_data[sign].push(op);
			
		}
		console.log(m_data[sign]);
	}
} 


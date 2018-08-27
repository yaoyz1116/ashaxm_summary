var part_default = [];
var part_ruku = [];
var part_baofei = [];
var depart_all = [];
var dealParts = [];
var kuguaners = [];
var backParts = [];
var userId = null;
var user = null
var optType = "";  //工装入库还是报废  0 入库  1报废
var service="service/";
if(location.href.indexOf("localhost")>=0){
	service = "";
}

$(function() {
    var startDate = new Date(2018, 01, 01);
    var endDate = new Date(2018, 02, 01);
    var $alert = $('#my-alert-date');
    $('#my-start').datepicker().
      on('changeDate.datepicker.amui', function(event) {
        if (event.date.valueOf() > endDate.valueOf()) {
          $alert.find('p').text('开始日期应小于结束日期！').end().show();
        } else {
          $alert.hide();
          startDate = new Date(event.date);
          $('#my-startDate').text($('#my-start').data('date'));
        }
        $(this).datepicker('close');
      });

    $('#my-end').datepicker().
      on('changeDate.datepicker.amui', function(event) {
        if (event.date.valueOf() < startDate.valueOf()) {
          $alert.find('p').text('结束日期应大于开始日期！').end().show();
        } else {
          $alert.hide();
          endDate = new Date(event.date);
          $('#my-endDate').text($('#my-end').data('date'));
        }
        $(this).datepicker('close');
      });
});

initIndexPage();

//初始化页面
function initIndexPage(){
	initUser();
}

function initUser(){
	userId = localStorage.hx_userId;
	if(userId){
		//用户已经登录成功
		loadUserById();
	}else{
		//没有登录记录，需要登录
		$("#loginPage").show();
	}
}

function loadUserById(){
	$.ajax({
		type:'post',
		url:service+'user/loadUserById',
		data:{
			'userId' : userId
		}
	}).done(function(result){
		if(result.status==1){
			user = result.user;
			if(user.type==0){
				//车间人员
				chenjianLogin();
			}else if(user.type==1){
				//库管
				$("#shenqing").text("库存明细");
				$("#cebianDiv").show();
				showManagePart();
			}
		}
	});
}

function userLogin(){
	var usercode = $("#usercode").val();
	var password = $("#password").val();
	$.ajax({
		type:'post',
		url:service+'user/login',
		data:{
			'usercode' : usercode,
			'password' : password
		}
	}).done(function(result){
		if(result.status==1){
			console.log(result);
			user = result.user;
			if(user){
				localStorage.hx_userId = user.id;
				$("#loginPage").hide();
				if(user.type==0){
					//车间人员
					chenjianLogin();
				}else if(user.type==1){
					//库管
					$("#shenqing").text("库存明细");
					$("#cebianDiv").show();
					showManagePart();
				}
				
				
			}else{
				alert("工号或密码不正确");
			}
		}
	});
}

//车间人员 借用工装
function chenjianLogin(){
	if(user.type==0){		
		$("#stackDiv").show();
		$("#myinfoDiv").hide();
		$("#cebianDiv").show();
		$(".kuguancls").hide();
		$(".yuangongcls").show();
		$('#xinxi').css("color", "#999");
		$('#shenqing').css("color","orange");
	}else if(user.type==1){
		$("#stackDiv").show();
		$("#manageDiv").hide();
		$("#baofeiDiv").hide();	
		$("#liushuiDiv").hide();	
		$("#insertStackDiv").hide();
		$("#myinfoDiv").hide();
		$("#rukuDiv").hide();
		$('#xinxi').css("color", "#999");
		$('#liushui').css("color", "#999");
		$('#jieyongdiv').css("color", "#999");
		$('#tuihuandiv').css("color", "#999");
		$('#ruku').css("color", "#999");
		$('#baofei').css("color", "#999");
		$('#shenqing').css("color","orange");
	}
	searchPart();
}

//动态生成工装列表信息
function dynamicCreatePart(flag){
	if(part_default.length>0){
		var temp="";
		for(var i=0; i<part_default.length; i++){
			temp += '<li onclick="showDetailPart('+part_default[i].id+')">';
			temp += part_default[i].name+'('+part_default[i].code+')&nbsp;&nbsp;<span style="color:orange;font-size:16px;font-weight:600">库存：'+part_default[i].num+'</span>';
			temp += '</li>';
		}
		$("#stackPartsUL").empty();
		$("#stackPartsUL").append(temp);
	}
}

//点击 “搜索”按钮，查询指定的工装
function searchPart(){
	var aimSearch = $("#aimSearchPart").val();
	realLoadPart(aimSearch,99,1);
}

//向后台查询工单信息
function realLoadPart(aimSearch,length,flag){
	$.ajax({
		type:'post',
		url:service+'part/loadParts',
		data:{
			aimSearch : aimSearch,
			length : length
		}
	}).done(function(result){
		if(result.status==1){
			part_default = result.parts;
			dynamicCreatePart(flag);
		}
	});
}

//查询工装的详细信息
function showDetailPart(partId){
	var part_detail={};
	for(var i=0; i<part_default.length; i++){
		if(part_default[i].id==partId){
			part_detail = part_default[i];
			break;
		}
	}
	console.log(part_detail);
	$("#part_detail_name").html(part_detail.name);
	$("#part_detail_code").html(part_detail.code);
	$("#part_detail_val1").html(part_detail.val1);
	$("#part_detail_val2").html(part_detail.val2);
	$("#part_detail_num").html(part_detail.num);
	$("#part_detail_process").html(part_detail.processName);
	$("#part_detail_storage").html(part_detail.storageName);
	$("#part_detail_id").val(part_detail.id);
	$("#part_detail_user").text(user.code);
	if(user.type==1){
		$("#part_detail_kuguanTitle").text("工单详细信息");
		$(".kuguanNoSee").hide();
		$(".kuguanSee").show();
	}else{
		$("#part_detail_kuguanTitle").text("借用详情");
		$(".kuguanNoSee").show();
		$(".kuguanSee").hide();
	}
	$("#my-popup-borrow").modal();
}

function closeBorrow(){
	$("#my-popup-borrow").modal("close");
}


//加载所有的科室信息
function loadAllDepart(){
	$.ajax({
		type:'post',
		url:service+'depart/loadDeparts',
	}).done(function(result){
		if(result.status==1){
			depart_all = result.departs;
			console.log(depart_all);
			dynamicDeparts();
		}
	});
}

//动态生成科室下拉框
function dynamicDeparts(){
	var temp = '';
	temp += '<option value="0">请选择科室</option>';
	if(depart_all.length>0){
		for(var i=0; i<depart_all.length; i++){
			temp += '<option value="'+depart_all[i].id+'">'+depart_all[i].name+'('+depart_all[i].code+')</option>';
		}
	}
	$("#departSelect").empty();
	$("#departSelect").append(temp);
}

//动态生成科室中的人员信息
function dynamicUser(){
	var departId = $("#departSelect").val();
	if(departId==0){
		return;
	}
	$.ajax({
		type:'post',
		url:service+'user/loadUsers',
		data : {
			'departId' : departId
		}
	}).done(function(result){
		if(result.status==1){
			var users = result.users;
			console.log(users);
			var temp = '';
			temp += '<option value="0">请选择人员</option>';
			for(var i=0; i<users.length; i++){
				temp += '<option value="'+users[i].id+'">'+users[i].name+'</option>';
			}
			$("#userSelect").empty();
			$("#userSelect").append(temp);
		}
	});
}

//确认借用 按钮
function confirmBorrow(){
	var borrowNum = $("#part_detail_aimNum").val();
	if(borrowNum=='' || borrowNum<= 0){
		alert("请填写借用数量!");
		return;
	}
	if(parseInt(borrowNum) > parseInt($("#part_detail_num").text())){
		alert("借用数量超出库存，请重新填写借用数量！");
		return;
	}
	$.ajax({
		type:'post',
		url:service+'part/borrowParts',
		data : {
			'userId' : user.id,
			'partId' : $("#part_detail_id").val(),
			'departId' : user.departid,
			'num' : borrowNum
		}
	}).done(function(result){
		if(result.status==1){
			$("#part_detail_aimNum").val("");
			alert("借用成功，请联系库管确认！");
			$("#my-popup-borrow").modal("close");
		}else{
			alert("#借用失败，请刷新页面重试");
		}
	});
}




//点击“库管按钮”
function showManagePart(){
	
	$("#manageDiv").show();
	$("#insertStackDiv").hide();
	$("#stackDiv").hide();
	$("#myinfoDiv").hide();
	$("#rukuDiv").hide();
	$("#baofeiDiv").hide();	
	$("#liushuiDiv").hide();	
	$('#liushui').css("color", "#999");
	$('#xinxi').css("color", "#999");
	$('#shenqing').css("color", "#999");
	$('#tuihuandiv').css("color", "#999");
	$('#ruku').css("color", "#999");
	$('#baofei').css("color", "#999");
	$('#jieyongdiv').css("color","orange");
	
    $.ajax({
		type:'post',
		url:service+'part/dealParts',
	}).done(function(result){
		if(result.status==1){
			dealParts = result.dealParts;
			var temp = "";
			for(var i=0; i<dealParts.length; i++){
				temp += '<li onclick="showConfirmDetail('+dealParts[i].id+')">';
				temp += '部门：'+dealParts[i].dname+'（'+dealParts[i].dcode+'）&nbsp;&nbsp;&nbsp;&nbsp;姓名：'+dealParts[i].username+'（'+dealParts[i].usercode+'）<br/>';
				temp += '工装名称：'+dealParts[i].partname+'('+dealParts[i].partcode+')<br/>';
				temp += '借用数量：'+dealParts[i].num+'<br/>';
				temp += '借用时间：'+formatTime(dealParts[i].addtime)+'<br/>';
				temp += '</li>';
			}
			$("#dealPartUI").empty();
			$("#dealPartUI").append(temp);
		}
	});
}

//格式化时间
function formatTime(data){
	var time = new Date(data);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}

//格式化时间
function add0(m){
	return m<10?'0'+m:m;
}

//借用信息明细
function showConfirmDetail(aimId){
	var aimPart={};
	for(var i=0; i<dealParts.length; i++){
		if(dealParts[i].id == aimId){
			aimPart = dealParts[i];
		}
	}
	$("#popup_partname").text(aimPart.partname);
	$("#popup_prenum").text(aimPart.prenum);
	$("#popup_borrowuser").text(aimPart.username+"（"+aimPart.usercode+"）");
	$("#popup_borrowuserDepart").text(aimPart.dname+"（"+aimPart.dcode+"）");
	$("#popup_num").text(aimPart.num);
	$("#popup_id").val(aimPart.id);
	$("#popup_partId").val(aimPart.partid);
	$("#popup_kuguan").text(user.code);
	$("#my-popup").modal();
}

//库管确认借用
function kuguanConfirm(){
	$.ajax({
		type:'post',
		url:service+'part/confirmBorrowParts',
		data : {
			'hisId' : $("#popup_id").val(),
			'kuguanId' : user.id,
			'borrowNum' : $("#popup_num").text(),
			'partId' : $("#popup_partId").val()
		}
	}).done(function(result){
		if(result.status==1){
			$("#my-popup").modal("close");
			showManagePart();
		}
	});
}



//点击入库按钮
function showInsertStockPart(){
	
	$("#manageDiv").hide();
	$("#insertStackDiv").show();
	$("#stackDiv").hide();
	$("#myinfoDiv").hide();
	$("#rukuDiv").hide();
	$("#baofeiDiv").hide();	
	$("#liushuiDiv").hide();	
	$('#liushui').css("color", "#999");
	$('#xinxi').css("color", "#999");
	$('#shenqing').css("color", "#999");
	$('#tuihuandiv').css("color", "orange");
	$('#ruku').css("color", "#999");
	$('#baofei').css("color", "#999");
	$('#jieyongdiv').css("color","#999");
    $.ajax({
		type:'post',
		url:service+'part/backParts',
	}).done(function(result){
		if(result.status==1){
			backParts = result.backParts;
			kuguaners = result.kuguan;
			var temp = "";
			for(var i=0; i<backParts.length; i++){
				temp += '<li onclick="showConfirmBack('+backParts[i].id+')">';
				temp += '姓名：'+backParts[i].username+'<br/>';
				temp += '工装名称：'+backParts[i].partname+'('+backParts[i].partcode+')<br/>';
				temp += '借用数量：'+backParts[i].num+'&nbsp;&nbsp;&nbsp;&nbsp;已还数量：'+backParts[i].backnum+'<br/>';
				temp += '借用时间：'+formatTime(backParts[i].addtime)+'<br/>';
				temp += '</li>';
			}
			$("#backPartUI").empty();
			$("#backPartUI").append(temp);
		}
	});
}

//工单的退还详情
function showConfirmBack(aimId){
	var aimPart={};
	for(var i=0; i<backParts.length; i++){
		if(backParts[i].id == aimId){
			aimPart = backParts[i];
		}
	}
	console.log(aimPart);
	$("#popup_partname_back").text(aimPart.partname);
	$("#popup_prenum_back").text(aimPart.prenum);
	$("#popup_borrowuser_back").text(aimPart.username);
	$("#popup_num_back").text(aimPart.num);
	$("#popup_num_backed").text(aimPart.backnum);
	$("#popup_id_back").val(aimPart.id);
	$("#popup_partId_back").val(aimPart.partid);
	$("#popup_kuguan_back").text(user.code);
	$("#my-popup-back").modal();
	
}

//库管确认退还
function kuguanBack(){
	var nowBack = parseInt($("#popup_realnum_back").val());
	if(nowBack=="" || nowBack <0){
		alert("请填写退还数量");
		return;
	}
	if(nowBack + parseInt($("#popup_num_backed").text()) > parseInt($("#popup_num_back").text())){
		alert("退还数量大于已阶数量");
		return;
	}
	var backAll = false;
	if(nowBack + parseInt($("#popup_num_backed").text()) == parseInt($("#popup_num_back").text())){
		backAll = true;
	}
	$.ajax({
		type:'post',
		url:service+'part/backBorrowParts',
		data : {
			'hisId' : $("#popup_id_back").val(),
			'kuguanId' : user.id,
			'backNum' : nowBack,
			'partId' : $("#popup_partId_back").val(),
			'backAll' : backAll
		}
	}).done(function(result){
		if(result.status==1){
			$("#my-popup-back").modal("close");
			showInsertStockPart();
		}
	});
}

//我的信息
function showMyInfo(){
	if(user.type==0){
		$("#stackDiv").hide();
		$("#myinfoDiv").show();
		$("#cebianDiv").show();
		$(".kuguancls").hide();
		$(".yuangongcls").show();
		$('#xinxi').css("color", "orange");
		$('#shenqing').css("color","#999");
	}else if(user.type==1){
		$("#manageDiv").hide();
		$("#insertStackDiv").hide();
		$("#stackDiv").hide();
		$("#rukuDiv").hide();
		$("#baofeiDiv").hide();	
		$("#liushuiDiv").hide();	
		$("#myinfoDiv").show();
		$('#liushui').css("color", "#999");
		$('#xinxi').css("color", "orange");
		$('#shenqing').css("color", "#999");
		$('#tuihuandiv').css("color", "#999");
		$('#ruku').css("color", "#999");
		$('#baofei').css("color", "#999");
		$('#jieyongdiv').css("color","#999");
	}
    $("#myinfo_name").text(user.name);
    $("#myinfo_code").text(user.code);
    var tempType = "车间人员";
    if(user.type==1)
    	tempType = "库管";
    $("#myinfo_type").text(tempType);  
}

//退出登录
function userLogout(){
	localStorage.clear();
	initIndexPage();
}

//修改库存
function cgpwd(){
	$("#cgpwd_pre").val("");
	$("#cgpwd_now").val("");
	$("#cgpwd_preconfrim").val("");
	$("#my-popup-cgpwd").modal();
}

function confirmCgpwd(){
	var pre = $("#cgpwd_pre").val();
	var now = $("#cgpwd_now").val();
	var cfm = $("#cgpwd_preconfrim").val();
	if(pre=="" || pre.length==0){
		alert("请输入原密码");
		return;
	}
	if(now=="" || now.length==0){
		alert("请输入新密码");
		return;
	}
	if(cfm=="" || cfm.length==0 || cfm!=now){
		alert("两次输入的密码不一致");
		return;
	}
	$.ajax({
		type:'post',
		url:service+'user/cgpwd',
		data : {
			'userId' : user.id,
			'prePwd' : pre,
			'nowPwd' : cfm
		}
	}).done(function(result){
		if(result.status==1){
			alert("密码修改成功，请重新登录！");
			$("#my-popup-cgpwd").modal("close");			
			userLogout();
		}else if(result.status==2){
			alert("原密码不正确");
		}
	});
}

function showRukuDiv(){
	$("#manageDiv").hide();
	$("#insertStackDiv").hide();
	$("#stackDiv").hide();
	$("#rukuDiv").show();
	$("#myinfoDiv").hide();
	$("#baofeiDiv").hide();	
	$("#liushuiDiv").hide();	
	$('#liushui').css("color", "#999");
	$('#xinxi').css("color", "#999");
	$('#shenqing').css("color", "#999");
	$('#tuihuandiv').css("color", "#999");
	$('#ruku').css("color", "orange");
	$('#baofei').css("color", "#999");
	$('#jieyongdiv').css("color","#999");
}

function searchRukuPart(){
	var aimCode = $("#ruku_searchCode").val();
	$.ajax({
		type:'post',
		url:service+'part/loadParts',
		data:{
			aimSearch : aimCode,
			length : 99
		}
	}).done(function(result){
		if(result.status==1){
			part_ruku = result.parts;
			if(part_ruku.length>0){
				var temp="";
				for(var i=0; i<part_ruku.length; i++){
					temp += '<li onclick="showDetailPartRukuOrBaofei('+part_ruku[i].id+',0)">';
					temp += part_ruku[i].name+'('+part_ruku[i].code+')&nbsp;&nbsp;<span style="color:orange;font-size:16px;font-weight:600">库存：'+part_ruku[i].num+'</span>';
					temp += '</li>';
				}
				$("#rukuUI").empty();
				$("#rukuUI").append(temp);
			}else{
				var temp = "<li style='color:red;font-size:20px;font-weight:600;'>现有库存中没有找到此工装，请联系系统管理员添加</li>";
				$("#rukuUI").empty();
				$("#rukuUI").append(temp);
			}
		}
	});
}



function showBaofeiDiv(){
	$("#manageDiv").hide();
	$("#insertStackDiv").hide();
	$("#stackDiv").hide();
	$("#rukuDiv").hide();
	$("#myinfoDiv").hide();
	$("#baofeiDiv").show();	
	$("#liushuiDiv").hide();
	$('#liushui').css("color", "#999");
	$('#xinxi').css("color", "#999");
	$('#shenqing').css("color", "#999");
	$('#tuihuandiv').css("color", "#999");
	$('#ruku').css("color", "#999");
	$('#baofei').css("color", "orange");
	$('#jieyongdiv').css("color","#999");
}

function searchBaofeiPart(){
	var aimCode = $("#baofei_searchCode").val();
	$.ajax({
		type:'post',
		url:service+'part/loadParts',
		data:{
			aimSearch : aimCode,
			length : 99
		}
	}).done(function(result){
		if(result.status==1){
			part_baofei = result.parts;
			if(part_baofei.length>0){
				var temp="";
				for(var i=0; i<part_baofei.length; i++){
					temp += '<li onclick="showDetailPartRukuOrBaofei('+part_baofei[i].id+',1)">';
					temp += part_baofei[i].name+'('+part_baofei[i].code+')&nbsp;&nbsp;<span style="color:orange;font-size:16px;font-weight:600">库存：'+part_baofei[i].num+'</span>';
					temp += '</li>';
				}
				$("#baofeiUI").empty();
				$("#baofeiUI").append(temp);
			}else{
				var temp = "<li style='color:red;font-size:20px;font-weight:600;'>现有库存中没有找到此工装，请联系系统管理员添加</li>";
				$("#baofeiUI").empty();
				$("#baofeiUI").append(temp);
			}
		}
	});
}

function showDetailPartRukuOrBaofei(partId,type){
	optType = type;
	var part_detail={};
	for(var i=0; i<part_ruku.length; i++){
		if(part_ruku[i].id==partId){
			part_detail = part_ruku[i];
			break;
		}
	}
	if(type==0){
		$("#rukuOrbaofei").text("工装入库");
	}else if(type==1){
		$("#rukuOrbaofei").text("工装报废");
	}
	$("#part_ruku_name").html(part_detail.name);
	$("#part_ruku_code").html(part_detail.code);
	$("#part_ruku_val1").html(part_detail.val1);
	$("#part_ruku_val2").html(part_detail.val2);
	$("#part_ruku_num").html(part_detail.num);
	$("#part_ruku_process").html(part_detail.processName);
	$("#part_ruku_storage").html(part_detail.storageName);
	$("#part_ruku_id").val(part_detail.id);
	$("#part_ruku_user").text(user.code);
	$("#my-popup-ruku").modal();
}


function confirmRuku(){
	var aimNum = $("#part_ruku_aimNum").val();
	if(aimNum=="" || aimNum<0){
		alert("请输入本次新入库的工装数量");
		return;
	}
	$.ajax({
		type:'post',
		url:service+'part/ruku',
		data : {
			'manageid' : user.id,
			'partId' : $("#part_ruku_id").val(),
			'aimNum' : aimNum,
			'type' : optType
		}
	}).done(function(result){
		if(result.status==1){
			$("#my-popup-ruku").modal("close");
			alert("操作成功");
			if(optType==0){			
				showRukuDiv();
			}else if(optType==1){
				showBaofeiDiv();
			}
		}
	});
}


function showLiushui(){
	$("#manageDiv").hide();
	$("#insertStackDiv").hide();
	$("#stackDiv").hide();
	$("#rukuDiv").hide();
	$("#myinfoDiv").hide();
	$("#baofeiDiv").hide();	
	$("#liushuiDiv").show();
	$('#liushui').css("color", "orange");
	$('#xinxi').css("color", "#999");
	$('#shenqing').css("color", "#999");
	$('#tuihuandiv').css("color", "#999");
	$('#ruku').css("color", "#999");
	$('#baofei').css("color", "#999");
	$('#jieyongdiv').css("color","#999");
}


function insertNewPart(){
	$("#part_new_name").val("");
	$("#part_new_code").val("");
	$("#part_new_val1").val("");
	$("#part_new_val2").val("");
	$("#part_new_processcode").val("");
	$("#part_new_storagecode").val("");
	$("#part_new_num").val("");
	$("#part_new_user").text(user.code);
	$("#my-popup-new").modal();
}


function confirmNewRuku(){
	var code= $("#part_new_code").val();
	var num = $("#part_new_num").val();
	if(code=="" || code.length==0){
		alert("请输入工装代码");
		return;
	}
	if(num=="" || num.length==0){
		alert("请输入工装数量");
		return;
	}
	$.ajax({
		type:'post',
		url:service+'part/newRuku',
		data : {
			'manageid' : user.id,
			'name' : $("#part_new_name").val().trim(),
			'code' : $("#part_new_code").val().trim(),
			'val1' : $("#part_new_val1").val().trim(),
			'val2' : $("#part_new_val2").val().trim(),
			'process' : $("#part_new_processcode").val().trim(),
			'storage' : $("#part_new_storagecode").val().trim(),
			'num' : $("#part_new_num").val().trim()		
		}
	}).done(function(result){
		if(result.status==1){
			$("#my-popup-new").modal("close");
			alert("入库成功");
			showRukuDiv();
		}else if(result.status==2){
			alert("输入的工序代码不存在！");
		}else if(result.status==3){
			alert("输入的库位代码不存在！")
		}else if(result.status==4){
			alert("工装代码已经存在，请先在输入框中搜索工装，再添加相应的数量！");
			$("#my-popup-new").modal("close");
			showRukuDiv();
		}
	});
	
}


function downLoadExcel(type){
	var start = "";
	var end = "";
	if(type==0){
		start = $("#my-startDate").text();
		end = $("#my-endDate").text();
	}
	$.ajax({
		type:'post',
		url:service+'part/downLoadExcel',
		data : {
			'type' : type,	
			'start' : start,	
			'end' : end
		}
	}).done(function(result){
		if(result.status==1){
			try{ 	
	            var elemIF = document.createElement("iframe");   
	            elemIF.src = "http://huanxin.ashaxm.top/content/huanxin/" + result.filename;   
	            elemIF.style.display = "none";   
	            document.body.appendChild(elemIF);   
	        }catch(e){
	        	$.smallBox({
					title : "信息",
					content : "<i class='fa fa-warning'></i> <i>加载失败！</i>",
					color : "#FF0033",
					iconSmall : "fa fa-thumbs-up bounce animated",
					timeout : 2000
				});
	        } 
		}
	});
	
	
	
}



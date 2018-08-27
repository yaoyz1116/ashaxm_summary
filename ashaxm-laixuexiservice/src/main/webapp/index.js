initWinnerTables();

function initWinnerTables(){
	var tody = new Date();
	var year = tody.getFullYear();
	var month = tody.getMonth() + 1;
	var day = tody.getDate();
	month = month < 10 ? "0" + month : month;
	day = day < 10 ? "0" + day : day;
	var end = year +'-' + month +'-'+day;
	var start = end.substring(0,end.length-2)+"01";
	$('#startdate').val(start);
	$('#enddate').val(end);
	$('#winnerList_grid').dataTable(
			{
				"processing" : true,
				"serverSide" : true,
				"deferRender" : true,
				"filter"	:	false,
				"displayStart" : nowPrizeWinnerNumCopy,
				"ajax" :'prizeInfo/getWinnerList', 
				"fnServerParams":function(aoData){
	     			aoData.secretBoxCode = secretBoxCode;
	     			aoData.startdate = prizeInfoStartTime;
	     			aoData.enddate = prizeInfoEndTime;
				},	
				"serverMethod" : "POST",
				"stateSave" : false,
				"sort" : false,
				"pagingType" : "full_numbers",
				"language" : {
					"url" : "assets/plugin/datatables/Chinese.json"
				},
				"dom" : "<'dt-toolbar'<'col-sm-9 col-xs-12'<'#customer-toolbar'>><'col-sm-3 col-xs-12 hidden-xs'l>>"
						+ "t"
						+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
			    "fnInfoCallback": function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
			    	nowPrizeWinnerNum=(iStart-1)*1;
			    	return "显示第 "+ iStart+" 至 "+iEnd+" 项结果，共 "+iTotal+" 项";
			    },
				"initComplete":function(){
					$('#customer-toolbar').addClass('dataTables_filter').html('<button id="excelBtn" onclick="downloadWinnerExcel(1)" class="btn btn-success">导出全部Excel</button>'
							+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id="excel2Btn" onclick="downloadWinnerExcel(2)" class="btn btn-success">导出该时间段Excel</button>'
							+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="batchManualSendRedPacket()" class="btn btn-warning">批量发送本页红包</button>');
							},
				"aoColumnDefs" : [
						{
							"data" 		: "userid",
							"targets"	: [ 0 ],
							"visible"	: true,
							"width" 	: "80px"
						},
						{
							"data"		: "name",
							"targets" 	: [ 1 ],
							"visible"	: true,
							"width" 	: "60px"
						},
						{
							"data" 		: "phone",
							"targets"	: [ 2 ],
							"visible"	: true,
							"width"		: "80px"
						},
						{
							"data" 		: "prizename",
							"targets"	: [ 3 ],
							"visible"	: true,
							"width"		: "80px"
						},
						{
							"data" 		: "prizecontent",
							"targets"	: [ 4 ],
							"visible"	: true
						},
						{
							"data" 		: "lotterytime",
							"targets"	: [ 5 ],
							"visible"	: true,
							"width"		: "135px"
						},
						{
							"data" 		: "isdraw",
							"targets"	: [ 6 ],
							"visible"	: true,
							"width"		: "80px",
							"render" : function(data, type, full) {
								var isdraw="";
								if(data==-1){
									isdraw =  "<span class='label label-default'>发送失败</span>";
								}else if(data==-2){
									isdraw =  "<span class='label label-info'>禁止发送</span>";
								}else if(data==1){
									isdraw =  "<span class='label label-success'>手机兑奖</span>";
								}else if(data==2){
									isdraw =  "<span class='label label-success'>平台兑奖</span>";
								}else if(data==3){
									isdraw =  "<span class='label label-success'>自动兑奖</span>";
								}else if(data==4){
									isdraw =  "<span class='label label-success'>发送中</span>";
								}else if(data==5){
									isdraw =  "<span class='label label-success'>短信验证完毕</span>";
								}else if(data==0){
									if(full.prizetype!=0){
										if(full.sendtype==2){
											isdraw = "<span class='label label-danger'>未兑奖,等待短信验证</span>";
										}else if(full.sendtype==0){
											isdraw = "<span class='label label-danger'>等待发送</span>";
										}else if(full.sendtype==1){
											isdraw = "<span class='label label-danger'>等待人工确认</span>";
										}
									}else{
										isdraw = "<span class='label label-danger'>未兑奖</span>";
									}
								}
								return isdraw;
								
							}
						},
						{
							"data" 		: "drawtime",
							"targets"	: [ 7 ],
							"visible"	: true,
							"width"		: "135px"
						},
						{
							"data" 		: "description",
							"targets"	: [ 8 ],
							"visible"	: true,
							"render" : function(data, type, full) {
								var desc='';
								if(full.description){
									desc += full.description +',';
								}
								if(full.value1){
									desc +=full.value1 + ",";
								}
								if(full.value2){
									desc +=full.value2 + ",";
								}
								if(full.value3){
									desc +=full.value3 + ",";
								}
								if(full.value4){
									desc +=full.value4 + ",";
								}
								
								if(desc.length>0){
									desc = desc.substr(0,desc.length-1);
								}
								return desc;
							}
						},
						{
							"data" 		: "id",
							"targets"	: [ 9 ],
							"visible"	: true,
							"width"		: "160px",
							"render"  :  function(data, type, full){
									if(full.isdraw>0){
										return "";
									}else if((full.isdraw==0 &&(full.prizetype==1||full.prizetype==2) && (full.sendtype==1||full.sendtype==2)) 
											|| (full.isdraw==-1 &&(full.prizetype==1||full.prizetype==2))){
										return "<button class='btn btn-success btn-xs' onclick=\"manualSendRedPacket("+data+",'"+full.prizename+','+ full.prizecontent +"')\">手动发送</button>"
											+"&nbsp;&nbsp;<button class='btn btn-danger btn-xs' onclick=\"updateDraw("+data+",-2,'"+full.name+"')\">禁止发此</button>";
									}else if(full.prizetype==0){
										return "<button class='btn btn-success btn-xs' onclick=\"ConfirmLotteryWinner("+data+")\">兑奖</button>";
									}else{
										return "";
									}
							}
						}						
						]
			});
}
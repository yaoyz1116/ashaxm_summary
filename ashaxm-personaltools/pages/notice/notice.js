const app = getApp()

Page({

  data:{
    delBtnWidth:180,//删除按钮宽度单位（rpx）
    list : []
  },
  onLoad:function(options){
    this.initEleWidth();
  },
  onShow : function(){
    this.loadNotices()
  },
  loadNotices : function(){
    var _this=this
    wx.request({
      url: app.globalData.SERVICE_URL+"/notice/loadNotices", 
      method : "POST",
      data: {
         "userId":  app.globalData.userId
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        if(res.data.status==1){
          _this.setData({
            list : res.data.notices
          })
        }
      }
    })
  },
  createNewNotice : function(){
    wx.navigateTo({
      url: '../newNotice/newNotice'
    })
  },
  touchS:function(e){
    if(e.touches.length==1){
      this.setData({
        // 设置触摸起始点水平方向位置
        startX:e.touches[0].clientX
      });
    }
  },
  touchM:function(e){
    if(e.touches.length==1){
      //手指移动时水平方向位置
      var moveX = e.touches[0].clientX;
      //手指起始点位置与移动期间的差值
      var disX = this.data.startX - moveX;
      var delBtnWidth = this.data.delBtnWidth;
      var txtStyle = "";
      if(disX == 0 || disX < 0){//如果移动距离小于等于0，文本层位置不变
        txtStyle = "left:0px";
      }else if(disX > 0 ){//移动距离大于0，文本层left值等于手指移动距离
        txtStyle = "left:-"+disX+"px";
        if(disX>=delBtnWidth){
          //控制手指移动距离最大值为删除按钮的宽度
          txtStyle = "left:-"+delBtnWidth+"px";
        }
      }
      //获取手指触摸的是哪一项
      var index = e.target.dataset.index;
      var list = this.data.list;
      list[index].txtStyle = txtStyle;
      //更新列表的状态
      this.setData({
        list:list
      });
    }
  },
  touchE:function(e){
    if(e.changedTouches.length==1){
      //手指移动结束后水平位置
      var endX = e.changedTouches[0].clientX;
      //触摸开始与结束，手指移动的距离
      var disX = this.data.startX - endX;
      var delBtnWidth = this.data.delBtnWidth;
      //如果距离小于删除按钮的1/2，不显示删除按钮
      var txtStyle = disX > delBtnWidth/2 ? "left:-"+delBtnWidth+"px":"left:0px";
      //获取手指触摸的是哪一项
      var index = e.target.dataset.index;
      var list = this.data.list;
      list[index].txtStyle = txtStyle;
      //更新列表的状态
      this.setData({
        list:list
      });
    }
  },
  // 获取元素自适应后的实际宽度
  getEleWidth:function(w){
    var real = 0;
    try{
      var res = wx.getSystemInfoSync().windowWidth;
      var scale = (750/2)/(w/2);//以宽度750px设计稿做宽度的自适应
      // console.log(scale);
      real = Math.floor(res/scale);
      return real;
    } catch(e) {
      return false;
     // Do something when catch error
    }
  },
  initEleWidth:function(){
    var delBtnWidth = this.getEleWidth(this.data.delBtnWidth);
    this.setData({
      delBtnWidth:delBtnWidth
    });
  },
  //点击删除按钮事件
  delItem:function(e){
    var index = e.target.dataset.index;
    var list = this.data.list;
    var detId = this.data.list[index].id
    var _this=this
    wx.request({
      url: app.globalData.SERVICE_URL+"/notice/deleteNotice", 
      method : "POST",
      data: {
         "noticeId":  detId
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        if(res.data.status==1){
          wx.showToast({
            title: '删除成功',
            icon: 'success',
            duration: 2000
          })
          list.splice(index,1);
          _this.setData({
            list:list
          });
        }
      }
    })
  },
  seeDetailItem : function(e){
    var index = e.target.dataset.index
    var title = this.data.list[index].title
    var des = this.data.list[index].des
    wx.showModal({
        title: title,
        content: des,
        showCancel : false,
        confirmText : '我知道了'
      })
    },
    onShareAppMessage: function (res) {
      return {
        title: app.globalData.SHARE_TITLE,
        path: app.globalData.SHARE_PAGE,
        imageUrl : app.globalData.SHARE_IMG
      }
    }
})
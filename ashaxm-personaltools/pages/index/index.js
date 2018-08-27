const app = getApp()

Page({
  data: {
    userInfo : {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onReady: function () {
    this.loadUser()
  },
  loadUser : function(){
    var _this = this
    if(app.globalData.userInfo==null){
      setTimeout(function(){
        _this.loadUser()
      },200);
      return
    }
    if (app.globalData.userInfo.nickName) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    }
  },
  getUserInfo: function(e) {
    app.globalData.userInfo.nickName = e.detail.userInfo.nickName
    app.globalData.userInfo.image = e.detail.userInfo.avatarUrl
    this.setData({
      userInfo: app.globalData.userInfo,
      hasUserInfo: true
    })
    // 将头像和昵称插入数据库
    let _this=this
    wx.request({
      url:app.globalData.SERVICE_URL+'/user/updateUser', 
      method : "POST",
      data: {
         'userId' : app.globalData.userId,
         'name' : e.detail.userInfo.nickName,
         'headimg' : e.detail.userInfo.avatarUrl
      },
      header: {
          'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
      }
    })
  },
  seeDailyCost : function(){
    wx.navigateTo({
      url: '../cost/cost'
    })
  },
  seeJiuJie : function(){
    wx.navigateTo({
      url: '../jiujie/jiujie'
    })
  },
  seeNotice : function(){
    wx.navigateTo({
      url: '../notice/notice'
    })
  },
  seeMyInfo : function(){
    wx.showModal({
      title: '提示',
      showCancel : false,
      content: '此小程序由个人开发者开发，如果您有任何开发需求，欢迎微信联系：yaoyuzhao000'
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

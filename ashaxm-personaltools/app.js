//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    this.globalData.userId = wx.getStorageSync('wx_personaltools_userid') 
    if(this.globalData.userId){
      this.loadUserById()
    }else{
      this.loadUserByCode()
    }
  },
  loadUserById : function(){
    let _this=this
    wx.request({
      url: _this.globalData.SERVICE_URL+'/user/loadMyinfo', 
      method : "POST",
      data: {
         'userId':this.globalData.userId
      },
      header: {
          'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        if(res.data.status==1){
          console.log(res.data.user)
          _this.globalData.userInfo = res.data.user
        }
      }
    })
  },
  loadUserByCode : function(){
      // 没有保存userid，利用code去查询
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
          let _this=this
          wx.request({
            url: _this.globalData.SERVICE_URL+'/user/loadBaseInfo', 
            method : "POST",
            data: {
               'code' : res.code
            },
            header: {
                'content-type': 'application/x-www-form-urlencoded' 
            },
            success: function(res) {
              if(res.data.status==1){
                console.log(res.data.user)
                _this.globalData.userId = res.data.user.id
                _this.globalData.userInfo = res.data.user
                wx.setStorageSync('wx_personaltools_userid', res.data.user.id)
              }
            }
          })
        }
      })
  },
  globalData: {
    userId: 0,
    userInfo : null,
    SERVICE_URL : 'https://www.ashaxm.top/ptservice',
    SHARE_TITLE : '你的私人小秘书',
    SHARE_PAGE : '/pages/index/index',
    SHARE_IMG : 'http://content.fengchuanba.com/file/user/10418/material20180626180026.jpg'
  }
})
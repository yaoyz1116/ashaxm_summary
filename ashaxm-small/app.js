//app.js
App({
  onLaunch: function () {
    this.globalData.userInfo = wx.getStorageSync('userInfo')
    if(!this.globalData.userInfo){
        this.loaduserinfoInAPPOne()
    }
    
  },
  // 小程序启动时，首先执行onLaunch,然后执行onShow
  onShow : function(options){
    // console.log("app onShow")
    // console.log(options.path)
    // console.log(options.query)
    // console.log(options.scene)
    // console.log(options.shareTicket)
    // console.log(options.referrerInfo)
  },
  loaduserinfoInAPPOne : function(){
    //调用登录接口，获取 code  
    var _this=this
    wx.login({  
      success: function (res) {  
        wx.getSetting({  
          success(setRes) {  
            // 判断是否已授权  
            if (!setRes.authSetting['scope.userInfo']) {  
              // 授权访问  
              wx.authorize({  
                scope: 'scope.userInfo',  
                success() {  
                  //获取用户信息  
                  wx.getUserInfo({  
                    lang: "zh_CN",  
                    success: function (userRes) {  
                      //发起网络请求  
                      _this.loaduserinfoInAPPTwo(res.code,userRes.userInfo.avatarUrl,userRes.userInfo.nickName,userRes.encryptedData,userRes.iv)
                    }  
                  })  
                }  
              })  
            } else {  
              //获取用户信息  
              wx.getUserInfo({  
                lang: "zh_CN",  
                success: function (userRes) {
                   _this.loaduserinfoInAPPTwo(res.code,userRes.userInfo.avatarUrl,userRes.userInfo.nickName,userRes.encryptedData,userRes.iv)
                }  
              })  
            }  
          }  
        })  
      }  
    }) 
  },
  // app启动1的时候，加载登录人的信息
  loaduserinfoInAPPTwo : function(code,avatarUrl,nickName,encryptedData,iv){
      var _this=this
      //发起网络请求  
      wx.request({  
        url: this.globalData.Service+"/wx/loaduserinfo",  
        data: {  
          code: code,  
          avatarUrl : avatarUrl,
          nickName : nickName,
          encryptedData: encryptedData,  
          iv: iv  
        },  
        header: {  
          "Content-Type": "application/x-www-form-urlencoded"  
        },  
        method: 'POST',  
        //服务端的回掉  
        success: function (result) {  
          var data = result.data.userInfo;  
          wx.setStorageSync("userInfo", data)
          _this.globalData.userInfo = data
        }  
      }) 
  },
  globalData: {
    userInfo: null,
    Service : "https://www.ashaxm.top/service"
  }
})






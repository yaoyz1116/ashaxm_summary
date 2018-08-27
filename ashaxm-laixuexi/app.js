App({
  onLaunch: function () {
    // 展示本地存储能力
    this.globalData.adminId = wx.getStorageSync('wx_mzl_adminId') 
    this.loadAdmin()
    this.loadBaseClassAndDance()
  },
  loadAdmin : function(){   
    if(this.globalData.adminId){
      console.log("adminid-----"+this.globalData.adminId)
      // 已经保存了用户id,直接去admin表查询对应信息
      this.loadAdminById()
    }else{
      console.log("no adminid")
      this.loadAdminByCode()
    }
  },
  loadAdminById : function(){
    let _this=this
    wx.request({
      url: _this.globalData.SERVICE_URL+'/visitor/loadBaseInfoById', 
      method : "POST",
      data: {
         'adminId':this.globalData.adminId
      },
      header: {
          'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        if(res.data.status==1){
          _this.globalData.adminInfo = res.data.admin
        }
      }
    })
  },
  loadAdminByCode : function(){
      // 没有保存adminId，利用code去查询
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
          let _this=this
            wx.request({
              url: _this.globalData.SERVICE_URL+'/visitor/loadBaseInfoByCode', 
              method : "POST",
              data: {
                 'code' : res.code
              },
              header: {
                  'content-type': 'application/x-www-form-urlencoded' 
              },
              success: function(res) {
                if(res.data.status==1){
                  _this.globalData.adminId = res.data.admin.id
                  _this.globalData.adminInfo = res.data.admin
                  wx.setStorageSync('wx_mzl_adminId', res.data.admin.id)
                }
              }
            })
        }
      })
  },
  loadBaseClassAndDance : function(){
    let _this=this
    wx.request({
      url: _this.globalData.SERVICE_URL+'/visitor/loadBaseClassAndDance', 
      method : "POST",
      header: {
          'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        if(res.data.status==1){
          _this.globalData.danceArr = res.data.dances
          _this.globalData.classArr = res.data.classes
        }
      }
    })
  },
  globalData: {
    adminId: 0,
    adminInfo : null,
    SERVICE_URL : 'https://www.ashaxm.top/laixuexiService',
    SHARE_TITLE : '麦哲伦舞蹈学习中心',
    SHARE_PAGE : '/pages/students/students',
    SHARE_IMG : '../../imgs/logo.jpg',
    danceArr : [],
    classArr : []
  }
})
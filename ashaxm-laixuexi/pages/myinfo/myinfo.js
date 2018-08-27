const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    adminInfo : {},
    hasAdminInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onLoad: function () {
    if (app.globalData.adminInfo.name) {
      this.setData({
        adminInfo: app.globalData.adminInfo,
        hasAdminInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        console.log("我也不知道这个是什么")
        console.log(res.userInfo)
        this.setData({
          adminInfo: res.userInfo,
          hasAdminInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.adminInfo = res.userInfo
          this.setData({
            adminInfo: res.userInfo,
            hasAdminInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    app.globalData.adminInfo.name = e.detail.userInfo.nickName
    app.globalData.adminInfo.headImg = e.detail.userInfo.avatarUrl
    this.setData({
      adminInfo: app.globalData.adminInfo,
      hasAdminInfo: true
    })
    // 将头像和昵称插入数据库
    let _this=this
    wx.request({
      url:app.globalData.SERVICE_URL+'/visitor/updateAdmin', 
      method : "POST",
      data: {
         'adminId' : app.globalData.adminId,
         'name' : e.detail.userInfo.nickName,
         'headimg' : e.detail.userInfo.avatarUrl
      },
      header: {
          'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
      },
      success: function(res) {
        console.log("信息修改成功")
      }
    })
  },
  addNewStudent : function(){
    wx.navigateTo({
      url: '../newStudent/newStudent'
    })
  },
  editDance : function(){
    wx.navigateTo({
      url: '../dance/dance'
    })
  },
  editClass : function(){
    wx.navigateTo({
      url: '../myClass/myClass'
    })
  }
})

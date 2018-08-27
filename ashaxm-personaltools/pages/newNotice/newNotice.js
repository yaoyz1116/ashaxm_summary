const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    dates : '',
    times : '12:00',
    title : '',
    desc : '',
    btnDisabled : false
  },
  onReady : function(){
    this.setData({
      dates : util.formatDate7(new Date())

    })
  },
  changeTitle : function(e){
    this.setData({
      title : e.detail.value
    })
  },
  changeDesc : function(e){
    this.setData({
      desc : e.detail.value
    })
  },
  bindDateChange: function (e) {
    this.setData({
      dates: e.detail.value
    })
  },
  bindTimeChange: function (e) {
    this.setData({
      times: e.detail.value
    })
  },
  saveNotice : function(e){
    this.setData({
      btnDisabled : true
    })
    if(this.data.title==""){
      wx.showModal({
        title: '提示',
        content: '请填写标题！',
        showCancel : false
      })
      this.setData({
        btnDisabled : false
      })
      return
    }
    var _this=this
    var tempTime = this.data.dates.substring(0,4)+"-"+this.data.dates.substring(5,7)+"-"+this.data.dates.substring(8,10)
      +" "+this.data.times.substring(0,2)+":"+this.data.times.substring(3,5)+":00"
    wx.request({
      url: app.globalData.SERVICE_URL+"/notice/addNotice", 
      method : "POST",
      data: {
         "userId": app.globalData.userId,
         "formId":  e.detail.formId,
         "title":  _this.data.title,
         "desc":  _this.data.desc,
         "noticeTime": tempTime
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
      },
      success: function(res) {
        console.log("success")
        _this.setData({
          btnDisabled : false
        })
        wx.showModal({
          title: '新建成功',
          content: '小秘书已经记录你本次事件，会准时在指定的时间内以微信服务通知的形式提醒你！',
          showCancel : false,
          confirmText : '感谢秘书',
          success: function(res) {
            wx.navigateBack({
              delta: 1
            })
          }
        })

      }
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

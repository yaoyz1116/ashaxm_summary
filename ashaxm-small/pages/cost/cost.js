const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    dates: '',
    objectArray: ['日','一','二','三','四','五','六'],
    index: 0,
    price : '',
    viewpricelist : [],
    sumprice : 0,
    avgprice : 0,
    groupnum : 1,
    userInfo : {},
    hasUserInfo: false,
    // canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUse: '',
    hiddenUpdate : true,
    updatepredate : '',
    updatepreprice : '',
    updateprice : '',
    updatedailyid : 0
  },
  onReady: function () {
    this.ajaxloadUserinfo() 
  },
  ajaxloadUserinfo : function(e){
    if (app.globalData.userInfo) {
        console.log(app.globalData.userInfo)
        this.setData({
          userInfo: app.globalData.userInfo,
          hasUserInfo: true
        }) 
        this.loadCost()     
    }else {
      setTimeout(this.ajaxloadUserinfo, 1000)       
    }
  },
  getUserInfo: function(e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  //  点击日期组件确定事件  
  bindDateChange: function (e) {
    this.setData({
      dates: e.detail.value
    })
  },
  //  点击城市组件确定事件  
  bindPickerChange: function (e) {
    this.setData({
      index: e.detail.value
    })
  },
  showFriendmsg : function(e){
    wx.showModal({
      title: '友情提示',
      content: '此小程序可以用来记录每天日常的各种开销，如果需要记录小组内成员的开销总和，请联系开发者微信免费添加：yaoyuzhao000',
      showCancel : false,
    })
  },
  //  输入价格时触发的事件
  bindpriceinput: function (e) {
    this.setData({
      price: e.detail.value
    })
  },
  // 将页面上的内容写入文件
  recordPrice : function(e){
    if(this.data.price==""){
      wx.showToast({
        title: '请填写价格',
        icon: 'none',
        duration: 2000
      })
      return
    }
    if(isNaN(Number(this.data.price))){
      wx.showToast({
        title: '请填写正确的价格',
        icon: 'none',
        duration: 2000
      })
      return
    }
    var _this=this
    wx.request({
      url: app.globalData.Service+"/dailycost/addCost", 
      method : "POST",
      data: {
         "userid": this.data.userInfo.id,
         "groupid": this.data.userInfo.groupid,
         "costdate": this.data.dates,
         "costweek": this.data.index,
         "costprice": this.data.price
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        _this.loadCost()
      }
    })

  },
  deleteCost : function(e){
    const aimid = e.target.dataset.dailyid
    const aimdate = e.target.dataset.dailydate
    const _this=this
    wx.showModal({
      title: '提示',
      content: '确定删除'+aimdate+"的消费记录",
      success: function(res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.Service+"/dailycost/deleteCost", 
            method : "POST",
            data: {
               "userid": _this.data.userInfo.id,
               "dailyid": aimid
            },
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },
            success: function(res) {
              _this.loadCost()
            }
          })
        } else if (res.cancel) {
          
        }
      }
    })
  },
  updateCost : function(e){
    const aimid = e.target.dataset.dailyid
    const aimdate = e.target.dataset.dailydate
    const aimprice = e.target.dataset.dailyprice
    const _this=this
    this.setData({
      hiddenUpdate: false,
      updatedailyid : aimid,
      updatepredate : aimdate,
      updatepreprice : aimprice
    })
  },
  bindupdatepriceinput : function(e){
    this.setData({
      updateprice: e.detail.value
    })
  },
  confirmUpdate : function(e){
    const _this=this
    wx.request({
      url: app.globalData.Service+"/dailycost/updateCost", 
      method : "POST",
      data: {
         "userid": _this.data.userInfo.id,
         "dailyid": _this.data.updatedailyid,
         "preprice": _this.data.updatepreprice,
         "updateprice" : _this.data.updateprice
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        _this.loadCost()
        _this.setData({
          hiddenUpdate : true
        })
      }
    })
  },
  cancelUpdate : function(e){
    this.setData({
      hiddenUpdate: true,
      updatedailyid : '',
      updatepredate : '',
      updatepreprice : ''
    })
  },
  playMusic : function(e){
    // const backgroundAudioManager = wx.getBackgroundAudioManager()
    // backgroundAudioManager.title = '此时此刻'
    // backgroundAudioManager.epname = '此时此刻'
    // backgroundAudioManager.singer = '许巍'
    // backgroundAudioManager.coverImgUrl = 'http://y.gtimg.cn/music/photo_new/T002R300x300M000003rsKF44GyaSk.jpg?max_age=2592000'
    // backgroundAudioManager.src = 'http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E061FF02C31F716658E5C81F5594D561F2E88B854E81CAAB7806D5E4F103E55D33C16F3FAC506D1AB172DE8600B37E43FAD&fromtag=46' // 设置了 src 之后会自动播放
    // backgroundAudioManager.play


  },
  loadCost : function(e){
    var _this=this
    wx.request({
      url: app.globalData.Service+"/dailycost/loadAllCost", 
      method : "POST",
      data: {
         "groupid": this.data.userInfo.groupid,
         "userid":  this.data.userInfo.id
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        _this.setData({
          viewpricelist: res.data.data,
          groupnum : res.data.groupnum
        })
        _this.initview()
      }
    })
  },
  initview : function(e){
    var week = util.formatWeek(new Date())
    let tempprice=0
    for(let i=0; i<this.data.viewpricelist.length; i++){
      tempprice += this.data.viewpricelist[i].costprice*1
    }
    let temppricetwo = (tempprice/this.data.groupnum).toFixed(1)
    this.setData({
      dates: util.formatDate(new Date()),
      sumprice: tempprice,
      avgprice: temppricetwo,
      price: '',
      index : util.formatWeek(new Date())
    })
  },
  onShareAppMessage : function(){
    return {
      title: '个人助手',
      path: '/pages/cost/cost',
      imageUrl : '/imgs/share.png'
    }
  }

})
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
    hiddenUpdate : true,
    updatepredate : '',
    updatepreprice : '',
    updateprice : '',
    updatedailyid : 0
  },
  onReady: function () {
    this.loadCost()   
  },
  loadCost : function(e){
    wx.showLoading({
      title: '加载中',
    })
    var _this=this
    wx.request({
      url: app.globalData.SERVICE_URL+"/dailycost/loadAllCost", 
      method : "POST",
      data: {
         "groupid": app.globalData.userInfo.groupId,
         "userid":  app.globalData.userId
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
        wx.hideLoading()
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
   // 点击日期组件确定事件  
  bindDateChange: function (e) {
    this.setData({
      dates: e.detail.value
    })
  },
   // 点击城市组件确定事件  
  bindPickerChange: function (e) {
    this.setData({
      index: e.detail.value
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
        title: '请填写价格',
        icon: 'none',
        duration: 2000
      })
      return
    }
    var _this=this
    wx.request({
      url: app.globalData.SERVICE_URL+"/dailycost/addCost", 
      method : "POST",
      data: {
         "userid": app.globalData.userId,
         "groupid": app.globalData.userInfo.groupId,
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
            url: app.globalData.SERVICE_URL+"/dailycost/deleteCost", 
            method : "POST",
            data: {
               "userid": app.globalData.userId,
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
    if(this.data.updateprice==""){
      wx.showToast({
        title: '请填写价格',
        icon: 'none',
        duration: 2000
      })
      return
    }
    if(isNaN(Number(this.data.updateprice))){
      wx.showToast({
        title: '请填写价格',
        icon: 'none',
        duration: 2000
      })
      return
    }
    const _this=this
    wx.request({
      url: app.globalData.SERVICE_URL+"/dailycost/updateCost", 
      method : "POST",
      data: {
         "userid": app.globalData.userId,
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
  onShareAppMessage : function(){
    return {
      title: app.globalData.SHARE_TITLE,
      path: app.globalData.SHARE_PAGE,
      imageUrl : app.globalData.SHARE_IMG
    }
  }

})
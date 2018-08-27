const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    visitor : {},
    noEditBaseInfo : true,
    showReCharge : false,
    btnDisabled : false,
    reChargeCost : 0,
    reChargeTime : 0,
    classArr : [],
    classIndex : 0,
    danceArr : [],
    danceIndex : 0,
    updateStudent : false
  },
  onLoad: function (options) {
    var queryBean = JSON.parse(options.itemStudent);
    this.setData({
      visitor : queryBean,
      classArr : app.globalData.classArr,
      danceArr : app.globalData.danceArr
    })
    this.findClass()
    this.findDance()
  },
  findClass :function(){
    for(var i=0; i<this.data.classArr.length; i++){
      if(this.data.visitor.classId == this.data.classArr[i].id){
        this.setData({
          classIndex : i
        })
      }
    }
  },
  findDance : function(){
    for(var i=0; i<this.data.danceArr.length; i++){
      if(this.data.visitor.danceId == this.data.danceArr[i].id){
        this.setData({
          danceIndex : i
        })
      }
    }
  },
  changeSex : function(e){
    this.data.visitor.sex = e.detail.value
    this.setData({
      visitor : this.data.visitor
    })
  },
  changeAge : function(e){
    this.data.visitor.age = e.detail.value
    this.setData({
      visitor : this.data.visitor
    })
  },
  changeParent : function(e){
    this.data.visitor.parent = e.detail.value
    this.setData({
      visitor : this.data.visitor
    })
  },
  changePhone : function(e){
    this.data.visitor.phone = e.detail.value
    this.setData({
      visitor : this.data.visitor
    })
  },
  bindClassChange : function(e){
    this.data.visitor.classId = this.data.classArr[e.detail.value].id
    this.setData({
      classIndex : e.detail.value,
      visitor : this.data.visitor
    })
  },
  bindDanceChange : function(e){
    this.data.visitor.danceId = this.data.danceArr[e.detail.value].id
    this.setData({
      danceIndex : e.detail.value,
      visitor : this.data.visitor
    })
  },
  updateStuBtn : function(){
    if(app.globalData.adminInfo.type!=1){
      wx.showModal({
        title: '提示',
        content: '您不是系统管理员，无权操作！',
        showCancel : false
      })
      return
    }
    console.log(this.data.visitor)
    this.setData({
      updateStudent : true
    })
    let that=this
    wx.showModal({
      title: '提示',
      content: '确定修改 '+that.data.visitor.name+' 的信息？',
      success: function(res) {
        if (res.confirm) {
          wx.request({
            url:app.globalData.SERVICE_URL+'/visitor/updateVisitor', 
            method : "POST",
            data: {
               'userId' : that.data.visitor.id,
               'adminId' : app.globalData.adminId,
               'name' : that.data.visitor.name,
               'sex' : that.data.visitor.sex,
               'parent' : that.data.visitor.parent,
               'phone' : that.data.visitor.phone,
               'age' : that.data.visitor.age,
               'danceId' : that.data.visitor.danceId,
               'classId' : that.data.visitor.classId
            },
            header: {
                'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
            },
            success: function(res) {
              if(res.data.status==1){
                wx.showToast({
                  title: '添加成功',
                  icon: 'success',
                  duration: 1000
                })
              }else{
                wx.showModal({
                  title: '提示',
                  content: '您不是系统管理员，无权操作！',
                  showCancel : false
                })
              }
              that.setData({
                updateStudent : false
              })
            }
          }) 
        } else if (res.cancel) {
          that.setData({
            updateStudent : false
          })
        }
      }
    })
  },
  controlBtn : function(flag){
    this.setData({
      btnDisabled : flag
    })
  },
  showMyModal : function(){
    if(app.globalData.adminInfo.type!=1){
      wx.showModal({
        title: '提示',
        content: '您不是系统管理员，无权操作！',
        showCancel : false
      })
      return
    }
    this.controlBtn(true)
    let that=this
    wx.showModal({
      title: '提示',
      content: that.data.visitor.name+' 确认签到？签到成功后系统自动给预留号码发送短信，并扣除相应费用，此操作不可撤回！',
      success: function(res) {
        if (res.confirm) {
          that.confirmStudy()
        } else if (res.cancel) {
          that.controlBtn(false)
        }
      }
    })
  },
  confirmStudy : function(){
    if(app.globalData.adminInfo.type!=1){
      wx.showModal({
        title: '提示',
        content: '您不是系统管理员，无权操作！',
        showCancel : false
      })
      return
    }
    let _this=this
    wx.request({
      url:app.globalData.SERVICE_URL+'/visitor/confirmStudy', 
      method : "POST",
      data: {
         'userId' : _this.data.visitor.id,
         'adminId' : app.globalData.adminId
      },
      header: {
          'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
      },
      success: function(res) {
        if(res.data.status==1){
          _this.data.visitor.spareTime--
          _this.data.visitor.studyTime++
          _this.setData({
            visitor : _this.data.visitor
          })
          wx.showToast({
            title: '签到成功',
            icon: 'success',
            duration: 1000
          })
        }else{
          wx.showModal({
            title: '提示',
            content: '您不是系统管理员，无权操作！',
            showCancel : false
          })
        }
      }
    })    
  },
  reChargeFun : function(){
    this.setData({
      showReCharge : true,
      reChargeCost : 0,
      reChargeTime : 0
    })
  },
  changeRechargeCost : function(e){
    this.setData({
      reChargeCost : e.detail.value
    })
  },
  changeRechargeTime : function(e){
    this.setData({
      reChargeTime : e.detail.value
    })
  },
  confimRecharge : function(){
    console.log("_this.data.reChargeCost----"+this.data.reChargeCost)
    if(app.globalData.adminInfo.type!=1){
      wx.showModal({
        title: '提示',
        content: '您不是系统管理员，无权操作！',
        showCancel : false
      })
      return
    }
    let _this=this
    wx.request({
      url:app.globalData.SERVICE_URL+'/visitor/rechargeVisitor', 
      method : "POST",
      data: {
         'userId' : _this.data.visitor.id,
         'adminId' : app.globalData.adminId,
         'cost' : _this.data.reChargeCost,
         'time' : _this.data.reChargeTime
      },
      header: {
          'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
      },
      success: function(res) {
        if(res.data.status==1){
          _this.data.visitor.spareTime += parseInt(_this.data.reChargeTime)
          _this.data.visitor.cost += parseInt(_this.data.reChargeCost)
          _this.setData({
            visitor : _this.data.visitor,
            reChargeTime : 0,
            reChargeCost : 0,
            showReCharge : false
          })
          wx.showToast({
            title: '续费成功',
            icon: 'success',
            duration: 1000
          })
        }else{
          wx.showModal({
            title: '提示',
            content: '您不是系统管理员，无权操作！',
            showCancel : false
          })
        }
      }
    }) 
  }
})

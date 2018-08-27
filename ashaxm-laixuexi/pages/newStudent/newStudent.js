const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    name : '',
    sex : '男',
    parent : '',
    phone : '',
    cost : 0,
    time : 0,
    age : 0,
    danceId : 0,
    classId : 0,
    btnDisabled : false,
    classArr : [],
    classIndex : 0,
    danceArr : [],
    danceIndex : 0
  },
  onLoad: function (options) {
    this.setData({
      classArr : app.globalData.classArr,
      danceArr : app.globalData.danceArr,
      classId : app.globalData.classArr[0].id,
      danceId : app.globalData.danceArr[0].id
    })
  },
  changeName : function(e){
    this.setData({
      name: e.detail.value
    })
  },
  changeAge : function(e){
    this.setData({
      age: e.detail.value
    })
  },
  changeSex : function(e){
    this.setData({
      sex: e.detail.value
    })
  },
  changeParent : function(e){
    this.setData({
      parent: e.detail.value
    })
  },
  changePhone : function(e){
    this.setData({
      phone: e.detail.value
    })
  },
  changeCost : function(e){
    this.setData({
      cost: e.detail.value
    })
  },
  changeTime : function(e){
    this.setData({
      time: e.detail.value
    })
  },
  bindClassChange : function(e){
    this.setData({
      classIndex : e.detail.value,
      classId : this.data.classArr[e.detail.value].id
    })
  },
  bindDanceChange : function(e){
    this.setData({
      danceIndex : e.detail.value,
      danceId : this.data.danceArr[e.detail.value].id
    })
  },
  checkInfo : function(){
    if(this.data.name==""){
      wx.showModal({
        title: '提示',
        content: '请填写学生姓名！',
        showCancel : false
      })
      return false
    }
    if(this.data.phone==""){
      wx.showModal({
        title: '提示',
        content: '请填写联系方式！',
        showCancel : false
      })
      return false
    }
    if(this.data.cost==""){
      wx.showModal({
        title: '提示',
        content: '请填写缴纳费用！',
        showCancel : false
      })
      return false
    }
    if(this.data.time==""){
      wx.showModal({
        title: '提示',
        content: '请填写学习课时！',
        showCancel : false
      })
      return false
    }
    console.log(this.data.name+"----"+this.data.sex+"----"+this.data.parent+"----"+this.data.phone+"----"+this.data.cost+"-----"+this.data.time)
    return true
  },
  controlBtn : function(flag){
    this.setData({
      btnDisabled : flag
    })
  },
  saveStudent : function(){
    if(app.globalData.adminInfo.type!=1){
      wx.showModal({
        title: '提示',
        content: '您不是系统管理员，无权操作！',
        showCancel : false
      })
      return
    }
    this.controlBtn(true)
    if(this.checkInfo()){
      let _this=this
      wx.request({
        url:app.globalData.SERVICE_URL+'/visitor/addVisitor', 
        method : "POST",
        data: {
           'adminId' : app.globalData.adminId,
           'name' : _this.data.name,
           'age' : _this.data.age,
           'sex' : _this.data.sex,
           'parent' : _this.data.parent,
           'phone' : _this.data.phone,
           'cost' : _this.data.cost,
           'time' : _this.data.time,
           'danceId' : _this.data.danceId,
           'classId' : _this.data.classId
        },
        header: {
            'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
        },
        success: function(res) {
          _this.controlBtn(false)
          if(res.data.status==1){
            wx.showToast({
              title: '添加成功',
              icon: 'success',
              duration: 1000
            })
            setTimeout(_this.goStudents,1000)
          }else{
            wx.showModal({
              title: '提示',
              content: '您不是系统管理员，无权操作！',
              showCancel : false
            })
          }
        }
      })
    }else{
      this.controlBtn(false)
    }
    
  },
  goStudents : function(){
    wx.switchTab({
      url: '../students/students'
    })
  }
})

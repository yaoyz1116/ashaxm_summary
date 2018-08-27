const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    students : [],
    classArr : [],
    nowClickClassId : 10
  },
  onShow: function () {
    this.setData({
      classArr : app.globalData.classArr
    })
    this.loadDebates()
  },
  loadDebates : function(){
    let _this=this
    wx.request({
      url: app.globalData.SERVICE_URL+'/visitor/loadVisitors', 
      method : "POST",
      header: {
          'content-type': 'application/x-www-form-urlencoded' 
      },
      success: function(res) {
        if(res.data.status==1){
          _this.setData({
            students : res.data.visitors,
          })
          console.log(_this.data.students)
        }
      }
    })
  },
  changeClassId : function(e){
    this.setData({
      nowClickClassId : e.target.dataset.id
    })
  },
  goStudentDetail : function(e){
    let itemStudent={}
    for(let idx=0; idx<this.data.students.length; idx++){
      if(this.data.students[idx].id==e.target.dataset.id){
        itemStudent = this.data.students[idx]
        break;
      }
    }
    var queryBean = JSON.stringify(itemStudent)
    wx.navigateTo({
      url: '../detailStudent/detailStudent?itemStudent='+queryBean
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

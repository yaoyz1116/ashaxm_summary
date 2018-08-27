const app = getApp()


Page({
  data: {
    classArr : [],
    aimClass : '',
    inputvalue : ''
  },
  onLoad(opt) {
    this.setData({
      classArr : app.globalData.classArr
    })
  },
  changeaimClass:function(e){
    this.setData({
      aimClass: e.detail.value
    })
  },
  addaimClass:function(e){
    if(this.data.aimClass != ""){
        if(app.globalData.adminInfo.type!=1){
          wx.showModal({
            title: '提示',
            content: '您不是系统管理员，无权操作！',
            showCancel : false
          })
          return
        }
        var _this=this;
        wx.request({
          url:app.globalData.SERVICE_URL+'/visitor/addClass', 
          method : "POST",
          data: {
             'adminId' : app.globalData.adminId,
             'name' : _this.data.aimClass
          },
          header: {
              'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
          },
          success: function(res) {
            if(res.data.status==1){
              _this.setData({
                classArr : res.data.classes,
                aimClass : '',
                inputvalue : ''
              })
              app.globalData.classArr = res.data.classes
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
  },
  removeClass : function(e){
    if(app.globalData.adminInfo.type!=1){
      wx.showModal({
        title: '提示',
        content: '您不是系统管理员，无权操作！',
        showCancel : false
      })
      return
    }
    var _this=this;
    wx.showModal({
      title : "提示",
      content : "是否删除",
      success : function(res){
        if(res.confirm){
          wx.request({
            url:app.globalData.SERVICE_URL+'/visitor/deleteClass', 
            method : "POST",
            data: {
              'adminId' : app.globalData.adminId,
               'classId' : e.target.id
            },
            header: {
                'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
            },
            success: function(res) {
              if(res.data.status==1){
                _this.setData({
                  classArr : res.data.classes,
                  aimClass : '',
                  inputvalue : ''
                })
                app.globalData.classArr = res.data.classes
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
      }
    })
  }
})



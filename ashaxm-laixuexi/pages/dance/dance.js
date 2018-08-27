const app = getApp()


Page({
  data: {
    danceArr : [],
    aimDance : '',
    inputvalue : ''
  },
  onLoad(opt) {
    this.setData({
      danceArr : app.globalData.danceArr
    })
  },
  changeaimDance:function(e){
    this.setData({
      aimDance: e.detail.value
    })
  },
  addaimDance:function(e){
    if(this.data.aimDance != ""){
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
          url:app.globalData.SERVICE_URL+'/visitor/addDance', 
          method : "POST",
          data: {
             'adminId' : app.globalData.adminId,
             'danceName' : _this.data.aimDance
          },
          header: {
              'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
          },
          success: function(res) {
            if(res.data.status==1){
              _this.setData({
                danceArr : res.data.dances,
                aimDance : '',
                inputvalue : ''
              })
              app.globalData.danceArr = res.data.dances
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
  removeDance : function(e){
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
            url:app.globalData.SERVICE_URL+'/visitor/deleteDance', 
            method : "POST",
            data: {
              'adminId' : app.globalData.adminId,
               'danceId' : e.target.id
            },
            header: {
                'content-type': 'application/x-www-form-urlencoded;charset=UTF-8' 
            },
            success: function(res) {
              if(res.data.status==1){
                _this.setData({
                  danceArr : res.data.dances,
                  aimDance : '',
                  inputvalue : ''
                })
                app.globalData.danceArr = res.data.dances
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



const util = require('../../utils/util.js')
const app = getApp()

Page({
  data: {
    imgUrls: [
      'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
    ],
    iconType: [
      'success', 'success_no_circle', 'info', 'warn', 'waiting', 'cancel', 'download', 'search', 'clear'
    ],
    poster: 'http://y.gtimg.cn/music/photo_new/T002R300x300M000003rsKF44GyaSk.jpg?max_age=2592000',
    name: '此时此刻',
    author: '许巍',
    src: 'http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E06DCBDC9AB7C49FD713D632D313AC4858BACB8DDD29067D3C601481D36E62053BF8DFEAF74C0A5CCFADD6471160CAF3E6A&fromtag=46',
 
  },
  // 第一步、生命周期函数--监听页面加载，执行一次
  onLoad: function () {
    console.log("page onLoad")
  },
  // 第二步、生命周期函数--监听页面显示，执行N次
  onShow : function(){
    console.log("page onShow")
  },
  // 第三步、生命周期函数--监听页面初次渲染完成，执行一次
  onReady : function(){
    console.log("page onReady")
    this.videoCtx = wx.createVideoContext('myVideo')
  },
  // 第四步、生命周期函数--监听页面隐藏，执行N此
  onHide : function(){
    console.log("page onHide")
  },
  // 第五步、  生命周期函数--监听页面卸载，不知道什么时候执行，应该是小程序销毁时
  onUnload : function(){
    console.log("page onUnload")
  },
  // 页面相关事件处理函数--监听用户下拉动作
  onPullDownRefresh : function(){
    console.log("page onPullDownRefresh")
  },
  // 页面上拉触底事件的处理函数
  onReachBottom : function(){
    console.log("page onReachBottom")
  },
  // 用户点击右上角转发
  onShareAppMessage : function(){
    console.log("page onShareAppMessage")
    return {
      title: '个人助手',
      path: '/pages/apishow/apishow',
      imageUrl : '/pages/index/01.png',
      success: function(res) {
        // 转发成功
        console.log("转发成功")
      },
      fail: function(res) {
        // 转发失败
        console.log("转发失败")
      }
    }
  },
  // 页面滚动触发事件的处理函数
  onPageScroll : function(){
    console.log("page onPageScroll")
  },
  // 当前是 tab 页时，点击 tab 时触发
  onTabItemTap : function(){
    console.log("page onTabItemTap")
  },
  upper : function(){
    console.log("scroll view 已经滚动到了最上方")
  },
  lower : function(){
    console.log("scroll view 已经滚动到了最下方")
  },
  play() {
    this.videoCtx.play()
  },
  pause() {
    this.videoCtx.pause()
  },
  takePhoto() {
      const ctx = wx.createCameraContext()
      ctx.takePhoto({
          quality: 'high',
          success: (res) => {
              this.setData({
                  camerasrc: res.tempImagePath
              })
          }
      })
  },
})
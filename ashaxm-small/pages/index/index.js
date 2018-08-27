const app = getApp()


Page({
  data: {
    aimcontent : '',
    contentarray : [],
    inputvalue : '',
    lotteryArrLen : 1,
    canRoll : true,
    num : 1
  },
  onLoad(opt) {
    this.setPlateData();
    let aniData = wx.createAnimation({ 
      duration: 2000,
      timingFunction: 'ease'
    });
    this.aniData = aniData; 
  },
  changeaimcontent:function(e)
  {
    this.setData({
      aimcontent: e.detail.value
    })
  },
  heool : function (e){
    console.log(e);
  },
  addaimcontent:function(e)
  {
    if(this.data.aimcontent != ""){
      this.data.contentarray = this.data.contentarray.concat(this.data.aimcontent)
      this.setData({
        aimcontent: ""
      })
      
      this.setData({
        inputvalue: ""
      })
      if(this.data.contentarray.length % 2 >0){
        for (var i = 0; i < this.data.contentarray.length; i++) {
          console.log(this.data.contentarray[i])
          if(this.data.contentarray[i] == "再来一次"){
            this.data.contentarray.splice(i,1);    
          }
        }
      }
      this.setData({
        contentarray: this.data.contentarray
      })
      this.setPlateData();
    }
  },
  removeaimcontent : function(e){
    var _this=this;
    var flag=true;
    wx.showModal({
      title : "提示",
      content : "是否删除",
      success : function(res){
        if(res.confirm){
              _this.data.contentarray.splice(e.target.id,1);
              if(_this.data.contentarray.length >0){
                for (var i = 0; i < _this.data.contentarray.length; i++) {
                  console.log(_this.data.contentarray[i])
                  if(_this.data.contentarray[i] == "再来一次"){
                    _this.data.contentarray.splice(i,1);  
                    flag=false;  
                  }
                }  
                if(flag){
                  _this.data.contentarray = _this.data.contentarray.concat("再来一次")
                }
              }
                      
              _this.setData({
                  contentarray:_this.data.contentarray
              });
        }
      }
    })
  },
  startRollTap() { 
    if (this.data.canRoll) {
      setTimeout((function callback(){
          this.setData({ canRoll: true});
      }).bind(this),2000);
      this.data.canRoll = false;
      let aniData = this.aniData; 
      let rightNum = ~~(Math.random() * this.data.lotteryArrLen); 
      aniData.rotate(3600 * this.data.num - 360 / this.data.lotteryArrLen * rightNum).step(); 
      this.setData({
        aniData: aniData.export()
      })
      this.data.num++;
    }
  },
  setPlateData() { 
    this.data.lotteryArrLen = this.data.contentarray.length; 
    if(this.data.lotteryArrLen  == 0){
      let evenArr = new Array(4);
      for(let i=0; i<4; i++){
        evenArr[i] = "再来一次"
      } 
      this.setData({
        contentarray: evenArr
      })
      this.data.lotteryArrLen =4;   
    }else if(this.data.lotteryArrLen  == 1){
      let evenArr = new Array(4);
      for(let i=0; i<3; i++){
        evenArr[i] = "再来一次"
      } 
      evenArr[3] = this.data.contentarray[0];
      this.setData({
        contentarray: evenArr
      })
      this.data.lotteryArrLen =4;   
    }else if(this.data.lotteryArrLen  == 2){
      let evenArr = new Array(4);
      evenArr[0]="再来一次";
      evenArr[2]="再来一次";
      evenArr[1]=this.data.contentarray[0];
      evenArr[3]=this.data.contentarray[1];
      this.setData({
        contentarray: evenArr
      })
      this.data.lotteryArrLen =4;   
    }else if(this.data.lotteryArrLen  == 3){
      let evenArr = new Array(4);
      evenArr[0]="再来一次";
      evenArr[2]=this.data.contentarray[2];
      evenArr[1]=this.data.contentarray[0];
      evenArr[3]=this.data.contentarray[1];
      this.setData({
        contentarray: evenArr
      })
      this.data.lotteryArrLen =4;   
    }else if (this.data.lotteryArrLen %2 >0) { 
      let evenArr = new Array(this.data.lotteryArrLen + 1); 
      for (let i = 0; i < (this.data.lotteryArrLen + 1); i++) {
        if (i < this.data.lotteryArrLen) { 
          evenArr[i] = this.data.contentarray[i]; 
        } else {
          evenArr[i] = '再来一次' 
        }
      }
      this.setData({
        contentarray: evenArr
      })
      this.data.lotteryArrLen += 1;
    }
  },
  onShareAppMessage: function (res) {
    return {
      title: '滚蛋吧，困难症！',
      path: '/page/index/index',
      imageUrl : '/page/index/01.png',
      success: function(res) {
        // 转发成功
      },
      fail: function(res) {
        // 转发失败
      }
    }
  }
})



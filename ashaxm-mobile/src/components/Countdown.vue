<template>
  <div>
    <mt-header title="倒计时" class="headercls">
      <router-link to="/home" slot="left">
        <mt-button icon="back">返回</mt-button>
      </router-link>
      <mt-button icon="more" slot="right" v-on:click="seeCountmore()"></mt-button>
    </mt-header>
    <div v-if="nowmodel==0">
      <img src="../assets/img/countdown.gif" class="imgcls">
    </div>
    <div v-for="itemcount in countlist" >
        <mt-cell-swipe :title="itemcount.title" 
          :right="[{ content: 'Delete', style: { background: 'red', color: '#fff' }, handler: () => this.deletecount() }]">
        </mt-cell-swipe>
    </div>

    <!-- 添加新倒计时-->
    <div v-if="nowmodel==1" class="newcls">
      <mt-field label="标题" placeholder="请输入标题" v-model="title"></mt-field>
      <mt-field label="时间" placeholder="请输入时间" type="date" v-model="settime"></mt-field>
      <mt-field label="内容" placeholder="请输入内容" v-model="contenttext" type="textarea" rows="4" ></mt-field>
      <br><br>
      <mt-button class="btncls" style="margin-right:2vw;" type="primary" size="large" @click="confirmnewcount()">确定添加</mt-button>
      <mt-button class="btncls" style="margin-left:2vw;" type="danger" size="large" @click="canclenewcount()">取消</mt-button>
    </div>
    <!-- 添加新倒计时-->


     <mt-actionsheet  
        :actions= "sheets"  
        v-model="sheetVisible">  
    </mt-actionsheet>  
  </div>
</template>

<script>
import { Actionsheet } from 'mint-ui';
import { Toast } from 'mint-ui';

export default {
  name: 'Countdown',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      sheetVisible : false,
      sheets: [{  
        name: '添加新日程',  
        method : this.addnewcount
      }],
      nowmodel : 0,
      title : '',
      settime : '',
      contenttext : '',
      countlist : []
    }
  },
  mounted : function(){
    this.loadallcount()
  },
  filters : {
    filtertime : function(value){
      return value
    },
  },
  methods : {
    seeCountmore: function(){
      this.sheetVisible=true
    },
    addnewcount : function(){
      this.nowmodel=1
    },
    confirmnewcount : function(){
      if(this.title=="" || this.settime=="" || this.contenttext==""){
        let instance = Toast('信息请填写完整!');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return
      }
      var params = new URLSearchParams();
      params.append("title",this.title);
      params.append("settime",this.settime);
      params.append("content",this.contenttext);
      params.append("loginuserid",loginuserid);
      var _this=this;
      this.$axios({
          method: "POST",
          withCredentials: true,
          url: "/service/countdown/addnew",
          params
      })
      .then(function(res) {
          if(res.data.status==1){
              _this.loadallcount()
          }else{
              alert("网络异常，请稍后重试！")                      
          }
      })
      .catch(function(err) {
           alert("网络异常，请稍后重试！")
      });
      this.nowmodel=0
    },
    canclenewcount : function(){
      this.nowmodel=0
    },
    loadallcount : function(){
      var params = new URLSearchParams();
      params.append("loginuserid",loginuserid);
      var _this=this;
      this.$axios({
          method: "POST",
          withCredentials: true,
          url: "/service/countdown/loadallcount",
          params
      })
      .then(function(res) {
          if(res.data.status==1){
              _this.countlist=res.data.list
          }else{
              alert("网络异常，请稍后重试！")                      
          }
      })
      .catch(function(err) {
           alert("网络异常，请稍后重试！")
      });
    },
    deletecount : function(){
      alert("删除")
    }
  }
}
</script>

<style scoped>
  .headercls{
    background-color: orange;
  }
  .newcls{
    width: 90vw;
    margin: 5vh auto;
  }
  .btncls{
    width: 43vw;
    margin: 0 auto;
    float: left;
  }
  .imgcls{
    width: 100%;
    max-height: 40vh;
  }
</style>

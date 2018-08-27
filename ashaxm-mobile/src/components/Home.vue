<template>
  <div>
    <mt-swipe :auto="5000" :show-indicators="false" style="width:100vw;height:60vh;">
      <mt-swipe-item><img src="../assets/img/05.jpg" class="logocls"></mt-swipe-item>
      <mt-swipe-item><img src="../assets/img/04.jpg" class="logocls"></mt-swipe-item>
      <mt-swipe-item><img src="../assets/img/03.jpg" class="logocls"></mt-swipe-item>
      <mt-swipe-item><img src="../assets/img/02.jpg" class="logocls"></mt-swipe-item>
      <mt-swipe-item><img src="../assets/img/01.jpg" class="logocls"></mt-swipe-item>
    </mt-swipe> 
    <div class="bottomdiv">
      <div class="nineblock1">
        <div class="centerlogo"><i class="fa  fa-user-circle fa-lg" style="color:blue;"></i><div class="centertext">我的信息</div></div>
      </div>
      <div class="nineblock2">
        <div class="centerlogo"><i class="fa  fa-book fa-lg" style="color:orange;"></i><div class="centertext">备忘录</div></div>       
      </div>
      <div class="nineblock3">
        <div class="centerlogo"><i class="fa  fa-yen (alias) fa-lg" style="color:red;"></i><div class="centertext">记账本</div></div>
      </div>
      <div class="nineblock4" v-on:click="seeCountDown()">
        <div class="centerlogo"><i class="fa  fa-calendar-times-o fa-lg" style="color:#CC9966;"></i><div class="centertext">倒计时</div></div>
      </div>
      <div class="nineblock5" v-on:click="pwdmanage()">
        <div class="centerlogo"><i class="fa  fa-lock fa-lg" style="color:purple;"></i><div class="centertext">密码管理</div></div>
      </div>
      <div class="nineblock6">
        <div class="centerlogo"><i class="fa  fa-comment-o fa-lg" style="color:blue;"></i><div class="centertext">提出意见</div></div>
      </div>
    </div>
  </div>
</template>

<script>
import { Swipe, SwipeItem } from 'mint-ui';
export default {
  name: 'Home',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  mounted: function () {
    
  },
  methods : {
    seeCountDown: function(){
      this.$router.push('/countdown')
    },
    pwdmanage : function(){
      this.$router.push('/pwdmanage')      
    }
  }
}
</script>

<style scoped>
  .logocls{
    max-height: 60vh;
    width: 100%;
  }
  .bottomdiv{
    padding: 5vw 0;
    margin: 0 auto;
    width: 93vw;
  }
  .nineblock1{
    height: 30vw;
    width: 30vw;
    border-right: 1px solid #aaa;
    border-bottom: 1px solid #aaa;
    float: left;
  }
  .nineblock2{
    height: 30vw;
    width: 30vw;
    float: left;
  }
  .nineblock3{
    height: 30vw;
    width: 30vw;
    border-left: 1px solid #aaa;
    border-bottom: 1px solid #aaa;
    float: left;
  }
  .nineblock4{
    height: 30vw;
    width: 30vw;
    float: left;
  }
  .nineblock5{
    height: 30vw;
    width: 30vw;
    border: 1px solid #aaa;
    border-bottom: none;
    float: left;
  }
  .nineblock6{
    height: 30vw;
    width: 30vw;
    float: left;
  }
  .centerlogo{
    text-align: center;
    margin: 8vw auto;
  }
  .centertext{
    margin-top: 2vw;
  }



</style>

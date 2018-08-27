<template>
  <div class="">
    <mt-header title="密码管理" class="headercls">
      <router-link to="/home" slot="left">
        <mt-button icon="back">返回</mt-button>
      </router-link>
    </mt-header>
    <img src="../assets/img/pwd.jpg" class="imgcls">
    <div class="explaincls">
      <div style="color:red;text-align:center;font-size:14px;">使用说明</div>
      使用本软件在搭配一个密码管理器使用，推荐EverPass,可以在EverPass中记录账号和对应的密码序号，然后在本页面记录序号和真正的密码，两者结合，绝对万无一失。比如在EverPass中记录 QQ账号 1742938037------pwd01,然后在本页面记录一条信息pwd01-----123456，这样任何人也没有办法获取你的账号密码了。
    </div>
    <div>
      
    </div>
    <div class="newbtncls"><mt-button type="primary" size="large"  v-on:click="addnewpwd()">新增</mt-button></div>


    <div v-if="alertshow" class="alertcls">
      <mt-field label="序号" class="inputfloat" v-model="newseq"></mt-field>
      <mt-field label="密码" class="inputfloat" v-model="newpwd"></mt-field>   
      <mt-button type="primary" style="margin-left:4vw;" class="btnfloat" v-on:click="confirmnew()">确定</mt-button>
      <mt-button type="danger"  class="btnfloat" v-on:click="canclenew()">取消</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Pwdmanage',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      alertshow : false,
    }
  },
  methods : {
    addnewpwd : function(){
      this.alertshow=true
    },
    confirmnew : function(){

    },
    canclenew : function(){
      this.alertshow=false
    }
  }
}
</script>

<style scoped>

.imgcls{
  width: 100%;
  max-height: 40vh;
}
.explaincls{
  margin: 1rem;
  font-size: 12px;
}
.newbtncls{
  width: 60vw;
  margin: 0 auto;
}
.inputfloat{
  width: 60vw;
}
.btnfloat{
  width: 25vw;
  margin-top: 1rem;
}
.alertcls{
  background-color: rgba(200,200,200,0.7);
  padding: 25vh 20vw;
  z-index:1000;
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;

}

</style>

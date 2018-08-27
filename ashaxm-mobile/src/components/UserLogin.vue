<template>
  <div class="bgdiv">
    <div class="logindiv" v-if="showflag==0">
      <mt-field label="用户名" placeholder="请输入用户名" v-model.trim="username" class="infinite animated" :class="{rubberBand : isnameactive, hidediv : isnameshow}"></mt-field>
      <mt-field label="密码" placeholder="请输入密码" type="password" v-model.trim="password" class="infinite animated" :class="{shake : ispwdactive, hidediv : ispwdshow}"></mt-field><br>
      <mt-button type="primary" size="large" v-on:click="userLoad()" class="infinite animated" :class="{slideInRight : isbuttonactive, hidediv : isbtnshow}">登&nbsp;&nbsp;&nbsp;&nbsp;录</mt-button>
      <div class="changedivflag infinite animated"  v-on:click="changediv(1)"  :class="{fadeOutDown : istextactive, hidediv : istextshow}">没有账号？立即注册</div>
    </div>

    <div class="registerdiv" v-if="showflag==1">       
      <mt-field label="用户名" placeholder="请输入用户名" v-model.trim="registername"></mt-field>
      <mt-field label="手机号" placeholder="请输入手机号" type="tel" v-model.trim="phone"></mt-field>
      <mt-field label="密码" placeholder="请输入密码" type="password" v-model.trim="registerpwd"></mt-field>
      <mt-field label="确认密码" placeholder="请再次输入密码" type="password" v-model.trim="confirmpwd"></mt-field><br>
      <mt-button type="danger" size="large" v-on:click="registeruser()">注&nbsp;&nbsp;&nbsp;&nbsp;册</mt-button>
      <div class="changedivflag"  v-on:click="changediv(0)">返回登录页面</div>
    </div>
  </div>
</template>

<script>
import { Toast } from 'mint-ui';
export default {
  name: 'UserLogin',
  data () {
    return {
      username: '',
      password: '',
      showflag: 0,
      registername : '',
      registerpwd : '',
      confirmpwd : '',
      phone : '',
      isnameactive : true,
      ispwdactive : false,
      isbuttonactive : false,
      istextactive : false,
      activeflag : true,
      isnameshow : false,
      ispwdshow : true,
      isbtnshow : true,
      istextshow : true
    }
  },
  mounted: function () {
    setTimeout(() => {
          this.changeactive()
        }, 1000);
  },
  methods:{
    changediv : function(value){
      this.showflag=value
    },
    registeruser : function(){
      if(this.registername==""){
        let instance = Toast('用户名不能为空');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return;
      }
      if(this.phone==""){
        let instance = Toast('手机号不能为空');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return;
      }
      if(this.registerpwd==""){
        let instance = Toast('密码不能为空');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return;
      }
      if(this.registerpwd != this.confirmpwd){
        let instance = Toast('两次输入的密码不一致');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return;
      }
      var params = new URLSearchParams();
      params.append("name",this.registername);
      params.append("password",this.registerpwd);
      params.append("phone",this.phone);
      var _this=this;
      this.$axios({
          method: "POST",
          withCredentials: true,
          url: "/service/user/register",
          params
      })
      .then(function(res) {
           if(res.data.status==1){
            loginuserid =res.data.loginuserid
            _this.$router.push('/home')
          }else{
            let instance = Toast({
              message: '手机号码已经被注册'
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
      })
      .catch(function(err) {
           alert("网络异常，请稍后重试！")
      });
    },
    userLoad : function(){
      if(this.username==""){
        let instance = Toast('用户名不能为空');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return;
      }
      if(this.password==""){
        let instance = Toast('用户名不能为空');
        setTimeout(() => {
          instance.close();
        }, 2000);
        return;
      }

      // var params = new URLSearchParams();
      // params.append("name",this.username);
      // params.append("password",this.password);
      var _this=this;
      this.$axios({
          method: "POST",
          withCredentials: true,
          url: "/service/user/load",
          transformRequest: [function (data) {
            console.log("data________")
            console.log(data)
            return data;
          }],
          params : {
            'name':'姚宇照',
            'password':'123456'
          }
          // params
      })
      .then(function(res) {
          if(res.data.status==1){
            loginuserid =res.data.loginuserid
            _this.$router.push('/home')
          }else{
            let instance = Toast({
              message: '用户名或密码不正确'
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
      })
      .catch(function(err) {
           console.log(err);
      })
    },
    changeactive : function(){
      if(this.isnameactive){
        this.ispwdshow=false;
        this.isnameactive=false;
        this.ispwdactive=true;
      }else if(this.ispwdactive){
        this.isbtnshow=false;
        this.ispwdactive=false;
        this.isbuttonactive=true;
      }else if(this.isbuttonactive){
        this.istextshow=false;
        this.isbuttonactive=false;
        this.istextactive=true;
      }else if(this.istextactive){
        this.istextactive=false;
        this.activeflag=false;
      }
      if(this.activeflag){
        setTimeout(() => {
          this.changeactive()
        }, 1000);
      }

    }
  }
}
</script>

<style scoped>
  .bgdiv{
    background-image: url("http://content.fengchuanba.com/file/default/material/mt//20171214132945.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    width: 100vw;
    height: 100vh;
  }
  .logindiv{
    padding: 30vh 10vw;
  }
  .changedivflag{
    float: right;
    color: red;
    font-size: 14px;
    margin-top: 1rem;
  }
  .registerdiv{
    padding: 30vh 10vw;    
  }
  .hidediv{
    display: none;
  }
</style>

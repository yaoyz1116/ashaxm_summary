<template>
  <div>
    <mt-header title="备忘录">
      <router-link to="/" slot="left">
        <mt-button icon="back">返回</mt-button>
      </router-link>
      <mt-button icon="more" slot="right" v-on:click="seeMemorymore()"></mt-button>
    </mt-header>
  </div>
</template>

<script>
export default {
  name: 'Memorandom',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods : {
    seeMemorymore: function(){
      alert("123")
    }
  }
}
</script>

<style scoped>

</style>

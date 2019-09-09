<template>
  <div id="app">
    <Header/>
    <!--<button  class="btn btn-default">Button</button>-->
    <Login/>
    <Footer/>
    <router-view/>
  </div>


</template>

<script>
import Header from './components/layout/Header'
import Login from './components/layout/Login'
import Footer from './components/layout/Footer'
export default {
  name: 'App',
  components:{
    Header,
    Login,
    Footer
  }
}
</script>

<style>
  html, body {
    height: 100%;
  }

#app{
  display: flex;
  flex-direction: column;
  height: 100%;
}


</style>

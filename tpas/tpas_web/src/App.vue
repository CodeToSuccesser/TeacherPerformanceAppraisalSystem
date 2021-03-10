<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('userType', this.$store.getters.userType)
      sessionStorage.setItem('name', this.$store.getters.name)
      sessionStorage.setItem('account', this.$store.getters.name)
      sessionStorage.setItem('contact', this.$store.getters.contact)
      sessionStorage.setItem('avatar', this.$store.getters.avatar)
      sessionStorage.setItem('id', this.$store.getters.id)
    })
  }
}
</script>

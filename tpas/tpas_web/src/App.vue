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
      sessionStorage.setItem('rolesName', this.$store.getters.rolesName)
      sessionStorage.setItem('name', this.$store.getters.name)
      sessionStorage.setItem('account', this.$store.getters.account)
      sessionStorage.setItem('contact', this.$store.getters.contact)
      sessionStorage.setItem('portrait', this.$store.getters.portrait)
      sessionStorage.setItem('id', this.$store.getters.id)
      sessionStorage.setItem('routerMenus', this.$store.getters.routerMenus)
    })
  }
}
</script>

<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    // 页面刷新之前保留原有信息
    if (sessionStorage.getItem('stateStore')) {
      const state = this.$store.state
      const json = sessionStorage.getItem('stateStore')
      const data = JSON.parse(json)
      data.user.routerMenus = JSON.parse(data.user.routerMenus)
      data.user.rolesName = JSON.parse(data.user.rolesName)
      data.user.permissionMap = JSON.parse(data.user.permissionMap)
      this.$store.replaceState(Object.assign({}, state, data))
    }
    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    const that = this
    window.addEventListener('beforeunload', () => {
      const state = that.$store.state
      state.user.routerMenus = JSON.stringify(state.user.routerMenus)
      state.user.rolesName = JSON.stringify(state.user.rolesName)
      state.user.permissionMap = JSON.stringify(state.user.permissionMap)
      console.log('beforeunload: ', state)
      sessionStorage.setItem('stateStore', JSON.stringify(state))
    })
  }
}
</script>

const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  account: state => state.user.account,
  contact: state => state.user.contact,
  userType: state => state.user.userType,
  rolesName: state => state.user.rolesName,
  routerMenus: state => state.user.routerMenus,
  addRoutes: state => state.permission.addRoutes,
  routes: state => state.permission.routes,
  permission_routes: state => state.permission.routes,
  id: state => state.user.id
}
export default getters

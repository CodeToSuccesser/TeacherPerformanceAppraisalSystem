const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  portrait: state => state.user.portrait,
  name: state => state.user.name,
  account: state => state.user.account,
  contact: state => state.user.contact,
  rolesName: state => state.user.rolesName,
  routerMenus: state => state.user.routerMenus,
  addRoutes: state => state.permission.addRoutes,
  routes: state => state.permission.routes,
  permission_routes: state => state.permission.routes,
  id: state => state.user.id
}
export default getters

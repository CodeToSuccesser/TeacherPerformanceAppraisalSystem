module.exports = {

  title: '教师绩效考核系统',

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: true, // 控制showLogo字段,fixedHeader:true ==>为true则固定头部，为false则滚动

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: true, // 控制showLogo字段,sidebarLogo: true ==>为true则显示侧边栏logo，为false则隐藏
  // sidebarLogo：徽标在侧边栏中的位置。如果要在侧边栏顶部放置一些内容，请重写此选项。

  /**
   * 密码正则
   */
  passwordReg: '(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{6,20}$',
  passwordTip: '至少6个字符，至多20个字符，至少一个数字，至少一个字母'
}

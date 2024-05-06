import request from '@/utils/request'

export default {
  /* -------------------------------------------------------------------------------------------------------------- */
  getBannerList() {
    return request({
      url: '/banner/list',
      method: 'get'
    })
  },

  saveBanner(content, url) {
    return request({
      url: `/banner/save`,
      method: 'post',
      params: {
        content: content,
        url: url
      }
    })
  },

  deleteBanner(id) {
    return request({
      url: `/banner/delete/${id}`,
      method: 'delete'
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */
  getRechargeList(accountId) {
    return request({
      url: '/recharge/list',
      method: 'get',
      params: {
        accountId: accountId
      }
    })
  },

  chargeMoney(data) {
    return request({
      url: '/recharge/add',
      method: 'post',
      params: {
        accountId: data.accountId,
        type: data.type,
        money: data.money
      }
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */
  saveGood(data) {
    return request({
      url: '/good/save',
      method: 'post',
      data: data
    })
  },

  getGoodList(goodName, type) {
    return request({
      url: '/good/list',
      method: 'get',
      params: {
        goodName: goodName,
        type: type
      }
    })
  },

  buyGood(id, accountId) {
    return request({
      url: `/good/buy/${id}/${accountId}`,
      method: 'post',
      params: {
        accountId: accountId,
        goodId: id
      }
    })
  },

  deleteGood(id) {
    return request({
      url: `/good/delete/${id}`,
      method: 'delete'
    })
  },

  changeKuCun(data) {
    return request({
      url: `/good/changeCount`,
      method: 'post',
      params: data
    })
  },

  getDetail(id) {
    return request({
      url: `/good/detail/${id}`,
      method: 'get'
    })
  },

  updateGood(form) {
    return request({
      url: `/good/update`,
      method: 'post',
      data: form
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */
  changeCount(goodId, accountId, type) {
    return request({
      url: '/car/changeCount',
      method: 'post',
      params: {
        accountId: accountId,
        goodId: goodId,
        type: type
      }
    })
  },

  addCar(data) {
    return request({
      url: '/car/add',
      method: 'post',
      params: {
        accountId: data.accountId,
        goodId: data.goodId,
        count: data.count
      }
    })
  },

  deleteCar(accountId, goodId) {
    return request({
      url: '/car/delete',
      method: 'delete',
      params: {
        accountId: accountId,
        goodId: goodId
      }
    })
  },

  getCarList(accountId) {
    return request({
      url: '/car/list',
      method: 'get',
      params: {
        accountId: accountId
      }
    })
  },

  calc(accountId) {
    return request({
      url: '/car/calc',
      method: 'post',
      params: {
        accountId: accountId
      }
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */

  getCommentList(goodId) {
    return request({
      url: '/comment/list',
      method: 'get',
      params: {
        goodId: goodId
      }
    })
  },

  getCommentListByAccountId(accountId) {
    return request({
      url: '/comment/getList',
      method: 'get',
      params: {
        accountId: accountId
      }
    })
  },

  saveComment(accountId, orderId, goodId, content) {
    return request({
      url: '/comment/save',
      method: 'post',
      params: {
        accountId: accountId,
        orderId: orderId,
        goodId: goodId,
        content: content
      }
    })
  },

  deleteComment(id) {
    return request({
      url: `/comment/delete/${id}`,
      method: 'delete'
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */

  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      params: {
        accountId: data.accountId,
        password: data.password
      }
    })
  },

  logout() {
    return request({
      url: '/user/logout',
      method: 'post'
    })
  },

  register(data) {
    return request({
      url: '/user/save',
      method: 'post',
      params: {
        accountId: data.accountId,
        password: data.password,
        email: data.email
      }
    })
  },

  findBack(accountId, password, code) {
    return request({
      url: '/user/findBack',
      method: 'post',
      params: {
        accountId: accountId,
        password: password,
        code: code
      }
    })
  },

  getLoginUser() {
    return request({
      url: '/user/getLoginUser',
      method: 'get'
    })
  },

  updateUser(form) {
    return request({
      url: '/user/update',
      method: 'post',
      data: form
    })
  },

  updateUserRole(form) {
    return request({
      url: '/user/updateRole',
      method: 'post',
      data: form
    })
  },

  getUserById(id) {
    return request({
      url: `/user/detail/${id}`,
      method: 'get'
    })
  },

  deleteUser(id) {
    return request({
      url: `/user/delete/${id}`,
      method: 'delete'
    })
  },

  getUserList() {
    return request({
      url: '/user/list',
      method: 'get'
    })
  },

  sendCode(accountId) {
    return request({
      url: '/user/sendEmail',
      method: 'get',
      params: {
        accountId: accountId
      }
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */

  getOrderList(accountId) {
    return request({
      url: '/order/list',
      method: 'get',
      params: {
        accountId: accountId
      }
    })
  },

  getOrderDetail(orderId) {
    return request({
      url: '/order/detail',
      method: 'get',
      params: {
        orderId: orderId
      }
    })
  },

  finish(id) {
    return request({
      url: `/order/finish/${id}`,
      method: 'post',
      params: {}
    })
  },

  cancel(orderId, accountId) {
    return request({
      url: `/order/cancel`,
      method: 'post',
      params: {
        orderId: orderId,
        accountId: accountId
      }
    })
  },

  total() {
    return request({
      url: `/order/total`,
      method: 'get'
    })
  }
}

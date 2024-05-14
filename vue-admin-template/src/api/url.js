import request from '@/utils/request'

export default {
  /* -------------------------------------------------------------------------------------------------------------- */
  getBannerList() {
    return request({
      url: '/banner/list',
      method: 'post'
    })
  },

  saveSupplier(form) {
    return request({
      url: `/supplier/saveOrUpdate`,
      method: 'post',
      data: form
    })
  },

  changeStatus(id, status) {
    return request({
      url: `/caigou/changeStatus`,
      method: 'post',
      params: {
        id: id,
        status: status
      }
    })
  },

  deleteBanner(id) {
    return request({
      url: `/banner/delete/${id}`,
      method: 'post'
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */
  getRechargeList(accountId) {
    return request({
      url: '/recharge/list',
      method: 'post',
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
      method: 'post',
      params: {
        goodName: goodName,
        type: type
      }
    })
  },

  getBackOrderList() {
    return request({
      url: '/order/backList',
      method: 'post'
    })
  },

  getKuCunList(goodName, type, min, max) {
    return request({
      url: '/good/kucunList',
      method: 'post',
      params: {
        goodName: goodName,
        type: type,
        min: min,
        max: max
      }
    })
  },

  getBing() {
    return request({
      url: '/good/bing',
      method: 'post'
    })
  },

  buyGood(detail, radio) {
    return request({
      url: `/good/buy`,
      method: 'post',
      params: {
        vipFlag: radio,
        goodId: detail.id,
        userName: detail.userName,
        count: detail.count,
        phone: detail.phone,
        address: detail.address
      }
    })
  },

  deleteGood(id) {
    return request({
      url: `/good/delete/${id}`,
      method: 'post'
    })
  },

  deleteStock(id) {
    return request({
      url: `/good/deleteKuCun/${id}`,
      method: 'post'
    })
  },

  changeKuCun(goodId, type, changeCount) {
    return request({
      url: `/ruchu/change`,
      method: 'post',
      params: {
        goodId: goodId,
        type: type,
        count: changeCount
      }
    })
  },

  getKuCunById(goodId) {
    return request({
      url: `/ruchu/getById`,
      method: 'post',
      params: {
        goodId: goodId
      }
    })
  },

  getDetail(id) {
    return request({
      url: `/good/detail/${id}`,
      method: 'post'
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
      method: 'post',
      params: {
        accountId: accountId,
        goodId: goodId
      }
    })
  },

  getCarList(accountId) {
    return request({
      url: '/car/list',
      method: 'post',
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

  getCaiGouList(name, status) {
    return request({
      url: '/caigou/list',
      method: 'post',
      params: {
        supplierName: name,
        status: status
      }
    })
  },

  caigouGood(obj) {
    return request({
      url: '/caigou/save',
      method: 'post',
      data: obj
    })
  },

  getSupplierList(name) {
    return request({
      url: '/supplier/list',
      method: 'post',
      params: {
        name: name
      }
    })
  },

  getSupplierGoodList(id) {
    return request({
      url: '/supplierGood/list',
      method: 'post',
      params: {
        supplierId: id
      }
    })
  },

  getCommentListByAccountId(accountId) {
    return request({
      url: '/comment/getList',
      method: 'post',
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
      method: 'post'
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */

  login(data, role) {
    return request({
      url: '/user/login',
      method: 'post',
      params: {
        accountId: data.accountId,
        password: data.password,
        role: role
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
        phone: data.phone,
        address: data.address,
        role: data.role
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
      method: 'post'
    })
  },

  updateSupplier(form) {
    return request({
      url: '/supplier/saveOrUpdate',
      method: 'post',
      data: form
    })
  },

  getUserById(id) {
    return request({
      url: `/user/detail/${id}`,
      method: 'post'
    })
  },

  deleteUser(id) {
    return request({
      url: `/user/delete/${id}`,
      method: 'post'
    })
  },

  deleteSupplier(id) {
    return request({
      url: `/supplier/delete/${id}`,
      method: 'post'
    })
  },

  deleteVip(id) {
    return request({
      url: `/vip/delete`,
      method: 'post',
      params: {
        id: id
      }
    })
  },

  getUserList(accountId, role) {
    return request({
      url: '/user/list',
      method: 'post',
      params: {
        accountId: accountId,
        role: role
      }
    })
  },

  getVipList(userName, level) {
    return request({
      url: '/vip/list',
      method: 'post',
      params: {
        userName: userName,
        level: level
      }
    })
  },

  saveOrUpdateVip(form) {
    return request({
      url: '/vip/saveOrUpdate',
      method: 'post',
      data: form
    })
  },

  updateUser(form) {
    return request({
      url: '/user/update',
      method: 'post',
      data: form
    })
  },

  sendCode(accountId) {
    return request({
      url: '/user/sendEmail',
      method: 'post',
      params: {
        accountId: accountId
      }
    })
  },

  /* -------------------------------------------------------------------------------------------------------------- */

  getOrderList(userName, orderId) {
    return request({
      url: '/order/list',
      method: 'post',
      params: {
        orderId: orderId,
        userName: userName
      }
    })
  },

  getOrderDetail(orderId) {
    return request({
      url: '/order/detail',
      method: 'post',
      params: {
        orderId: orderId
      }
    })
  },

  finish(id) {
    return request({
      url: `/order/finish`,
      method: 'post',
      params: {
        id: id
      }
    })
  },

  back(id) {
    return request({
      url: `/order/back`,
      method: 'post',
      params: {
        id: id
      }
    })
  },

  deleteOrder(id) {
    return request({
      url: `/order/delete`,
      method: 'post',
      params: {
        id: id
      }
    })
  },

  total() {
    return request({
      url: `/order/total`,
      method: 'post'
    })
  }
}

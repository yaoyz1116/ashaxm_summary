const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('/') 
}

const formatDate7 = date => {
  var nowDay = new Date()
  var aimDay = new Date()
  aimDay.setDate(nowDay.getDate() + 7)

  const year = aimDay.getFullYear()
  const month = aimDay.getMonth() + 1
  const day = aimDay.getDate()

  return [year, month, day].map(formatNumber).join('/') 
}


const formatWeek = date => {
  const week = date.getDay()
  return week
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}


module.exports = {
  formatTime: formatTime,
  formatDate : formatDate,
  formatWeek : formatWeek,
  formatDate7 : formatDate7
}
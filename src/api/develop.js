import request from '@/util/request';

export function pageLog(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/log/page',
    method: 'post',
    data
  });
}

export function deleteLog(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/log/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除日志'
  });
}

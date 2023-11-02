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

export function pageMqMsg(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/mq_msg/page',
    method: 'post',
    data
  });
}

export function listMqMsgByIdList(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/mq_msg/list_by_id_list',
    method: 'post',
    data
  });
}

export function updateMqMsg(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/mq_msg/update',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新MQ消息'
  });
}

export function pageFile(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/file/record/page',
    method: 'post',
    data
  });
}

export function deleteFileRecord(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/file/record/delete',
    method: 'post',
    data
  });
}

export function deleteFileStorage(serviceUrl, data) {
  return request({
    url: serviceUrl + '/plugin/file/storage/delete_by_id',
    method: 'post',
    data
  });
}

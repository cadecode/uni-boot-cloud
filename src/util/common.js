/**
 * 通用工具
 */
import settings from '@/settings';

const title = settings.title || 'Vue Admin Template';

/**
 * 从settings获取页面标题
 * @param {string} pageTitle
 * @returns {string} 标题
 */
function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`;
  }
  return `${title}`;
}

/**
 * url查询参数转对象
 * @param {string} url 链接
 * @returns {Object} 查询参数所转的对象
 */
function queryToObject(url) {
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ');
  if (!search) {
    return {};
  }
  const obj = {};
  const searchArr = search.split('&');
  searchArr.forEach(v => {
    const index = v.indexOf('=');
    if (index !== -1) {
      const name = v.substring(0, index);
      obj[name] = v.substring(index + 1, v.length);
    }
  });
  return obj;
}

/**
 * 对象转url查询参数
 * @param obj 支持 {a:1, b:[2,3,4]} 格式的对象，不支持嵌套对象
 * @return {string} 对象属性转查询参数
 */
function objectToQuery(obj) {
  return Object.keys(obj).reduce((p, n) => {
    if (Array.isArray(obj[n])) {
      obj[n].forEach(o => p.append(n, o));
      return p;
    }
    p.append(n, obj[n]);
    return p;
  }, new URLSearchParams()).toString();
}

/**
 * 转换时间到字符串
 * @param {(Object|string|number)} time Date 对象 | 时间字符串 |时间戳
 * @param {string} cFormat 格式
 * @returns {string | null} 时间字符串
 */
function parseTime(time, cFormat) {
  if (arguments.length === 0 || !time) {
    return null;
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
  let date;
  if (typeof time === 'object') {
    date = time;
  } else {
    if ((typeof time === 'string')) {
      if ((/^[0-9]+$/.test(time))) {
        // support "1548221490638"
        time = parseInt(time);
      } else {
        // support safari
        // docs:https://stackoverflow.com/questions/4310953/invalid-date-in-safari
        time = time.replace(new RegExp(/-/gm), '/');
      }
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000;
    }
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  };
  return format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key];
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value];
    }
    return value.toString().padStart(2, '0');
  });
}

/**
 * 格式化时间为描述
 * @param {number} time 时间戳
 * @param {string} option parseTime 用到的时间格式 cFormat
 * @returns {string} 时间描述
 */
function formatTime(time, option) {
  if (('' + time).length === 10) {
    time = parseInt(time) * 1000;
  } else {
    time = +time;
  }
  const d = new Date(time);
  const now = Date.now();
  const diff = (now - d) / 1000;
  if (diff < 30) {
    return '刚刚';
  } else if (diff < 3600) {
    // 一小时内
    return Math.ceil(diff / 60) + '分钟前';
  } else if (diff < 3600 * 24) {
    // 一天内
    return Math.ceil(diff / 3600) + '小时前';
  } else if (diff < 3600 * 24 * 2) {
    return '1天前';
  }
  if (option) {
    return parseTime(time, option);
  } else {
    return (
      d.getMonth() + 1 + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
    );
  }
}

/**
 * 是否是外部链接
 * @param {string} path 链接
 * @return {boolean} 是否是外部链接
 */
function isExternalUrl(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

/**
 * 防抖函数：每一次的高频触发只执行一次
 * 核心是有计时器就清除，并开启新计时器
 * 立即执行的逻辑是执行后开启一个定时器保持不可执行状态
 *
 * @param {function} fn 目标函数
 * @param {number} delay 延迟时间
 * @param {boolean} triggerNow 是否立即执行
 * @return {function()} 增强后的函数
 */
function debounce(fn, delay, triggerNow) {
  let timer = 0;
  let executed = false;
  return function() {
    const _args = arguments;
    clearTimeout(timer);
    // 先关闭定时器
    if (triggerNow) {
      // 如果需要立即执行
      // 判断 executed 是否为 false，为 false 则执行
      // 开启新定时器防止短时间内再次触发
      if (!executed) {
        fn.apply(this, _args);
        executed = true;
      }
      timer = setTimeout(function() {
        executed = false;
      }, delay);
    } else {
      // 如果不需要立即执行
      // 每次触发开启新定时器即可
      timer = setTimeout(function() {
        fn.apply(this, _args);
      }, delay);
    }
  };
}

/**
 * 节流函数：高频触发时，按指定间隔执行
 * 核心是有计时器就 return
 *
 * @param {function} fn 目标函数
 * @param {number} interval 时间间隔
 * @return {function()} 增强后的函数
 */
function throttle(fn, interval) {
  let timer = 0;

  return function() {
    const _args = arguments;

    // 有定时器则返回
    if (timer) {
      return;
    }
    timer = setTimeout(() => {
      fn.apply(this, _args);
      timer = 0;
    }, interval);
  };
}

export {
  getPageTitle,
  objectToQuery,
  queryToObject,
  parseTime,
  formatTime,
  isExternalUrl,
  debounce,
  throttle
};

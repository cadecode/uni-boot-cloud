import Clipboard from 'clipboard';
import {Message} from 'element-ui';

function clipboardSuccess() {
  Message.success({message: '已复制', duration: 1500});
}

function clipboardError() {
  Message.error('复制失败');
}

/**
 * 处理剪切板事件
 * @param {string} text 文件
 * @param {Event} event 事件对象
 */
export default function handleClipboard(text, event) {
  const clipboard = new Clipboard(event.target, {
    text: () => text
  });
  clipboard.on('success', () => {
    clipboardSuccess();
    clipboard.destroy();
  });
  clipboard.on('error', () => {
    clipboardError();
    clipboard.destroy();
  });
  clipboard.onClick(event);
}

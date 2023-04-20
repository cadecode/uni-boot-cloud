<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    isExternal: {
      type: Boolean,
      default: false
    }
  },
  // 使用render渲染，避免使用一个根节点，可利用title slot
  render(h, context) {
    const {icon, title, isExternal} = context.props;
    const vNodes = [];
    if (icon) {
      // 兼容ElementUI图标和svg-icon
      if (icon.includes('el-icon')) {
        vNodes.push(<i class={[icon, 'sub-el-icon']}/>);
      } else {
        vNodes.push(<svg-icon icon-class={icon}/>);
      }
    }
    // 使用slot插入el-menu-item
    if (title) {
      vNodes.push(<span slot='title'>{(title)}</span>);
    }
    // 外部链接追加一个link图标
    if (isExternal) {
      vNodes.push(<i slot='title' class='el-icon-link'/>);
    }
    return vNodes;
  }
};
</script>

<style scoped>
.sub-el-icon {
    color: currentColor;
    width: 1em;
    height: 1em;
}

.el-icon-link {
    font-size: 12px;
}
</style>


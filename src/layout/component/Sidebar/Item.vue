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
    }
  },
  render(h, context) {
    const { icon, title } = context.props
    const vNodes = []

    if (icon) {
      if (icon.includes('el-icon')) {
        vNodes.push(<i class={[icon, 'sub-el-icon']}/>)
      } else {
        vNodes.push(<svg-icon icon-class={icon}/>)
      }
    }

    if (title) {
      vNodes.push(<span slot='title'>{(title)}</span>)
    }
    return vNodes
  }
}
</script>

<style scoped>
.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
}
</style>

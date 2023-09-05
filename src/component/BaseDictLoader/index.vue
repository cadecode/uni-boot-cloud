<template>
  <Fragment>
    <slot :dict-list="dictList" :default-dict="defaultDict" />
  </Fragment>
</template>

<script>
import {listDictByType} from '@/api/system';

export default {
  name: 'BaseDictLoader',
  props: {
    dictType: {
      type: String,
      required: true
    },
    multiple: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      dictList: [],
      defaultDict: null
    };
  },
  created() {
    listDictByType(this.dictType).then(res => {
      this.dictList = res.data;
      const defaultDictList = res.data.filter(o => o.defaultFlag);
      if (defaultDictList.length > 0) {
        if (this.multiple) {
          this.defaultDict = defaultDictList.map(o => o.value);
        } else {
          this.defaultDict = defaultDictList[0].value;
        }
      }
      this.$emit('load', this.dictList, this.defaultDict);
    });
  }
};
</script>

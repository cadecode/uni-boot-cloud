<template>
  <component :is="type" v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
import {isExternalUrl} from '@/util/common';

export default {
  name: 'AppSidebarLink',
  props: {
    to: {
      type: String,
      required: true
    }
  },
  computed: {
    isExternalUrl() {
      return isExternalUrl(this.to);
    },
    type() {
      if (this.isExternalUrl) {
        return 'a';
      }
      return 'router-link';
    }
  },
  methods: {
    linkProps(to) {
      if (this.isExternalUrl) {
        return {
          href: to,
          target: '_blank',
          rel: 'noopener'
        };
      }
      return {
        to: to
      };
    }
  }
};
</script>

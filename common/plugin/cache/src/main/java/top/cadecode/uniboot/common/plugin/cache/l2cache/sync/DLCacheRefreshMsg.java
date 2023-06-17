package top.cadecode.uniboot.common.plugin.cache.l2cache.sync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二级缓存刷新消息
 *
 * @author Cade Li
 * @date 2023/6/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DLCacheRefreshMsg {

    private String cacheName;

    private Object key;
}

package com.github.cadecode.uniboot.example.svc.bean.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试消息 DO
 *
 * @author Cade Li
 * @since 2023/8/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExampleMsgDo {

    private String testKey;

    private String testVal;
}

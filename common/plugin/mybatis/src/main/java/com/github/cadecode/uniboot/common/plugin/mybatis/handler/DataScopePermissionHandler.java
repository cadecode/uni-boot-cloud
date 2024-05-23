package com.github.cadecode.uniboot.common.plugin.mybatis.handler;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.github.cadecode.uniboot.common.plugin.mybatis.aspect.DataScopeAspect;
import com.github.cadecode.uniboot.common.plugin.mybatis.aspect.DataScopeAspect.DataScopeParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * mybatis-plus 数据权限处理器
 *
 * @author Cade Li
 * @since 2024/5/23
 */
@Slf4j
public class DataScopePermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScopeParam dataScopeParam = DataScopeAspect.currDataScope();
        if (Objects.isNull(dataScopeParam) || !dataScopeParam.isEnableFilter()) {
            return where;
        }
        DataScopeResolver dataScopeResolver = dataScopeParam.getDataScopeResolver();
        // admin 不作限制
        if (dataScopeResolver.isAdmin()) {
            return where;
        }
        InExpression inExpression = new InExpression();
        inExpression.setLeftExpression(new Column(dataScopeParam.getField()));
        List<Object> scopes = dataScopeResolver.getScopes();
        // 没配置数据权限时不作限制
        if (ObjUtil.isEmpty(scopes)) {
            return where;
        }
        ExpressionList expressionList = new ExpressionList(scopes.stream().map(o -> new StringValue(o.toString())).collect(Collectors.toList()));
        inExpression.setRightItemsList(expressionList);
        if (Objects.isNull(where)) {
            return inExpression;
        }
        return new AndExpression(where, inExpression);
    }
}

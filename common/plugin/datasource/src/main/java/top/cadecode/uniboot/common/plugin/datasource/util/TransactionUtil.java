package top.cadecode.uniboot.common.plugin.datasource.util;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务工具类
 *
 * @author Cade Li
 * @since 2023/6/13
 */
public class TransactionUtil {

    /**
     * 在当前事务完成后执行回调
     *
     * @param runnable 回调
     */
    public static void doAfterCompletion(Runnable runnable) {
        // 若没有事务
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new DoTransactionCompletion(runnable));
    }

    public static class DoTransactionCompletion implements TransactionSynchronization {

        private final Runnable runnable;

        public DoTransactionCompletion(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void afterCompletion(int status) {
            if (status != TransactionSynchronization.STATUS_COMMITTED) {
                return;
            }
            this.runnable.run();
        }
    }
}

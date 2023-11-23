package com.github.cadecode.uniboot.common.plugin.storage.util;

import cn.hutool.extra.ftp.AbstractFtp;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * FTP 连接池工具类
 *
 * @author Cade Li
 * @date 2023/11/23
 */
public class FtpPoolUtil {

    /**
     * 获取一个 FTP 连接池
     *
     * @param connectionNum 连接数量
     * @param supplier      创建 AbstractFtp 方式
     * @return FtpPool
     */
    public static FtpPool initPool(int connectionNum, Supplier<AbstractFtp> supplier) {
        return initPool(connectionNum, connectionNum, supplier);
    }

    public static FtpPool initPool(int minConnectionNum, int maxConnectionNum, Supplier<AbstractFtp> supplier) {
        FtpFactory ftpFactory = new FtpFactory(supplier);
        GenericObjectPoolConfig<AbstractFtp> config = new GenericObjectPoolConfig<>();
        // 设置连接数量
        config.setMaxTotal(minConnectionNum);
        config.setMinIdle(maxConnectionNum);
        // 设置为阻塞获取
        config.setMaxWaitMillis(-1L);
        return new FtpPool(ftpFactory, config);
    }

    /**
     * 关闭 FTP 连接池
     *
     * @param pool 连接池
     */
    public static void closePool(FtpPool pool) {
        if (Objects.nonNull(pool)) {
            pool.close();
        }
    }

    /**
     * 获取一个 FTP 连接
     *
     * @param pool 连接池
     * @return AbstractFtp
     */
    public static AbstractFtp takeFtp(FtpPool pool) {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 还回一个 FTP 连接
     *
     * @param pool 连接池
     * @param ftp  FTP 连接
     */
    public static void returnFtp(FtpPool pool, AbstractFtp ftp) {
        if (Objects.nonNull(ftp)) {
            pool.returnObject(ftp);
        }
    }

    /**
     * 获取一个 FTP 连接并消费
     *
     * @param pool     连接池
     * @param consumer FTP 消费者
     */
    public static void useFtp(FtpPool pool, Consumer<AbstractFtp> consumer) {
        AbstractFtp ftp = null;
        try {
            ftp = takeFtp(pool);
            consumer.accept(ftp);
        } finally {
            returnFtp(pool, ftp);
        }
    }

    /**
     * FTP 工厂
     */
    public static class FtpFactory extends BasePooledObjectFactory<AbstractFtp> {

        private final Supplier<AbstractFtp> supplier;

        public FtpFactory(Supplier<AbstractFtp> supplier) {
            this.supplier = supplier;
        }

        @Override
        public AbstractFtp create() {
            return supplier.get();
        }

        @Override
        public PooledObject<AbstractFtp> wrap(AbstractFtp ftp) {
            return new DefaultPooledObject<>(ftp);
        }

        @Override
        public void destroyObject(PooledObject<AbstractFtp> p) throws Exception {
            // 关闭连接
            p.getObject().close();
            super.destroyObject(p);
        }

        @Override
        public boolean validateObject(PooledObject<AbstractFtp> p) {
            // 测试连接状态
            try {
                p.getObject().pwd();
            } catch (Exception e) {
                return false;
            }
            return super.validateObject(p);
        }
    }

    /**
     * FTP Pool
     */
    public static class FtpPool extends GenericObjectPool<AbstractFtp> {

        public FtpPool(PooledObjectFactory<AbstractFtp> factory, GenericObjectPoolConfig<AbstractFtp> config) {
            super(factory, config);
        }
    }
}

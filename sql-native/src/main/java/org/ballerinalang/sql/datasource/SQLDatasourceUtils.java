/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.sql.datasource;

import io.ballerina.runtime.api.TypeTags;
import io.ballerina.runtime.api.types.Type;
import io.ballerina.runtime.api.utils.TypeUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.transactions.BallerinaTransactionContext;
import io.ballerina.runtime.transactions.TransactionLocalContext;
import io.ballerina.runtime.transactions.TransactionResourceManager;
import org.ballerinalang.sql.Constants;
import org.ballerinalang.sql.transaction.SQLTransactionContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

/**
 * Class contains utility methods for SQL datasource operations.
 *
 * @since 1.2.0
 */
public class SQLDatasourceUtils {

    private static final String POOL_MAP_KEY = UUID.randomUUID().toString();

    static Map<PoolKey, SQLDatasource> retrieveDatasourceContainer(
            BMap<BString, Object> poolOptions) {
        return (ConcurrentHashMap<PoolKey, SQLDatasource>) poolOptions.getNativeData(POOL_MAP_KEY);
    }

    public static synchronized Map<PoolKey, SQLDatasource> putDatasourceContainer(
            BMap<BString, Object> poolOptions,
            ConcurrentHashMap<PoolKey, SQLDatasource> datasourceMap) {
        Map<PoolKey, SQLDatasource> existingDataSourceMap =
                (Map<PoolKey, SQLDatasource>) poolOptions.getNativeData(POOL_MAP_KEY);
        if (existingDataSourceMap != null) {
            return existingDataSourceMap;
        }
        poolOptions.addNativeData(POOL_MAP_KEY, datasourceMap);
        return datasourceMap;
    }

    static boolean isSupportedDbOptionType(Object value) {
        boolean supported = false;
        if (value != null) {
            if (value instanceof Properties) {
                return true;
            }
            Type type = TypeUtils.getType(value);
            int typeTag = type.getTag();
            supported = (typeTag == TypeTags.STRING_TAG || typeTag == TypeTags.INT_TAG || typeTag == TypeTags.FLOAT_TAG
                    || typeTag == TypeTags.BOOLEAN_TAG || typeTag == TypeTags.DECIMAL_TAG
                    || typeTag == TypeTags.BYTE_TAG);

        }
        return supported;
    }

    public static Connection getConnection(TransactionResourceManager trxResourceManager, BObject client,
                                           SQLDatasource datasource)
            throws SQLException {
        Connection conn;
        try {
            boolean isInTransaction = trxResourceManager.isInTransaction();
            if (!isInTransaction) {
                conn = datasource.getConnection();
                return conn;
            } else {
                //This is when there is an infected transaction block. But this is not participated to the transaction
                //since the action call is outside of the transaction block.
                if (!trxResourceManager.getCurrentTransactionContext().hasTransactionBlock()) {
                    conn = datasource.getConnection();
                    return conn;
                }
            }
            String connectorId = (String) client.getNativeData(Constants.SQL_CONNECTOR_TRANSACTION_ID);
            boolean isXAConnection = datasource.isXADataSource();
            TransactionLocalContext transactionLocalContext = trxResourceManager.getCurrentTransactionContext();
            String globalTxId = transactionLocalContext.getGlobalTransactionId();
            String currentTxBlockId = transactionLocalContext.getCurrentTransactionBlockId();
            BallerinaTransactionContext txContext = transactionLocalContext.getTransactionContext(connectorId);
            if (txContext == null) {
                if (isXAConnection) {
                    XAConnection xaConn = datasource.getXAConnection();
                    XAResource xaResource = xaConn.getXAResource();
                    TransactionResourceManager.getInstance()
                            .beginXATransaction(globalTxId, currentTxBlockId, xaResource);
                    conn = xaConn.getConnection();
                    txContext = new SQLTransactionContext(conn, xaResource);
                } else {
                    conn = datasource.getConnection();
                    conn.setAutoCommit(false);
                    txContext = new SQLTransactionContext(conn);
                }
                transactionLocalContext.registerTransactionContext(connectorId, txContext);
                TransactionResourceManager.getInstance().register(globalTxId, currentTxBlockId, txContext);
            } else {
                conn = ((SQLTransactionContext) txContext).getConnection();
            }
        } catch (SQLException e) {
            throw new SQLException("error while getting the connection for " + Constants.CONNECTOR_NAME + ". "
                    + e.getMessage(), e.getSQLState(), e.getErrorCode());
        }
        return conn;
    }

}

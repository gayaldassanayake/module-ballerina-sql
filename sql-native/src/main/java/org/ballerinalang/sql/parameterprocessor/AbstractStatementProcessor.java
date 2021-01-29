package org.ballerinalang.sql.parameterprocessor;

import io.ballerina.runtime.api.types.Field;
import io.ballerina.runtime.api.values.BObject;
import org.ballerinalang.sql.exception.ApplicationError;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


abstract class AbstractStatementParameterProcessor {

    protected abstract int getCustomOutParameter(BObject typedValue) throws ApplicationError;
    protected abstract int getCustomSQLType(BObject typedValue) throws ApplicationError;
    protected abstract void setCustomSqlTypedParam(Connection connection, PreparedStatement preparedStatement,
                            int index, BObject typedValue)
            throws SQLException, ApplicationError, IOException;
    protected abstract Object[] getCustomArrayData(Object value) throws ApplicationError;
    protected abstract Object[] getCustomStructData(Object value, Connection conn) 
            throws SQLException, ApplicationError;

    protected abstract void setVarchar(int index, String value, PreparedStatement preparedStatement)
            throws SQLException;
    protected abstract void setText(int index, String value, PreparedStatement preparedStatement) throws SQLException;
    protected abstract void setChar(int index, String value, PreparedStatement preparedStatement) throws SQLException;
    protected abstract void setNChar(int index, String value, PreparedStatement preparedStatement) throws SQLException;
    protected abstract void setNVarchar(int index, String value, PreparedStatement preparedStatement)
            throws SQLException;
    protected abstract void setBit(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setBoolean(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setInteger(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setBigInt(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setSmallInt(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setFloat(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setReal(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setDouble(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setNumeric(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setDecimal(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setBinary(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError, IOException;
    protected abstract void setVarBinary(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError, IOException;
    protected abstract void setBlob(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError, IOException;
    protected abstract void setClob(int index, Object value, PreparedStatement preparedStatement, String sqlType,
            Connection connection)
            throws SQLException, ApplicationError;
    protected abstract void setNClob(int index, Object value, PreparedStatement preparedStatement, String sqlType,
            Connection connection)
            throws SQLException, ApplicationError;
    protected abstract void setRow(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setStruct(int index, Object value, PreparedStatement preparedStatement,
            Connection connection)
            throws SQLException, ApplicationError;
    protected abstract void setRef(int index, Object value, PreparedStatement preparedStatement, Connection connection)
            throws SQLException, ApplicationError;
    protected abstract void setArray(int index, Object value, PreparedStatement preparedStatement,
            Connection connection)
            throws SQLException, ApplicationError;
    protected abstract void setDateTime(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setTimestamp(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setDate(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract void setTime(int index, Object value, PreparedStatement preparedStatement, String sqlType)
            throws SQLException, ApplicationError;
    protected abstract Object[] getIntArrayData(Object value) throws ApplicationError;
    protected abstract Object[] getFloatArrayData(Object value) throws ApplicationError;
    protected abstract Object[] getDecimalArrayData(Object value) throws ApplicationError;
    protected abstract Object[] getStringArrayData(Object value) throws ApplicationError;
    protected abstract Object[] getBooleanArrayData(Object value) throws ApplicationError;
    protected abstract Object[] getNestedArrayData(Object value) throws ApplicationError;

    protected abstract void getRecordStructData(Object bValue, Object[] structData, Connection conn, int i)
            throws SQLException, ApplicationError;
    protected abstract void getArrayStructData(Field field, Object bValue, Object[] structData, int i,
            String structuredSQLType)
            throws SQLException, ApplicationError;

}

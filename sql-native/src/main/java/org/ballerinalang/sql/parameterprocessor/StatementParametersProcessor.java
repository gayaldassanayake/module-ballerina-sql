import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

class StatementParametersProcessor extends AbstractStatementParametersProcessor{

    @Override
    protected int getCustomOutParameter(BObject typedValue) throws ApplicationError {
        String sqlType = typedValue.getType().getName();
        throw new ApplicationError("Unsupported OutParameter type: " + sqlType);
    }

    @Override
    protected int getCustomSQLType(BObject typedValue) throws ApplicationError {
        String sqlType = typedValue.getType().getName();
        throw new ApplicationError("Unsupported SQL type: " + sqlType);
    }

    @Override
    protected void setCustomSqlTypedParam(Connection connection, PreparedStatement preparedStatement, int index,
                                          BObject typedValue)
            throws SQLException, ApplicationError, IOException {
        String sqlType = typedValue.getType().getName();
        throw new ApplicationError("Unsupported SQL type: " + sqlType);
    }

    @Override
    protected Object[] getCustomArrayData(Object value) throws ApplicationError {
//        throw throwInvalidParameterError(value, Constants.SqlTypes.ARRAY);
        return null;
    }

    @Override
    protected Object[] getCustomStructData(Object value, Connection conn) throws SQLException, ApplicationError {
        Type type = TypeUtils.getType(value);
        String structuredSQLType = type.getName().toUpperCase(Locale.getDefault());
        throw new ApplicationError("unsupported data type of " + structuredSQLType
                + " specified for struct parameter");
    }

    @Override
    protected void setVarchar(int index, String value, PreparedStatement preparedStatement) throws SQLException {
        if (value == null) {
            preparedStatement.setString(index, null);
        } else {
            preparedStatement.setString(index, value.toString());
        }
    }

    @Override
    protected void setText(int index, String value, PreparedStatement preparedStatement) throws SQLException {
        if (value == null) {
            preparedStatement.setString(index, null);
        } else {
            preparedStatement.setString(index, value.toString());
        }
    }

    @Override
    protected void setChar(int index, String value, PreparedStatement preparedStatement) throws SQLException {
        if (value == null) {
            preparedStatement.setString(index, null);
        } else {
            preparedStatement.setString(index, value.toString());
        }
    }

    @Override
    protected void setNChar(int index, String value, PreparedStatement preparedStatement) throws SQLException {
        if (value == null) {
            preparedStatement.setNString(index, null);
        } else {
            preparedStatement.setNString(index, value.toString());
        }
    }

    @Override
    protected void setNVarchar(int index, String value, PreparedStatement preparedStatement) throws SQLException {
        if (value == null) {
            preparedStatement.setNString(index, null);
        } else {
            preparedStatement.setNString(index, value.toString());
        }
    }

    @Override
    protected void setBit(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BOOLEAN);
        } else if (value instanceof BString) {
            preparedStatement.setBoolean(index, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Integer || value instanceof Long) {
            long lVal = ((Number) value).longValue();
            if (lVal == 1 || lVal == 0) {
                preparedStatement.setBoolean(index, lVal == 1);
            } else {
                throw new ApplicationError("Only 1 or 0 can be passed for " + sqlType
                        + " SQL Type, but found :" + lVal);
            }
        } else if (value instanceof Boolean) {
            preparedStatement.setBoolean(index, (Boolean) value);
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setBoolean(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BOOLEAN);
        } else if (value instanceof BString) {
            preparedStatement.setBoolean(index, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Integer || value instanceof Long) {
            long lVal = ((Number) value).longValue();
            if (lVal == 1 || lVal == 0) {
                preparedStatement.setBoolean(index, lVal == 1);
            } else {
                throw new ApplicationError("Only 1 or 0 can be passed for " + sqlType
                        + " SQL Type, but found :" + lVal);
            }
        } else if (value instanceof Boolean) {
            preparedStatement.setBoolean(index, (Boolean) value);
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setInteger(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.INTEGER);
        } else if (value instanceof Integer || value instanceof Long) {
            preparedStatement.setInt(index, ((Number) value).intValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setBigInt(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BIGINT);
        } else if (value instanceof Integer || value instanceof Long) {
            preparedStatement.setLong(index, ((Number) value).longValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setSmallInt(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.SMALLINT);
        } else if (value instanceof Integer || value instanceof Long) {
            preparedStatement.setShort(index, ((Number) value).shortValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setFloat(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.FLOAT);
        } else if (value instanceof Double || value instanceof Long ||
                value instanceof Float || value instanceof Integer) {
            preparedStatement.setFloat(index, ((Number) value).floatValue());
        } else if (value instanceof BDecimal) {
            preparedStatement.setFloat(index, ((BDecimal) value).decimalValue().floatValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setReal(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.FLOAT);
        } else if (value instanceof Double || value instanceof Long ||
                value instanceof Float || value instanceof Integer) {
            preparedStatement.setFloat(index, ((Number) value).floatValue());
        } else if (value instanceof BDecimal) {
            preparedStatement.setFloat(index, ((BDecimal) value).decimalValue().floatValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setDouble(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.DOUBLE);
        } else if (value instanceof Double || value instanceof Long ||
                value instanceof Float || value instanceof Integer) {
            preparedStatement.setDouble(index, ((Number) value).doubleValue());
        } else if (value instanceof BDecimal) {
            preparedStatement.setDouble(index, ((BDecimal) value).decimalValue().doubleValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setNumeric(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.DECIMAL);
        } else if (value instanceof Double || value instanceof Long) {
            preparedStatement.setBigDecimal(index, new BigDecimal(((Number) value).doubleValue(),
                    MathContext.DECIMAL64));
        } else if (value instanceof Integer || value instanceof Float) {
            preparedStatement.setBigDecimal(index, new BigDecimal(((Number) value).doubleValue(),
                    MathContext.DECIMAL32));
        } else if (value instanceof BDecimal) {
            preparedStatement.setBigDecimal(index, ((BDecimal) value).decimalValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setDecimal(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.DECIMAL);
        } else if (value instanceof Double || value instanceof Long) {
            preparedStatement.setBigDecimal(index, new BigDecimal(((Number) value).doubleValue(),
                    MathContext.DECIMAL64));
        } else if (value instanceof Integer || value instanceof Float) {
            preparedStatement.setBigDecimal(index, new BigDecimal(((Number) value).doubleValue(),
                    MathContext.DECIMAL32));
        } else if (value instanceof BDecimal) {
            preparedStatement.setBigDecimal(index, ((BDecimal) value).decimalValue());
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setBinary(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setBytes(index, null);
        } else if (value instanceof BArray) {
            BArray arrayValue = (BArray) value;
            if (arrayValue.getElementType().getTag() == org.wso2.ballerinalang.compiler.util.TypeTags.BYTE) {
                preparedStatement.setBytes(index, arrayValue.getBytes());
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else if (value instanceof BObject) {
            BObject objectValue = (BObject) value;
            if (objectValue.getType().getName().equalsIgnoreCase(Constants.READ_BYTE_CHANNEL_STRUCT) &&
                    objectValue.getType().getPackage().toString()
                            .equalsIgnoreCase(IOConstants.IO_PACKAGE_ID.toString())) {
                Channel byteChannel = (Channel) objectValue.getNativeData(IOConstants.BYTE_CHANNEL_NAME);
                preparedStatement.setBinaryStream(index, byteChannel.getInputStream());
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setVarBinary(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setBytes(index, null);
        } else if (value instanceof BArray) {
            BArray arrayValue = (BArray) value;
            if (arrayValue.getElementType().getTag() == org.wso2.ballerinalang.compiler.util.TypeTags.BYTE) {
                preparedStatement.setBytes(index, arrayValue.getBytes());
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else if (value instanceof BObject) {
            BObject objectValue = (BObject) value;
            if (objectValue.getType().getName().equalsIgnoreCase(Constants.READ_BYTE_CHANNEL_STRUCT) &&
                    objectValue.getType().getPackage().toString()
                            .equalsIgnoreCase(IOConstants.IO_PACKAGE_ID.toString())) {
                Channel byteChannel = (Channel) objectValue.getNativeData(IOConstants.BYTE_CHANNEL_NAME);
                preparedStatement.setBinaryStream(index, byteChannel.getInputStream());
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setBlob(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setBytes(index, null);
        } else if (value instanceof BArray) {
            BArray arrayValue = (BArray) value;
            if (arrayValue.getElementType().getTag() == org.wso2.ballerinalang.compiler.util.TypeTags.BYTE) {
                preparedStatement.setBytes(index, arrayValue.getBytes());
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else if (value instanceof BObject) {
            BObject objectValue = (BObject) value;
            if (objectValue.getType().getName().equalsIgnoreCase(Constants.READ_BYTE_CHANNEL_STRUCT) &&
                    objectValue.getType().getPackage().toString()
                            .equalsIgnoreCase(IOConstants.IO_PACKAGE_ID.toString())) {
                Channel byteChannel = (Channel) objectValue.getNativeData(IOConstants.BYTE_CHANNEL_NAME);
                preparedStatement.setBinaryStream(index, byteChannel.getInputStream());
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setClob(int index, Object value, PreparedStatement preparedStatement, String sqlType, Connection connection) throws SQLException {
        Clob clob;
        if (value == null) {
            preparedStatement.setNull(index, Types.CLOB);
        } else {
            if (sqlType.equalsIgnoreCase(Constants.SqlTypes.NCLOB)) {
                clob = connection.createNClob();
            } else {
                clob = connection.createClob();
            }
            if (value instanceof BString) {
                clob.setString(1, value.toString());
                preparedStatement.setClob(index, clob);
            } else if (value instanceof BObject) {
                BObject objectValue = (BObject) value;
                if (objectValue.getType().getName().equalsIgnoreCase(Constants.READ_CHAR_CHANNEL_STRUCT) &&
                        objectValue.getType().getPackage().toString()
                                .equalsIgnoreCase(IOConstants.IO_PACKAGE_ID.toString())) {
                    CharacterChannel charChannel = (CharacterChannel) objectValue.getNativeData(
                            IOConstants.CHARACTER_CHANNEL_NAME);
                    preparedStatement.setCharacterStream(index, new CharacterChannelReader(charChannel));
                } else {
                    throw throwInvalidParameterError(value, sqlType);
                }
            }
        }
    }

    @Override
    protected void setNClob(int index, Object value, PreparedStatement preparedStatement, String sqlType, Connection connection) throws SQLException {
        Clob clob;
        if (value == null) {
            preparedStatement.setNull(index, Types.CLOB);
        } else {
            if (sqlType.equalsIgnoreCase(Constants.SqlTypes.NCLOB)) {
                clob = connection.createNClob();
            } else {
                clob = connection.createClob();
            }
            if (value instanceof BString) {
                clob.setString(1, value.toString());
                preparedStatement.setClob(index, clob);
            } else if (value instanceof BObject) {
                BObject objectValue = (BObject) value;
                if (objectValue.getType().getName().equalsIgnoreCase(Constants.READ_CHAR_CHANNEL_STRUCT) &&
                        objectValue.getType().getPackage().toString()
                                .equalsIgnoreCase(IOConstants.IO_PACKAGE_ID.toString())) {
                    CharacterChannel charChannel = (CharacterChannel) objectValue.getNativeData(
                            IOConstants.CHARACTER_CHANNEL_NAME);
                    preparedStatement.setCharacterStream(index, new CharacterChannelReader(charChannel));
                } else {
                    throw throwInvalidParameterError(value, sqlType);
                }
            }
        }
    }

    @Override
    protected void setRow(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setRowId(index, null);
        } else if (value instanceof BArray) {
            BArray arrayValue = (BArray) value;
            if (arrayValue.getElementType().getTag() == org.wso2.ballerinalang.compiler.util.TypeTags.BYTE) {
                RowId rowId = arrayValue::getBytes;
                preparedStatement.setRowId(index, rowId);
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
        } else {
            throw throwInvalidParameterError(value, sqlType);
        }
    }

    @Override
    protected void setStruct(int index, Object value, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        Object[] structData = getStructData(value, connection);
        Object[] dataArray = (Object[]) structData[0];
        String structuredSQLType = (String) structData[1];
        if (dataArray == null) {
            preparedStatement.setNull(index, Types.STRUCT);
        } else {
            Struct struct = connection.createStruct(structuredSQLType, dataArray);
            preparedStatement.setObject(index, struct);
        }
    }

    @Override
    protected void setRef(int index, Object value, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        Object[] structData = getStructData(value, connection);
        Object[] dataArray = (Object[]) structData[0];
        String structuredSQLType = (String) structData[1];
        if (dataArray == null) {
            preparedStatement.setNull(index, Types.STRUCT);
        } else {
            Struct struct = connection.createStruct(structuredSQLType, dataArray);
            preparedStatement.setObject(index, struct);
        }
    }

    @Override
    protected void setArray(int index, Object value, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        Object[] arrayData = getArrayData(value);
        if (arrayData[0] != null) {
            Array array = connection.createArrayOf((String) arrayData[1], (Object[]) arrayData[0]);
            preparedStatement.setArray(index, array);
        } else {
            preparedStatement.setArray(index, null);
        }
    }

    @Override
    protected void setDateTime(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setTimestamp(index, null);
        } else {
            Timestamp timestamp;
            if (value instanceof BString) {
                timestamp = Timestamp.valueOf(value.toString());
            } else if (value instanceof Long) {
                timestamp = new Timestamp((Long) value);
            } else if (value instanceof BMap) {
                BMap<BString, Object> dateTimeStruct = (BMap<BString, Object>) value;
                if (dateTimeStruct.getType().getName()
                        .equalsIgnoreCase(org.ballerinalang.stdlib.time.util.Constants.STRUCT_TYPE_TIME)) {
                    ZonedDateTime zonedDateTime = TimeUtils.getZonedDateTime(dateTimeStruct);
                    timestamp = new Timestamp(zonedDateTime.toInstant().toEpochMilli());
                } else {
                    throw throwInvalidParameterError(value, sqlType);
                }
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
            preparedStatement.setTimestamp(index, timestamp);
        }
    }

    @Override
    protected void setTimestamp(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setTimestamp(index, null);
        } else {
            Timestamp timestamp;
            if (value instanceof BString) {
                timestamp = Timestamp.valueOf(value.toString());
            } else if (value instanceof Long) {
                timestamp = new Timestamp((Long) value);
            } else if (value instanceof BMap) {
                BMap<BString, Object> dateTimeStruct = (BMap<BString, Object>) value;
                if (dateTimeStruct.getType().getName()
                        .equalsIgnoreCase(org.ballerinalang.stdlib.time.util.Constants.STRUCT_TYPE_TIME)) {
                    ZonedDateTime zonedDateTime = TimeUtils.getZonedDateTime(dateTimeStruct);
                    timestamp = new Timestamp(zonedDateTime.toInstant().toEpochMilli());
                } else {
                    throw throwInvalidParameterError(value, sqlType);
                }
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
            preparedStatement.setTimestamp(index, timestamp);
        }
    }

    @Override
    protected void setDate(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        Date date;
        if (value == null) {
            preparedStatement.setDate(index, null);
        } else {
            if (value instanceof BString) {
                date = Date.valueOf(value.toString());
            } else if (value instanceof Long) {
                date = new Date((Long) value);
            } else if (value instanceof BMap) {
                BMap<BString, Object> dateTimeStruct = (BMap<BString, Object>) value;
                if (dateTimeStruct.getType().getName()
                        .equalsIgnoreCase(org.ballerinalang.stdlib.time.util.Constants.STRUCT_TYPE_TIME)) {
                    ZonedDateTime zonedDateTime = TimeUtils.getZonedDateTime(dateTimeStruct);
                    date = new Date(zonedDateTime.toInstant().toEpochMilli());
                } else {
                    throw throwInvalidParameterError(value, sqlType);
                }
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
            preparedStatement.setDate(index, date);
        }
    }

    @Override
    protected void setTime(int index, Object value, PreparedStatement preparedStatement, String sqlType) throws SQLException {
        if (value == null) {
            preparedStatement.setTime(index, null);
        } else {
            Time time;
            if (value instanceof BString) {
                time = Time.valueOf(value.toString());
            } else if (value instanceof Long) {
                time = new Time((Long) value);
            } else if (value instanceof BMap) {
                BMap<BString, Object> dateTimeStruct = (BMap<BString, Object>) value;
                if (dateTimeStruct.getType().getName()
                        .equalsIgnoreCase(org.ballerinalang.stdlib.time.util.Constants.STRUCT_TYPE_TIME)) {
                    ZonedDateTime zonedDateTime = TimeUtils.getZonedDateTime(dateTimeStruct);
                    time = new Time(zonedDateTime.toInstant().toEpochMilli());
                } else {
                    throw throwInvalidParameterError(value, sqlType);
                }
            } else {
                throw throwInvalidParameterError(value, sqlType);
            }
            preparedStatement.setTime(index, time);
        }
    }

    @Override
    protected Object[] getIntArrayData(Object value) throws ApplicationError {
        int arrayLength = ((BArray) value).size();
        Object[] arrayData = new Long[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            arrayData[i] = ((BArray) value).getInt(i);
        }
        return new Object[]{arrayData, "BIGINT"};
    }

    @Override
    protected Object[] getFloatArrayData(Object value) throws ApplicationError {
        int arrayLength = ((BArray) value).size();
        Object[] arrayData = new Double[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            arrayData[i] = ((BArray) value).getFloat(i);
        }
        return new Object[]{arrayData, "DOUBLE"};
    }

    @Override
    protected Object[] getDecimalArrayData(Object value) throws ApplicationError {
        int arrayLength = ((BArray) value).size();
        Object[] arrayData = new BigDecimal[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            arrayData[i] = ((BDecimal) ((BArray) value).getRefValue(i)).value();
        }
        return new Object[]{arrayData, "DECIMAL"};
    }

    @Override
    protected Object[] getStringArrayData(Object value) throws ApplicationError {
        int arrayLength = ((BArray) value).size();
        Object[] arrayData = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            arrayData[i] = ((BArray) value).getBString(i).getValue();
        }
        return new Object[]{arrayData, "VARCHAR"};
    }

    @Override
    protected Object[] getBooleanArrayData(Object value) throws ApplicationError {
        int arrayLength = ((BArray) value).size();
        Object[] arrayData = new Boolean[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            arrayData[i] = ((BArray) value).getBoolean(i);
        }
        return new Object[]{arrayData, "BOOLEAN"};
    }

    @Override
    protected Object[] getNestedArrayData(Object value) throws ApplicationError {
        Type type = TypeUtils.getType(value);
        Type elementType = ((ArrayType) type).getElementType();
        Type elementTypeOfArrayElement = ((ArrayType) elementType)
                .getElementType();
        if (elementTypeOfArrayElement.getTag() == TypeTags.BYTE_TAG) {
            BArray arrayValue = (BArray) value;
            Object[] arrayData = new byte[arrayValue.size()][];
            for (int i = 0; i < arrayData.length; i++) {
                arrayData[i] = ((BArray) arrayValue.get(i)).getBytes();
            }
            return new Object[]{arrayData, "BINARY"};
        } else {
            throw throwInvalidParameterError(value, Constants.SqlTypes.ARRAY);
        }
    }

    @Override
    protected void getRecordStructData(Object bValue, Object[] structData, Connection conn, int i) throws SQLException, ApplicationError {
        Object structValue = bValue;
        Object[] internalStructData = getStructData(structValue, conn);
        Object[] dataArray = (Object[]) internalStructData[0];
        String internalStructType = (String) internalStructData[1];
        structValue = conn.createStruct(internalStructType, dataArray);
        structData[i] = structValue;
    }

    @Override
    protected void getArrayStructData(Field field, Object bValue, Object[] structData, int i, String structuredSQLType)throws SQLException, ApplicationError {
        Type elementType = ((ArrayType) field
                .getFieldType()).getElementType();
        if (elementType.getTag() == TypeTags.BYTE_TAG) {
            structData[i] = ((BArray) bValue).getBytes();
        } else {
            throw new ApplicationError("unsupported data type of " + structuredSQLType
                    + " specified for struct parameter");
        }
    }

}


//class BObject{
//    public String getName(){
//        return "1";
//    }
//    public BObject getType(){
//        return new BObject();
//    }
//}
//class ApplicationError extends Error{
//    public ApplicationError(String message){
//        super(message);
//    }
//}
//class Constants{
//    static class SqlTypes{
//        public static String ARRAY = "Array";
//    }
//}
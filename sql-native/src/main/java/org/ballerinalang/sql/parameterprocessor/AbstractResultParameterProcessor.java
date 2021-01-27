/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.sql.parameterprocessor;

import io.ballerina.runtime.api.types.Field;
import io.ballerina.runtime.api.types.StructureType;
import io.ballerina.runtime.api.types.Type;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.sql.exception.ApplicationError;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;

/**
 * This class has abstract implementation of methods required convert SQL types into ballerina types and
 * other methods that process the parameters of the result.
 */
public abstract class AbstractResultParameterProcessor {

    protected abstract BArray createAndPopulateCustomValueArray(Object firstNonNullElement, Object[] dataArray)
            throws ApplicationError;

    protected abstract BArray createAndPopulateCustomBBRefValueArray(Object firstNonNullElement, Object[] dataArray,
                                                                     Type type) throws ApplicationError;

    protected abstract void createUserDefinedTypeSubtype(Field internalField, StructureType structType)
            throws ApplicationError;

    protected abstract BArray convert(Array array, int sqlType, Type type) throws SQLException, ApplicationError;

    protected abstract BString convert(String value, int sqlType, Type type) throws ApplicationError;

    protected abstract Object convert(
    String value, int sqlType, Type type, String sqlTypeName) throws ApplicationError;

    protected abstract Object convert(byte[] value, int sqlType, Type type, String sqlTypeName) throws ApplicationError;

    protected abstract Object convert(long value, int sqlType, Type type, boolean isNull) throws ApplicationError;

    protected abstract Object convert(double value, int sqlType, Type type, boolean isNull) throws ApplicationError;

    protected abstract Object convert(BigDecimal value, int sqlType, Type type, boolean isNull) throws ApplicationError;

    protected abstract Object convert(Blob value, int sqlType, Type type) throws ApplicationError, SQLException;

    protected abstract Object convert(java.util.Date date, int sqlType, Type type) throws ApplicationError;

    protected abstract Object convert(boolean value, int sqlType, Type type, boolean isNull) throws ApplicationError;

    protected abstract Object convert(Struct value, int sqlType, Type type) throws ApplicationError;

    protected abstract Object convert(SQLXML value, int sqlType, Type type) throws ApplicationError, SQLException;

    protected abstract void populateChar(BObject parameter, int paramIndex);

    protected abstract void populateVarchar(BObject parameter, int paramIndex);

    protected abstract void populateLongVarchar(BObject parameter, int paramIndex);

    protected abstract void populateNChar(BObject parameter, int paramIndex);

    protected abstract void populateNVarchar(BObject parameter, int paramIndex);

    protected abstract void populateLongNVarchar(BObject parameter, int paramIndex);

    protected abstract void populateBinary(BObject parameter, int paramIndex);

    protected abstract void populateVarBinary(BObject parameter, int paramIndex);

    protected abstract void populateLongVarBinary(BObject parameter, int paramIndex);

    protected abstract void populateBlob(BObject parameter, int paramIndex);

    protected abstract void populateClob(BObject parameter, int paramIndex);

    protected abstract void populateNClob(BObject parameter, int paramIndex);

    protected abstract void populateDate(BObject parameter, int paramIndex);

    protected abstract void populateTime(BObject parameter, int paramIndex);

    protected abstract void populateTimeWithTimeZone(BObject parameter, int paramIndex);

    protected abstract void populateTimestamp(BObject parameter, int paramIndex);

    protected abstract void populateTimestampWithTimeZone(BObject parameter, int paramIndex);

    protected abstract void populateArray(BObject parameter, int paramIndex);

    protected abstract void populateRowID(BObject parameter, int paramIndex);

    protected abstract void populateTinyInt(BObject parameter, int paramIndex);

    protected abstract void populateSmallInt(BObject parameter, int paramIndex);

    protected abstract void populateInteger(BObject parameter, int paramIndex);

    protected abstract void populateBigInt(BObject parameter, int paramIndex);

    protected abstract void populateReal(BObject parameter, int paramIndex);

    protected abstract void populatepopulateFloat(BObject parameter, int paramIndex);

    protected abstract void populateDouble(BObject parameter, int paramIndex);

    protected abstract void populateNumeric(BObject parameter, int paramIndex);

    protected abstract void populateDecimal(BObject parameter, int paramIndex);

    protected abstract void populateBit(BObject parameter, int paramIndex);

    protected abstract void populateBoolean(BObject parameter, int paramIndex);

    protected abstract void populateRef(BObject parameter, int paramIndex);

    protected abstract void populateStruct(BObject parameter, int paramIndex);

    protected abstract void populateSQLXML(BObject parameter, int paramIndex);

}

package com.godwin.debugger.common;

/**
 * Created by Godwin on 4/26/2018 9:50 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public interface Common {
    int COMMON_PORT1 = 45569;
    int COMMON_PORT2 = 45570;
    int COMMON_PORT3 = 45571;
    int COMMON_PORT4 = 45572;
    int COMMON_PORT5 = 45573;

    int REQUEST_DB = 1001;
    int REQUEST_TABLE = 1002;
    int REQUEST_TABLE_DETAILS = 1003;
    int REQUEST_CLOSE = 1004;
    int REQUEST_APP_DETAILS = 1005;
    int REQUEST_EXECUTE_QUERY = 1006;

    String REQUEST_TYPE = "RequestType";
    String RESPONSE_TYPE = "ResponseType";

    String KEY_PKG = "keyPkg";
    String KEY_NAME = "keyName";
    String KEY_DB_NAME = "keyDbName";
    String KEY_DB_PATH = "keyDbPath";
    String KEY_TABLE_NAME = "keyTableName";
    String KEY_TABLES = "keyTables";
    String KEY_DATA = "keyData";
    String KEY_ROW = "keyRow";
    String KEY_VERSION = "keyVersion";
    String KEY_ICON = "keyIcon";
    String KEY_QUERY = "keyQuery";
    String KEY_ERR = "keyErr";
    String KEY_ERR_CODE = "keyErrCode";
    String KEY_ERR_MSG = "keyErrMsg";

    int ERR_CODE_NO_SUPPORT = 2001;
    int ERR_CODE_EXCEPTION = 2002;

    String ERR_MSG_NO_SUPPORT = "Currently this type of query not supported";
}

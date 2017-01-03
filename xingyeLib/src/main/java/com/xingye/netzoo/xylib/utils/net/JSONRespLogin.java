package com.xingye.netzoo.xylib.utils.net;

import org.json.JSONObject;

/**
 * Created by yx on 16/12/28.
 */

public class JSONRespLogin extends JSONRespBase {
    /**
     * {"ext":{"realName":"姚醒","userName":"yaoxing6","userId":"115449","email":"yaoxing1@jd.com"},
     * "code":"0","msg":"发送成功到185****5256，请查收！"}
     */
    public UserInfo ext;
    public class UserInfo {
        public String realName;
        public String userName;
        public String userId;
        public String email;
    }
}

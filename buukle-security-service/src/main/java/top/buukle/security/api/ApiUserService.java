package top.buukle.security.api;


import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.security.entity.User;
import top.buukle.security.entity.vo.UserQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description UserService 接口类
*/
public interface ApiUserService {


    CommonResponse innerLogin(User user, HttpServletRequest request, HttpServletResponse response);

    void sessionUserResource(HttpServletRequest request, User userInfo, boolean isUpdate);

    void deleteSession(String userId);

    void kickOutUser(String userId, User user, int expire);

    void refreshSession(String userId, String k, Object v, int expire);

    void refreshDDL(String userId, int expire);

    void registerInSessionContext(HttpServletRequest request, String userId, Integer expire);

    void removeFromSessionContext(String userId);

    CommonResponse countSessionContext();
}
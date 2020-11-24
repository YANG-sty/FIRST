package com.gree.config.shiro;

import com.gree.first.student.service.StudentService;
import com.gree.first.utils.SpringContextHolder;
import com.gree.first.utils.ShiroUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 登录身份过滤器
 *
 * @author yangLongFei 2020-11-21-16:57
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if (isLoginRequest(servletRequest, servletResponse)) {
            return true;
        } else {
            //获得访问的资源
            String pathWithinApplication = WebUtils.getPathWithinApplication((HttpServletRequest) servletRequest);
            //todo 判断当前用户是否有访问当前 url 的权限
            StudentService bean = SpringContextHolder.getBean(StudentService.class);
            List<String> resource = bean.getResource(pathWithinApplication);
            System.err.println(resource.get(0));

            return true;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = WebUtils.toHttp(servletRequest);
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        /**
         * 如果是 ajax 请求则不进行跳转
         */
        if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            response.setHeader("sessionstatus", "timeout");
            return false;
        } else {
            /**
             * 第一次点击页面
             */
            String referer = request.getHeader("Referer");
            if (referer == null) {
                saveRequestAndRedirectToLogin(request, response);
                return false;
            } else {
                /**
                 * 从其他页面 跳转 过来
                 */
                if (ShiroUtils.getSession().getAttribute("sessionFlag") == null) {
                    request.setAttribute("loginError", "会话超时或无权限请重新登录");
                    PrintWriter writer = response.getWriter();
                    writer.println("<html>");
                    writer.println("<script>");
                    String url = request.getContextPath() + "/toLogin";
                    writer.println("window.open('" + url + "','_top')");
                    writer.println("</script>");
                    writer.println("</html>");
                    return false;
                } else {
                    saveRequestAndRedirectToLogin(request, response);
                    return false;
                }
            }
        }
    }
}

package cn.yyn.last_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨以诺
 * by 2020-10-26 11:05
 */
@Controller
public class ExceptionController implements ErrorController {

    @RequestMapping("/error")
    public String getErrorPath(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                return "error/404";
            case 400:
                return "error/400";
            default:
                return "error/500";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

package com.pt.controller.threed;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppVersionController {

    @ResponseBody
    @RequestMapping(value = "/test" , method = {RequestMethod.POST,RequestMethod.GET})
    public void search(@Param("name") String name) throws Exception {
        System.out.println(name);
        System.out.println(123456);

    }

}

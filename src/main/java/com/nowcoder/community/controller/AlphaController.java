package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha") //browser can access this class through alpha
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/data")
    @ResponseBody //return String instead of a website page
    public String getData() {
        return alphaService.find();
    }


    @RequestMapping("/hello")
    @ResponseBody //return String instead of a website page
    public String sayHello() {
        return "hello world";
    }


    @RequestMapping("/http")

    public void http(HttpServletRequest request, HttpServletResponse response) {
        //把需要的信息封装到HttpServletRequest 和 HttpServletResponse；
        // 和 response两个class里面，通过这两个类调用需要的信息


        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        //enumeration  迭代器，功能等同于iterator，现在不怎么用了
        while(enumeration.hasMoreElements()) { // 请求行以key value pair的形式存储
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name +": " + value);
        }

        // 请求体
        System.out.println(request.getParameter("code"));


        // 获取响应数据
        response.setContentType("Text/html;charset=utf-8");// 返回的数据类型是个网页；charset表示返回的中文
        try (
                PrintWriter writer = response.getWriter();   //输出流,java7之后可以写到try的括号里
                ){
            writer.write("<h1>牛客网</h1>");                           // 网页返回的一级标题
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //get请求
    //查询所有的学生
    // /students?current=1&limit=20     分页返回，当前页和数量的限制
    @RequestMapping(path = "/students" , method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
    ) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //  student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {  //传参的过程
        System.out.println(id);
        return "a student";
    }

    //POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


    // 响应HTML数据
    //比如查询一个老师的数据

    //返回HTML就不要加ResponseBody的注解了，默认返回都是HTML

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "卡卡罗特");
        mav.addObject("age", "25");
        mav.setViewName("demo/view"); // 设置返回的模板的路径 其实是个html文件view.html，这里省略没写
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "ufl");
        model.addAttribute("age", "124");
       return "demo/view";
    }

    //响应JSON数据
    //Java对象 浏览器通过JSON 转为 JS对象，通过转为字符串的方式
    //跨语言时用JSON，所有语言都有字符串类型

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "贝吉塔");
        emp.put("age", "50");
        emp.put("salary", 100000);
        return emp;
    }

    //返回map的list，集合的形式
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Kobe");
        emp.put("age", "42");
        emp.put("salary", 24000000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "MJ");
        emp.put("age", "57");
        emp.put("salary", 23000000);
        list.add(emp);


        return list;
    }

}

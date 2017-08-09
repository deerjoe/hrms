package com.jason.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jason.domain.Employee;
import com.jason.domain.Post;
import com.jason.service.EmployeeService;
import com.jason.util.IntegrateObject;
import com.jason.util.JsonDateValueProcessor;
import com.jason.util.ResponseUtil;

/**类中的所有响应方法都被映射到 /empl 路径下
 * 
 * @author shiyanlou
 *
 */
@Controller
@RequestMapping("/empl")
public class EmployeeController {

    // 自动注入 employeeService
    @Resource
    private EmployeeService employeeService;

    /**处理查询员工请求
     * 
     * @param employee
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(Employee employee, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断查询条件是否为空，如果是，对条件做数据库模糊查询的处理
        if (employee.getId() != null && !"".equals(employee.getId().trim())) {
            map.put("id", "%" + employee.getId() + "%");
        }
        if (employee.getName() != null && !"".equals(employee.getName().trim())) {
            map.put("name", "%" + employee.getName() + "%");
        }
        if (employee.getSex() != null && !"".equals(employee.getSex().trim())) {
            map.put("sex", "%" + employee.getSex() + "%");
        }
        if (employee.getDepartment() != null) {
            if (employee.getDepartment().getName() != null
                    && !"".equals(employee.getDepartment().getName().trim())) {
                map.put("department_name", "%"
                        + employee.getDepartment().getName() + "%");
            }
        }
        if (employee.getPosition() != null) {
            if (employee.getPosition().getName() != null
                    && !"".equals(employee.getPosition().getName().trim())) {
                map.put("position_name", "%" + employee.getPosition().getName()
                        + "%");
            }
        }
        List<Post> postList = employeeService.findEmployees(map);
        Integer total = employeeService.getCount(map);
        // 处理日期使之能在 easyUI 的 datagrid 中正常显示
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(postList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理保存员工请求
     * 
     * @param dept_id
     * @param pos_id
     * @param updateFlag
     * @param employee
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(@RequestParam("dept_id") Integer dept_id,
            @RequestParam("pos_id") Integer pos_id, @RequestParam("updateFlag") String updateFlag, Employee employee,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        int resultTotal = 0;
        // 完成 Department 和 Position 在 Employee 中的关联映射
        IntegrateObject.genericAssociation(dept_id, pos_id, employee);

        JSONObject result = new JSONObject();
        // 根据 updateFlag 的值，判断保存方式，如果值为 no，则添加员工，如果值为 yes，则修改员工
        if (updateFlag.equals("no")){
            // 捕获 service 层插入时主键重复抛出的异常，如果捕获到则置 success 值为 false，返回给前端
            try {
                resultTotal = employeeService.addEmployee(employee);
                if (resultTotal > 0) {
                    result.put("success", true);
                } else {
                    result.put("success", false);
                }
            } catch (Exception e) {
                result.put("success", false);
            }

        }else if(updateFlag.equals("yes")){
            resultTotal = employeeService.updateEmployee(employee);
            if (resultTotal > 0) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理删除员工请求
     * 
     * @param ids
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids,
            HttpServletResponse response, HttpSession session) throws Exception {
        JSONObject result = new JSONObject();
        // 将要删除的部门的 id 进行处理
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            employeeService.deleteEmployee(idsStr[i]);

        }
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**springmvc 日期绑定
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

}

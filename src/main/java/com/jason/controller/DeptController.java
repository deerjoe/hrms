package com.jason.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.domain.Department;
import com.jason.service.DepartmentService;
import com.jason.util.ResponseUtil;

/**类中的所有响应方法都被映射到 /dept 路径下
 * 
 * @author shiyanlou
 *
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    // 自动注入 departmentService
    @Resource
    private DepartmentService departmentService;

    /**处理查询部门请求
     * 
     * @param department
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(Department department, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断查询条件是否为空，如果是，对条件做数据库模糊查询的处理
        if (department.getName() != null
                && !"".equals(department.getName().trim())) {
            map.put("name", "%" + department.getName() + "%");
        }
        List<Department> deptList = departmentService.findDepartments(map);
        Integer total = departmentService.getCount(map);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(deptList);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理保存部门请求
     * 
     * @param department
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Department department, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        // 如果 id 不为空，则添加部门，否则修改部门
        if (department.getId() == null)
            resultTotal = departmentService.addDepartment(department);
        else
            resultTotal = departmentService.updateDepartment(department);
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理删除部门请求
     * 
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids,
            HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        // 将要删除的部门的 id 进行处理
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            // 捕获 service 层抛出的异常，如果捕获到则置 success 值为 false，返回给前端
            try {
                departmentService.deleteDepartment(Integer.parseInt(idsStr[i]));
                result.put("success", true);
            } catch (Exception e) {
                result.put("success", false);
            }                    
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理获得部门 id 与 name 请求，用于前端 easyUI combobox 的显示
     * 
     * @param request
     * @return
     */
    @RequestMapping("/getcombobox")
    @ResponseBody
    public JSONArray getDept(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Department> deptList = departmentService.findDepartments(map);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Department dept : deptList) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", dept.getId());
            result.put("name", dept.getName());
            list.add(result);
        }
        // 返回 JSON 
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray;
    }
}

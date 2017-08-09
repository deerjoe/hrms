package com.jason.controller;

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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jason.domain.Admin;
import com.jason.domain.Post;
import com.jason.service.PostService;
import com.jason.util.DateUtil;
import com.jason.util.JsonDateValueProcessor;
import com.jason.util.ResponseUtil;

/**类中的所有响应方法都被映射到 /post 路径下
 * 
 * @author shiyanlou
 *
 */
@Controller
@RequestMapping("/post")
public class PostController {

    // 自动注入 postService
    @Resource
    private PostService postService;

    /**处理查询公告请求
     * 
     * @param post
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(Post post, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断查询条件是否为空，如果是，对条件做数据库模糊查询的处理
        if (post.getTitle() != null && !"".equals(post.getTitle().trim())) {
            map.put("title", "%" + post.getTitle() + "%");
        }
        List<Post> postList = postService.findPosts(map);
        Integer total = postService.getCount(map);

        // 处理日期使之能在 easyUI 的 datagrid 中正常显示
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());
        // 将数据以 JSON 格式返回前端
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(postList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理保存公告请求
     * 
     * @param post
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Post post, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) throws Exception {
        Admin admin = (Admin)session.getAttribute("currentAdmin");
        post.setAdmin(admin);
        post.setDate(DateUtil.getDate());
        int resultTotal = 0;
        // 如果 id 不为空，则添加公告，否则修改公告
        if (post.getId() == null)
            resultTotal = postService.addPost(post);
        else
            resultTotal = postService.updatePost(post);
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理删除公告请求
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
        // 将要删除的公告的 id 进行处理
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            postService.deletePost(Integer.parseInt(idsStr[i]));

        }
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**处理根据 id 查询公告请求
     * 
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getById")
    public String getById(@RequestParam(value = "id") Integer id,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Post post = postService.getPostById(id);
        request.setAttribute("postContent", post.getContent());
        return "postContent";
    }
}

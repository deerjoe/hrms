package com.jason.service;

import java.util.List;
import java.util.Map;

import com.jason.domain.Post;

public interface PostService {

    /** 根据条件查询公告
     * 
     * @param map
     * @return
     */
    public List<Post>findPosts(Map<String, Object> map);

    /** 根据条件查询公告数量
     * 
     * @param map
     * @return
     */
    public Integer getCount(Map<String, Object> map);

    /** 添加公告
     * 
     * @param post
     * @return
     */    
    public Integer addPost(Post post);

    /** 修改公告
     * 
     * @param post
     * @return
     */
    public Integer updatePost(Post post);

    /** 删除公告
     * 
     * @param id
     * @return
     */
    public Integer deletePost(Integer id);

    /** 根据 ID 查询公告信息
     * 
     * @param id
     * @return
     */
    public Post getPostById(Integer id);
}

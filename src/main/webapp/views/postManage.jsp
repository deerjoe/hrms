<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>postManage</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/ueditor/ueditor.config.js">

</script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js">

</script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
    var url;
    function ResetEditor() {
        UE.getEditor('myEditor', {
            initialFrameHeight : 480,
            initialFrameWidth : 660,
            enableAutoSave : false,
            elementPathEnabled : false,
            wordCount : false,

        });
    }
    /* 根据条件查询公告 */
    function searchPost() {
        $("#dg").datagrid('load', {
            "title" : $("#postTitle").val(),
        });
    }
    /* 删除公告，可以是多个 */
    function deletePost() {
        var selectedRows = $("#dg").datagrid('getSelections');
        if (selectedRows.length == 0) {
            $.messager.alert("system prompt", "Please choose a data to edit!");
            return;
        }
        var strIds = [];
        for ( var i = 0; i < selectedRows.length; i++) {
            strIds.push(selectedRows[i].id);
        }
        var ids = strIds.join(",");
        $.messager
                .confirm(
                        "Please choose a data to edit!",
                        "Do you want to delete the <font color=red>"
                                + selectedRows.length + "</font> data?",
                        function(r) {
                            if (r) {
                                $
                                        .post(
                                                "${pageContext.request.contextPath}/post/delete",
                                                {
                                                    ids : ids
                                                },
                                                function(result) {
                                                    if (result.success) {
                                                        $.messager
                                                                .alert(
                                                                        "system prompt",
                                                                        "Delete successful!");
                                                        $("#dg").datagrid(
                                                                "reload");
                                                    } else {
                                                        $.messager
                                                                .alert(
                                                                        "system prompt",
                                                                        "Delete error!");
                                                    }
                                                }, "json");
                            }
                        });

    }

    function openPostAddDialog() {
        var html = '<div id="myEditor" name="content"></div>';
        $('#editor').append(html);
        ResetEditor(editor);
        var ue = UE.getEditor('myEditor');
        ue.ready(function() {
            ue.setContent("");
        });

        $("#dlg").dialog("open").dialog("setTitle", "Add post");
        url = "${pageContext.request.contextPath}/post/save";

    }
    /* 保存公告，根据不同的 url 选择是添加还是修改 */
    function savePost() {
        $("#fm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                $.messager.alert("system prompt", "Save successful!");
                $("#dlg").dialog("close");
                $("#dg").datagrid("reload");
                resetValue();
            }
        });
    }

    function openPostModifyDialog() {
        var selectedRows = $("#dg").datagrid('getSelections');
        if (selectedRows.length != 1) {
            $.messager.alert("system prompt", "Please choose a data to edit!");
            return;
        }
        var row = selectedRows[0];
        $("#dlg").dialog("open").dialog("setTitle", "Edit post");
        $('#fm').form('load', row);
        var html = '<div id="myEditor" name="content"></div>';
        $('#editor').append(html);
        ResetEditor(editor);
        var ue = UE.getEditor('myEditor');
        ue.ready(function() {
            ue.setContent(row.content);
        });

        url = "${pageContext.request.contextPath}/post/save?id=" + row.id;
    }

    function formatHref(val, row) {
        return "<a href='${pageContext.request.contextPath}/post/getById?id="
                + row.id + "' target='_blank'>Show Content</a>";
    }

    function resetValue() {
        $("#title").val("");
        $("#container").val("");
        ResetEditor();
    }

    function closePostDialog() {
        $("#dlg").dialog("close");
        resetValue();
    }
</script>
</head>
<body style="margin: 1px;" id="ff">
    <table id="dg" title="Post Manage" class="easyui-datagrid"
        pagination="true" rownumbers="true" fit="true"
        data-options="pageSize:10"
        url="${pageContext.request.contextPath}/post/list" toolbar="#tb">
        <thead data-options="frozen:true">
            <tr>
                <th field="cb" checkbox="true" align="center"></th>
                <th field="id" width="10%" align="center" hidden="true">id</th>
                <th field="title" width="300" align="center">title</th>
                <th field="date" width="150" align="center">create_date</th>
                <th field="admin.username" width="150" align="center">announcer</th>
                <th field="content" width="150" align="center"
                    formatter="formatHref">operation</th>
            </tr>
        </thead>
    </table>
    <div id="tb">
        <div>
            <a href="javascript:openPostAddDialog()" class="easyui-linkbutton"
                iconCls="icon-add" plain="true">Add</a> <a
                href="javascript:openPostModifyDialog()" class="easyui-linkbutton"
                iconCls="icon-edit" plain="true">Modify</a> <a
                href="javascript:deletePost()" class="easyui-linkbutton"
                iconCls="icon-remove" plain="true">Delete</a>
        </div>
        <div>
            &nbsp;Title:&nbsp;<input type="text" id="postTitle" size="20"
                onkeydown="if(event.keyCode==13) searchPost()" />&nbsp; <a
                href="javascript:searchPost()" class="easyui-linkbutton"
                iconCls="icon-search" plain="true">Search</a>
        </div>
    </div>

    <div id="dlg" class="easyui-dialog"
        style="width: 850px; height: 555px; padding: 10px 20px; position: relative; z-index: 1000;"
        closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="8px">
                <tr>
                    <td>TiTle:</td>
                    <td><input type="text" id="title" name="title"
                        class="easyui-validatebox" required="true" />&nbsp;<font
                        color="red">*</font></td>
                </tr>
                <tr>
                    <td>Content:</td>
                    <td id="editor"></td>
                </tr>
            </table>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:savePost()" class="easyui-linkbutton"
            iconCls="icon-ok">Save</a> <a href="javascript:closePostDialog()"
            class="easyui-linkbutton" iconCls="icon-cancel">Close</a>
    </div>
</body>
</html>

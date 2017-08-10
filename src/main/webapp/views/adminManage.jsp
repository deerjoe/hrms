<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/jquery-easyui-1.5.2/themes/icon.css">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript">
    var url;
    /* 根据条件查询管理员 */
    function searchAdmin() {
        $("#dg").datagrid('load', {
            "username" : $("#s_username").val()
        });
    }
    /* 删除管理员，可以是多个 */
    function deleteAdmin() {
        var selectedRows = $("#dg").datagrid('getSelections');
        if (selectedRows.length == 0) {
            $.messager.alert("system prompt",
                    "Please choose the data to delete!");
            return;
        }
        var strIds = [];
        for ( var i = 0; i < selectedRows.length; i++) {
            strIds.push(selectedRows[i].id);
        }
        var ids = strIds.join(",");
        $.messager
                .confirm(
                        "system prompt",
                        "Do you want to delete the <font color=red>"
                                + selectedRows.length + "</font> data?",
                        function(r) {
                            if (r) {
                                $
                                        .post(
                                                "${pageContext.request.contextPath}/admin/delete",
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
                                                                        "Can't delete superAdmin or current admin!");
                                                    }
                                                }, "json");
                            }
                        });
    }

    function openAdminAddDialog() {
        $("#dlg").dialog("open").dialog("setTitle", "Add new admin");
        url = "${pageContext.request.contextPath}/admin/save";
    }
    /* 保存管理员，根据不同的 url 选择是添加还是修改 */
    function saveAdmin() {
        $("#fm").form("submit", {
            url : url,
            onSubmit : function() {
                return $(this).form("validate");
            },
            success : function(result) {
                $.messager.alert("system prompt", "Save successful!");
                resetValue();
                $("#dlg").dialog("close");
                $("#dg").datagrid("reload");
            }
        });
    }

    function openAdminModifyDialog() {
        var selectedRows = $("#dg").datagrid('getSelections');
        if (selectedRows.length != 1) {
            $.messager.alert("system prompt", "Please choose a data to edit!");
            return;
        }
        var row = selectedRows[0];
        if (row.id == 1) {
            $.messager.alert("system prompt",
                    "Can't modity superadmin' information!");
            return;
        }
        $("#dlg").dialog("open").dialog("setTitle", "Edit admin information");
        $('#fm').form('load', row);
        $("#password").val("******");
        url = "${pageContext.request.contextPath}/admin/save?id=" + row.id;
    }

    function resetValue() {
        $("#username").val("");
        $("#password").val("");
    }

    function closeAdminDialog() {
        $("#dlg").dialog("close");
        resetValue();
    }
</script>
</head>
<body style="margin: 1px;">
    <table id="dg" title="Admin Manage" class="easyui-datagrid"
        fitColumns="true" pagination="true" rownumbers="true"
        url="${pageContext.request.contextPath}/admin/list" fit="true"
        toolbar="#tb">
        <thead>
            <tr>
                <th field="cb" checkbox="true" align="center"></th>
                <th field="id" width="50" align="center">id</th>
                <th field="username" width="80" align="center">username</th>
                <th field="role_name" width="80" align="center">role_name</th>
            </tr>
        </thead>
    </table>
    <div id="tb">
        <div>
            <a href="javascript:openAdminAddDialog()" class="easyui-linkbutton"
                iconCls="icon-add" plain="true">Add</a> <a
                href="javascript:openAdminModifyDialog()" class="easyui-linkbutton"
                iconCls="icon-edit" plain="true">Modify</a> <a
                href="javascript:deleteAdmin()" class="easyui-linkbutton"
                iconCls="icon-remove" plain="true">Delete</a>
        </div>
        <div>
            &nbsp;Username:&nbsp;<input type="text" id="s_username" size="20"
                onkeydown="if(event.keyCode==13) searchAdmin()" /> <a
                href="javascript:searchAdmin()" class="easyui-linkbutton"
                iconCls="icon-search" plain="true">Search</a>
        </div>
    </div>

    <div id="dlg" class="easyui-dialog"
        style="width: 620px; height: 250px; padding: 10px 20px" closed="true"
        buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="8px">
                <tr>
                    <td>Username:</td>
                    <td><input type="text" id="username" name="username"
                        class="easyui-validatebox" required="true" />&nbsp;<font
                        color="red">*</font></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="text" id="password" name="password"
                        class="easyui-validatebox" required="true" />&nbsp;<font
                        color="red">*</font></td>
                </tr>
            </table>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:saveAdmin()" class="easyui-linkbutton"
            iconCls="icon-ok">Save</a> <a href="javascript:closeAdminDialog()"
            class="easyui-linkbutton" iconCls="icon-cancel">Close</a>
    </div>
</body>
</html>

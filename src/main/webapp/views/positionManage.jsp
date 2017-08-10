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
    /* 根据条件查询职位 */
    function searchPosition() {
        $("#dg").datagrid('load', {
            "name" : $("#s_name").val()
        });
    }
    /* 删除职位，可以是多个 */
    function deletePosition() {
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
                                                "${pageContext.request.contextPath}/position/delete",
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
                                                                        "Delete error! The position has employees!");
                                                    }
                                                }, "json");
                            }
                        });
    }

    function openPositionAddDialog() {
        $("#dlg").dialog("open").dialog("setTitle", "Add new position");
        url = "${pageContext.request.contextPath}/position/save";
    }
    /* 保存职位，根据不同的 url 选择是添加还是修改 */
    function savePosition() {
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

    function openPositionModifyDialog() {
        var selectedRows = $("#dg").datagrid('getSelections');
        if (selectedRows.length != 1) {
            $.messager.alert("system prompt", "Please choose a data to edit!");
            return;
        }
        var row = selectedRows[0];
        $("#dlg").dialog("open")
                .dialog("setTitle", "Edit position information");
        $('#fm').form('load', row);
        url = "${pageContext.request.contextPath}/position/save?id=" + row.id;
    }

    function resetValue() {
        $("#name").val("");
        $("#description").val("");
    }

    function closePositionDialog() {
        $("#dlg").dialog("close");
        resetValue();
    }
</script>
</head>
<body style="margin: 1px;">
    <table id="dg" title="Position Manage" class="easyui-datagrid"
        fitColumns="true" pagination="true" rownumbers="true"
        url="${pageContext.request.contextPath}/position/list" fit="true"
        toolbar="#tb">
        <thead>
            <tr>
                <th field="cb" checkbox="true" align="center"></th>
                <th field="id" width="50" align="center" hidden="true">id</th>
                <th field="name" width="80" align="center">name</th>
                <th field="description" width="200" align="center">description</th>
            </tr>
        </thead>
    </table>
    <div id="tb">
        <div>
            <a href="javascript:openPositionAddDialog()"
                class="easyui-linkbutton" iconCls="icon-add" plain="true">Add</a> <a
                href="javascript:openPositionModifyDialog()"
                class="easyui-linkbutton" iconCls="icon-edit" plain="true">Modify</a>
            <a href="javascript:deletePosition()" class="easyui-linkbutton"
                iconCls="icon-remove" plain="true">Delete</a>
        </div>
        <div>
            &nbsp;Name:&nbsp;<input type="text" id="s_name" size="20"
                onkeydown="if(event.keyCode==13) searchPosition()" /> <a
                href="javascript:searchPosition()" class="easyui-linkbutton"
                iconCls="icon-search" plain="true">Search</a>
        </div>
    </div>

    <div id="dlg" class="easyui-dialog"
        style="width: 450px; height: 250px; padding: 10px 20px" closed="true"
        buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="8px">
                <tr>
                    <td>Name:</td>
                    <td><input type="text" id="name" name="name"
                        style="width: 180px" class="easyui-validatebox" required="true" />&nbsp;<font
                        color="red">*</font></td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td><textarea id="description" name="description"
                            style="width: 180px; height: 80px;" class="easyui-validatebox"
                            required="true"></textarea>&nbsp;<font color="red">*</font></td>
                </tr>
            </table>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:savePosition()" class="easyui-linkbutton"
            iconCls="icon-ok">Save</a> <a href="javascript:closePositionDialog()"
            class="easyui-linkbutton" iconCls="icon-cancel">Close</a>
    </div>
</body>
</html>

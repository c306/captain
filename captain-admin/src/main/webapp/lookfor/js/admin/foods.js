Ext.onReady(function() {
	var dataStore;
	var sm;
	var cm;
	var ids;
	var sexstore = new Ext.data.ArrayStore( {
		fields : [ 'sex' ],
		data : [ [ '男', '男' ], [ '女', '女' ] ]
	});
	/*
	 * 设置复选框
	 **/
	sm = new Ext.grid.CheckboxSelectionModel( {
		singleSelect : false
	});

	//sm = new Ext.grid.RowSelectionModel({singleSelect: false});

	var dataStore = new Ext.data.JsonStore( {
		root : 'page.datas',
		totalProperty : 'page.recordCount',
		idProperty : 'IFoodCde',
		remoteSort : true,
		fields : [ {name: 'IFoodCde', type: 'int'}, 'scode', 'name', {name: 'price', type: 'float'},
		           {name: 'memberprice', type: 'float'}, 'supplierName', {name: 'supplierId', type: 'int'} ],
		proxy : new Ext.data.HttpProxy( {
			url : ctx + '/admin/foods!ajaxList.action'
		})
	});
	dataStore.setDefaultSort('IFoodCde', 'desc');

	/*
	 * 设置列
	 */
	cm = new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(), sm, {
		header : "编号",
		width : 200,
		dataIndex : 'scode',
		renderer: renderTopic,
		sortable : true
	}, {
		header : "商品名称",
		width : 280,
		dataIndex : 'name',
		renderer: renderTopic,
		sortable : true
	}, {
		header : "市场价",
		width : 100,
		dataIndex : 'price',
		sortable : true
	}, {
		header : "会员价",
		width : 100,
		dataIndex : 'memberprice',
		sortable : true
	}, {
		header : "供应厂商",
		width : 200,
		dataIndex : 'supplierName',
		sortable : false
	} ]);
	/*
	 * 默认排序设置为可排序
	 */
	cm.defaultSortable = true;
	/*
	 * 创建GridPanel
	 */
	var DataGrid = new Ext.grid.GridPanel( {
		height : '400',
		title : '数据列表',
		store : dataStore,
		cm : cm,
		sm : sm,
		animCollapse : false,
		trackMouseOver : false,
		loadMask : {
			msg : '载入中,请稍候...'
		},
		stripeRows : true,
		/*
		 * 上方工具条
		 */
		tbar : [ {
			id : 'addDicButton',
			text : '增加',
			iconCls : 'silk-add',
			tooltip : '添加一条记录'
		}, '-', {
			id : 'deleteDicButton',
			text : '删除',
			iconCls : 'silk-delete',
			tooltip : '删除所选择的记录'
		} ],
		bbar : new Ext.PagingToolbar( {
			pageSize : 10,
			store : dataStore,
			displayInfo : true,
			displayMsg : '显示 {0} - {1} 共 {2} 条',
			emptyMsg : "没有数据显示！",
			beforePageText : "页码 ",
			afterPageText : "共 {0} 页",
			firstText : "首页",
			lastText : "末页",
			nextText : "下一页",
			prevText : "上一页",
			refreshText : "刷新"
		})
	});
	
	new Ext.Viewport({
        layout: 'border',
        items: [{
            region: 'center',
            xtype: 'panel',
            layout: 'fit',
            items: DataGrid
        }]
    });
	/*
	 * 加载grid
	 */
//	DataGrid.render();
	/*
	 * 设置分页数据
	 */
	dataStore.load( {
		params : {
			//'page.pageNo' : 1, 'page.pageSize' : 10
		}
	});


    // 添加
    var addDicWin;
    var addButton = Ext.get('addDicButton');
    addButton.on('click', addButtonClick);
    function addButtonClick() {
        if (!addDicWin) {
            addDicWin = new Ext.Window({
                        el : 'addDicWin',
                        title : '产品信息编辑',
                        layout : 'fit',
                        width : 600,
                        autoHeight: true,
                        closeAction : 'hide',
                        plain : true,
                        modal : true,
                        items : addForm
                    });
        }
        addForm.getForm().reset();
        addDicWin.show(this);
    }
    
    var categoryId = new Ext.ux.TreeCombo({
        fieldLabel : '产品类别',
        anchor:'70%',
        allowBlank : false,
        //name : 'domain.categoryId',
        hiddenName : 'domain.categoryId',
        rootVisible : true,
        root : new Ext.tree.AsyncTreeNode({
                    text : '业务列表',
                    expanded : true,
                    levelNum : 0,
                    id : '0'
                 }),
        dataUrl : '/category!children.action'
     });
 // 添加form
    var addForm = new Ext.FormPanel({
        labelAlign: 'right',
        frame: true,
        bodyStyle: 'padding:5px 5px 0 0',
        width: 600,
        autoHeight: true,
        method : 'POST',
        url : ctx + '/admin/foods!ajaxSaveOrUpdate.action',
        //defaultType: 'textfield',//此行不能加
        items: [{
            xtype:'textfield',
            fieldLabel: '产品编号',
            name: 'domain.scode',
            allowBlank : false,
            anchor:'100%'
        },categoryId,{
            xtype:'textfield',
            fieldLabel: '产品名称',
            allowBlank : false,
            name: 'domain.name',
            anchor:'100%'
        },{
            layout: 'column',
            items: [{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'numberfield',
                    fieldLabel: '市场价',
                    name: 'domain.price',
                    anchor:'100%'
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'numberfield',
                    fieldLabel: '会员价',
                    name: 'domain.memberprice',
                    //vtype:'email',
                    anchor:'100%'
                }]
            }]
        },{
            xtype:'textfield',
            name: 'domain.supplierName',
            fieldLabel:'供应商',
            anchor:'100%'
        },{
            xtype:'textfield',
            name: 'domain.madein',
            fieldLabel:'产地',
            anchor:'100%'
        },{
            xtype:'textfield',
            name: 'domain.weight',
            fieldLabel:'净重（克）',
            anchor:'100%'
        },{
            xtype:'textfield',
            name: 'domain.guige',
            fieldLabel:'规格<br/>（如：7.8g×8泡×4罐）',
            anchor:'100%'
        },{    
            xtype : 'hidden',
            name: 'domain.IFoodCde'
        }]
    });
    //addForm.render(document.body);
    
    // 验证数据并提交
    addForm.addButton({text:'提交',height:40}, function() {
//            var field=addForm.getForm().findField('isDisabled');
//            if(field.getValue()){
//              addForm.getForm().findField('intDisabled').setValue(1);
//            }else{
//              addForm.getForm().findField('intDisabled').setValue(0);
//            }
            //return false;
            if(addForm.form.isValid()){
                addForm.getForm().submit({
                            waitMsg : '保存数据',
                            success : function(from ,action) {
			                	if(action.result.success){
			                		//Ext.MessageBox.alert('提示', action.result.message);
			                		dataStore.reload();
			                        Ext.MessageBox.alert('提示', '提交成功！');
			                        addDicWin.hide();
			                	}
                            },
                            failure : function() {
                                Ext.MessageBox.alert('提示', '提交失败！');
                                addDicWin.hide();
                            }
                        });
            }
    });

    addForm.addButton({text:'关闭',height:40}, function() {
            addDicWin.hide();
    });

    //修改
    DataGrid.on('rowdblclick',function(g, rIdx, e){
    	var record = g.getStore().getAt(rIdx);
        if (record.data.IFoodCde > 0) {
            Ext.Ajax.request({
		        method : 'POST',
		        url : ctx + '/admin/foods!ajaxEdit.action',
		        params: {IFoodCde: record.data.IFoodCde},
		        success : function(response, options) {
		        	var obj = Ext.decode(response.responseText);
		            if(obj.success){
		            	if(Ext.isIE){
		                    Ext.getDom("addDicButton").click();
		                }else{
		                    addButtonClick();
		                }
		            	//addForm.getForm().setValues(obj);
		            	setDomainValues(addForm.getForm(), obj.domain);
		            }else{
		            	Ext.MessageBox.alert('提示 ', '提取数据失败！');
		            }
		        },
		        failure : function() {
		            Ext.MessageBox.alert('提示 ', '提取数据失败！');
		        }
		    });
        }
	});
    
    // 删除
    var deleteButton = Ext.get('deleteDicButton');
    deleteButton.on('click', function() {
                if (DataGrid.getSelectionModel().getSelections().length > 0)
                    Ext.MessageBox.confirm('消息', '确认要删除所选记录?', doDelProc);
                else
                    Ext.MessageBox.alert('警告', '最少需要选择一条记录!');
    });
    function doDelProc(btn) {
        if (btn == 'yes') {
            if (DataGrid.getSelectionModel().hasSelection()) {
                var records = DataGrid.getSelectionModel().getSelections();
                var curid = records[0].data["IFoodCde"];
                Ext.Ajax.request({
                            method : 'POST',
                            url : ctx + '/admin/foods!ajaxDelete.action',
                            params: {keys: curid},
                            success : function(response, options) {
                              var obj = Ext.decode(response.responseText);
                              if(obj.success == true){
                            	  Ext.MessageBox.alert('提示', '数据删除成功！');
                            	  dataStore.reload();
                              } else {
                            	  Ext.MessageBox.alert('提示', '数据删除失败！');
                              }
                            },
                            failure : function() {
                                Ext.MessageBox.alert('提示', '数据删除失败！');
                                dataStore.reload();
                            }
                        });
            }
        }
    };


    
    categoryId.treePanel.on('beforeload', function(node) {
    	//categoryId.treePanel.loader.dataUrl = '/category!children.action';
    	alert(node.id);
    });
    categoryId.treePanel.on('load', function(loader, node) {
    	categoryId.setValue('999999999999999999');
    });
    //categoryId.treePanel.expandAll();
    

function renderTopic(value, p, record){
	return String.format('<b><a href="javascript:void(0);">{0}</a></b>', value, record.id);
}

});

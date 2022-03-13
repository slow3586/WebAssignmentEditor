define([
        //DOJO
        "dojo/_base/declare",
        "dojo/_base/kernel",
        "dojo/aspect",
        "dijit/layout/ContentPane", 
        "dijit/form/Button",
        "dijit/form/Select",
        "dijit/form/TextBox",
        "dijit/Dialog",
        'dstore/Rest',
        'dstore/SimpleQuery',
        'dstore/Trackable',
        'dgrid/Grid',
        'dgrid/Selection',
        'dgrid/extensions/Pagination',
        //LOCAL
        "dojo/i18n!mydojo/nls/everything",
], function(
        //DOJO
        declare, kernel, aspect, 
        ContentPane, Button, Select, TextBox, Dialog,
        Rest, SimpleQuery, Trackable, 
        Grid, Selection, Pagination,
        //LOCAL
        i18
){
    return declare("BaseTabAll", [ContentPane], {
        title: "BaseTabAll",
        closable: true,
        filterColumn : "id",
        grid: "",
        gridData: "",
        selectedRow: "",
        filterValue: "",
        postCreate : function(){
            var self = this;
            
            this.addChild(new Button({
                label: i18.base_tab_all_add,
                onClick: function(){self.openAddTab()}
            }));
            this.addChild(new Button({
                label: i18.base_tab_all_edit,
                onClick: function(){self.openEditTab()}
            }));
            this.addChild(new Button({
                label: i18.base_tab_all_delete,
                onClick: function(){self.openDeleteDialog()}
            }));
            var grid = this.createGrid();
            this.addChild(new Button({
                label: i18.base_tab_all_update,
                onClick: function(){
                    self.grid.refresh();
                }
            }));

            var searchoptcb = new Select({
                options: self.searchColumnChoices,
                value: "id",
                onChange: function(newValue){
                    self.filterColumn = newValue;
                    self.filterAll();
                }
            });
            this.addChild(searchoptcb);
            var searchtb = new TextBox({
                label: "Search"
            });
            this.addChild(searchtb);
            
            this.addChild(new Button({
                label: i18.base_tab_all_search,
                onClick: function(){
                    self.filterValue = searchtb.get("value");
                    self.filterAll();
                }
            }));
            
            this.addChild(grid);
            kernel.global.mainTabContainer.addChild(this);
            grid.startup();
            this.assignGlobalVar();
        },
        assignGlobalVar : function(){},
        filterAll : function(){
            var filterData = {'id':""};
            if(this.filterValue === undefined || this.filterValue === ""){}
            else{
                if(this.filterColumn==="id"){
                    filterData = {'id':this.filterValue};
                }else if(this.filterColumn==="topic"){
                    filterData = {'topic':this.filterValue};
                }else if(this.filterColumn==="text"){
                    filterData = {'text':this.filterValue};
                }
            }
            this.grid.set("collection", this.gridData.filter(filterData));
        },
        openDeleteDialog : function(){
            //this._openDeleteDialog(titleText, contentText, rowId);
        },
        _openDeleteDialog : function(titleText, contentText, rowId){
            if(this.selectedRow===undefined) return;
            var self = this;
            var dialog = new Dialog({
                title: titleText,
                content: contentText
            });
            dialog.addChild(new Button({
                label: i18.base_yes,
                onClick: function(){
                    self.gridData.remove(rowId);
                    dialog.hide();
                    self.grid.refresh();
                }
            }));
            dialog.addChild(new Button({
                label: i18.base_cancel,
                onClick: function(){
                    dialog.hide();
                }
            }));

            dialog.show();
        },
        createGrid : function(){
            var self = this;
            
            var TrackableRest = declare([Rest, SimpleQuery, Trackable]);
            this.gridData = new TrackableRest({ 
                target: this.restTarget, 
                sortParam: "order_by",
                rangeStartParam: "from",
                rangeCountParam: "limit"
            });

            this.grid = new (declare([ Grid, Pagination, Selection ]))({
                collection: this.gridData,
                selectionMode: 'single',
                keepScrollPosition: 'true',
                pagingLinks: 1,
                pagingTextBox: true,
                firstLastArrows: true,
                pageSizeOptions: [10, 20, 30, 40, 50],
                columns: this.gridColumns,
                loadingMessage: i18.base_tab_all_loading_data,
                noDataMessage: i18.base_tab_all_no_data
            });
            aspect.after(this.grid, 'gotoPage', function (promise, args) {
                promise.then(function () {
                    self.onGridUpdate();
                });
                return promise;
            });
            this.grid.on('dgrid-select', function (event) {
                self.selectedRow = self.grid.row(event.rows[0]);
            });
            this.grid.on('dgrid-deselect', function (event) {
                self.selectedRow = undefined;
            });
            this.grid.on('.dgrid-row:dblclick', function (event) {
                self.openEditTab();
            });
            return this.grid;
        },
        onGridUpdate : function(){
        },
        openAddTab : function(){
        },
        openEditTab : function(){
        }
    });
});
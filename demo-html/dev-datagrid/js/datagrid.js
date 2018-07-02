/**
 * <pre>
 * 数据网格插件
 *
 *
 *
 * 数据网格分为: 1)表格;2)分页.
 * 分页里面有查询条件和分页信息.
 *
 * - 查询到的的分页数据有默认渲染方式, 也可以用户定制.
 * </pre>
 * @author Nisus Liu
 * @author Menzhanxi
 * @version 0.0.1
 * */

(function ($) {

    /**构造函数*/
    function DataGrid() {
        //定义初始的默认参数配置

        this.options = {
            /*--datagrid--*/
            //容器
            //container: $('<div></div>'),
            //筛选条件
            conditions: {}

            /*--table相关--*/
            //定义了列的别名,单元格数值格式化方式,列顺序,列过滤
            /*
             * <pre>
             * alias, formater, filter, order
             * 这里定义的别名用于页面的显示名称,
             * 这里面有的列才会渲染显示,
             * 这里的顺序就是列显示的顺序,
             * 这里的处理函数就是对改列对应的值进行进行加工的方法.
             * {
             *     name: ["别名",function(){//格式化}],
             *     .....
             * }
             * </pre>
             */
            //columnAssigner:{}

        };
    };


    /**初始方法 / $('selector').datagrid()*/
        //!TODO 每次调用要返回新对象!
    var dataGrid = $.fn.datagrid = function (options) {
            var dataGrid = new DataGrid();
            //合并配置
            //$.extend(dataGrid.options, options);
            dataGrid.setOptions(options);


            //将jq选择器对象设给dataGrid
            dataGrid.container = this;  //这里的this就是调用者--jq选择器对象
            dataGrid.pagination = new Pagination(dataGrid);
            dataGrid.tabler = new Tabler(dataGrid);

            //初始表格
            dataGrid.tableContainer = dataGrid.tabler.container = $('<table></table>');
            dataGrid.container.append(dataGrid.tableContainer);

            //初始分页条
            //定义分页条的容器
            dataGrid.pageContainer = dataGrid.pagination.container = $('<div class="page_div"></div>');
            dataGrid.container.append(dataGrid.pageContainer);      //分页条容器套进datagrid容器内部
            //pagination.init();        //有数据再显示调用方法生成分页条


            return dataGrid;        //返回DataGrid对象, 方便后面更新数据. 一个DataGrid对下对应一个数据表格
        };

    /**统一处理参数配置*/
    DataGrid.prototype.setOptions = function (options) {
        $.extend(this.options, options);
        //pagination的参数要同步(因为是基本类型)
        //if(this.pagination) this.pagination.synPageInfo(this.options);      //防止初始化时, 还没有定义pagination对象

        // if (this.options[key]) {
        //     this.options[key] = value;
        // }
        // console.warn(不支持的参数配置, key);
        return this;
    };

    DataGrid.prototype.paging = function () {
        this.pagination.init();
        return this;
    };

    /**加载数据
     * @param {String}uri 数据加载的资源路径, 必选
     * @param success ajax请求成功返回后, 对相应数据集的预处理, 如: 取出page数据(主要), 根据后台的msg给出弹窗提示.
     * */
    DataGrid.prototype.load = function (uri, conditions, pageNum, pageSize, success) {
        var _this = this;
        if (uri == null && _this.options.uri == null) {
            throw new Error("未知资源加载地址");
        }
        console.log("uri:", uri, "_this.options.uri:", _this.options.uri);
        if(uri!=null) _this.options.uri = uri;

        //同步更新查询条件参数
        if (conditions != null) _this.options.conditions = conditions;
        //增加属性, 下文reload用到
        if (pageNum != null) _this.pagination.pageInfo.pageNum = pageNum;
        if (pageSize != null) _this.pagination.pageInfo.pageSize = pageSize;
        if (success != null) _this.options.success = success;


        //加载得到的page实例要返回
        var page;
        //查询数据
        $.ajax({
            type: 'POST',
            url: _this.options.uri,
            data: {
                pageNum: _this.pagination.pageInfo.pageNum,
                pageSize: _this.pagination.pageInfo.pageSize,
                conditions: _this.options.conditions
            },
            async: false,       //改为同步
            success: function (responseResult) {
                if (!responseResult) {
                    console.warn("没有任何响应内容");
                    return;
                }
                // if (typeof responseResult != "object") {
                //     console.warn("响应数据格式有误, 需要'object'类型");
                // }

                //调用传入的结果处理函数(若有), 处理好结果扔给表格对象填充数据
                page = _this.options.success ? _this.options.success(responseResult) : responseResult;


                //渲染数据
                _this.render(page);
            }
        });

        return page;
    };



    /**渲染page数据*/
    DataGrid.prototype.render = function (page) {
        var _this = this;
        if (page.data == null) {
            console.warn("响应结果没有data");
            return;
        }

        //page.data必须为Array
        if (Object.prototype.toString.call(page.data) != "[object Array]") {
            throw new TypeError("page.data must be a `Array`");
        }


        if (page.count == null) {
            console.warn("响应结果没有count信息, 将使用当前data长度作为count")
            try {
                _this.pagination.pageInfo.count = page.data.length;
            } catch (e) {
                console.error("请确保data为数组", e);
                return;
            }
        } else {
            _this.pagination.pageInfo.count = page.count;
        }

        _this.tabler.fill(page.data);
        //更新分页条
        _this.pagination.init();

    };


    var Pagination = DataGrid.prototype.Pagination = function (dataGrid) {
        this.dataGrid = dataGrid;
        //初始化分页信息
        this.pageInfo = {
            /*--page相关--*/
            //页号
            pageNum: 1,
            //页大小
            pageSize: 10,
            //总数据条数
            count: 0
        };
        //总页数
        this.pageInfo.totalPage = function () {
            return Math.ceil(this.count / this.pageSize);             // ((this.count % this.pageSize) == 0 ? 0 : 1);
        };


        //this.container = this.options.container;
        //this.container =  $('#<div class="page_div"></div>');

        // /**将pageInfo的值更新至最新状态*/
        // this.synPageInfo = function (options) {
        //     if (options == null) {
        //         return;
        //     }
        //     //所在页页码
        //     this.pageInfo.pageNum = options.pageNum ? options.pageNum : 1;
        //
        //     //总页数
        //     this.pageInfo.totalPage = options.totalPage ? options.totalPage : 1;
        //
        //     //数据总数
        //     this.pageInfo.count = options.count ? options.count : 0;
        // }

    };


    //对Pagination的实例对象添加公共的属性和方法
    Pagination.prototype = {

        constructor: Pagination,

        init: function () {
            this.creatHtml();
        },

        handleColor: function (dom) {
            $(dom).css({
                "backgroundColor": "#b6b6b6",
                "borderColor": "#b6b6b6",
                "color": "#fff",
                "cursor": "not-allowed"
            });
        },

        //处于首页、尾页或总页数为0时禁用按钮并置灰
        changeBtnColor: function () {
            var that = this;
            if (that.pageInfo.totalPage() == 0) {
                that.handleColor(".firstPage");
                that.handleColor(".prePage");
                that.handleColor(".nextPage");
                that.handleColor(".lastPage");
            }

            if (that.pageInfo.pageNum == 1) {
                that.handleColor(".firstPage");
                that.handleColor(".prePage");
            }

            if (that.pageInfo.pageNum == that.pageInfo.totalPage()) {
                that.handleColor(".nextPage");
                that.handleColor(".lastPage");
            }
        },

        //创建页码的dom
        creatHtml: function () {
            var that = this;
            var content = "";
            var current = that.pageInfo.pageNum;
            var total = that.pageInfo.totalPage();
            var totalNum = that.pageInfo.count;
            content += "<a class='firstPage'>首页</a><a class='prePage'>上一页</a>";
            //总页数大于6时候
            if (total > 6) {
                //当前页数小于5时显示省略号
                if (current < 5) {
                    for (var i = 1; i < 6; i++) {
                        if (current == i) {
                            content += "<a class='current'>" + i + "</a>";
                        } else {
                            content += "<a>" + i + "</a>";
                        }
                    }
                    content += ". . .";
                    content += "<a>" + total + "</a>";
                } else {
                    //判断页码在末尾的时候
                    if (current < total - 3) {
                        for (var i = current - 2; i < current + 3; i++) {
                            if (current == i) {
                                content += "<a class='current'>" + i + "</a>";
                            } else {
                                content += "<a>" + i + "</a>";
                            }
                        }
                        content += ". . .";
                        content += "<a>" + total + "</a>";
                        //页码在中间部分时候
                    } else {
                        content += "<a>1</a>";
                        content += ". . .";
                        for (var i = total - 4; i < total + 1; i++) {
                            if (current == i) {
                                content += "<a class='current'>" + i + "</a>";
                            } else {
                                content += "<a>" + i + "</a>";
                            }
                        }
                    }
                }
                //页面总数小于6的时候
            } else {
                for (var i = 1; i < total + 1; i++) {
                    if (current == i) {
                        content += "<a class='current'>" + i + "</a>";
                    } else {
                        content += "<a>" + i + "</a>";
                    }
                }
            }
            content += "<a class='nextPage'>下一页</a>";
            content += "<a class='lastPage'>尾页</a>";
            content += "<span class='totalPages'> 共<span>" + total + "</span>页 </span>";
            content += "<span class='count'> 共<span>" + totalNum + "</span>条 </span>";
            // that.container.html(content);
            that.container.html(content);
            that.changeBtnColor();
            that.bindEvent();
        },

        //点击页码事件
        pageNumClick: function (pageNum) {
            //更新pageInfo
            this.pageInfo.pageNum = pageNum;

            //渲染分页元素
            this.creatHtml();
            //this.getDataCallback(num);
            //load数据
            this.dataGrid.load()


        },

        //添加页面操作事件
        bindEvent: function () {
            var that = this;
            that.container.off('click', 'a');
            that.container.on('click', 'a', function () {
                var num = $(this).html();
                var className = $(this).attr("class");

                if (className == "prePage") { //点击上一页
                    if (that.pageInfo.pageNum == 1) {
                        return false;
                    } else {
                        that.pageInfo.pageNum = +that.pageInfo.pageNum - 1;
                    }
                } else if (className == "nextPage") { //点击下一页
                    if (that.pageInfo.totalPage() == 0) {
                        return false;
                    } else if (that.pageInfo.pageNum == that.pageInfo.totalPage()) {
                        return false;
                    } else {
                        that.pageInfo.pageNum = +that.pageInfo.pageNum + 1;
                    }

                } else if (className == "firstPage") { //点击第一页
                    if (that.pageInfo.pageNum == 1) {
                        return false;
                    } else {
                        that.pageInfo.pageNum = 1;
                    }
                } else if (className == "lastPage") { //点击最后那一页
                    if (that.pageInfo.totalPage() == 0) {
                        return false;
                    } else if (that.pageInfo.pageNum == that.pageInfo.totalPage()) {
                        return false;
                    } else {
                        that.pageInfo.pageNum = that.pageInfo.totalPage();
                    }
                } else {
                    if (that.pageInfo.pageNum == num) {
                        return false;
                    } else {
                        that.pageInfo.pageNum = +num;
                    }
                }

                //页码点击事件
                that.pageNumClick(that.pageInfo.pageNum);
            });
        }
    };

    //tabler
    var Tabler = DataGrid.prototype.Tabler = function (dataGrid) {
        this.dataGrid = dataGrid;
        this.options = dataGrid.options;

        function buildDefaultColumnAssigner(data) {
            // //没有数据或者传入的不是数组, 则返回
            if (data == null || data.length === undefined || data.length < 1) {
                return null;
            }
            var aRow = data[0];

            var assigner = {};
            var colNames = Object.getOwnPropertyNames(aRow);

            for (var i = 0; i < colNames.length; i++) {
                var colName = colNames[i];
                //assigner[colName] = {};
                assigner[colName] = [];
                assigner[colName][0] = colName;  //默认别名就是自己
                assigner[colName][1] = function (cellVal) {
                    if (!cellVal) cellVal = '';       //没有值用''替换
                    return cellVal;
                }

            }

            return assigner;

        }

        /**
         * <pre>
         * alias, formater, filter, order
         * 这里定义的别名用于页面的显示名称,
         * 这里面有的列才会渲染显示,
         * 这里的顺序就是列显示的顺序,
         * 这里的处理函数就是对改列对应的值进行进行加工的方法.
         * {
         *     name: ["别名",function(){//格式化}],
         *     .....
         * }
         * </pre>
         */
        this.columnAssigner = this.options && this.options.columnAssigner ? this.options.columnAssigner : null;    //若没有指定, 则等数据来时生成默认的


        //若没有定制fill()方法则使用定义默认方法
        this.fill = this.options && this.options.fill ? this.options.fill : function (data) {
            var _this = this;
            //初始columnAssigner, 保证下文可用
            if (_this.columnAssigner == null) {
                _this.columnAssigner = buildDefaultColumnAssigner(data);
            }


            //thead
            var thead = _this.thead(data);

            //tbody
            var tbody = _this.tbody(data);

            var table = '<table>' + thead + tbody + '</table>';

            //填充
            _this.container.html(thead + tbody);

            return _this;
        };

        //thead
        /**根据数据生成thead标签*/
        this.thead = this.options && this.options.thead ? this.options.thead : function (data) {
            var _this = this;

            //没有数据或者传入的不是数组, 则返回
            if (data == null || data.length === undefined || data.length < 1) {
                return;
            }

            var ths = '';

            //使用别名作为显示的列头名
            for (var colAssKey in _this.columnAssigner) {
                var colAlias = _this.columnAssigner[colAssKey][0];
                if (colAlias == null) colAlias = colAssKey;    //没有就不用别名
                ths += '<th>' + colAlias + '</th>';
            }
            return '<table><thead><tr>' + ths + '</tr></thead></table>';

            // var aRow = data[0];
            // var colNames = Object.getOwnPropertyNames(aRow);
            // for (var i = 0; i < colNames.length; i++) {     //默认使用数据源的顺序自左到右
            //     var colName = colNames[i];
            //     var colShowName = (alias && alias[colName]) ? alias[colName] : colName;     //没有定义别名规则, 就原样显示
            //     ths += '<th name="' + colName + '">' + colShowName + '</th>';       //增加定制属性name, 后文用来定位列的值, 保证对应
            // }
            // return '<table><thead><tr>' + ths + '</tr></thead></table>';

        };

        //tbody
        /**根据数据生成tbody标签*/
        this.tbody = this.options && this.options.tbody ? this.options.tbody : function (data) {
            var _this = this;
            //没有数据或者传入的不是数组, 则返回
            if (data == null || data.length === undefined || data.length < 1) {
                return;
            }

            var trs = '';
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                var tr = '';
                //填充数据以columnAssigner基准
                for (var colAssKey in _this.columnAssigner) {
                    var cellVal = row[colAssKey];
                    if (_this.columnAssigner[colAssKey][1] != null && cellVal != null) {
                        cellVal = _this.columnAssigner[colAssKey][1](cellVal);
                    }
                    tr += '<td>' + cellVal + '</td>';
                }

                tr = '<tr>' + tr + '</tr>'
                trs += tr;
            }

            return '<tbody>' + trs + '</tbody>';
        }


    }


})(jQuery, window);











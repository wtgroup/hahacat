
/*
* 函数表达式立即执行
* invocation名称告知此函数是立即调用(不会创建全局函数变量)
* invocation是一个模块函数
* 命名空间: Tree*/
var Tree = (function invocation() {
    //构造函数
    function Tree(name,radius,age) {
        this.name=name;
        this.radius = radius;
        this.age = age;
    }
    
    //定义实例方法
    Tree.prototype.grow = function () {
        console.log("我是颗["+this.name+"]树, 正在努力的生长...");
    }
    Tree.prototype.getHeight = function () {
        console.log(measure(this.radius, this.age));
    }
    
    /*模块难免会用到很多辅助函数或变量
    * 他们不属于模块共有API, 因此应当隐藏在这个函数作用域内(当然可以用'_'前缀约定表示, 但并不能实现隐藏的目的)*/
    function measure(radius,age) {
        return radius * age;    //树高=半径*树龄
    }

    /*本模块共有API是Tree()构造函数
    * 需要将这个函数从私有命名空间中导出来
    * 以便外部可以使用它*/
    return Tree;

}())    // () 定义函数立即执行
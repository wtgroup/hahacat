var Trees = (function namespace() {
    //通用'tree'
    var AbstractTree = function (name) {
        // var _name = name || 'AbstractTree';   //真正的私有字段 外界不可见
        // this.getName = function () {
        //     return _name;
        // }

        this.name = name || 'AbstractTree';
        this.getName = function () {
            return this.name;
        }
    }

    //在这里定义多种'tree'
    var BroadLeafTree = function (name,radius,age,leafPetalNum) {
        this.name = name;
        this.radius = radius;
        this.age = age;
        this.leafPetalNum = leafPetalNum; //阔叶的页瓣个数
    }
    BroadLeafTree.prototype = new AbstractTree();

    var NeedleTree = function (name, radius, age, leafLength) {
        this.name = name;
        this.radius = radius;
        this.age = age;
        this.leafLength = leafLength; //针叶叶子长度
    }

    return {
        //导出属性名: 局部变量名字
        AbstractTree: AbstractTree,
        BroadLeafTree: BroadLeafTree,
        NeedleTree: NeedleTree
    }

}())

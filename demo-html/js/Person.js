/*
 * 模拟面向对象的标准JavaScript类
 *
 * 类属性
 * 类方法
 * 实例属性
 * 实例方法
 *
 */
//构造函数
/**
 *
 * @param {String} name
 * @param {Number} age
 * @param {String} sex
 * @constructor
 */
function Person(name, age, sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
}

//实例方法
/**
 * @param {String} content
 */
Person.prototype.speak = function (content) {
    console.log(this.name + "正在说:" + content);
}

Person.prototype.toString = function () {
    //返回属性组成的json串
    return '{name:' + this.name + ',age:' + this.age + ',sex:' + this.sex + '}';
}


//类字段       ~=java中静态属性  public static
Person.GOODMAN = new Person('good man', 100, 'male');
Person.MAN = new Person('man', 22, 'male');
Person.WOMAN = new Person('woman', 18, 'female');

//类方法       ~=java中的静态方法 public static
/**
 *
 * @param {String} personString person实例的json串
 */
Person.parse = function (personString) {
    //判断是否时json串
    if (!personString.match(Person._jsonReg)) {
        throw new TypeError('请传入一个json串');
    }
    var p = JSON.parse(personString);
    //让新创建的对象编程Person类实例
    p = new Person(p.name, p.age, p.sex);
    console.log(">>Json parse得到的person实例:");
    console.log(p);
    return p;
}

//类的私有字段(仅通过'_'标识以下, 并不是真的不可见)        private static
Person._jsonReg = /^\{("[^,]+":[^,]+,*)+\}$/;   //json字符串正则表达式
//Person._jsonRegKV = /"\w+":\w+/;   //匹配出一个个的 键:值
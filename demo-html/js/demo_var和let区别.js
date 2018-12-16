/*
* var定义的话, 最后结果都是10, 因为var定义的变量会出for范围, 保留在.
* let定义的话, 只在for里面生效, i每次会变成常量*/

function createFunctions() {
    let funcs = new Array();
    for(let i=0; i<10; i++){
        funcs[i]=function () {
            return i;
        }
    }
    return funcs;
}

var funcs = createFunctions();
for(let i=0; i<funcs.length; i++){
    let a = funcs[i];
    console.log(a());
}